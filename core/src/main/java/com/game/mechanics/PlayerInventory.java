package com.game.mechanics;

import com.badlogic.gdx.utils.Array;
import com.game.items.Item;

import java.util.Objects;

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

    public void setItem(int index, Item item) {
        items.set(index, item);
    }

    public void addItem(Item item) {
        boolean added = false;
        for (int i = 0; i < items.size; i++) {
            if(items.get(i) != null && Objects.equals(items.get(i).name, item.name)){
                Item temp = items.get(i);
                temp.amount ++;
                items.set(i, temp);
                added = true;
                break;
            }
        }
        if(!added) {
            for (int i = 0; i < items.size; i++) {
                if (items.get(i) == null) {
                    items.set(i, item);
                    break;
                }
            }
        }
    }

    public void removeItem(int index) {
        items.set(index, null);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
