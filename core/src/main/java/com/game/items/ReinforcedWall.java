package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class ReinforcedWall extends Item {
    public ReinforcedWall(){
        super();
        this.sprite =  new Sprite(new Texture("items/Wall.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.ReinforcedWall);}
    public ReinforcedWall(int amount){
        this();
        this.amount = amount;
    }

    public ReinforcedWall(Item item) {
        super(item);
    }
    public Item clone(){return new ReinforcedWall(this);};
    public String getClassName(){
        return "ReinforcedWall";
    }
}
