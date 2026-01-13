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
import com.game.items.*;
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
    public String Language;
    @Override
    public void create() {
        TypeToString.init("Languages/PL.txt");
        Language = "PL";
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



        MainMenu.create();

    }
    public void createGameparts(){
        uiManager = new UIManager();
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

        Array<Item> LHrecipe = new Array<>();
        LHrecipe.add(new Brick(20));
        LHrecipe.add(new Glass(20));
        LHrecipe.add(new NewItem(5, new Assembler()));
        BuildPlace LHBP = new BuildPlace(20,54);
        LHBP.setBuild(new LightHouse(20,54));
        LHBP.setRecipe(LHrecipe);
        tileEntityManager.addEntity(LHBP);
        //for testing
        uiManager.inventory.setItem(0, new NewItem(50, new Chest()));
        uiManager.inventory.setItem(1, new NewItem(50, new Belt()));
        uiManager.inventory.setItem(2, new NewItem(50, new Inserter()));
        uiManager.inventory.setItem(3, new Gear(50));
        uiManager.inventory.setItem(4, new NewItem(50, new Assembler()));
        //uiManager.inventory.setItem(5, new NewItem(50, new Creator()));
        uiManager.inventory.setItem(6, new NewItem(50, new Deleter()));
        uiManager.inventory.setItem(7, new NewItem(50, new Miner()));
        uiManager.inventory.setItem(8, new NewItem(50, new LongInserter()));
        stuffAdded = true;
    }
    void loadSaveFile(){
        // tu dodać jak zdąrze
    }

    @Override
    public void render() {
        if (MainMenu.isWaiting()) {
            MainMenu.render();
            return; // Exit early
        }
        String selection = MainMenu.selectedOption;
        if(selection==null){
            MainMenu.reset();
            return;
        }
        switch (selection) {
            case "PLAY":
                if (!stuffAdded) loadSaveFile();

                if (!stuffAdded) {
                    createGameparts();
                    resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                }
                break;
            case "OPTIONS":
                System.out.println("hello");
                MainMenu.reset();
                MainMenu.createOptions();
                // options
                return;
            case "BACK":
                MainMenu.reset();
                MainMenu.create();
                // back to main menu
                return;
            case "LANG_SWITCH":
                if (Language.equals("PL")) {
                    TypeToString.init("Languages/ENG.txt");
                    Language = "EN";
                } else if (Language.equals("EN")) {
                    TypeToString.init("Languages/PL.txt");
                    Language = "PL";
                }
                MainMenu.reset();
                MainMenu.createOptions();
                // lang switch
                return;
            case "SAVE_RESET":

                MainMenu.reset();
                MainMenu.create();
                // save reset
            case "EXIT":
                Gdx.app.exit();
                return;
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

        itemEntityManager.renderOnGround(batch);
        tileEntityManager.render(batch);
        //tileEntityManager.renderItemsOnBelts(batch,false);
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
        if(worldManager!=null)worldManager.resize(width, height);
        if(uiManager!=null)uiManager.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        worldManager.dispose();
        uiManager.dispose();
        MainMenu.dispose();
    }
}
