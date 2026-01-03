package com.game.tileenttities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public abstract class TileEntity {
    public String name;
    public int x, y;
    public Rectangle bounds;
    public Sprite sprite;
    public BitmapFont font;
    public TileEntity(){

    }
    public TileEntity(int x, int y) {
        this.x = x;
        this.y = y;
        this.bounds = getBounds();

    }
    public TileEntity(TileEntity other) {
        this.x = other.x;
        this.y = other.y;
        this.bounds = new Rectangle(other.getBounds());
        this.sprite = new Sprite(other.sprite);
        this.name = other.name;
    }
    public abstract TileEntity clone();
    public abstract void update(float delta);
    public void placingUpdate(){};
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
    public void shapeRender(ShapeRenderer shapeR){}

    public void set(int x, int y){
        this.x = x;
        this.y = y;
        this.bounds = getBounds();
        if (sprite != null) {
            sprite.setSize(bounds.width, bounds.height);
            sprite.setOriginCenter();
            sprite.setPosition(bounds.x, bounds.y);
        }
    }

    public Array<Vector2> checkWhenPlacing(){
        Array<Vector2> tiles = new Array<>();
        tiles.add(new Vector2(this.x,this.y));
        return tiles;

    }

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, 1, 1);
    }
    public Rectangle getCollisionBox() {return new Rectangle(this.x, this.y, 1, 1);}
    public float getSpriteY(){
        return y;
    }
    public void placeOtherTiles(){

    }
    public void removeOtherTiles(){

    }


}
