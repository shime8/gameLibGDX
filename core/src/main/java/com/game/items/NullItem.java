package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class NullItem extends Item{

    public NullItem(){
        super();
        this.sprite =  new Sprite(new Texture("items/nullitem.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.NullItem);}
    public NullItem(int amount){
        this();
        this.amount = amount;

    }

    public NullItem(Item item) {
        super(item);
    }
    public Item clone(){return new NullItem(this);};
}
