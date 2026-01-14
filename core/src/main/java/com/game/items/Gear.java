package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;
import com.game.tileenttities.TileEntity;

public class Gear extends Item {
    public Gear(){
        super();
        this.sprite =  new Sprite(new Texture("items/gear.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Gear);}
    public Gear(int amount){
        this();
        this.amount = amount;
    }

    public Gear(Item item) {
        super(item);
    }
    public Item clone(){return new Gear(this);};
    public String getClassName(){
        return "Gear";
    }
}
