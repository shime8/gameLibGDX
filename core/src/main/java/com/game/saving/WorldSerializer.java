package com.game.saving;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.items.Item;
import com.game.items.ItemEntity;
import com.game.items.ItemEntityManager;
import com.game.items.NewItem;
import com.game.mechanics.MouseSlot;
import com.game.mechanics.PlayerInventory;
import com.game.tileenttities.Assembler;
import com.game.tileenttities.AssemblerPointer;
import com.game.tileenttities.TileEntity;
import com.game.tileenttities.TileEntityManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static com.game.main.Main.itemEntityManager;
import static com.game.main.Main.tileEntityManager;

public class WorldSerializer {

    // ---------------- Save ----------------
    public static String serializeWorld(
        ObjectMap<GridPoint2, TileEntity> tileMap,
        ObjectMap<GridPoint2, Array<ItemEntity>> itemMap,
        PlayerInventory playerInventory,
        MouseSlot mouseSlot) {

        StringBuilder sb = new StringBuilder();
        sb.append("{");

        sb.append("\"tileEntities\":").append(serializeTileMap(tileMap)).append(",");
        sb.append("\"itemEntities\":").append(serializeItemMap(itemMap)).append(",");
        sb.append("\"playerInventory\":").append(playerInventory.toJSON()).append(",");
        sb.append("\"mouseSlot\":").append(mouseSlot.toJSON());

        sb.append("}");
        return sb.toString();
    }

    private static String serializeTileMap(ObjectMap<GridPoint2, TileEntity> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int count = 0;
        for (ObjectMap.Entry<GridPoint2, TileEntity> entry : map.entries()) {
            GridPoint2 pos = entry.key;
            TileEntity entity = entry.value;

            sb.append("{");
            sb.append("\"pos\": {\"x\":").append(pos.x).append(",\"y\":").append(pos.y).append("},");
            sb.append("\"entity\":").append(entity.toJSON());
            sb.append("}");

            if (count < map.size - 1) sb.append(",");
            count++;
        }
        sb.append("]");
        return sb.toString();
    }

    private static String serializeItemMap(ObjectMap<GridPoint2, Array<ItemEntity>> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int count = 0;
        for (ObjectMap.Entry<GridPoint2, Array<ItemEntity>> entry : map.entries()) {
            GridPoint2 pos = entry.key;
            Array<ItemEntity> items = entry.value;

            sb.append("{");
            sb.append("\"pos\": {\"x\":").append(pos.x).append(",\"y\":").append(pos.y).append("},");
            sb.append("\"items\": [");

            for (int j = 0; j < items.size; j++) {
                sb.append(items.get(j).toJSON());
                if (j < items.size - 1) sb.append(",");
            }

            sb.append("]}");

            if (count < map.size - 1) sb.append(",");
            count++;
        }
        sb.append("]");
        return sb.toString();
    }

    public static void saveWorld(
        ObjectMap<GridPoint2, TileEntity> tileMap,
        ObjectMap<GridPoint2, Array<ItemEntity>> itemMap,
        PlayerInventory playerInventory,
        MouseSlot mouseSlot,
        String filePath) {

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(serializeWorld(tileMap, itemMap, playerInventory, mouseSlot));
            System.out.println("World saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------- Load ----------------
    public static void loadWorld(
        String filePath,
        TileEntityManager tileEntityManager,
        ItemEntityManager itemEntityManager,
        PlayerInventory playerInventory,
        MouseSlot mouseSlot) {

        try {
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(new FileReader(filePath));

            // --- TileEntities ---
            tileEntityManager = new TileEntityManager(new ObjectMap<>());
            for (JsonValue tileJson : root.get("tileEntities")) {
                //JsonValue pos = tileJson.get("pos");
                //int x = pos.getInt("x");
                //int y = pos.getInt("y");
                TileEntity entity = TileEntityFactory.createTileEntityFromJSON(tileJson.get("entity"));
                if(entity!=null) {
                    tileEntityManager.addEntity(entity);
                }
            }

            // --- ItemEntities ---
            itemEntityManager = new ItemEntityManager(new ObjectMap<>());
            for (JsonValue itemJson : root.get("itemEntities")) {
                //JsonValue pos = itemJson.get("pos");
                //int x = pos.getInt("x");
                //int y = pos.getInt("y");

                Array<ItemEntity> items = new Array<>();
                for (JsonValue i : itemJson.get("items")) {
                    if(ItemEntityFactory.createItemEntityFromJSON(i)!=null)itemEntityManager.addItemEntity(ItemEntityFactory.createItemEntityFromJSON(i));
                }


            }

            // --- PlayerInventory ---
            PlayerInventory loadedInventory = PlayerInventoryFactory.createFromJSON(root.get("playerInventory"));
            playerInventory.items.clear();
            playerInventory.items.addAll(loadedInventory.items);

            // --- MouseSlot ---
            MouseSlot loadedMouse = MouseSlotFactory.createFromJSON(root.get("mouseSlot"));
            mouseSlot.item = loadedMouse.item;

            System.out.println("World loaded from " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
