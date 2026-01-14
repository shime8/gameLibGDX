package com.game.tileenttities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.game.UIs.TypeToString;
import com.game.items.Item;
import com.game.items.ItemEntity;

import static com.game.main.Main.itemEntityManager;
import static com.game.main.Main.tileEntityManager;

public class BuildPlace extends TileEntity{

    public TileEntity WhatToBuild;
    public Array<Item> Recipe;
    float accumulator;
    public BuildPlace(){
        super();
        sprite = new Sprite(new Texture("tiles/BuildPlace.png"));
        Recipe = new Array<>();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(0.02f);
        font.setUseIntegerPositions(false);
        accumulator = 0f;
    }
    public BuildPlace(int x, int y) {
        this();
        set(x,y);

    }
    public BuildPlace(BuildPlace other){
        super(other);
        this.WhatToBuild = other.WhatToBuild;
        this.Recipe = other.Recipe;
        this.font = other.font;
        accumulator = 0f;
    }
    public String getname(){
            return "BuildPlace";
    }
    public void setBuild(TileEntity t){
        WhatToBuild = t;
    }

    public void setRecipe(Array<Item> recipe) {
        Recipe = recipe;
    }

    @Override
    public TileEntity clone() {
        return new BuildPlace(this);
    }

    @Override
    public void update(float delta) {
        accumulator += delta;
        if(accumulator >= 1f){
            checkItems();
        }
    }

    public void checkItems(){
        boolean AllItemsDone = true;
        for (float x : new float[] { -1f, 0f, 1f }) {
            for (float y : new float[] { -1f, 0f, 1f }) {
                Array<ItemEntity> Items = itemEntityManager.getItemEntityList(this.x+x,this.y+y);
                if(Items!=null && !Items.isEmpty() && Recipe!=null && !Recipe.isEmpty()) {
                    for (ItemEntity ie : Items) {
                        for (Item ir : Recipe) {
                            if (ir.getname().equals(ie.item.getname()) && ir.amount > 0) {
                                itemEntityManager.removeItemEntity(ie);
                                ir.amount -= 1;
                            }
                            if (ir.amount > 0) {
                                AllItemsDone = false;
                            }
                        }
                    }
                }else if(Recipe!=null && !Recipe.isEmpty()){
                    for (Item ir : Recipe) {
                        if (ir.amount > 0) {
                            AllItemsDone = false;
                            break;
                        }
                    }
                }else{
                    AllItemsDone = false;
                }
            }
        }
        if(AllItemsDone){
            tileEntityManager.removeEntity(this);
            tileEntityManager.addEntity(new LightHouse(this.x,this.y));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        float verse = 0f;
        for(Item item : Recipe){
            if(item.amount != 0) {
                String text = "" + item.amount;
                batch.draw(item.sprite.getTexture(), x+0.5f,y+0.5f-verse, 0.5f, 0.5f);
                font.draw(batch, text, x, y+1-verse);
                verse += 0.5f;
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        if(WhatToBuild == null) {
            return new Rectangle(this.x-1, this.y-1, 3, 3);
        }else{
            return WhatToBuild.getBounds();
        }
    }

    @Override
    public Rectangle getHighlightBounds() {
        if(WhatToBuild == null) {
            return new Rectangle(this.x-1, this.y-1, 3, 3);
        }else{
            return WhatToBuild.getHighlightBounds();
        }
    }

    @Override
    public float getSpriteY() {
        return y+2;
    }

    public String getClassName(){
        return "BuildPlace";
    }
}
