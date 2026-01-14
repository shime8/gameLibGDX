package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class MotorPart extends Item {
    public MotorPart(){
        super();
        this.sprite =  new Sprite(new Texture("items/motor_part.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.MotorPart);}
    public MotorPart(int amount){
        this();
        this.amount = amount;
    }

    public MotorPart(Item item) {
        super(item);
    }
    public Item clone(){return new MotorPart(this);};
    public String getClassName(){
        return "MotorPart";
    }
}
