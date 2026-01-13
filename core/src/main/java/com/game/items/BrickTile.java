package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class BrickTile extends Item {
    public BrickTile(){
        super();
        this.sprite =  new Sprite(new Texture("items/BrickTile.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.BrickTile);}
    public BrickTile(int amount){
        this();
        this.amount = amount;
    }

    public BrickTile(Item item) {
        super(item);
    }
    public Item clone(){return new BrickTile(this);};
}
