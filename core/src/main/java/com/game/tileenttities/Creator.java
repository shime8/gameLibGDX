package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.game.items.Gear;
import com.game.items.Item;

public class Creator extends TileEntity implements HasInventory{

    public Creator(){
        super();
        sprite = new Sprite(new Texture("tiles/InfiniMiner.png") );
        name = "Creator";
    }
    public Creator(int x, int y) {
        this();
        set(x,y);
        sprite.setSize(bounds.width, bounds.height);
        sprite.setOriginCenter();
        sprite.setPosition(x, y);

    }
    public Creator(Creator other){
        super(other);

    }
    @Override
    public Item getAnyItem() {
        return new Gear(1);
    }

    @Override
    public boolean addItem(Item item) {
        return false;
    }

    @Override
    public TileEntity clone() {
        return new Creator(this);
    }

    @Override
    public void update(float delta) {

    }
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
}
