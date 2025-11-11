package com.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.player.Player;
import com.game.tileenttities.Chest;
import com.game.tileenttities.TileEntity;
import com.game.tileenttities.TileEntityManager;
import com.game.world.worldtemp;
import com.game.world.worldManager;

public class Main extends ApplicationAdapter {
    //private worldtemp worldController;
    private worldManager worldManager;
    private Player player;
    private ObjectMap<GridPoint2, TileEntity> tileEntityMap;
    TileEntityManager tileEntityManager;
    SpriteBatch batch;
    @Override
    public void create() {
        float unitscale = 1f / 32f;
        player = new Player(10,8, unitscale);
        worldManager = new worldManager();
        worldManager.player = player;
        worldManager.unitScale = unitscale;
        tileEntityMap = new ObjectMap<>();
        tileEntityManager = new TileEntityManager(tileEntityMap);

        tileEntityManager.addEntity(new Chest(3, 3));

        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        //input
        worldManager.mouseActions(tileEntityManager);
        //update
        float dt = Gdx.graphics.getDeltaTime();
        tileEntityManager.update(dt);
        worldManager.update(dt);
        //draw sprite batch

        batch.begin();
        //draw map
        worldManager.drawMap();

        //draw tile entities
        tileEntityManager.render(batch);

        // draw player
        batch.setProjectionMatrix(worldManager.camera.combined);//ustawienie batch na world a nie screen
        float w = player.texture.getWidth() * player.unitScale;
        float h = player.texture.getHeight() * player.unitScale;
        batch.draw(player.texture, player.x - w/2, player.y - h/2, w, h);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        worldManager.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        worldManager.dispose();
    }
}
