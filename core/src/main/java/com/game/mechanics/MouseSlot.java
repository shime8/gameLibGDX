package com.game.mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.items.Item;
import com.game.tileenttities.Directional;

import java.util.Objects;

public class MouseSlot {
    Item item;
    BitmapFont font;

    public MouseSlot(){
        item = null;
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(2f);
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


    public void render(SpriteBatch batch, float x,float y, Vector2 direction){
        if(item!=null) {
//            batch.disableBlending();
//            batch.draw(item.sprite, x, y, 32, 32);
//            batch.enableBlending();
            float angle = MathUtils.atan2(direction.y, direction.x) * MathUtils.radiansToDegrees;
            item.sprite.setBounds(x, y, 32, 32);
            item.sprite.setOrigin(16, 16);
            if(item.Tile != null && item.Tile instanceof Directional) {
                item.sprite.setRotation(angle);
            }
            item.sprite.draw(batch);
            font.draw(batch,String.valueOf(item.amount), x+32, y+4);
        }
    }

}
