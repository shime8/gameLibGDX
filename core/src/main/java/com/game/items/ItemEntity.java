package com.game.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ItemEntity {
    public Item item;
    public float worldX;
    public float worldY;
    Rectangle bounds;
    float size;
//    public BitmapFont font;
    public ItemEntity(Item item, float worldX, float worldY){
        this.item = item;
        this.item.sprite = new Sprite(item.sprite);
        this.worldX = worldX;
        this.worldY = worldY;
        size = 0.5f;
        update();
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(0.1f);
    }
    public void update(){
        bounds = new Rectangle(worldX-size/2,worldY-size/2,size,size);
        if (item.sprite != null) {
            item.sprite.setSize(bounds.width, bounds.height);
            item.sprite.setOriginCenter();
            item.sprite.setPosition(bounds.x, bounds.y);
        }
    }
    public void render(SpriteBatch batch){
        if(item!=null) {

            batch.disableBlending();
            item.sprite.draw(batch);
            batch.enableBlending();
//            font.draw(batch,String.valueOf((int)worldX), worldX, worldY);
//            font.draw(batch,String.valueOf((int)worldY), worldX+2f, worldY);
        }
    }
}
