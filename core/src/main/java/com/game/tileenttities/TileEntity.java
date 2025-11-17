package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class TileEntity {
    public String name;
    public int x, y;
    public Rectangle bounds;
    public Texture texture;
    public TileEntity(){}
    public TileEntity(int x, int y) {
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, 1,1);
    }
    public TileEntity(TileEntity other) {
        this.x = other.x;
        this.y = other.y;
        this.bounds = new Rectangle(x, y, 1,1);
        this.texture = other.texture;
        this.name = other.name;
    }

    public abstract void update(float delta);

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, bounds.width, bounds.height);
    }

    public void set(int x, int y){
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, 1,1);
    }

}
