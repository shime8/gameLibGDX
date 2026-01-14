package com.game.saving;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.game.items.Item;
import com.game.mechanics.Recipe;

public class RecipeFactory {

    public static Recipe createRecipeFromJSON(JsonValue json) {
        Array<Item> itemsIn = ItemFactory.createItemArrayFromJSON(json.get("itemsCIn"));

        Array<Item> itemsOut = ItemFactory.createItemArrayFromJSON(json.get("itemsCOut"));

        float time = json.getFloat("time");
        System.out.println(new Recipe(itemsIn, itemsOut, time));
        if(itemsOut.isEmpty() && itemsIn.isEmpty() && time!=0){
            for(Item item : itemsIn){
                if(item==null){return null;}
            }
            for(Item item : itemsOut){
                if(item==null){return null;}
            }
            return new Recipe(itemsIn, itemsOut, time);
        }else{
            return null;
        }
    }
}
