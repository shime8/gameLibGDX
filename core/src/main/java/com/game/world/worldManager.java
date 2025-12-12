package com.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.game.UIs.UIManager;
import com.game.items.Item;
import com.game.items.ItemEntity;
import com.game.items.ItemEntityManager;
import com.game.player.Player;
import com.game.tileenttities.*;

import java.util.Objects;

import static com.game.main.Main.*;

public class worldManager {
    public static float mapWidth, mapHeight;
    public static OrthographicCamera camera;
    public static OrthogonalTiledMapRenderer mapRenderer;
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public static Vector3 mouseWorld = new Vector3();
    public Player player;
    public static TiledMap map;
    TiledMapTileLayer collisionLayer;
    public float tileBreakTimer;
    public static Vector2 direction;
    public worldManager() {
        map = new TmxMapLoader().load("maps/mapv1.tmx");
        collisionLayer = (TiledMapTileLayer) map.getLayers().get("Warstwa Kafelków 1");

        MapProperties props = map.getProperties();
        mapWidth = props.get("width", Integer.class);
        mapHeight = props.get("height", Integer.class);
        //int tileWidth = props.get("tilewidth", Integer.class);
        //int tileHeight = props.get("tileheight", Integer.class);

        float w = Gdx.graphics.getWidth() * unitScale;
        float h = Gdx.graphics.getHeight() * unitScale;
        camera = new OrthographicCamera(w, h);
        camera.setToOrtho(false, w, h);

        mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);

        shapeRenderer = new ShapeRenderer();
        direction = new Vector2(1,0);
    }
    public void update(float dt) {
        player.update(dt,mapWidth,mapHeight,collisionLayer);

    }
    public void drawMap() {


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render map
        mapRenderer.setView(camera);
        mapRenderer.render();

    }
    public void dispose() {
        player.dispose();
        map.dispose();
        mapRenderer.dispose();
        shapeRenderer.dispose();
    }

    public void updateCamera() {
        camera.position.set(player.x, player.y, 0);

        // clamp to map bounds
        //float halfW = camera.viewportWidth / 2f;
        //float halfH = camera.viewportHeight / 2f;

        //if (mapWidth > camera.viewportWidth)
        //    camera.position.x = clamp(camera.position.x, halfW, mapWidth - halfW);
        //else
        //    camera.position.x = mapWidth / 2f;

        //if (mapHeight > camera.viewportHeight)
        //    camera.position.y = clamp(camera.position.y, halfH, mapHeight - halfH);
        //else
        //    camera.position.y = mapHeight / 2f;
        //endof clamp

        camera.update();
    }

    public void mouseActions(float dt){

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !uiManager.inventoryOpen) {
            mouseWorld.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouseWorld);
            int tileX = (int) Math.floor(mouseWorld.x);
            int tileY = (int) Math.floor(mouseWorld.y);
            if(tileEntityManager.getEntityAt(tileX,tileY) instanceof Chest){
                uiManager.openChest((Chest)tileEntityManager.getEntityAt(tileX,tileY));
            }
            if(tileEntityManager.getEntityAt(tileX,tileY) == null && uiManager.mouseSlot.getItem() != null && uiManager.mouseSlot.getItem().Tile != null) {
                TileEntity mouseTE = uiManager.mouseSlot.getItem().Tile;
                mouseTE.set(tileX,tileY);
                if(mouseTE instanceof Directional d){d.setDirection(new Vector2(this.direction));}
                tileEntityManager.addEntity(mouseTE);
                uiManager.decreaseAndAutoGet();
                uiManager.refreshInventoryUI();
            }
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && !uiManager.inventoryOpen) {
            mouseWorld.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouseWorld);
            int tileX = (int) Math.floor(mouseWorld.x);
            int tileY = (int) Math.floor(mouseWorld.y);
            if(tileEntityManager.getEntityAt(tileX,tileY) != null) {
                uiManager.inventory.addItem(new Item(1,tileEntityManager.getEntityAt(tileX,tileY)));
                uiManager.refreshInventoryUI();
                tileEntityManager.removeEntity(tileEntityManager.getEntityAt(tileX,tileY));
            }
        }

    }
    public void handleInputs(){
        //drop item on tile
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && uiManager.mouseSlot.getItem()!=null) {
            Item tempItem = new Item(uiManager.mouseSlot.getItem());
            tempItem.amount = 1;

            mouseWorld.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouseWorld);
            ItemEntity tempItemEntity = new ItemEntity(tempItem, mouseWorld.x, mouseWorld.y);
            Array<ItemEntity> ItemEs = itemEntityManager.getItemEntityList(mouseWorld.x, mouseWorld.y);
            if(ItemEs==null || ItemEs.size<4){
                itemEntityManager.addItemEntity(tempItemEntity);
                uiManager.decreaseAndAutoGet();
            }
        }

        //pickup items from floor
        if(Gdx.input.isKeyPressed(Input.Keys.F)){
            Array<ItemEntity> TileItems = itemEntityManager.getItemEntityList(player.x, player.y);
            if(TileItems!=null && !TileItems.isEmpty()) {
                ItemEntity tempItemE = TileItems.first();
                uiManager.inventory.addItem(tempItemE.item);
                TileItems.removeIndex(0);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
              direction.rotate90(-1);
//            Item item = uiManager.mouseSlot.getItem();
//            if (item != null && item.Tile instanceof Directional d) {
//                d.setDirection(d.getDirection().rotate90(1) );
//            }
        }


    }
    public void drawShapes(){
        renderMouseHighlight();
    }
    private void renderMouseHighlight() {
        // get mouse world position
        mouseWorld.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouseWorld);

        // determine tile coords
        int tileX = (int) Math.floor(mouseWorld.x);
        int tileY = (int) Math.floor(mouseWorld.y);

        // highlight area
        float tileSize = 1f; // 1 world unit = 1 tile if unitScale=1/32f

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0.3f); // semi-transparent white
        shapeRenderer.rect(tileX, tileY, tileSize, tileSize);
        shapeRenderer.end();

        // optional: draw border
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(tileX, tileY, tileSize, tileSize);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width * unitScale;
        camera.viewportHeight = height * unitScale;
        camera.update();
    }


}
