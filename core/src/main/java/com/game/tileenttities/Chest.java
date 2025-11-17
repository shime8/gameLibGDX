package com.game.tileenttities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class Chest extends TileEntity {

    public Chest(){
        texture = new Texture("tiles/chest.png");
        name = "Chest";
    }
    public Chest(int x, int y) {
        this();
        set(x,y);
    }
    public Chest(Chest other){
        super(other);
    }
    public void update(float delta) {
    }
}
