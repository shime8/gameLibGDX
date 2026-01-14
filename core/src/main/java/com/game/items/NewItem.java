package com.game.items;

import com.game.tileenttities.TileEntity;

public class NewItem extends Item{
    public NewItem(int amount, TileEntity Tile){
        super();
        this.amount = amount;
        this.Tile = Tile;
        this.sprite = Tile.sprite;
        this.getname();
    }
    public NewItem(Item item){
        super();
        this.amount = item.amount;
        this.Tile = item.Tile;
        if(item.Tile == null){
            this.sprite = item.sprite;
        }else {
            this.sprite = item.Tile.sprite;
        }
        this.getname();

    }

    public Item clone(){return new NewItem(this);};
    public String getname() {
        if(this.Tile != null){
            return this.Tile.getname();
        }else{
            throw new RuntimeException("bruh");
            //System.out.println("SumtingWentWong Item");
            //return "NameNotSet";
        }
    }
    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        // Save type and amount (from Item)
        sb.append("\"type\":\"").append(getname()).append("\",");
        sb.append("\"amount\":").append(this.amount);

        // Save TileEntity if it exists
        if (this.Tile != null) {
            sb.append(",\"tile\":").append(Tile.toJSON());
        }

        sb.append("}");
        return sb.toString();
    }

}
