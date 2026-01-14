package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class RoofTile extends Item {
    public RoofTile(){
        super();
        this.sprite =  new Sprite(new Texture("items/SolarRoof.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.RoofTile);}
    public RoofTile(int amount){
        this();
        this.amount = amount;
    }

    public RoofTile(Item item) {
        super(item);
    }
    public Item clone(){return new RoofTile(this);};
    public String getClassName(){
        return "RoofTile";
    }
}
