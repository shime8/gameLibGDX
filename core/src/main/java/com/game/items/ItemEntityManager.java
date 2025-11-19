package com.game.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.tileenttities.TileEntity;

public class ItemEntityManager {
    private ObjectMap<GridPoint2, Array<ItemEntity>> itemEntityMap;

    public ItemEntityManager(ObjectMap<GridPoint2, Array<ItemEntity>> itemEntityMap) {
        this.itemEntityMap = itemEntityMap;
    }
    public void addItemEntity(ItemEntity itemEntity){
        GridPoint2 key = new GridPoint2((int)Math.floor(itemEntity.worldX), (int)Math.floor(itemEntity.worldY));
        Array<ItemEntity> list = itemEntityMap.get(key);
        if (list == null) {
            list = new Array<>();
            itemEntityMap.put(key, list);
        }
        list.add(itemEntity);
    }
    public void removeItemEntity(ItemEntity itemEntity){
        removeItemEntity(itemEntity, new GridPoint2((int)Math.floor(itemEntity.worldX), (int)Math.floor(itemEntity.worldY)));
    }
    public void removeItemEntity(ItemEntity itemEntity, GridPoint2 key){
        Array<ItemEntity> list = itemEntityMap.get(key);
        if(list != null){
            list.removeValue(itemEntity, true);
        }
    }
    public Array<ItemEntity> getItemEntityList(GridPoint2 gridPoint2){
        return itemEntityMap.get(gridPoint2);
    }
    public Array<ItemEntity> getItemEntityList(float x, float y){
        GridPoint2 key = new GridPoint2((int)Math.floor(x), (int)Math.floor(y));
        return itemEntityMap.get(key);
    }
    public void updateItemEntity(ItemEntity itemEntity, GridPoint2 OldKey){
        removeItemEntity(itemEntity, OldKey);
        addItemEntity(itemEntity);
    }

    public void render(SpriteBatch batch){
        for (Array<ItemEntity> itemEntityArray : itemEntityMap.values()) {
            for (ItemEntity itemEntity : itemEntityArray) {
                itemEntity.render(batch);
            }
        }
    }

}
