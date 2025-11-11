package com.game.tileenttities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class TileEntity {
    public int x, y;
    public Rectangle bounds;

    public TileEntity(int x, int y) {
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, 1,1);
    }

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);
}
