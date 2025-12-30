package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.UIs.TypeToString;

public class ClayOre extends TileEntity implements Mineable, CantPickup, CantPlace{

    public ClayOre(){
        super();
        sprite = new Sprite(new Texture("tiles/Clay.png") );
        name = TypeToString.get(TypeToString.Dictionary.Clay);
    }
    public ClayOre(int x, int y){
        this();
        set(x,y);

    }
    public ClayOre(ClayOre other){
        super(other);
    }
    @Override
    public TileEntity clone() {
        return new ClayOre(this);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public TileEntity pickupee() {
        return null;
    }
}
