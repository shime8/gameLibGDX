package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.tileenttities.TileEntity;

public class Gear extends Item {
    public Gear(){
        this.name = "Gear";
        this.sprite =  new Sprite(new Texture("items/gear.png"));
        this.amount = 0;
    }
    public Gear(int amount){
        this();
        this.amount = amount;
    }

    public Gear(Item item) {
        super(item);
    }

}
