package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Brick extends Item {
    public Brick(){
        this.name = "Brick";
        this.sprite =  new Sprite(new Texture("items/brick.png"));
        this.amount = 0;
    }
    public Brick(int amount){
        this();
        this.amount = amount;
    }

    public Brick(Item item) {
        super(item);
    }

}
