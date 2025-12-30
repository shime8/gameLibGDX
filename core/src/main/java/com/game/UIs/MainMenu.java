package com.game.UIs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenu {
    public static Stage stage;
    static Texture background;
    static Texture buttonTexture;
    static Texture buttonPressedTexture;
    static BitmapFont font;

    static volatile boolean waiting = true;
    static volatile String selectedOption = null;


    public static void create(){
        create(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    public static void create(float width, float height) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load textures
        background = new Texture("ui/menu_bg.png");
        buttonTexture = new Texture("ui/button_unpressed.png");
        buttonPressedTexture = new Texture("ui/button_pressed.png");

        // Create font
        font = new BitmapFont(); // Default font, or load custom: new BitmapFont(Gdx.files.internal("myfont.fnt"))
        font.getData().setScale(2f); // Make text bigger
        font.setColor(Color.WHITE);

        // Add background
        Image bg = new Image(background);
        bg.setSize(width, height);
        stage.addActor(bg);

        // Create Play button with text
        TextButton playBtn = createTextButton(TypeToString.get(TypeToString.Dictionary.Play), buttonTexture, buttonPressedTexture);
        playBtn.setPosition(
            Gdx.graphics.getWidth() / 2f - playBtn.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f + 100
        );
        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedOption = "PLAY";
                waiting = false;
            }
        });
        stage.addActor(playBtn);

        // Create Options button with text
        TextButton optionsBtn = createTextButton(TypeToString.get(TypeToString.Dictionary.Settings), buttonTexture, buttonPressedTexture);
        optionsBtn.setPosition(
            Gdx.graphics.getWidth() / 2f - optionsBtn.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f
        );
        optionsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedOption = "OPTIONS";
                waiting = false;
            }
        });
        stage.addActor(optionsBtn);

        // Create Exit button with text
        TextButton exitBtn = createTextButton(TypeToString.get(TypeToString.Dictionary.Quit), buttonTexture, buttonPressedTexture);
        exitBtn.setPosition(
            Gdx.graphics.getWidth() / 2f - exitBtn.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - 100
        );
        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedOption = "EXIT";
                waiting = false;
            }
        });
        stage.addActor(exitBtn);

        waiting = true;
        selectedOption = null;
    }

    private static TextButton createTextButton(String text, Texture normal, Texture pressed) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(normal);
        style.down = new TextureRegionDrawable(pressed);
        style.font = font;
        style.fontColor = Color.WHITE;
        style.downFontColor = Color.LIGHT_GRAY; // Color when pressed

        return new TextButton(text, style);
    }

    public static void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public static String wait_for_selection() {
        while (waiting) {
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return selectedOption;
    }

    public static void dispose() {
        if (stage != null) stage.dispose();
        if (background != null) background.dispose();
        if (buttonTexture != null) buttonTexture.dispose();
        if (buttonPressedTexture != null) buttonPressedTexture.dispose();
        if (font != null) font.dispose();
    }

    public static boolean isWaiting() {
        return waiting;
    }

    public static void resize(int width, int height) {
            dispose();
            create(width,height);
    }
}
