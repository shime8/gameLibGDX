package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class Sand extends Item {
    public Sand(){
        super();
        this.name = TypeToString.get(TypeToString.Dictionary.Sand);
        this.sprite =  new Sprite(new Texture("items/sand.png"));
        this.amount = 0;
    }
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Sand);}
    public Sand(int amount){
        this();
        this.amount = amount;
    }

    public Sand(Item item) {
        super(item);
    }

}
