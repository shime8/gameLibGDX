package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

import java.beans.Transient;

public class Brick extends Item {
    public Brick(){
        super();
        this.sprite =  new Sprite(new Texture("items/brick.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Brick);}
    public Brick(int amount){
        this();
        this.amount = amount;
    }

    public Brick(Item item) {
        super(item);
    }
    public Item clone(){return new Brick(this);};
    public String getClassName(){
        return "Brick";
    }
}
