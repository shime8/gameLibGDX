package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Chest extends TileEntity {

    public Chest(){
        super();
        sprite = new Sprite(new Texture("tiles/chest.png") );
        name = "Chest";
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
    }
    @Override
    public TileEntity clone() {
        return new Chest(this);
    }
    public void update(float delta) {
    }
}
