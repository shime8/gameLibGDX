package com.game.tileenttities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class TileEntity {
    public String name;
    public int x, y;
    public Rectangle bounds;
    public Sprite sprite;
    public BitmapFont font;
    public TileEntity(){

    }
    public TileEntity(int x, int y) {
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, 1,1);

    }
    public TileEntity(TileEntity other) {
        this.x = other.x;
        this.y = other.y;
        this.bounds = new Rectangle(x, y, 1,1);
        this.sprite = new Sprite(other.sprite);
        this.name = other.name;
    }
    public abstract TileEntity clone();
    public abstract void update(float delta);

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void set(int x, int y){
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, 1,1);
        if (sprite != null) {
            sprite.setSize(bounds.width, bounds.height);
            sprite.setOriginCenter();
            sprite.setPosition(x, y);
        }
    }

}
