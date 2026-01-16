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

import java.io.FileWriter;
import java.io.IOException;

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
    public static int AssemblersUnlockingTier = 10;
    public float SaveAccumulator = 0f;
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


    }
    void addGameAssets(){
        addOres();

        Array<Item> LHrecipe = new Array<>();
//        LHrecipe.add(new RoofTile(20));
//        LHrecipe.add(new GlassTile(20));
//        LHrecipe.add(new Motor(10));
//        LHrecipe.add(new Reflector(10));
//        LHrecipe.add(new ReinforcedWall(20));
        LHrecipe.add(new NewItem(3,new Assembler()));
        BuildPlace LHBP = new BuildPlace(20,54);
        LHBP.setBuild(new LightHouse(20,54));
        LHBP.setRecipe(LHrecipe);
        tileEntityManager.addEntity(LHBP);
        //for gam
//        uiManager.inventory.setItem(0, new NewItem(2, new Chest()));
//        uiManager.inventory.setItem(1, new NewItem(5, new Inserter()));
//        uiManager.inventory.setItem(1, new NewItem(3, new LongInserter()));
//        uiManager.inventory.setItem(3, new NewItem(3, new Assembler()));
//        uiManager.inventory.setItem(4, new NewItem(1, new Miner()));
//        uiManager.inventory.setItem(5, new NewItem(1, new Deleter()));
        // for testing
        uiManager.inventory.setItem(0, new NewItem(20, new Chest()));
        uiManager.inventory.setItem(1, new NewItem(50, new FastInserter()));
        uiManager.inventory.setItem(2, new NewItem(40, new LongInserter()));
        uiManager.inventory.setItem(3, new NewItem(30, new AssemblerT2()));
        uiManager.inventory.setItem(4, new NewItem(10, new Miner()));
        uiManager.inventory.setItem(5, new NewItem(50, new Belt()));
        uiManager.inventory.setItem(6, new NewItem(10, new Deleter()));
        uiManager.inventory.setItem(7, new NewItem(10, new Inserter()));
        uiManager.inventory.setItem(8, new NewItem(10, new Assembler()));
        uiManager.inventory.setItem(9, new NewItem(10, new FastBelt()));

        stuffAdded = true;
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
                if (!stuffAdded) {
                    createGameparts();
                    addGameAssets();
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
                createGameparts();
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

        SaveAccumulator += dt;
        if(SaveAccumulator > 10f){
            SaveAccumulator = 0;

        }
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
    void addOres(){
        tileEntityManager.addEntity(new MetalOre(20, 39));
        tileEntityManager.addEntity(new MetalOre(20, 40));
        tileEntityManager.addEntity(new MetalOre(21, 40));
        tileEntityManager.addEntity(new MetalOre(20, 41));

        tileEntityManager.addEntity(new ClayOre(38, 20));
        tileEntityManager.addEntity(new ClayOre(38, 21));
        tileEntityManager.addEntity(new ClayOre(39, 21));
        tileEntityManager.addEntity(new ClayOre(40, 20));

        tileEntityManager.addEntity(new MetalOre(57, 18));
        tileEntityManager.addEntity(new MetalOre(57, 19));
        tileEntityManager.addEntity(new MetalOre(58, 19));
        tileEntityManager.addEntity(new MetalOre(57, 20));

        tileEntityManager.addEntity(new MetalOre(70, 25));
        tileEntityManager.addEntity(new MetalOre(71, 26));
        tileEntityManager.addEntity(new MetalOre(69, 26));
        tileEntityManager.addEntity(new MetalOre(70, 27));

        tileEntityManager.addEntity(new MetalOre(78, 35));
        tileEntityManager.addEntity(new MetalOre(78, 36));
        tileEntityManager.addEntity(new MetalOre(79, 37));
        tileEntityManager.addEntity(new MetalOre(78, 37));

        tileEntityManager.addEntity(new MetalOre(37, 79));
        tileEntityManager.addEntity(new MetalOre(38, 79));

        tileEntityManager.addEntity(new MetalOre(47, 80));
        tileEntityManager.addEntity(new MetalOre(47, 79));

        tileEntityManager.addEntity(new MetalOre(58, 81));
        tileEntityManager.addEntity(new MetalOre(57, 80));

        tileEntityManager.addEntity(new MetalOre(68, 78));
        tileEntityManager.addEntity(new MetalOre(69, 79));

        tileEntityManager.addEntity(new MetalOre(75, 69));
        tileEntityManager.addEntity(new MetalOre(74, 69));

        tileEntityManager.addEntity(new MetalOre(80, 55));
        tileEntityManager.addEntity(new MetalOre(80, 56));
    }
}
