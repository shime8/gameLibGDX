package com.game.tileenttities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.game.items.ItemEntity;
import com.game.items.ItemEntityManager;

import static com.game.main.Main.itemEntityManager;

public class Belt extends TileEntity implements Directional{
    public Vector2 direction;
    float speed;
    public Belt(){
        super();
        sprite = new Sprite(new Texture("tiles/belt.png"));
        name = "Belt";
        speed = 1f;
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(0.1f);
    }
    public Belt(int x, int y) {
        this();
        set(x,y);
        sprite.setSize(bounds.width, bounds.height);
        sprite.setOriginCenter();
        sprite.setPosition(x, y);

    }
    public Belt(Belt other){
        super(other);
        this.direction = other.direction;
        this.speed = other.speed;
        this.font = other.font;
    }
    @Override
    public TileEntity clone() {
        return new Belt(this);
    }
    @Override
    public void update(float delta) {
        if(itemEntityManager.getItemEntityList(x, y)!=null){
            for (ItemEntity iteme : itemEntityManager.getItemEntityList(x, y)) {
                int tempX = (int)Math.floor(iteme.worldX);
                int tempY = (int)Math.floor(iteme.worldY);

                iteme.worldX += direction.x * speed * delta;
                iteme.worldY += direction.y * speed * delta;
                iteme.update();
                if(tempX != (int)Math.floor(iteme.worldX) || tempY != (int)Math.floor(iteme.worldY)){
                    itemEntityManager.updateItemEntity(iteme, new GridPoint2(tempX, tempY));
                }
            }
        }
    }

    @Override
    public Vector2 getDirection() {
        return this.direction;
    }

    @Override
    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.setRotation(getAngle(direction));
        super.render(batch);
    }
}
