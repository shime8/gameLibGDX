package com.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.tileenttities.TileEntity;

public class Item {
    public String name;
    public int amount;
    public Sprite sprite;
    public TileEntity Tile;

    public Item(int amount, TileEntity Tile){
            this.amount = amount;
            this.Tile = Tile;
            this.sprite = Tile.sprite;
            this.name = Tile.name;
    }
    public Item(Item item){
        this.name = item.name;
        this.amount = item.amount;
        this.Tile = item.Tile;
        this.sprite = item.Tile.sprite;
        this.name = item.Tile.name;
    }

}
