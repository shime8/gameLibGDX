package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class Reflector extends Item {
    public Reflector(){
        super();
        this.sprite =  new Sprite(new Texture("items/Reflector.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Reflector);}
    public Reflector(int amount){
        this();
        this.amount = amount;
    }

    public Reflector(Item item) {
        super(item);
    }
    public Item clone(){return new Reflector(this);};
    public String getClassName(){
        return "Reflector";
    }
}
