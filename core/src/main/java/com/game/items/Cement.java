package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class Cement extends Item {
    public Cement(){
        super();
        this.sprite =  new Sprite(new Texture("items/cement.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Cement);}
    public Cement(int amount){
        this();
        this.amount = amount;
    }

    public Cement(Item item) {
        super(item);
    }
    public Item clone(){return new Cement(this);};
}
