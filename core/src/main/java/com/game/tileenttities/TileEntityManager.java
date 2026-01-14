package com.game.tileenttities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.items.ItemEntity;

import java.util.Comparator;

import static com.game.main.Main.*;

public class TileEntityManager {
    public ObjectMap<GridPoint2, TileEntity> tileEntityMap;
    Array<ObjectMap.Entry<GridPoint2, TileEntity>> TemSorted;
    public TileEntityManager(ObjectMap<GridPoint2, TileEntity> tileEntities) {
        this.tileEntityMap = tileEntities;
    }

    public void addEntity(TileEntity entity) {
        GridPoint2 key = new GridPoint2(entity.x, entity.y);
        TileEntity temp = entity.clone();
        tileEntityMap.put(key, temp);
        System.out.println(key);
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
            if(entity!=null)entity.update(delta);
        }

    }

    public void render(SpriteBatch batch) {
//        for (TileEntity tileEntity : tileEntityMap.values()) {
//            tileEntity.render(batch);
//        }
        boolean PlayerRendered = false;
        if(TemSorted!=null){
            for (ObjectMap.Entry<GridPoint2, TileEntity> entry : TemSorted) {
//            GridPoint2 point = entry.key;
                TileEntity tile = entry.value;
                if (!PlayerRendered && tile.getSpriteY() + 0.6f < worldManager.player.y) {
                    worldManager.player.draw(batch);
                    PlayerRendered = true;
                }
                tile.render(batch);
                renderItemsOnBelts(batch, tile);
            }
        }
        if(!PlayerRendered){worldManager.player.draw(batch);}
    }
    int[][] directionsForBelts = {
        {-1, 0},
        { 1, 0},
        { 0, 1},
        { 0,-1}
    };
    public void renderItemsOnBelts(SpriteBatch batch, TileEntity tile){
        if(tile instanceof Belt b && itemEntityManager.getItemEntityList(b.x, b.y)!=null){
            for (ItemEntity ie : itemEntityManager.getItemEntityList(b.x, b.y)) {
                ie.render(batch);
            }
            for (int[] d : directionsForBelts) {
                int x = d[0];
                int y = d[1];
                if(tileEntityManager.getEntityAt(b.x+x,b.y+y) instanceof Belt bb && itemEntityManager.getItemEntityList(bb.x, bb.y)!=null){
                    for (ItemEntity ie : itemEntityManager.getItemEntityList(bb.x, bb.y)) {
                        ie.render(batch);
                    }
                }
            }
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
                .comparingDouble((ObjectMap.Entry<GridPoint2, TileEntity> a) -> -a.value.getSpriteY())
                .thenComparingInt(a -> a.key.x)
        );
    }

    public boolean isEmpty(){
        return (tileEntityMap.isEmpty());
    }

}
