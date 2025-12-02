package com.game.tileenttities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.game.items.Item;

import java.util.Objects;

public class Chest extends TileEntity implements HasInventory{
    public int width, height;
    public Array<Item> items;
//    public BitmapFont font;
    public Chest(){
        super();
        sprite = new Sprite(new Texture("tiles/chest.png") );
        name = "Chest";
        this.width = 2;
        this.height = 2;
        this.items = new Array<>(width * height);
        for (int i = 0; i < width * height; i++) items.add(null);
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(0.1f);
    }
    public Chest(int x, int y) {
        this();
        set(x,y);
        sprite.setSize(bounds.width, bounds.height);
        sprite.setOriginCenter();
        sprite.setPosition(x, y);

    }
    public Chest(Chest other){
        super(other);
        this.width = other.width;
        this.height = other.height;
        this.items = other.items;
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(0.1f);
    }
    @Override
    public TileEntity clone() {
        return new Chest(this);
    }
    public void update(float delta) {
    }

    @Override
    public Item getAnyItem() {
        for (int i = 0; i < items.size; i++) {
            if(items.get(i) != null){
                Item item = new Item(items.get(i));
                items.get(i).amount--;
                if(items.get(i).amount == 0){items.set(i,null);}
                item.amount = 1;
                return item;
            }
        }
        return null;
    }

    @Override
    public boolean addItem(Item item) {
        boolean added = false;
        for (int i = 0; i < items.size; i++) {
            if(items.get(i) != null && Objects.equals(items.get(i).name, item.name)){
                Item temp = items.get(i);
                temp.amount += item.amount;
                items.set(i, temp);
                added = true;
                return true;
            }
        }
        if(!added) {
            for (int i = 0; i < items.size; i++) {
                if (items.get(i) == null) {
                    items.set(i, item);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
//        for(Item i : items){if(i != null){font.draw(batch,String.valueOf(i.amount), x, y);}}
    }
}
