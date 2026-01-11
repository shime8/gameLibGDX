package com.game.tileenttities;

import com.badlogic.gdx.utils.Array;
import com.game.items.Item;

public interface HasInventory {
    public Item getAnyItem();
//    public Item getThisItem(Item item);
    public boolean addItem(Item item);
    public Array<Item> ItemsOnBreak();

}
