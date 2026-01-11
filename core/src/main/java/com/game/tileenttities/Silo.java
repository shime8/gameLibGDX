package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.game.UIs.TypeToString;

public class Silo extends Chest{
    public Silo(){
        super();
        sprite = new Sprite(new Texture("tiles/silo.png") );
        this.size = 4;
        this.items = new Array<>(size);
        for (int i = 0; i < size; i++) items.add(null);
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(0.1f);
    }
    public String getname(){return TypeToString.get(TypeToString.Dictionary.Silo);}
    public Silo(int x, int y) {
        this();
        set(x,y);

    }
    public Silo(Silo other){
        super(other);
        this.size = other.size;
        this.items = new Array<>(other.items);
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(0.1f);
    }
    @Override
    public TileEntity clone() {
        return new Silo(this);
    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, 1, 4);
    }
}
