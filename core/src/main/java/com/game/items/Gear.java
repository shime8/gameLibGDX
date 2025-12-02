package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.tileenttities.TileEntity;

public class Gear extends Item {

    public Gear(int amount){
        this.name = "Gear";
        this.amount = amount;
        this.sprite =  new Sprite(new Texture("items/gear.png"));
    }

    public Gear(Item item) {
        super(item);
    }

}
