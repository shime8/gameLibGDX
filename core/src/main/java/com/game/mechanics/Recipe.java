package com.game.mechanics;

import com.badlogic.gdx.utils.Array;
import com.game.items.Item;

public class Recipe {
    public Array<Item> itemsCIn;
    public Array<Item> itemsCOut;

    public Recipe(Array<Item> itemsCIn, Array<Item> itemsCOut){
        this.itemsCIn = itemsCIn;
        this.itemsCOut = itemsCOut;
    }
    public Recipe(Recipe recipe){
        this.itemsCIn = recipe.itemsCIn;
        this.itemsCOut = recipe.itemsCOut;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("itemsIn");
        for (Item item : itemsCIn){
            string.append(" N:").append(item.name);
            string.append(" A:").append(item.amount);
        }
        string.append(" itemsOut");
        for (Item item : itemsCOut){
            string.append(" N:").append(item.name);
            string.append(" A:").append(item.amount);
        }
        return string.toString();
    }
}
