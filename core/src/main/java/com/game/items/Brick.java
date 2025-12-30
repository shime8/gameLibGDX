package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class Brick extends Item {
    public Brick(){
        this.name = TypeToString.get(TypeToString.Dictionary.Brick);
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
