package com.game.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ItemEntity {
    public Item item;
    public float worldX;
    public float worldY;
    public Rectangle bounds;
    float size;
    public Vector2 direction;
//    public BitmapFont font;
    public ItemEntity(Item item, float worldX, float worldY){
        this.item =item.clone();
        this.item.sprite = new Sprite(item.sprite);
        this.worldX = worldX;
        this.worldY = worldY;
        size = 0.5f;
        update();
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(0.1f);
    }
    public ItemEntity(ItemEntity itemEntity){
        this.item = itemEntity.item.clone();
        this.item.sprite = new Sprite(itemEntity.item.sprite);
        this.worldX = itemEntity.worldX;
        this.worldY = itemEntity.worldY;
        this.bounds = itemEntity.bounds;
        this.size = itemEntity.size;
        this.direction = itemEntity.direction;
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(0.1f);
        update();
    }
    public String getname(){
        if(this.item != null){
            return this.item.getname();
        }else{
            System.out.println("SumtingWentWong itemEntity");
            return "NameNotSet";
        }
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

//            batch.disableBlending();
            if(item.sprite.getX()>0 && item.sprite.getY()>0){
                item.sprite.draw(batch);
            }
//            batch.enableBlending();
//            font.draw(batch,String.valueOf((int)worldX), worldX, worldY);
//            font.draw(batch,String.valueOf((int)worldY), worldX+2f, worldY);
        }
    }
    public boolean lessThanHalf(){
        return worldX%1<0.5 && direction.x>0
            || worldX%1>0.5 && direction.x<0
            || worldY%1<0.5 && direction.y>0
            || worldY%1>0.5 && direction.y<0;
    }

}
