package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class Glass extends Item {
    public Glass(){
        super();
        this.sprite =  new Sprite(new Texture("items/glass.png"));
        this.amount = 0;
    }

    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Glass);}

    public Glass(int amount){
        this();
        this.amount = amount;
    }

    public Glass(Item item) {
        super(item);
    }
    public Item clone(){return new Glass(this);};

}
