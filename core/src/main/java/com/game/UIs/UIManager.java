package com.game.UIs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.game.items.Item;
import com.game.items.ItemEntity;
import com.game.items.ItemEntityManager;
import com.game.mechanics.MouseSlot;
import com.game.mechanics.PlayerInventory;
import com.game.world.worldManager;
public class UIManager {
    public worldManager worldManager;
    public Stage stage;
    public Skin skin;
    public boolean paused = false;
    public boolean inventoryOpen = false;
    public Table pauseMenu;
    public Table inventoryUI;
    public PlayerInventory inventory;
    public MouseSlot mouseSlot;
    ShapeRenderer shapeRenderer;


    public UIManager(worldManager worldManager) {
        this.worldManager = worldManager;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // You can load a skin from assets if you have one
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        inventory = new PlayerInventory(5, 4);
        mouseSlot = new MouseSlot();

        createPauseMenu();
        createInventoryUI();
        shapeRenderer = new ShapeRenderer();
    }

    private void createPauseMenu() {
        pauseMenu = new Table();
        pauseMenu.setFillParent(true);

        Label pauseLabel = new Label("Game Paused", skin);
        TextButton resumeButton = new TextButton("Resume", skin);
        TextButton quitButton = new TextButton("Quit", skin);

        pauseMenu.add(pauseLabel).pad(10);
        pauseMenu.row();
        pauseMenu.add(resumeButton).pad(10);
        pauseMenu.row();
        pauseMenu.add(quitButton).pad(10);

        stage.addActor(pauseMenu);
        pauseMenu.setVisible(false);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                togglePause();
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void createInventoryUI() {
        inventoryUI = new Table();
        inventoryUI.setFillParent(true);

        Label title = new Label("Inventory", skin);
        inventoryUI.add(title).colspan(inventory.getWidth()).padBottom(10);
        inventoryUI.row();

        // Create grid
        for (int y = 0; y < inventory.getHeight(); y++) {
            for (int x = 0; x < inventory.getWidth(); x++) {
                int index = y * inventory.getWidth() + x;
                TextButton slot = new TextButton("",skin);
                slot.getLabel().setColor(Color.LIGHT_GRAY);
                slot.setColor(new Color(1, 1, 1, 0.5f));
                slot.pad(10);

                slot.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float px, float py) {
                        inventory.setItem(index,mouseSlot.switchItem(inventory.getItem(index)));
                        refreshInventoryUI();
                    }
                });

                inventoryUI.add(slot).size(64, 64).pad(5);
            }
            inventoryUI.row();
        }
        stage.addActor(inventoryUI);
        inventoryUI.setVisible(false);
    }

    public void refreshInventoryUI() {
        // When items change, refresh button labels
        int i = 0;
        for (Actor actor : inventoryUI.getChildren()) {
            if (actor instanceof TextButton && i < inventory.getSize()) {
                Item item = inventory.getItem(i);
                ((TextButton) actor).setText(item == null ? "" : item.name+" "+item.amount);
                i++;
            }
        }
    }

    public void update(float delta) {
        handleInput();

        if (paused || inventoryOpen) {
            stage.act(delta);
        }
    }

    public void render() {
        if (paused || inventoryOpen) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, 0.5f);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            stage.draw();

        }
    }
    public void renderMouseItem(SpriteBatch batch){
        Vector2 mousePos = stage.screenToStageCoordinates(
            new Vector2(Gdx.input.getX(), Gdx.input.getY())
        );
        mouseSlot.render(batch, mousePos.x, mousePos.y);
    }

    private void handleInput() {
        // Toggle pause
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            togglePause();
        }

        // Toggle inventory
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            toggleInventory();
        }



    }

    public void togglePause() {
        paused = !paused;
        pauseMenu.setVisible(paused);
    }

    public void toggleInventory() {
        inventoryOpen = !inventoryOpen;
        inventoryUI.setVisible(inventoryOpen);
        refreshInventoryUI();
    }

    public boolean isPaused() {
        return paused;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        shapeRenderer.dispose();

    }

    public void decreaseAndAutoGet() {
        Item tempItem1 = new Item(mouseSlot.getItem());
        mouseSlot.decreseAmount();
        if(mouseSlot.getItem()==null){
            mouseSlot.switchItem(inventory.getItem(tempItem1));
            inventory.removeItem(tempItem1);
        }
    }
}
