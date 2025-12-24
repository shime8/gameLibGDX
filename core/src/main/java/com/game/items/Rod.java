package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Rod extends Item{
    public Rod(){
        this.name = "Rod";
        this.sprite =  new Sprite(new Texture("items/MetalRod.png"));
        this.amount = 0;
    }
    public Rod(int amount){
        this();
        this.amount = amount;
    }

    public Rod(Item item) { super(item); }
}
