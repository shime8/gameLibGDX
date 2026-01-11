package com.game.tileenttities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.items.ItemEntity;

import java.util.Comparator;

import static com.game.main.Main.itemEntityManager;
import static com.game.main.Main.worldManager;

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
        boolean PlayerRendered = false;
        for (ObjectMap.Entry<GridPoint2, TileEntity> entry : TemSorted) {
//            GridPoint2 point = entry.key;
            TileEntity tile = entry.value;
            if(!PlayerRendered && tile.getSpriteY()+0.6f<worldManager.player.y){
                renderItemsOnBelts(batch,true);
                worldManager.player.draw(batch);
                PlayerRendered = true;
                tile.render(batch);

            }else{
                tile.render(batch);
            }


        }
        if(!PlayerRendered){
            renderItemsOnBelts(batch,true);
            worldManager.player.draw(batch);
        }
    }
    public void renderItemsOnBelts(SpriteBatch batch, boolean UpDirection){
        for (TileEntity tileEntity : tileEntityMap.values()) {
            if(tileEntity instanceof Belt b && itemEntityManager.getItemEntityList(b.x, b.y)!=null ){
                if (UpDirection ? b.y >= worldManager.player.y-1 : b.y < worldManager.player.y+1) {
                    for (ItemEntity ie : itemEntityManager.getItemEntityList(b.x, b.y)) {
                        if(UpDirection || ie.worldY <= worldManager.player.y-0.5f)ie.render(batch);
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
