package com.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class Player {
    public float x, y;
    public float speed = 5f; // plytki na sekunde
    public Texture texture;
    public float unitScale;
    Rectangle hitbox;

    public Player(float startX, float startY, float unitScale) {
        this.x = startX;
        this.y = startY;
        this.unitScale = unitScale;
        this.texture = new Texture(Gdx.files.internal("player/player.png"));
        this.hitbox = new Rectangle(x-0.5f,y-0.5f,1,1);
    }

    public void update(float dt, float mapWidth, float mapHeight, TiledMapTileLayer layer) {
        float dx = 0f, dy = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            dx = -1f;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            dx = 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            dy = 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            dy = -1f;

        // normalizacja skosu
        if (dx != 0 && dy != 0) {
            float inv = 1f / (float)Math.sqrt(2);
            dx *= inv; dy *= inv;
        }

        float newX = x + dx * speed * dt;
        float newY = y + dy * speed * dt;
        hitbox.setCenter(x, y);
        Rectangle newHitbox = new Rectangle(hitbox);
        newHitbox.setCenter(newX,newY);

        boolean blocked =
            isCellBlocked(layer, newHitbox.x, newHitbox.y) ||
            isCellBlocked(layer, newHitbox.x+newHitbox.width, newHitbox.y) ||
            isCellBlocked(layer, newHitbox.x, newHitbox.y+newHitbox.height) ||
            isCellBlocked(layer, newHitbox.x+newHitbox.width, newHitbox.y+newHitbox.height);
        if(!blocked){
            x = newX;
            y = newY;
        }else{
            boolean blockedX =
                isCellBlocked(layer, newHitbox.x, hitbox.y) ||
                isCellBlocked(layer, newHitbox.x+newHitbox.width, hitbox.y) ||
                isCellBlocked(layer, newHitbox.x, hitbox.y+hitbox.height) ||
                isCellBlocked(layer, newHitbox.x+newHitbox.width, hitbox.y+newHitbox.height);
            boolean blockedY =
                isCellBlocked(layer, hitbox.x, newHitbox.y) ||
                    isCellBlocked(layer, hitbox.x+hitbox.width, newHitbox.y) ||
                    isCellBlocked(layer, hitbox.x, newHitbox.y+newHitbox.height) ||
                    isCellBlocked(layer, hitbox.x+hitbox.width, newHitbox.y+newHitbox.height);
            if(!blockedX){ x = newX;}
            if(!blockedY){ y = newY;}
        }


        // Clamp gracza do mapy
        float halfW = (texture.getWidth() * unitScale) / 2f;
        float halfH = (texture.getHeight() * unitScale) / 2f;
        x = clamp(x, halfW, mapWidth - halfW);
        y = clamp(y, halfH, mapHeight - halfH);
    }
    public void draw(SpriteBatch batch){
        float w = texture.getWidth() * unitScale;
        float h = texture.getHeight() * unitScale;
        batch.draw(texture, x - w/2, y - h/2, w, h);
    }
    public void dispose() {
        texture.dispose();
    }



    private float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }
    private boolean isCellBlocked(TiledMapTileLayer layer, float worldX, float worldY) {
        int tileX = (int) Math.floor(worldX);
        int tileY = (int) Math.floor(worldY);

        TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);
        if (cell == null) return false;

        MapProperties props = cell.getTile().getProperties();
        return props.containsKey("collidable") && (boolean) props.get("collidable");
    }

}
