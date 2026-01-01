package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.UIs.TypeToString;
import com.game.items.Item;
import com.game.items.ItemEntity;
import com.game.items.ItemEntityManager;
import com.game.world.worldManager;

import static com.game.main.Main.*;

public class Inserter extends TileEntity implements Directional{
    public Vector2 direction;
    float speed;
    Item item;
    ItemEntity itemEntity;
    float accumulator = 0f;

    public Inserter(){
        super();
        sprite = new Sprite(new Texture("tiles/inserter_up.png"));
        name = TypeToString.get(TypeToString.Dictionary.Inserter);
        speed = 2f;
    }
    public Inserter(int x, int y) {
        this();
        set(x,y);
    }
    public Inserter(Inserter other){
        super(other);
        setDirection(other.direction);
        this.speed = other.speed;
        this.font = other.font;
        this.item = null;
        this.itemEntity = null;
        accumulator = 0f;
    }
    @Override
    public TileEntity clone() {
        return new Inserter(this);
    }
    @Override
    public void update(float delta) {
        accumulator-=delta;
        if(accumulator<=0f){
            accumulator = 0f;
            swing();
        }

        if(this.itemEntity != null) {
            this.itemEntity.update();
        }
    }
    public void swing(){
        TileEntity TEfront = tileEntityManager.getEntityAt(x+(int)(direction.x),y+(int)(direction.y));
        TileEntity TEback = tileEntityManager.getEntityAt(x-(int)(direction.x),y-(int)(direction.y));
        checkFront(TEfront);
        checkBack(TEback);
        swap();

    }
    public void checkBack(TileEntity TEback){
        //////////////////////////// check back
        if(this.item==null && this.itemEntity==null){
            if (TEback == null || TEback instanceof Belt) {
                // check if tile has item entities
                Array<ItemEntity> IElist = itemEntityManager.getItemEntityList(new GridPoint2(x - (int) (direction.x), y - (int) (direction.y)));
                if (IElist != null && !IElist.isEmpty()) {
                    // get itemEntity to inserter storage
                    this.itemEntity = IElist.first();
                    IElist.removeValue(this.itemEntity, true);
                    accumulator = 1f/speed;
                }

            } else if (TEback instanceof HasInventory) {
                // get 1 of first item
                Item item = ((HasInventory) TEback).getAnyItem();
                if (item != null) {
                    // get item to inserter storage
                    this.item = item;
                    accumulator = 1f/speed;
                }
            }
        }
    }
    public void swap(){
        if( this.item != null ^ this.itemEntity != null) {
            //........................ swap
            if (this.itemEntity == null) {
                this.itemEntity = new ItemEntity(this.item, x + 0.5f, y);
            }
            this.itemEntity = new ItemEntity(this.itemEntity.item, x + 0.5f, y);
        }
    }
    public void checkFront(TileEntity TEfront){
        if( this.item != null || this.itemEntity != null){
            //-----------------------check front
            if(TEfront == null || TEfront instanceof Belt){
                // check if tile has item entities
                Array<ItemEntity> IElist = itemEntityManager.getItemEntityList(new GridPoint2(x+(int)(direction.x),y+(int)(direction.y)));
                if(IElist == null ||  IElist.isEmpty() /*|| IElist.size<1*/){
                    // get itemEntity from inserter storage to Tile
                    ItemEntity IEmoved = new ItemEntity(this.itemEntity.item,x+direction.x+0.5f,y+direction.y+0.5f);
                    itemEntityManager.addItemEntity(IEmoved);
                    this.item = null;
                    this.itemEntity = null;
                    accumulator = 1f/speed;
                }

            }else if(TEfront instanceof HasInventory){
                // get item from inserter storage to inventory
                this.itemEntity.item.amount = 1;
                boolean didiadd = ((HasInventory) TEfront).addItem(new Item(this.itemEntity.item));
                if(didiadd) {
                    this.item = null;
                    this.itemEntity = null;
                    accumulator = 1f/speed;
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
//        sprite.setSize(3f, 1f);
//        sprite.setOriginCenter();
//        sprite.setPosition(getBounds().x-Math.abs(direction.y), getBounds().y+Math.abs(direction.y));
        sprite.setRotation(getAngle(direction));
    }
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if(this.itemEntity != null) {
            this.itemEntity.render(batch);
        }
    }
//    @Override
//    public Rectangle getBounds() {
////        if(direction==null){
////            return new Rectangle(this.x, this.y, 1, 1);
////        }else {
//            return new Rectangle(this.x - (int)Math.abs(direction.x), this.y - (int)Math.abs(direction.y), 1f + (int)(Math.abs(direction.x) *2) ,1f + (int)(Math.abs(direction.y)*2));
////        }
//    }
}
