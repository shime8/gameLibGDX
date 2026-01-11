package com.game.mechanics;

import com.badlogic.gdx.utils.Array;
import com.game.items.Item;
import com.game.items.ItemEntity;
import com.game.items.ItemEntityManager;

import java.util.Objects;

import static com.game.main.Main.itemEntityManager;
import static com.game.main.Main.worldManager;

public class PlayerInventory {
    public final int width, height;
    public final Array<Item> items;

    public PlayerInventory(int width, int height) {
        this.width = width;
        this.height = height;
        this.items = new Array<>(width * height);
        for (int i = 0; i < width * height; i++) items.add(null);
    }

    public int getSize() {
        return width * height;
    }

    public Item getItem(int index) {
        return items.get(index);
    }
    public Item getItem(Item item){
        for (int i = 0; i < items.size; i++) {
            if(items.get(i) != null && Objects.equals(items.get(i).getname(), item.getname())){
                return items.get(i);
            }
        }
        return null;
    }

    public void setItem(int index, Item item) {
        items.set(index, item);
    }

    public void addItem(Item item) {
        boolean added = false;
        for (int i = 0; i < items.size; i++) {
            if(items.get(i) != null && Objects.equals(items.get(i).getname(), item.getname())){
                Item temp = items.get(i);
                temp.amount += item.amount;
                items.set(i, temp);
                if(temp.amount<=temp.StackSize) {
                    items.set(i, temp);
                    added = true;
                    break;
                }else{
                    item.amount = temp.amount - temp.StackSize;
                    temp.amount = temp.StackSize;
                }
            }
        }
        if(!added) {
            for (int i = 0; i < items.size; i++) {
                if (items.get(i) == null) {
                    items.set(i, item);
                    added = true;
                    break;
                }
            }
        }
        if(!added){
            int temp = item.amount;
            item.amount = 1;
            for (int i = 1; i <= temp; i++) {

                int[] xy = indexToXYForDropping(i);
                int x = xy[0];
                int y = xy[1];

                ItemEntity ie = new ItemEntity(
                    item,
                    worldManager.player.x + (x*0.1f),
                    worldManager.player.y - (y*0.1f)
                );
                itemEntityManager.addItemEntity(ie);
            }
        }
    }

    public void removeItem(int index) {
        items.set(index, null);
    }
    public void removeItem(Item item){
        for (int i = 0; i < items.size; i++) {
            if(items.get(i) != null && Objects.equals(items.get(i).getname(), item.getname())){
                items.set(i, null);
            }
        }

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    static int[] indexToXYForDropping(int i) {
        // find diagonal d
        int d = (int)Math.floor((Math.sqrt(8 * i - 7) - 1) / 2);

        // number of elements before this diagonal
        int prev = d * (d + 1) / 2;

        int k = i - prev - 1;

        int x, y;
        if (d % 2 == 0) {
            x = d - k;
            y = k;
        } else {
            x = k;
            y = d - k;
        }

        return new int[] { x, y };
    }
}
