package com.game.mechanics;

import com.badlogic.gdx.utils.Array;
import com.game.items.Item;

public class Recipe {
    public Array<Item> itemsCIn;
    public Array<Item> itemsCOut;
    public float time;

    public Recipe(Array<Item> itemsCIn, Array<Item> itemsCOut, float time){
        this.itemsCIn = itemsCIn;
        this.itemsCOut = itemsCOut;
        this.time = time;
    }
    public Recipe(Recipe recipe){
        this.itemsCIn = recipe.itemsCIn;
        this.itemsCOut = recipe.itemsCOut;
        this.time = recipe.time;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("itemsIn");
        for (Item item : itemsCIn){
            string.append(" N:").append(item.getname());
            string.append(" A:").append(item.amount);
        }
        string.append(" itemsOut");
        for (Item item : itemsCOut){
            string.append(" N:").append(item.getname());
            string.append(" A:").append(item.amount);
        }
        string.append("T:").append(time);
        return string.toString();
    }
}
