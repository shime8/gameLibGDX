package com.game.tileenttities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.ObjectMap;

public class TileEntityManager {
    private ObjectMap<GridPoint2, TileEntity> tileEntities;

    public TileEntityManager(ObjectMap<GridPoint2, TileEntity> tileEntities) {
        this.tileEntities = tileEntities;
    }

    public void addEntity(TileEntity entity) {
        GridPoint2 key = new GridPoint2(entity.x, entity.y);
        TileEntity temp = new TileEntity(entity) {
            @Override
            public void update(float delta) {
            }

            @Override
            public void render(SpriteBatch batch) {
                batch.draw(texture, x, y, bounds.width, bounds.height);
            }
        };
        tileEntities.put(key, temp);
    }

    public void removeEntity(TileEntity entity) {
        tileEntities.remove(new GridPoint2(entity.x, entity.y));
    }

    public TileEntity getEntityAt(int tileX, int tileY) {
        return tileEntities.get(new GridPoint2(tileX, tileY));
    }

    public void update(float delta) {
        for (TileEntity entity : tileEntities.values()) {
            entity.update(delta);
        }

    }

    public void render(SpriteBatch batch) {
        for (TileEntity entity : tileEntities.values()) {
            entity.render(batch);
        }
    }
}
