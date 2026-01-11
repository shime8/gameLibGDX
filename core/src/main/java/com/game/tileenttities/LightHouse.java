package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.game.UIs.TypeToString;

public class LightHouse extends TileEntity implements CantPickup{

    public LightHouse(){
        super();
        sprite = new Sprite(new Texture("tiles/LightHousen.png"));
        name = TypeToString.get(TypeToString.Dictionary.LightHouse);
//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(0.1f);
    }
    public String getname(){return TypeToString.get(TypeToString.Dictionary.LightHouse);}
    public LightHouse(int x, int y) {
        this();
        set(x,y);

    }
    public LightHouse(LightHouse other){
        super(other);
    }
    @Override
    public TileEntity clone() {
        return new LightHouse(this);
    }

    @Override
    public void update(float delta) {

    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle(this.x-1, this.y-1, 3, 10);
    }
    @Override
    public Rectangle getHighlightBounds() {return new Rectangle(this.x-1, this.y-1, 3, 3);}

    @Override
    public float getSpriteY() {
        return y-1.01f;
    }

    @Override
    public TileEntity pickupee() {
        return null;
    }
}
