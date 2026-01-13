package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class FastBelt extends Belt{
    public FastBelt(){
        super();
        speed = 6f;
        sprite = new Sprite(new Texture("tiles/fastBelt.png") );
    }
    public FastBelt(FastBelt other){
        super(other);
    }
    public String getname(){return TypeToString.get(TypeToString.Dictionary.FastBelt);}
    @Override
    public TileEntity clone() {
        return new FastBelt(this);
    }
}
