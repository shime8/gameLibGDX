package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.UIs.TypeToString;
import com.game.items.Item;
import com.game.items.ItemEntity;
import com.game.items.ItemEntityManager;
import com.game.items.NewItem;
import com.game.world.worldManager;

import static com.game.main.Main.*;

public class Inserter extends TileEntity implements Directional, HasInventory {
    public Vector2 direction;
    float speed;
    Item item;
    ItemEntity itemEntity;
    float accumulator = 0f;
    float swingAccumulator = 0f;
    TileEntity TEfront;
    TileEntity TEback;
    Sprite spriteHandle;
    float handDistance;
    public Inserter() {
        super();
        sprite = new Sprite(new Texture("tiles/NewInserter.png"));
        spriteHandle = new Sprite(new Texture("tiles/NewInserterHandle.png"));
        speed = 5f;
        handDistance = 1f;
    }

    public String getname() {
        return TypeToString.get(TypeToString.Dictionary.Inserter);
    }

    public Inserter(int x, int y) {
        this();
        set(x, y);
    }

    @Override
    public void set(int x, int y) {
        super.set(x, y);
            if (spriteHandle != null) {
                spriteHandle.setSize(bounds.width, bounds.height);
                spriteHandle.setOriginCenter();
                spriteHandle.setPosition(bounds.x, bounds.y);
            }

    }

    public Inserter(Inserter other) {
        super(other);
        this.spriteHandle = new Sprite(other.spriteHandle);
        setDirection(other.direction);
        this.speed = other.speed;
        this.font = other.font;
        this.item = null;
        this.itemEntity = null;
        this.TEback = null;
        this.TEfront = null;
        accumulator = 0f;
        swingAccumulator = 0f;
        handDistance = other.handDistance;
    }

    @Override
    public TileEntity clone() {
        return new Inserter(this);
    }

    @Override
    public void update(float delta) {
        accumulator -= delta;
        if (accumulator <= 0f) {
            accumulator = 0f;
            swingAccumulator += delta;
            updateTE();
            if (swingAccumulator > 0.5f / speed) {
                checkFront(TEfront);
                swingAccumulator = 0;
            } else {
                checkBack(TEback);
                swap();
            }

        }

        if (this.itemEntity != null) {
            this.itemEntity.update();
        }
    }

    public void swing() {
        updateTE();
        checkFront(TEfront);
        checkBack(TEback);
        swap();

    }

    public void updateTE() {
        TEfront = tileEntityManager.getEntityAt(x + (int) (direction.x), y + (int) (direction.y));
        TEback = tileEntityManager.getEntityAt(x - (int) (direction.x), y - (int) (direction.y));
    }

    public void checkBack(TileEntity TEback) {
        //////////////////////////// check back
        if (this.item == null && this.itemEntity == null) {
            if (TEback == null || TEback instanceof Belt) {
                // check if tile has item entities
                Array<ItemEntity> IElist = itemEntityManager.getItemEntityList(new GridPoint2(x - (int) (direction.x), y - (int) (direction.y)));
                if (IElist != null && !IElist.isEmpty()) {
                    // get itemEntity to inserter storage
                    this.itemEntity = IElist.first();
                    IElist.removeValue(this.itemEntity, true);
                    accumulator = 1f / speed;
                }

            } else if (TEback instanceof HasInventory) {
                // get 1 of first item
                Item item = ((HasInventory) TEback).getAnyItem();
                if (item != null) {
                    // get item to inserter storage
                    this.item = item;
                    accumulator = 1f / speed;
                }
            }
        }
    }

    public void swap() {
        if (this.item != null ^ this.itemEntity != null) {
            //........................ swap
            if (this.itemEntity == null) {
                this.itemEntity = new ItemEntity(this.item, x + 0.5f, y+0.5f);
            }
            this.itemEntity = new ItemEntity(this.itemEntity.item, x + 0.5f, y+0.5f);
        }
    }

    public void checkFront(TileEntity TEfront) {
        if (this.item != null || this.itemEntity != null) {
            //-----------------------check front
            if (TEfront == null || TEfront instanceof Belt) {
                // check if tile has item entities
                Array<ItemEntity> IElist = itemEntityManager.getItemEntityList(new GridPoint2(x + (int) (direction.x), y + (int) (direction.y)));
                if (IElist == null || IElist.isEmpty() /*|| IElist.size<1*/) {
                    // get itemEntity from inserter storage to Tile
                    ItemEntity IEmoved = new ItemEntity(this.itemEntity.item, x + direction.x + 0.5f, y + direction.y + 0.5f);
                    itemEntityManager.addItemEntity(IEmoved);
                    this.item = null;
                    this.itemEntity = null;
                    accumulator = 1f / speed;
                }

            } else if (TEfront instanceof HasInventory) {
                // get item from inserter storage to inventory
                this.itemEntity.item.amount = 1;
                boolean didiadd = ((HasInventory) TEfront).addItem(this.itemEntity.item.clone());
                if (didiadd) {
                    this.item = null;
                    this.itemEntity = null;
                    accumulator = 1f / speed;
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
        float angle = sprite.getRotation() + (swingPercent()*180f);
        spriteHandle.setRotation(angle);
        spriteHandle.draw(batch);

        if (this.itemEntity != null) {
            if(swingPercent()!=0){
                float radians = angle * MathUtils.degreesToRadians;

                float offsetX = MathUtils.cos(radians) * handDistance;
                float offsetY = MathUtils.sin(radians) * handDistance;

                float handleX = spriteHandle.getX() + spriteHandle.getOriginX();
                float handleY = spriteHandle.getY() + spriteHandle.getOriginY();

                itemEntity.worldX = handleX + offsetX + 0.3f - itemEntity.bounds.width / 2f;
                itemEntity.worldY = handleY + offsetY + 0.3f - itemEntity.bounds.height / 2f;
            }else{
                itemEntity.worldX = x + 0.5f;
                itemEntity.worldY = y + 0.5f;
            }

            itemEntity.render(batch);
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

    @Override
    public Item getAnyItem() {
        return null;
    }

    @Override
    public boolean addItem(Item item) {
        return false;
    }

    @Override
    public Array<Item> ItemsOnBreak() {
        Array<Item> items = new Array<>();
        if(itemEntity!=null)items.add(itemEntity.item);
        return items;
    }
    public String getClassName(){
        return "Inserter";
    }
    public float swingPercent() {
        float helper1, helper2;
        if(itemEntity!= null){
            helper1 = 1; helper2=0;
        }else{
            helper1=-1;helper2=1;
        }
        return Math.min(1f, Math.max(0f, accumulator * speed * helper1 + helper2));
    }

}
