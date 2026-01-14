package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class Motor extends Item {
    public Motor(){
        super();
        this.sprite =  new Sprite(new Texture("items/motor.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Motor);}
    public Motor(int amount){
        this();
        this.amount = amount;
    }

    public Motor(Item item) {
        super(item);
    }
    public Item clone(){return new Motor(this);};
    public String getClassName(){
        return "Motor";
    }
}
