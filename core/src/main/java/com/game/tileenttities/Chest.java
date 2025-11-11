package com.game.tileenttities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class Chest extends TileEntity {
    private Texture texture;

    public Chest(int x, int y) {
        super(x, y);
        texture = new Texture("tiles/chest.png");
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, bounds.width, bounds.height);
    }
}
