package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class Wire extends Item {
    public Wire(){
        super();
        this.sprite =  new Sprite(new Texture("items/Wire.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Wire);}
    public Wire(int amount){
        this();
        this.amount = amount;
    }

    public Wire(Item item) {
        super(item);
    }
    public Item clone(){return new Wire(this);};
    public String getClassName(){
        return "Wire";
    }
}
