package com.game.UIs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.game.items.Item;
import com.game.items.ItemEntity;
import com.game.items.ItemEntityManager;
import com.game.items.NewItem;
import com.game.mechanics.MouseSlot;
import com.game.mechanics.PlayerInventory;
import com.game.mechanics.Recipe;
import com.game.tileenttities.Assembler;
import com.game.tileenttities.Chest;
import com.game.tileenttities.Silo;
import com.game.world.worldManager;

import static com.game.main.Main.recipeManager;
import static com.game.main.Main.tileEntityManager;

public class UIManager {
    public Stage stage;
    public Skin skin;
    public boolean paused = false;
    public boolean inventoryOpen = false;
    public boolean chestOpen = false;
    public boolean assemblerOpen = false;
    public Table pauseMenu;
    public Table InventoryContainer;
    public Table inventoryUI;
    public Table chestUI;
    public Table assemblerUI;
    public PlayerInventory inventory;
    public Chest currentChest;
    public Assembler currentAssembler;
    static public MouseSlot mouseSlot;
    public float accumulator;
    ShapeRenderer shapeRenderer;
//    public BitmapFont font;
    public BitmapFont tooltipFont;
    private String hoveredItemName = null;
    private Recipe displayingRecipe = null;
    private Vector2 tooltipPosition = new Vector2();

    public UIManager() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // You can load a skin from assets if you have one
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        inventory = new PlayerInventory(5, 4);
        mouseSlot = new MouseSlot();


        createPauseMenu();
        createInventoryUI();
        createChestUI();
        createAssemblerUI();
        createInventoryContainer();
        shapeRenderer = new ShapeRenderer();
        tooltipFont = new BitmapFont();
        tooltipFont.setColor(Color.WHITE);
        tooltipFont.getData().setScale(1.5f);
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(2f);
        accumulator = 0f;
    }

    private void createPauseMenu() {
        pauseMenu = new Table();
        pauseMenu.setFillParent(true);

        Label pauseLabel = new Label(TypeToString.get(TypeToString.Dictionary.Pause), skin);
        TextButton resumeButton = new TextButton(TypeToString.get(TypeToString.Dictionary.Resume), skin);
        TextButton quitButton = new TextButton(TypeToString.get(TypeToString.Dictionary.BackToMenu), skin);

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
                //Gdx.app.exit();
                togglePause();
                MainMenu.create();
            }
        });
    }
    public void createInventoryContainer(){
        InventoryContainer = new Table();
        InventoryContainer.setFillParent(true);
        InventoryContainer.add(inventoryUI);
        stage.addActor(InventoryContainer);
        InventoryContainer.setVisible(false);
    }
    private void createInventoryUI() {
        inventoryUI = new Table();

        Label title = new Label(TypeToString.get(TypeToString.Dictionary.Inventory), skin);
        title.setAlignment(Align.center);
        inventoryUI.add(title).colspan(inventory.getWidth()).padBottom(10).align(Align.center);
        inventoryUI.row();

        // Create grid
        for (int y = 0; y < inventory.getHeight(); y++) {
            for (int x = 0; x < inventory.getWidth(); x++) {
                int index = y * inventory.getWidth() + x;
                InventorySlot slot = new InventorySlot(skin);
                slot.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float px, float py) {
                        inventory.setItem(index, mouseSlot.switchItem(inventory.getItem(index)));
                        refreshInventoryUI();
                    }
                });
                slot.addListener(new InputListener() {
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Item item = inventory.getItem(index);
                        if (item != null) {
                            hoveredItemName = item.getname();
                        }
                    }
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        hoveredItemName = null;
                    }
                    @Override
                    public boolean mouseMoved(InputEvent event, float x, float y) {
                        tooltipPosition.set(Gdx.input.getX(), Gdx.input.getY());
                        return false;
                    }
                });

//                slot.getLabel().setColor(Color.LIGHT_GRAY);
//                slot.setColor(new Color(1, 1, 1, 0.5f));
//                slot.pad(10);

                float slotSize = Gdx.graphics.getHeight() * 0.08f;
                float slotPadding = slotSize * 0.1f;

                inventoryUI.add(slot).size(slotSize, slotSize).pad(slotPadding);
            }
            inventoryUI.row();
        }
    }

    private void createChestUI() {
        //no longer needed but keeping it for future
    }

    private void createAssemblerUI() {
        assemblerUI = new Table();

        Label title = new Label(TypeToString.get(TypeToString.Dictionary.Assembler), skin);
        assemblerUI.add(title).colspan(4).padBottom(10);
        assemblerUI.row();

        // Create grid
        for (int y = 0; y < (recipeManager.length()/4)+1; y++) {
            for (int x = 0; x < 4; x++) {
                int index = y * 4 + x;
                if(recipeManager.length()>index) {
                    SelectorSlot slot = new SelectorSlot(skin);
                    Recipe recipe = recipeManager.getRecipe(index);
                    slot.setItem(recipe.itemsCOut.first());
                    slot.setRecipe(recipe);

                    float slotSize = Gdx.graphics.getHeight() * 0.08f;
                    float slotPadding = slotSize * 0.1f;

                    assemblerUI.add(slot).size(slotSize, slotSize).pad(slotPadding);
                }
            }
            assemblerUI.row();
        }
    }

    public void toggleInventory() {
        inventoryOpen = !inventoryOpen;
        InventoryContainer.setVisible(inventoryOpen);
        refreshInventoryUI();
        if (!inventoryOpen) {
            hoveredItemName = null;
        }
    }

    public void openChest(Chest chest) {
        chestUI = new Table();
        Label title;
        if(chest instanceof Silo){
            title = new Label(TypeToString.get(TypeToString.Dictionary.Silo), skin);
        }else{
            title = new Label(TypeToString.get(TypeToString.Dictionary.Chest), skin);
        }


        chestUI.add(title).colspan(4).padBottom(10);
        chestUI.row();
        currentChest = chest;
        inventoryOpen = true;
        chestOpen = true;
        // Create grid
        int index = 0;
        for (int y = 0; y < (currentChest.getSize()/4)+1; y++) {
            for (int x = 0; x < 4; x++) {
                index = y*4 + x;
                if(index < currentChest.getSize()){
                    InventorySlot slot = new InventorySlot(skin);
                    float slotSize = Gdx.graphics.getHeight() * 0.08f;
                    float slotPadding = slotSize * 0.1f;
                    slot.clearListeners();
                    int finalIndex = index;
                    slot.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float px, float py) {
                            currentChest.setItem(finalIndex, mouseSlot.switchItem(currentChest.getItem(finalIndex)));
                            refreshChestUI();
                        }
                    });
                    slot.addListener(new InputListener() {
                        @Override
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            Item item = currentChest.getItem(finalIndex);
                            if (item != null) {
                                hoveredItemName = item.getname();
                            }
                        }

                        @Override
                        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                            hoveredItemName = null;
                        }

                        @Override
                        public boolean mouseMoved(InputEvent event, float x, float y) {
                            tooltipPosition.set(Gdx.input.getX(), Gdx.input.getY());
                            return false;
                        }
                    });
                    chestUI.add(slot).size(slotSize, slotSize).pad(slotPadding);
                }
            }
            chestUI.row();
        }

        InventoryContainer.add(chestUI);
        InventoryContainer.setVisible(true);
        refreshInventoryUI();
        refreshChestUI();
    }

    public void closeChest() {
        chestOpen = false;
        inventoryOpen = false;
        currentChest = null;
        InventoryContainer.setVisible(false);
        InventoryContainer.removeActor(chestUI);
        chestUI = null;
        hoveredItemName = null;

    }

    public void openAssembler(Assembler assembler) {
        currentAssembler = assembler;
        inventoryOpen = true;
        assemblerOpen = true;
        // Update chest UI with click listeners
        int i = 0;
        for (Actor actor : assemblerUI.getChildren()) {
            if (actor instanceof SelectorSlot && i > 0) { // Skip the title label
                int index = i - 1; // Adjust for title
                Recipe recipe = recipeManager.getRecipe(index);
                    actor.clearListeners();
                    actor.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float px, float py) {
                            currentAssembler.setRecipe(recipe);
                            refreshChestUI();
                            closeAssembler();
                            tileEntityManager.sort();
                        }
                    });
                actor.addListener(new InputListener() {
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Item item = recipe.itemsCOut.first();
                        if (item != null) {
                            hoveredItemName = item.getname();
                            displayingRecipe = ((SelectorSlot) actor).recipe;
                        }
                    }
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        hoveredItemName = null;
                        displayingRecipe = null;
                    }
                    @Override
                    public boolean mouseMoved(InputEvent event, float x, float y) {
                        tooltipPosition.set(Gdx.input.getX(), Gdx.input.getY());
                        return false;
                    }
                });
            }
            i++;
        }
        InventoryContainer.add(assemblerUI);
        InventoryContainer.setVisible(true);
        refreshInventoryUI();
    }

    public void closeAssembler() {
        assemblerOpen = false;
        inventoryOpen = false;
        currentAssembler = null;
        InventoryContainer.setVisible(false);
        InventoryContainer.removeActor(assemblerUI);

    }

    public void refreshInventoryUI() {
        // When items change, refresh button labels
        int i = 0;
        for (Actor actor : inventoryUI.getChildren()) {
            if (actor instanceof InventorySlot && i < inventory.getSize()) {
                Item item = inventory.getItem(i);
                ((InventorySlot) actor).setItem(item);
                i++;
            }
        }
    }

    public void refreshChestUI() {
        if (currentChest == null) return;

        int i = 0;
        int slotIndex = 0;
        for (Actor actor : chestUI.getChildren()) {
            if (actor instanceof InventorySlot) {
                if (slotIndex < currentChest.getSize()) {
                    Item item = currentChest.getItem(slotIndex);
                    ((InventorySlot) actor).setItem(item);
                    slotIndex++;
                }
            }
        }
    }

    public void update(float delta) {

        handleInput();

        if (paused || inventoryOpen) {
            stage.act(delta);
        }
        accumulator += delta;
        if(accumulator>delta){
            accumulator = accumulator%0.1f;
            refreshChestUI();
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
        mouseSlot.render(batch, mousePos.x, mousePos.y, worldManager.direction);
        renderTooltip(batch,mousePos);
//        font.draw(batch,String.valueOf((int)worldManager.direction.x), mousePos.x, mousePos.y);
//        font.draw(batch,String.valueOf((int)worldManager.direction.y), mousePos.x+32f, mousePos.y);
    }
    public void renderTooltip(SpriteBatch batch,Vector2 mousePos){
        if (hoveredItemName != null && inventoryOpen) {
            float scale = Gdx.graphics.getHeight()/1080f;
            float tooltipX = mousePos.x + 15;
            float tooltipY = mousePos.y + 15;
            // Draw background for tooltip
            batch.end();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, 0.8f);
            com.badlogic.gdx.graphics.g2d.GlyphLayout layout = new com.badlogic.gdx.graphics.g2d.GlyphLayout();
            layout.setText(tooltipFont, hoveredItemName);
            float padding = 8;
            shapeRenderer.rect(tooltipX - padding, tooltipY - padding,
                layout.width + padding * 2, layout.height + padding * 2);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            batch.begin();
            tooltipFont.draw(batch, hoveredItemName, tooltipX, tooltipY + layout.height);
            if(assemblerOpen && displayingRecipe != null){
                float slotSize= 80*scale;
                float slotPadding = 20*scale;
                batch.end();
                Gdx.gl.glEnable(GL20.GL_BLEND);
                shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.8f);
                layout.setText(tooltipFont, hoveredItemName);
                shapeRenderer.rect(tooltipX - padding, tooltipY - padding * 2 - slotSize - slotPadding,
                    (slotSize+slotPadding)*displayingRecipe.itemsCIn.size + padding * 4, slotSize + slotPadding + padding);
                shapeRenderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);

                batch.begin();
                for (int i = 0; i<displayingRecipe.itemsCIn.size; i++){
                    batch.draw(displayingRecipe.itemsCIn.get(i).sprite.getTexture(), tooltipX + (i*(slotSize+slotPadding)), tooltipY - slotSize - padding, slotSize,slotSize);
                    tooltipFont.draw(batch, ""+displayingRecipe.itemsCIn.get(i).amount, tooltipX + slotSize + (i*(slotSize+slotPadding)), tooltipY - (slotSize-10));
                }
            }
        }
    }
    private void handleInput() {
        // Toggle pause
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (chestOpen) {
                closeChest();
            } else if(assemblerOpen){
                closeAssembler();
            }else{
                togglePause();
            }
        }

        // Toggle inventory (only when chest is not open)
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            if (chestOpen) {
                closeChest();
            } else if(assemblerOpen){
                closeAssembler();
            }else{
                toggleInventory();
            }
        }
    }

    public void togglePause() {
        paused = !paused;
        pauseMenu.setVisible(paused);
    }

    public boolean isPaused() {
        return paused;
    }

    public void resize(int width, int height) {

        stage.getViewport().update(width, height, true);
        float slotSize = height * 0.08f;
        float slotPadding = slotSize * 0.12f;

        for (Cell<?> cell : inventoryUI.getCells()) {
            cell.size(slotSize, slotSize).pad(slotPadding);
        }

        if(chestUI!=null)for (Cell<?> cell : chestUI.getCells()) {
            cell.size(slotSize, slotSize).pad(slotPadding);
        }

        for (Cell<?> cell : assemblerUI.getCells()) {
            cell.size(slotSize, slotSize).pad(slotPadding);
        }

        float buttonWidth = 1 + width * 0.2f;
        float buttonHeight = 1 + height * 0.08f;
        float buttonPadding = 1 + height * 0.02f;

        for (Cell<?> cell : pauseMenu.getCells()) {
            if (cell.getActor() instanceof TextButton) {
                cell.width(buttonWidth).height(buttonHeight).pad(buttonPadding);
            } else {
                // Keep padding for the label
                cell.pad(buttonPadding);
            }
        }

        float fontScale = 1 + height / 1080f;
        tooltipFont.getData().setScale(1.5f * fontScale);
        if (skin.has("default-font", BitmapFont.class)) {
            BitmapFont defaultFont = skin.getFont("default-font");
            defaultFont.getData().setScale(fontScale);
        }

        inventoryUI.invalidate();
        if(chestUI!=null)chestUI.invalidate();
        assemblerUI.invalidate();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        shapeRenderer.dispose();
        tooltipFont.dispose();
    }

    public void decreaseAndAutoGet() {
        Item tempItem1 = mouseSlot.getItem().clone();
        mouseSlot.decreseAmount();
        if(mouseSlot.getItem()==null){
            mouseSlot.switchItem(inventory.getItem(tempItem1));
            inventory.removeItem(tempItem1);
        }
    }
}
