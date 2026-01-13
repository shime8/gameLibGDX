package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class SolarCell extends Item {
    public SolarCell(){
        super();
        this.sprite =  new Sprite(new Texture("items/Solar_Panel.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.SolarCell);}
    public SolarCell(int amount){
        this();
        this.amount = amount;
    }

    public SolarCell(Item item) {
        super(item);
    }
    public Item clone(){return new SolarCell(this);};
}
