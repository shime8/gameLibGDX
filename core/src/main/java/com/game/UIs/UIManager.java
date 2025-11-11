package com.game.UIs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class UIManager {
    private Stage stage;
    private Skin skin;
    private boolean paused = false;
    private boolean inventoryOpen = false;
    private Table pauseMenu;
    private Table inventoryUI;
    private PlayerInventory inventory;
    ShapeRenderer shapeRenderer;

    public UIManager() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // You can load a skin from assets if you have one
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        inventory = new PlayerInventory(5, 4);

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
                TextButton slot = new TextButton("", skin);
                slot.getLabel().setColor(Color.LIGHT_GRAY);
                slot.setColor(new Color(1, 1, 1, 0.5f));
                slot.pad(10);

                slot.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float px, float py) {
                        if (inventory.getItem(index) == null) {
                            inventory.setItem(index, "Stone");
                        } else {
                            inventory.removeItem(index);
                        }
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

    private void refreshInventoryUI() {
        // When items change, refresh button labels
        int i = 0;
        for (Actor actor : inventoryUI.getChildren()) {
            if (actor instanceof TextButton && i < inventory.getSize()) {
                String item = inventory.getItem(i);
                ((TextButton) actor).setText(item == null ? "" : item.substring(0, 1));
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
    }

    public boolean isPaused() {
        return paused || inventoryOpen;
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        shapeRenderer.dispose();
    }
}
