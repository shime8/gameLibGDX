package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.game.tileenttities.TileEntity;

public class Item {
    public String name;
    public int amount;
    public Texture texture;
    public TileEntity Tile;

    public Item(int amount, TileEntity Tile){
            this.amount = amount;
            this.Tile = Tile;
            this.texture = Tile.texture;
            this.name = Tile.name;
    }
    public Item(Item item){
        this.name = item.name;
        this.amount = item.amount;
        this.Tile = item.Tile;
        this.texture = item.Tile.texture;
        this.name = item.Tile.name;
    }

}
