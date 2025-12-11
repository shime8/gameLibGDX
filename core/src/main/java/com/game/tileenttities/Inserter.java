package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.items.Item;
import com.game.items.ItemEntity;
import com.game.items.ItemEntityManager;

import static com.game.main.Main.itemEntityManager;
import static com.game.main.Main.tileEntityManager;

public class Inserter extends TileEntity implements Directional{
    public Vector2 direction;
    float speed;
    Item item;
    ItemEntity itemEntity;
    float accumulator = 0f;
    public Inserter(){
        super();
        sprite = new Sprite(new Texture("tiles/inserter_up.png"));
        name = "Inserter";
        speed = 2f;
    }
    public Inserter(int x, int y) {
        this();
        set(x,y);
        sprite.setSize(bounds.width, bounds.height);
        sprite.setOriginCenter();
        sprite.setPosition(x, y);

    }
    public Inserter(Inserter other){
        super(other);
        this.direction = other.direction;
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
        accumulator+=delta;
        if(accumulator>1f/speed){
            swing();
            accumulator = 0f;
        }

        if(this.itemEntity != null) {
            this.itemEntity.update();
        }
    }
    public void swing(){
        TileEntity TEfront = tileEntityManager.getEntityAt(x+(int)(direction.x),y+(int)(direction.y));
        TileEntity TEback = tileEntityManager.getEntityAt(x-(int)(direction.x),y-(int)(direction.y));

        //////////////////////////// check back
        if(this.item==null && this.itemEntity==null){
            if (TEback == null || TEback instanceof Belt) {
                // check if tile has item entities
                Array<ItemEntity> IElist = itemEntityManager.getItemEntityList(new GridPoint2(x - (int) (direction.x), y - (int) (direction.y)));
                if (IElist != null && !IElist.isEmpty()) {
                    // get itemEntity to inserter storage
                    this.itemEntity = IElist.first();
                    IElist.removeValue(this.itemEntity, true);
                }

            } else if (TEback instanceof HasInventory) {
                // get 1 of first item
                Item item = ((HasInventory) TEback).getAnyItem();
                if (item != null) {
                    // get item to inserter storage
                    this.item = item;
                }
            }
        }
        if( this.item != null || this.itemEntity != null){
            //........................ swap
            if(this.itemEntity == null){
                this.itemEntity = new ItemEntity(this.item,x+0.5f,y);
            }
            this.itemEntity = new ItemEntity(this.itemEntity.item,x+0.5f,y);

            //-----------------------check front
            if(TEfront == null || TEfront instanceof Belt){
                // check if tile has item entities
                Array<ItemEntity> IElist = itemEntityManager.getItemEntityList(new GridPoint2(x+(int)(direction.x),y+(int)(direction.y)));
                if(IElist == null ||  IElist.isEmpty() || IElist.size<1){
                    // get itemEntity from inserter storage to Tile
                    ItemEntity IEmoved = new ItemEntity(this.itemEntity.item,x+direction.x+0.5f,y+direction.y+0.5f);
                    itemEntityManager.addItemEntity(IEmoved);
                    this.item = null;
                    this.itemEntity = null;
                }

            }else if(TEfront instanceof HasInventory){
                    // get item from inserter storage to inventory
                    this.itemEntity.item.amount = 1;
                    boolean didiadd = ((HasInventory) TEfront).addItem(new Item(this.itemEntity.item));
                    if(didiadd) {
                        this.item = null;
                        this.itemEntity = null;
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
        if(this.itemEntity != null) {
            this.itemEntity.render(batch);
        }
        super.render(batch);
    }
}
