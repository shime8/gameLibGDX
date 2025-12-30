package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Sand extends Item {
    public Sand(){
        this.name = "Sand";
        this.sprite =  new Sprite(new Texture("items/sand.png"));
        this.amount = 0;
    }
    public Sand(int amount){
        this();
        this.amount = amount;
    }

    public Sand(Item item) {
        super(item);
    }

}
