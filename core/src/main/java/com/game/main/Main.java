package com.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.items.Gear;
import com.game.items.Item;
import com.game.items.ItemEntity;
import com.game.items.ItemEntityManager;
import com.game.player.Player;
import com.game.tileenttities.*;
import com.game.world.worldManager;
import com.game.UIs.UIManager;
public class Main extends ApplicationAdapter {
    //private worldtemp worldController;
    public static worldManager worldManager;
    private Player player;
    private ObjectMap<GridPoint2, TileEntity> tileEntityMap;
    public static TileEntityManager tileEntityManager;
    private ObjectMap<GridPoint2, Array<ItemEntity>> itemEntityMap;
    public static ItemEntityManager itemEntityManager;
    SpriteBatch batch;
    public static UIManager uiManager;
    public static float unitScale = 1f / 32f;
    @Override
    public void create() {
        player = new Player(30,30, unitScale);

        worldManager = new worldManager();
        worldManager.player = player;
        tileEntityMap = new ObjectMap<>();
        tileEntityManager = new TileEntityManager(tileEntityMap);
        itemEntityMap = new ObjectMap<>();
        itemEntityManager = new ItemEntityManager(itemEntityMap);
        batch = new SpriteBatch();
        uiManager = new UIManager();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiManager.stage); // UI input
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                // Zoom camera
                com.game.world.worldManager.camera.zoom += amountY * 0.05f * com.game.world.worldManager.camera.zoom;
                com.game.world.worldManager.camera.zoom = Math.max(0.1f, Math.min(com.game.world.worldManager.camera.zoom, 2f));
                return true;
            }
        });
        Gdx.input.setInputProcessor(multiplexer);


        tileEntityManager.addEntity(new Chest(33, 33));
        uiManager.inventory.setItem(0, new Item(100, new Chest()));
        uiManager.inventory.setItem(1, new Item(100, new Belt()));
        uiManager.inventory.setItem(2, new Item(100, new Inserter()));
        uiManager.inventory.setItem(3, new Gear(100));
        uiManager.inventory.setItem(4, new Item(100, new Assembler()));
        uiManager.inventory.setItem(5, new Item(50, new Creator()));
        uiManager.inventory.setItem(6, new Item(50, new Deleter()));
    }

    @Override
    public void render() {
        //inputs and update
        float dt = Gdx.graphics.getDeltaTime();

        uiManager.update(dt);
        if (!uiManager.isPaused()) {
            worldManager.mouseActions(dt);
            worldManager.handleInputs();
            tileEntityManager.update(dt);
            worldManager.update(dt);
        }

        //draw sprite batch
        batch.begin();
        worldManager.updateCamera();
        worldManager.drawMap();

        tileEntityManager.render(batch);
        itemEntityManager.render(batch);
        player.draw(batch);

        batch.end();

        worldManager.drawShapes();

        uiManager.render();
        uiManager.stage.getBatch().begin();
        uiManager.renderMouseItem((SpriteBatch) uiManager.stage.getBatch());
        uiManager.stage.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

        worldManager.resize(width, height);
        uiManager.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        worldManager.dispose();
        uiManager.dispose();
    }
}
