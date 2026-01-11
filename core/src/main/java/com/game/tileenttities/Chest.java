package com.game.tileenttities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.game.UIs.TypeToString;
import com.game.items.Item;
import com.game.items.NewItem;

import java.util.Objects;

public class Chest extends TileEntity implements HasInventory{
    public int size;
    public Array<Item> items;
//    public BitmapFont font;
    public Chest(){
        super();
        sprite = new Sprite(new Texture("tiles/chest.png") );
        this.size = 1;
        this.items = new Array<>(size);
        for (int i = 0; i < size; i++) items.add(null);
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(0.1f);
    }
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Chest);}
    public Chest(int x, int y) {
        this();
        set(x,y);

    }
    public Chest(Chest other){
        super(other);
        this.size = other.size;
        this.items = new Array<>(other.items);
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(0.1f);
    }
    @Override
    public TileEntity clone() {
        return new Chest(this);
    }
    @Override
    public void update(float delta) {}


    @Override
    public Item getAnyItem() {
        for (int i = 0; i < items.size; i++) {
            if(items.get(i) != null){
                Item item = items.get(i).clone();
                items.get(i).amount--;
                if(items.get(i).amount == 0){items.set(i,null);}
                item.amount = 1;
                return item;
            }
        }
        return null;
    }

    @Override
    public boolean addItem(Item item) {
        boolean added = false;
        for (int i = 0; i < items.size; i++) {
            if(items.get(i) != null && Objects.equals(items.get(i).getname(), item.getname())){
                Item temp = items.get(i);
                temp.amount += item.amount;
                if(temp.amount<=temp.StackSize) {
                    items.set(i, temp);
                    added = true;
                    return true;
                }else{
                    item.amount = temp.amount - temp.StackSize;
                    temp.amount = temp.StackSize;
                }
            }
        }
        if(!added) {
            for (int i = 0; i < items.size; i++) {
                if (items.get(i) == null) {
                    items.set(i, item);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
//        for(Item i : items){if(i != null){font.draw(batch,String.valueOf(i.amount), x, y);}}
    }

    public Item getItem(int index) {
        return items.get(index);
    }
    public void setItem(int index, Item item) {
        items.set(index, item);
    }
    public int getSize() {
        return size;
    }
    @Override
    public float getSpriteY(){
        return y+0.6f;
    }

    @Override
    public Array<Item> ItemsOnBreak() {
        if(items!=null) {
            return items;
        }else{
            return null;
        }
    }

}
