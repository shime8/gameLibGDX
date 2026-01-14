package com.game.saving;

import com.badlogic.gdx.utils.JsonValue;
import com.game.items.Item;
import com.badlogic.gdx.utils.Array;
import com.game.mechanics.PlayerInventory;

public class PlayerInventoryFactory {

    public static PlayerInventory createFromJSON(JsonValue json) {
        Array<Item> items = ItemFactory.createItemArrayFromJSON(json.get("items"));
        // You might need width/height; assuming items.size = width*height
        int size = items.size;
        PlayerInventory inv = new PlayerInventory(5,5); // Or your actual width/height
        inv.items.clear();
        inv.items.addAll(items);
        return inv;
    }
}
