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

        itemMap.put("gear", new Gear(0));
        itemMap.put("plate", new Plate(0));
        itemMap.put("rod", new Rod(0));
        itemMap.put("brick", new Brick(0));
        itemMap.put("sand", new Sand(0));
        itemMap.put("glass", new Glass(0));
        itemMap.put("casing", new Casing(0));
        itemMap.put("cement", new Cement(0));
        itemMap.put("circuitBoard", new CircuitBoard(0));
        itemMap.put("glassTile", new GlassTile(0));
        itemMap.put("motorPart", new MotorPart(0));
        itemMap.put("motor", new Motor(0));
        itemMap.put("reflector", new Reflector(0));
        itemMap.put("reinforcedWall", new ReinforcedWall(0));
        itemMap.put("roofTile", new RoofTile(0));
        itemMap.put("solarCell", new SolarCell(0));
        itemMap.put("brickTile", new BrickTile(0));
        itemMap.put("wire", new Wire(0));
        Item item = itemMap.get(string);

        if (item == null) {
            return new NullItem(0);
        }
        item.amount = amount;
        return item;
    }
}
