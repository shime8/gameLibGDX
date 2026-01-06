package com.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.game.world.worldManager;

import java.awt.*;

public class Player {
    public float x, y;
    public float speed = 5f; // plytki na sekunde
    public Texture texture;
    public Array<Texture> Animation;
    public float displayWidth;
    public float unitScale;
    float mapWidth;
    float mapHeight;
    float AnimAcumulator;
    int AnimCounter;

    Rectangle hitbox;

    public Player(float startX, float startY, float unitScale) {
        this.x = startX;
        this.y = startY;
        this.unitScale = unitScale;
        this.texture = new Texture(Gdx.files.internal("player/HQ Player.png"));
        this.Animation = new Array<>();
        this.Animation.add(new Texture(Gdx.files.internal("player/HQ Player.png")));
        this.Animation.add(new Texture(Gdx.files.internal("player/HQ Player_2.png")));
        this.Animation.add(new Texture(Gdx.files.internal("player/HQ Player.png")));
        this.Animation.add(new Texture(Gdx.files.internal("player/HQ Player_3.png")));
        this.hitbox = new Rectangle(x-0.25f,y-0.46f,0.5f,0.9f);
        this.displayWidth = 32f; // in pixels
        this.mapWidth = worldManager.mapWidth;
        this.mapHeight = worldManager.mapHeight;
        this.AnimAcumulator = 0;
        this.AnimCounter = 0;
    }

    public void update(float dt ) {
        float dx = 0f, dy = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            dx = -1f;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            dx = 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            dy = 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            dy = -1f;

        // animacja
        if(dx != 0 || dy != 0){
            AnimAcumulator += dt;
            if(AnimAcumulator > (1f/4f)){
                AnimAcumulator = AnimAcumulator % (1f/4f);
                AnimCounter += 1;
                AnimCounter = AnimCounter % 4;
                this.texture = Animation.get(this.AnimCounter);
            }
        }else{
            this.texture = this.Animation.first();
            AnimAcumulator = 0;
        }

        // normalizacja skosu
        if (dx != 0 && dy != 0) {
            float inv = 1f / (float)Math.sqrt(2);
            dx *= inv; dy *= inv;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            dx *= 2;
            dy *= 2;
        }

        float newX = x + dx * speed * dt;
        float newY = y + dy * speed * dt;
        hitbox.setCenter(x, y);
        Rectangle newHitbox = new Rectangle(hitbox);
        newHitbox.setCenter(newX,newY);

        boolean blocked =
            isCellBlocked( newHitbox.x, newHitbox.y) ||
            isCellBlocked( newHitbox.x+newHitbox.width, newHitbox.y) ||
            isCellBlocked( newHitbox.x, newHitbox.y+newHitbox.height) ||
            isCellBlocked( newHitbox.x+newHitbox.width, newHitbox.y+newHitbox.height);
        if(!blocked){
            x = newX;
            y = newY;
        }else{
            boolean blockedX =
                isCellBlocked( newHitbox.x, hitbox.y) ||
                isCellBlocked( newHitbox.x+newHitbox.width, hitbox.y) ||
                isCellBlocked( newHitbox.x, hitbox.y+hitbox.height) ||
                isCellBlocked( newHitbox.x+newHitbox.width, hitbox.y+newHitbox.height);
            boolean blockedY =
                isCellBlocked( hitbox.x, newHitbox.y) ||
                    isCellBlocked( hitbox.x+hitbox.width, newHitbox.y) ||
                    isCellBlocked( hitbox.x, newHitbox.y+newHitbox.height) ||
                    isCellBlocked( hitbox.x+hitbox.width, newHitbox.y+newHitbox.height);
            if(!blockedX){
                x = newX; //normalny ruch x
            }else{
                //gdy collision to clamp x do bloczka z collision
                if(newX<x){
                    x = (float)Math.floor(x) + hitbox.width/2f;
                }else if(newY>x){
                    x = (float)Math.floor(x) + 0.99f - hitbox.width/2f;
                }
            }
            if(!blockedY){ y = newY; //normalny ruch y
            }else{
                //gdy collision to clamp y do bloczka z collision
                if(newY<y){
                    y = (float)Math.floor(y) + hitbox.height/2f;
                }else if(newY>y){
                    y = (float)Math.floor(y) + 0.99f - hitbox.height/2f;
                }
            }
        }


        // Clamp gracza do mapy
        float halfW = (this.displayWidth * unitScale) / 2f;
        float halfH = (texture.getHeight() * unitScale * this.displayWidth) / (2f * texture.getWidth());
        x = clamp(x, halfW, mapWidth - halfW);
        y = clamp(y, halfH, mapHeight - halfH);
    }
    public void draw(SpriteBatch batch){
        float w = this.displayWidth * unitScale;
        float h = (texture.getHeight() * unitScale * this.displayWidth) / texture.getWidth();
        batch.draw(texture, x - w/2, y - h/2, w, h);
    }
    public void dispose() {
        texture.dispose();
    }



    private float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }
    private boolean isCellBlocked(float worldX, float worldY) {
       return worldManager.isCellBlocked(worldX,worldY);
    }

}
