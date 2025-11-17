package com.game.tileenttities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.items.ItemEntity;
import com.game.items.ItemEntityManager;

import java.util.ArrayList;

public class Belt extends TileEntity{
    ItemEntityManager itemEntityManager;
    Vector2 direction;
    float speed;
    public Belt(){
        texture = new Texture("tiles/belt.png");
        name = "Belt";
        speed = 1f;
    }
    public Belt(int x, int y) {
        this();
        set(x,y);
    }
    public Belt(Belt other){
        super(other);
        this.direction = other.direction;
        this.speed = other.speed;
    }
    @Override
    public void update(float delta) {
        for (ItemEntity iteme : itemEntityManager.getItemEntityList(x,y)){
            iteme.worldX += direction.x * speed * delta;
            iteme.worldY += direction.y * speed * delta;
        }
    }

}
