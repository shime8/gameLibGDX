package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Glass extends Item {
    public Glass(){
        this.name = "Glass";
        this.sprite =  new Sprite(new Texture("items/glass.png"));
        this.amount = 0;
    }
    public Glass(int amount){
        this();
        this.amount = amount;
    }

    public Glass(Item item) {
        super(item);
    }

}
