package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class NullItem extends Item{
    public NullItem(int amount){
        this.name = "NullItem";
        this.amount = amount;
        this.sprite =  new Sprite(new Texture("items/nullitem.png"));
    }

    public NullItem(Item item) {
        super(item);
    }
}
