package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.tileenttities.TileEntity;

public class Item {
    public String name;
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
            this.name = Tile.name;
    }
    public String getname(){
        if(this.Tile != null){
            return this.Tile.getname();
        }else{
            return "NameNotSet";
        }
    }
    public Item(Item item){
        this();
        this.amount = item.amount;
        this.Tile = item.Tile;
        if(item.Tile == null){
            this.sprite = item.sprite;
            this.name = item.name;
        }else {
            this.sprite = item.Tile.sprite;
            this.name = item.Tile.name;
        }

    }


}
