package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class Plate extends Item{
    public Plate(){
        super();
        this.name = TypeToString.get(TypeToString.Dictionary.Plate);
        this.sprite =  new Sprite(new Texture("items/MetalPlate.png"));
        this.amount = 0;
    }
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Plate);}
    public Plate(int amount){
        this();
        this.amount = amount;
    }

    public Plate(Item item) {
        super(item);
    }

}
