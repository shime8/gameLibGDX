package com.game.mechanics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.game.items.Item;

import java.util.Objects;

public class MouseSlot {
    Item item;
    public MouseSlot(){
        item = null;
    }
    public Item getItem(){
        return item;
    }
    public Item switchItem(Item item){
        if(item==null){
            if(this.item==null){
                return null;
            }else{
                Item temp = new Item(this.item);
                this.item = null;
                return temp;
            }
        }else{
            if(this.item==null){
                this.item = item;
                return null;
            }else{
                Item temp = new Item(this.item);
                if(Objects.equals(this.item.name, item.name)) {
                    temp.amount += item.amount;
                    this.item = null;
                }else{
                    this.item = item;
                }
                return temp;
            }
        }
    }
    public void decreseAmount(){
        decreseAmount(1);
    }
    public void decreseAmount(int i){
        item.amount -= 1;
        if(item.amount==0){item=null;}
    }
    public void render(SpriteBatch batch, float x,float y){
        if(item!=null)batch.draw(item.texture, x, y);
    }
}
