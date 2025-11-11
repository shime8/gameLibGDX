package com.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.game.player.Player;
import com.game.world.WorldController;
import com.game.world.WorldRenderer;

public class Main extends ApplicationAdapter {
    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private Player player;
    @Override
    public void create() {
        float unitscale = 1f / 32f;
        player = new Player(10,8, unitscale);
        worldController = new WorldController();
        worldController.player = player;
        worldController.unitScale = unitscale;
        worldRenderer = new WorldRenderer(worldController);

    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    public void input(){
        //wasd i strzałki zrobione w Player
    }
    public void logic(){
        worldController.update();
    }
    public void draw(){
        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void dispose() {
        worldRenderer.dispose();
    }
}
