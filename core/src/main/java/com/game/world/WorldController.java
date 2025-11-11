package com.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.game.player.Player;

public class WorldController {
    public float unitScale;
    public Player player;
    public TiledMap map;
    public float mapWidth, mapHeight; // in world units
    TiledMapTileLayer collisionLayer;

    public WorldController() {
        map = new TmxMapLoader().load("maps/basicmap.tmx");
        collisionLayer = (TiledMapTileLayer) map.getLayers().get("Warstwa Kafelków 1");

        MapProperties props = map.getProperties();
        mapWidth = props.get("width", Integer.class);
        mapHeight = props.get("height", Integer.class);
        //int tileWidth = props.get("tilewidth", Integer.class);
        //int tileHeight = props.get("tileheight", Integer.class);


    }

    public void update() {
        float dt = Gdx.graphics.getDeltaTime();
        player.update(dt,mapWidth,mapHeight,collisionLayer);


    }


}
