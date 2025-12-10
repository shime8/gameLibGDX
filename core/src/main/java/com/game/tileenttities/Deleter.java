package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.game.items.Item;

public class Deleter extends TileEntity implements HasInventory{

    public Deleter(){
        super();
        sprite = new Sprite(new Texture("tiles/deleter.png") );
        name = "Deleter";
    }
    public Deleter(int x, int y) {
        this();
        set(x,y);
        sprite.setSize(bounds.width, bounds.height);
        sprite.setOriginCenter();
        sprite.setPosition(x, y);

    }
    public Deleter(Deleter other){
        super(other);

    }
    @Override
    public Item getAnyItem() {
        return null;
    }

    @Override
    public boolean addItem(Item item) {
        return true;
    }

    @Override
    public TileEntity clone() {
        return new Deleter(this);
    }

    @Override
    public void update(float delta) {

    }
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
}
