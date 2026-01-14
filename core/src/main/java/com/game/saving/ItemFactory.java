package com.game.saving;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.game.items.*;
import com.game.tileenttities.*;

import java.util.HashMap;
import java.util.Map;

public class ItemFactory {

    // Rebuild a single Item
    public static Item createItemFromJSON(JsonValue json) {
        if (json.isNull()) return null;

        String type = json.getString("type");
        int amount = json.getInt("amount");

        // If it contains a TileEntity (NewItem)
        if (json.has("tile") && !json.get("tile").isNull()) {
            TileEntity tile = TileEntityFactory.createTileEntityFromJSON(json.get("tile"));
            if(tile!=null){return new NewItem(amount, tile);}else{return null;}
        }

        // Otherwise, normal item
        System.out.println(type);
        return StringToItem(type, amount); // You must implement this
    }

    // Rebuild an array of Items
    public static Array<Item> createItemArrayFromJSON(JsonValue arrayJson) {
        Array<Item> items = new Array<>();
        for (JsonValue i : arrayJson) {
            items.add(createItemFromJSON(i));
        }

        return items;
    }

    public static Item StringToItem(String string, int amount){
        Map<String, Item> itemMap = new HashMap<>();

        itemMap.put("Gear", new Gear(0));
        itemMap.put("Plate", new Plate(0));
        itemMap.put("Rod", new Rod(0));
        itemMap.put("Brick", new Brick(0));
        itemMap.put("Sand", new Sand(0));
        itemMap.put("Glass", new Glass(0));
        itemMap.put("Casing", new Casing(0));
        itemMap.put("Cement", new Cement(0));
        itemMap.put("CircuitBoard", new CircuitBoard(0));
        itemMap.put("GlassTile", new GlassTile(0));
        itemMap.put("MotorPart", new MotorPart(0));
        itemMap.put("Motor", new Motor(0));
        itemMap.put("Reflector", new Reflector(0));
        itemMap.put("ReinforcedWall", new ReinforcedWall(0));
        itemMap.put("RoofTile", new RoofTile(0));
        itemMap.put("SolarCell", new SolarCell(0));
        itemMap.put("BrickTile", new BrickTile(0));
        itemMap.put("Wire", new Wire(0));
        Item item = itemMap.get(string);

        if (item == null) {
            return new NullItem(0);
        }
        item.amount = amount;
        return item;
    }
}
