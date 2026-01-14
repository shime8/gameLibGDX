package com.game.saving;

import com.badlogic.gdx.utils.JsonValue;
import com.game.items.Item;
import com.game.items.ItemEntity;

public class ItemEntityFactory {

    public static ItemEntity createItemEntityFromJSON(JsonValue json) {
        Item item = ItemFactory.createItemFromJSON(json.get("item"));
        if(item!=null){
            float x = json.getFloat("worldX");
            float y = json.getFloat("worldY");
            return new ItemEntity(item, x, y);
        }else{
            return null;
        }
    }
}
