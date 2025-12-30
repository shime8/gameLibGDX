package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class NullItem extends Item{

    public NullItem(){
        this.name = TypeToString.get(TypeToString.Dictionary.NullItem);
        this.sprite =  new Sprite(new Texture("items/nullitem.png"));
        this.amount = 0;
    }
    public NullItem(int amount){
        this();
        this.amount = amount;

    }

    public NullItem(Item item) {
        super(item);
    }
}
