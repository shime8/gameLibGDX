package com.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Player {
    public float x, y;
    public float speed = 5f; // plytki na sekunde
    public Texture texture;
    public float unitScale;

    public Player(float startX, float startY, float unitScale) {
        this.x = startX;
        this.y = startY;
        this.unitScale = unitScale;
        this.texture = new Texture(Gdx.files.internal("player/player.png"));
    }

    public void update(float dt,float mapWidth, float mapHeight) {
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

        x += dx * speed * dt;
        y += dy * speed * dt;

        // Clamp gracza do mapy
        float halfW = (texture.getWidth() * unitScale) / 2f;
        float halfH = (texture.getHeight() * unitScale) / 2f;
        x = clamp(x, halfW, mapWidth - halfW);
        y = clamp(y, halfH, mapHeight - halfH);
    }
    private float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }

    public void dispose() {
        texture.dispose();
    }
}
