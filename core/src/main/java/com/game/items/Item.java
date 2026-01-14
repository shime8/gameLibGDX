package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.tileenttities.TileEntity;

public abstract class Item {
    public int amount;
    public Sprite sprite;
    public TileEntity Tile;
    public int StackSize;
    public Item(){
        StackSize = 50;
    }
    public Item(int amount, TileEntity Tile){
        this();
            this.amount = amount;
            this.Tile = Tile;
            this.sprite = Tile.sprite;

    }
    public abstract String getname();
    public abstract Item clone();
    public Item(Item item){
        this();
        this.amount = item.amount;
        this.Tile = item.Tile;
        if(item.Tile == null){
            this.sprite = item.sprite;
        }else {
            this.sprite = item.Tile.sprite;
        }

    }
    public Sprite getSprite(){
        if(Tile!=null){
            return Tile.sprite;
        }else{
            return sprite;
        }
    }



}
