package com.game.saving;

import com.badlogic.gdx.utils.JsonValue;
import com.game.items.Item;
import com.game.mechanics.MouseSlot;

public class MouseSlotFactory {

    public static MouseSlot createFromJSON(JsonValue json) {
        Item item = ItemFactory.createItemFromJSON(json.get("item"));
        MouseSlot ms = new MouseSlot();
        ms.item = item;
        return ms;
    }
}

