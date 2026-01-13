package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.game.UIs.TypeToString;

public class LongInserter extends Inserter{
    public LongInserter(){
        super();
        sprite = new Sprite(new Texture("tiles/LongInserter_up.png"));
        speed = 2f;
    }
    public String getname(){return TypeToString.get(TypeToString.Dictionary.LongInserter);}
    public LongInserter(int x, int y) {
        this();
        set(x,y);
        sprite.setSize(bounds.width, bounds.height);
        sprite.setOriginCenter();
        sprite.setPosition(x, y);

    }
    public LongInserter(LongInserter other){
        super(other);
        this.direction = other.direction;
        this.speed = other.speed;
        this.font = other.font;
        this.item = null;
        this.itemEntity = null;
        accumulator = 0f;
    }
    @Override
    public TileEntity clone() {
        return new LongInserter(this);
    }

    @Override
    public void setDirection(Vector2 direction) {
        this.direction = new Vector2(direction.x*2f,direction.y*2f);
        sprite.setRotation(getAngle(this.direction));
    }
}
