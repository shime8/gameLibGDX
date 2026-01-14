package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class Casing extends Item {
    public Casing(){
        super();
        this.sprite =  new Sprite(new Texture("items/Casing.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Casing);}
    public Casing(int amount){
        this();
        this.amount = amount;
    }

    public Casing(Item item) {
        super(item);
    }
    public Item clone(){return new Casing(this);};
    public String getClassName(){
        return "Casing";
    }
}
