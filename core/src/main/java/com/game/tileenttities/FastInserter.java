package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.game.UIs.TypeToString;

public class FastInserter extends Inserter{
    public FastInserter(){
        super();
        sprite = new Sprite(new Texture("tiles/FastInserter_up.png"));
        speed = 10f;
    }
    public String getname(){return TypeToString.get(TypeToString.Dictionary.FastInserter);}
    public FastInserter(int x, int y) {
        super(x,y);
    }
    public FastInserter(FastInserter other){
        super(other);
    }
    @Override
    public TileEntity clone() {
        return new FastInserter(this);
    }
    public String getClassName(){
        return "FastInserter";
    }
}
