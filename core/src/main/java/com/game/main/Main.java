package com.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.UIs.MainMenu;
import com.game.UIs.TypeToString;
import com.game.items.Gear;
import com.game.items.Item;
import com.game.items.ItemEntity;
import com.game.items.ItemEntityManager;
import com.game.mechanics.RecipeManager;
import com.game.player.Player;
import com.game.tileenttities.*;
import com.game.world.worldManager;
import com.game.UIs.UIManager;

import static com.game.world.worldManager.camera;

public class Main extends ApplicationAdapter {
    //private worldtemp worldController;
    public static worldManager worldManager;
    private Player player;
    private ObjectMap<GridPoint2, TileEntity> tileEntityMap;
    public static TileEntityManager tileEntityManager;
    private ObjectMap<GridPoint2, Array<ItemEntity>> itemEntityMap;
    public static ItemEntityManager itemEntityManager;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    public static UIManager uiManager;
    public static float unitScale = 1f / 32f;
    public static RecipeManager recipeManager;
    public static boolean stuffAdded = false;
    @Override
    public void create() {
        TypeToString.init("Languages/PL.txt");
        worldManager = new worldManager();
        player = new Player(30,30, unitScale);
        worldManager.player = player;
        tileEntityMap = new ObjectMap<>();
        tileEntityManager = new TileEntityManager(tileEntityMap);
        itemEntityMap = new ObjectMap<>();
        itemEntityManager = new ItemEntityManager(itemEntityMap);
        recipeManager = new RecipeManager();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        uiManager = new UIManager();

        MainMenu.create();

    }
    public void createGameparts(){
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiManager.stage); // UI input
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                // Zoom camera
                camera.zoom += amountY * 0.05f * camera.zoom;
                camera.zoom = Math.max(0.1f, Math.min(camera.zoom, 2f));
                return true;
            }
        });
        Gdx.input.setInputProcessor(multiplexer);

        tileEntityManager.addEntity(new Chest(33, 33));
        tileEntityManager.addEntity(new MetalOre(20, 39));
        tileEntityManager.addEntity(new MetalOre(20, 40));
        tileEntityManager.addEntity(new MetalOre(20, 41));

        tileEntityManager.addEntity(new ClayOre(20, 44));
        tileEntityManager.addEntity(new ClayOre(20, 45));
        tileEntityManager.addEntity(new ClayOre(20, 46));

        uiManager.inventory.setItem(0, new Item(100, new Chest()));
        uiManager.inventory.setItem(1, new Item(100, new Belt()));
        uiManager.inventory.setItem(2, new Item(100, new Inserter()));
        uiManager.inventory.setItem(3, new Gear(100));
        uiManager.inventory.setItem(4, new Item(100, new Assembler()));
        uiManager.inventory.setItem(5, new Item(50, new Creator()));
        uiManager.inventory.setItem(6, new Item(50, new Deleter()));
        uiManager.inventory.setItem(7, new Item(50, new Miner()));
        uiManager.inventory.setItem(8, new Item(50, new LongInserter()));
        stuffAdded = true;
    }

    @Override
    public void render() {
        if (MainMenu.isWaiting()) {
            MainMenu.render();
            return; // Exit early
        }

        String selection = MainMenu.wait_for_selection();
        if (selection != null) {
            switch (selection) {
                case "PLAY":
                    if(!stuffAdded)createGameparts();
                    // Game plays
                    break;
                case "OPTIONS":
                    // options
                    return;
                case "EXIT":
                    Gdx.app.exit();
                    return;
            }
        }


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
        if(tileEntityManager.isEmpty()){player.draw(batch);}
        worldManager.drawbatch(batch);
        batch.end();

        worldManager.prepareShapeR(shapeRenderer);
        worldManager.drawShapes(shapeRenderer);
        tileEntityManager.shapeRender(shapeRenderer);

        uiManager.render();
        uiManager.stage.getBatch().begin();
        uiManager.renderMouseItem((SpriteBatch) uiManager.stage.getBatch());
        uiManager.stage.getBatch().end();

    }

    @Override
    public void resize(int width, int height) {
        if (MainMenu.isWaiting()) MainMenu.resize(width,height);
        worldManager.resize(width, height);
        uiManager.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        worldManager.dispose();
        uiManager.dispose();
        MainMenu.dispose();
    }
}
