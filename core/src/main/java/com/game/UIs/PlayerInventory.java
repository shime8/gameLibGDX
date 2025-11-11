package com.game.UIs;

import com.badlogic.gdx.utils.Array;

public class PlayerInventory {
    private final int width, height;
    private final Array<String> items; // SUBJECT TO CHANGE

    public PlayerInventory(int width, int height) {
        this.width = width;
        this.height = height;
        this.items = new Array<>(width * height);
        for (int i = 0; i < width * height; i++) items.add(null);
    }

    public int getSize() {
        return width * height;
    }

    public String getItem(int index) {
        return items.get(index);
    }

    public void setItem(int index, String item) {
        items.set(index, item);
    }

    public void addItem(String item) {
        for (int i = 0; i < items.size; i++) {
            if (items.get(i) == null) {
                items.set(i, item);
                break;
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
