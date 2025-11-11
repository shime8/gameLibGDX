package com.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.game.world.WorldController;

public class WorldRenderer {
    private WorldController world;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Vector3 mouseWorld = new Vector3();


    public WorldRenderer(WorldController world) {
        this.world = world;

        float w = Gdx.graphics.getWidth() * world.unitScale;
        float h = Gdx.graphics.getHeight() * world.unitScale;
        camera = new OrthographicCamera(w, h);
        camera.setToOrtho(false, w, h);

        mapRenderer = new OrthogonalTiledMapRenderer(world.map, world.unitScale);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    public void render() {
        updateCamera();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // render player
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float w = world.player.texture.getWidth() * world.unitScale;
        float h = world.player.texture.getHeight() * world.unitScale;
        batch.draw(world.player.texture, world.player.x - w/2, world.player.y - h/2, w, h);
        batch.end();

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

    private void updateCamera() {
        // follow player
        camera.position.set(world.player.x, world.player.y, 0);

        // clamp to map bounds
        float halfW = camera.viewportWidth / 2f;
        float halfH = camera.viewportHeight / 2f;

        if (world.mapWidth > camera.viewportWidth)
            camera.position.x = clamp(camera.position.x, halfW, world.mapWidth - halfW);
        else
            camera.position.x = world.mapWidth / 2f;

        if (world.mapHeight > camera.viewportHeight)
            camera.position.y = clamp(camera.position.y, halfH, world.mapHeight - halfH);
        else
            camera.position.y = world.mapHeight / 2f;

        camera.update();
    }

    private float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width * world.unitScale;
        camera.viewportHeight = height * world.unitScale;
        camera.update();
    }

    public void dispose() {
        world.player.dispose();
        world.map.dispose();
        mapRenderer.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }
}
