package com.game.tileenttities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.ObjectMap;

public class TileEntityManager {
    private ObjectMap<GridPoint2, TileEntity> tileEntityMap;

    public TileEntityManager(ObjectMap<GridPoint2, TileEntity> tileEntities) {
        this.tileEntityMap = tileEntities;
    }

    public void addEntity(TileEntity entity) {
        GridPoint2 key = new GridPoint2(entity.x, entity.y);
        TileEntity temp = entity.clone();
        tileEntityMap.put(key, temp);
    }

    public void removeEntity(TileEntity entity) {
        tileEntityMap.remove(new GridPoint2(entity.x, entity.y));
    }

    public TileEntity getEntityAt(int tileX, int tileY) {
        return tileEntityMap.get(new GridPoint2(tileX, tileY));
    }

    public void update(float delta) {
        for (TileEntity entity : tileEntityMap.values()) {
            entity.update(delta);
        }

    }

    public void render(SpriteBatch batch) {
        for (TileEntity tileEntity : tileEntityMap.values()) {
            tileEntity.render(batch);
        }
    }
}
