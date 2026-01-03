package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class MetalOre extends TileEntity implements Mineable, CantPickup, CantPlace{

    public MetalOre(){
        super();
        sprite = new Sprite(new Texture("tiles/MetalOre.png") );
        name = TypeToString.get(TypeToString.Dictionary.MetalOre);
    }
    public MetalOre(int x, int y){
        this();
        set(x,y);

    }
    public MetalOre(MetalOre other){
        super(other);
    }
    @Override
    public TileEntity clone() {
        return new MetalOre(this);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public TileEntity pickupee() {
        return null;
    }
    public float getSpriteY() {return y+1;}
}
