package com.game.tileenttities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.game.UIs.TypeToString;

public class AssemblerT2 extends Assembler{
    public AssemblerT2(){
        super();
        speed = 1f;
        sprite = new Sprite(new Texture("tiles/FastAssembler.png") );
    }
    public AssemblerT2(Assembler other){
        super(other);
    }
    public String getname(){return TypeToString.get(TypeToString.Dictionary.AssemblerT2);}
    public TileEntity clone() {
        return new AssemblerT2(this);
    }
}
