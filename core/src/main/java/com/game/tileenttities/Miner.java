package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.UIs.TypeToString;
import com.game.items.Item;
import com.game.items.Sand;

import static com.game.main.Main.tileEntityManager;

public class Miner extends TileEntity implements HasInventory{

    public TileEntity minee;
    Item item;
    float accumulator;
    public Miner(){
        super();
        sprite = new Sprite(new Texture("tiles/Miner.png") );
        name = TypeToString.get(TypeToString.Dictionary.Miner);
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
    }

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
            Item item = new Item(this.item);
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
        if(item != null && item.amount<5){
            item.amount += 1;
        }else{
            if(minee != null) {
                item = new Item(1, minee.clone());
            }else{
                item = new Sand(1);
            }
        }
    }
}
