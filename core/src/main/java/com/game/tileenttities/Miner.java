package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.game.UIs.TypeToString;
import com.game.items.Gear;
import com.game.items.Item;
import com.game.items.NewItem;
import com.game.items.Sand;

import static com.game.main.Main.tileEntityManager;

public class Miner extends TileEntity implements HasInventory{

    public TileEntity minee;
    Item item;
    float accumulator;
    float AnimAccumulator;
    public Miner(){
        super();
        sprite = new Sprite(new Texture("tiles/Miner.png") );
        accumulator = 0f;
        //speed = 1f;
    }
    public Miner(int x, int y){
        this();
        set(x,y);
        minee = tileEntityManager.getEntityAt(x,y);

    }

    @Override
    public void set(int x, int y) {
        super.set(x, y);
        minee = tileEntityManager.getEntityAt(x,y);
    }

    public Miner(Miner other){
        super(other);
        this.minee = other.minee;
        this.accumulator = 0f;
        this.AnimAccumulator = 0f;
    }
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Miner);}

    @Override
    public TileEntity clone() {
        return new Miner(this);
    }

    @Override
    public void update(float delta) {
        //if(minee != null){
        accumulator += delta;
        if(accumulator > 1f){
            makeItem();
            accumulator = 0f;
        }
        //}
        AnimAccumulator += delta;
        while(AnimAccumulator > 0.628f){
            AnimAccumulator -= 0.628f;
        }
        if(item==null || item.amount<item.StackSize/10f){
            sprite.setScale(1,1+0.05f*(float)(Math.sin(AnimAccumulator*60f)));
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        if(minee!=null){
            minee.render(batch);
        }
        super.render(batch);
    }

    @Override
    public Item getAnyItem() {
        if(item!=null && item.amount>0){
            Item item = this.item.clone();
            this.item.amount--;
            item.amount = 1;
            return item;
        }
        return null;
    }

    @Override
    public boolean addItem(Item item) {
        return false;
    }
    public void makeItem(){
        if(item != null && item.amount<item.StackSize/10f){
            item.amount += 1;
        }
        if(item == null){
            if(minee != null) {
                item = new NewItem(1, minee.clone());
            }else{
                item = new Sand(1);
            }
        }
    }

    public float getSpriteY() {return y+0.9f;}
    @Override
    public Array<Item> ItemsOnBreak() {
        Array<Item> items = new Array<>();
        items.add(item);
        return items;
    }
}
