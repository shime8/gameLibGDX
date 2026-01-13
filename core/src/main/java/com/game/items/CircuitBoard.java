package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class CircuitBoard extends Item {
    public CircuitBoard(){
        super();
        this.sprite =  new Sprite(new Texture("items/chip.png"));
        this.amount = 0;
    }
    @Override
    public String getname(){return TypeToString.get(TypeToString.Dictionary.CircuitBoard);}
    public CircuitBoard(int amount){
        this();
        this.amount = amount;
    }

    public CircuitBoard(Item item) {
        super(item);
    }
    public Item clone(){return new CircuitBoard(this);};
}
