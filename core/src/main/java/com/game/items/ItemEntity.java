package com.game.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ItemEntity {
    public Item item;
    public float worldX;
    public float worldY;
    Rectangle bounds;
    public ItemEntity(Item item, float worldX, float worldY){
        this.item = item;
        this.worldX = worldX;
        this.worldY = worldY;
        float size = 0.5f;
        bounds = new Rectangle(worldX-size/2,worldY-size/2,size,size);
    }
    public void render(SpriteBatch batch){
        if(item!=null) {
            batch.disableBlending();
            batch.draw(item.texture,bounds.x,bounds.y,bounds.width,bounds.height);
            batch.enableBlending();
        }
    }
}
