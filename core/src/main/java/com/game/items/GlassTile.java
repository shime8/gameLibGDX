package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class GlassTile extends Item {
    public GlassTile(){
        super();
        this.sprite =  new Sprite(new Texture("items/GlassTile.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.GlassTile);}
    public GlassTile(int amount){
        this();
        this.amount = amount;
    }

    public GlassTile(Item item) {
        super(item);
    }
    public Item clone(){return new GlassTile(this);};
}
