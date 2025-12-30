package com.game.tileenttities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Comparator;

public class TileEntityManager {
    ObjectMap<GridPoint2, TileEntity> tileEntityMap;
    Array<ObjectMap.Entry<GridPoint2, TileEntity>> TemSorted;

    public TileEntityManager(ObjectMap<GridPoint2, TileEntity> tileEntities) {
        this.tileEntityMap = tileEntities;
    }

    public void addEntity(TileEntity entity) {
        GridPoint2 key = new GridPoint2(entity.x, entity.y);
        TileEntity temp = entity.clone();
        tileEntityMap.put(key, temp);
        temp.placeOtherTiles();
        sort();
    }

    public void removeEntity(TileEntity entity) {
        entity.removeOtherTiles();
        tileEntityMap.remove(new GridPoint2(entity.x, entity.y));
        sort();
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
//        for (TileEntity tileEntity : tileEntityMap.values()) {
//            tileEntity.render(batch);
//        }
        for (ObjectMap.Entry<GridPoint2, TileEntity> entry : TemSorted) {
//            GridPoint2 point = entry.key;
            TileEntity tile = entry.value;
            tile.render(batch);
        }
    }
    public void shapeRender(ShapeRenderer shapeR) {
        for (TileEntity tileEntity : tileEntityMap.values()) {
            tileEntity.shapeRender(shapeR);
        }
    }

    public void sort() {
        TemSorted = new Array<>();

        for (ObjectMap.Entry<GridPoint2, TileEntity> e : tileEntityMap.entries()) {
            ObjectMap.Entry<GridPoint2, TileEntity> copy =
                new ObjectMap.Entry<>();
            copy.key = e.key;
            copy.value = e.value/*.clone()*/;
            TemSorted.add(copy);
        }

        TemSorted.sort(
            Comparator
                .comparingInt((ObjectMap.Entry<GridPoint2, TileEntity> a) -> a.key.y)
                .thenComparingInt(a -> a.key.x)
        );
    }

}
