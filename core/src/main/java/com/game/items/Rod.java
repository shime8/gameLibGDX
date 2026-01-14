package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class Rod extends Item{
    public Rod(){
        super();
        this.sprite =  new Sprite(new Texture("items/MetalRod.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Rod);}
    public Rod(int amount){
        this();
        this.amount = amount;
    }

    public Rod(Item item) { super(item); }
    public Item clone(){return new Rod(this);};
    public String getClassName(){
        return "Rod";
    }
}
