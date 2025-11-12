package com.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.items.Item;
import com.game.player.Player;
import com.game.tileenttities.Chest;
import com.game.tileenttities.TileEntity;
import com.game.tileenttities.TileEntityManager;
import com.game.world.worldManager;
import com.game.UIs.UIManager;
public class Main extends ApplicationAdapter {
    //private worldtemp worldController;
    private worldManager worldManager;
    private Player player;
    private ObjectMap<GridPoint2, TileEntity> tileEntityMap;
    TileEntityManager tileEntityManager;
    SpriteBatch batch;
    private UIManager uiManager;
    @Override
    public void create() {
        float unitscale = 1f / 32f;
        player = new Player(10,8, unitscale);

        worldManager = new worldManager(unitscale);
        worldManager.player = player;
        worldManager.unitScale = unitscale;
        tileEntityMap = new ObjectMap<>();
        tileEntityManager = new TileEntityManager(tileEntityMap);
        batch = new SpriteBatch();
        uiManager = new UIManager(worldManager);
        worldManager.uiManager = uiManager;



        tileEntityManager.addEntity(new Chest(3, 3));
        uiManager.inventory.setItem(2, new Item(100, new Chest()));
    }

    @Override
    public void render() {
        //inputs and update
        float dt = Gdx.graphics.getDeltaTime();

        uiManager.update(dt);
        if (!uiManager.isPaused()) {
            worldManager.mouseActions(tileEntityManager);
            tileEntityManager.update(dt);
            worldManager.update(dt);
        }

        //draw sprite batch
        batch.begin();
        worldManager.updateCamera();
        worldManager.drawMap();

        tileEntityManager.render(batch);
        player.draw(batch);

        batch.end();

        worldManager.drawShapes();

        uiManager.render();
        batch.begin();
        uiManager.mouseSlot.render(batch, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
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
        uiManager.dispose();
    }
}
