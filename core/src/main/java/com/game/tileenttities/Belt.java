package com.game.tileenttities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.game.UIs.TypeToString;
import com.game.items.ItemEntity;
import com.game.items.ItemEntityManager;

import static com.game.main.Main.itemEntityManager;
import static com.game.main.Main.tileEntityManager;

public class Belt extends TileEntity implements Directional{
    int nextBeltItemAmount = 1;
    public Vector2 direction;
    float speed;
    float accumulator = 0f;
    TileEntity ForwardTE;
    boolean moveForward = false;
    public Belt(){
        super();
        sprite = new Sprite(new Texture("tiles/belt.png"));
        speed = 3f;
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(0.1f);
    }
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Belt);}
    public Belt(int x, int y) {
        this();
        set(x,y);

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
        accumulator+=delta;
        if(accumulator>1.0f){
            updateFrontTileEntityCheck();
            accumulator = 0f;
        }
        if(itemEntityManager.getItemEntityList(x, y)!=null ){
            boolean front = itemEntityManager.getItemEntityList(x+direction.x, y+direction.y) == null
                || itemEntityManager.getItemEntityList(x+direction.x, y+direction.y).size<nextBeltItemAmount;
            for (ItemEntity iteme : itemEntityManager.getItemEntityList(x, y)) {

                if(iteme.direction == null || !iteme.lessThanHalf() ){
                    iteme.direction = direction;
                }
                if((moveForward && front) || iteme.lessThanHalf()){
                    int tempX = (int) Math.floor(iteme.worldX);
                    int tempY = (int) Math.floor(iteme.worldY);

                    iteme.worldX += iteme.direction.x * speed * delta;
                    iteme.worldY += iteme.direction.y * speed * delta;
                    iteme.update();
                    if (tempX != (int) Math.floor(iteme.worldX) || tempY != (int) Math.floor(iteme.worldY)) {
                        itemEntityManager.updateItemEntity(iteme, new GridPoint2(tempX, tempY));
                    }
                }
            }
        }
    }
    @Override
    public void placingUpdate() {updateFrontTileEntityCheck();}
    @Override
    public Vector2 getDirection() {
        return this.direction;
    }

    @Override
    public void setDirection(Vector2 direction) {
        this.direction = direction;
        sprite.setRotation(getAngle(direction));
    }

    @Override
    public void render(SpriteBatch batch) {

        super.render(batch);
    }

    public void updateFrontTileEntityCheck(){
        ForwardTE =  tileEntityManager.getEntityAt((int)(x+direction.x), (int)(y+direction.y));
        if(ForwardTE != null && ForwardTE instanceof Belt){
            moveForward = true;
        }else{
            moveForward = false;
        }
    }
    public float getSpriteY() {return y+1;}

    public String getClassName(){
        return "Belt";
    }
}
