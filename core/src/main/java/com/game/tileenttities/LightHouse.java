package com.game.tileenttities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.game.UIs.TypeToString;

public class LightHouse extends TileEntity implements CantPickup{
    Sprite spriteLight;
    Array<Texture> buildAnim;
    float accumulator;
    public LightHouse(){
        super();
        sprite = new Sprite(new Texture("tiles/LightHouseBuildingT1.png"));
        buildAnim = new Array<>();
        buildAnim.add(new Texture("tiles/LightHouseBuildingT2.png"));
        buildAnim.add(new Texture("tiles/LightHouseBuildingT3.png"));
        buildAnim.add(new Texture("tiles/LightHousen.png"));
        accumulator = 0;
        spriteLight = new Sprite(new Texture("tiles/Light.png"));

//        font = new BitmapFont();
//        font.setColor(Color.BLACK);
//        font.getData().setScale(0.1f);
    }
    public String getname(){return TypeToString.get(TypeToString.Dictionary.LightHouse);}
    public LightHouse(int x, int y) {
        this();
        set(x,y);
        spriteLight.setSize(10,3);
        spriteLight.setOriginCenter();
        spriteLight.setOrigin(9.95f,1.5f);
        spriteLight.setOriginBasedPosition(x+0.5f, y+6.5f);
    }
    public LightHouse(LightHouse other){
        super(other);
        buildAnim = other.buildAnim;
        accumulator = 0;
        spriteLight = other.spriteLight;
    }
    @Override
    public TileEntity clone() {
        return new LightHouse(this);
    }

    @Override
    public void update(float delta) {
        if(!buildAnim.isEmpty()){
            accumulator += delta;
            if (accumulator > 3f) {
                accumulator = accumulator % 3f;
                sprite.setTexture(buildAnim.first());
                buildAnim.removeValue(buildAnim.first(), true);
            }
        }else{
            accumulator += delta;
//            while(accumulator > 0.02f){
//                accumulator = accumulator - 0.02f;
//                float overallScale = 3;
//                spriteLight.rotate(-(((float)Math.pow((2*(1.5f-(spriteLight.getScaleX()/overallScale))),2))/2));
//                float angle = 3.14f*(90+spriteLight.getRotation())/180f;
//                float scale1 = ((0.1f + (0.9f*(float) (Math.pow(Math.sin(angle),4)+Math.pow(Math.sin(angle),2))/2))*overallScale);
//                float scale2 = ((0.1f + (0.9f*(float) (Math.pow(Math.sin(1.57f+angle),4)+Math.pow(Math.sin(1.57f+angle),2))/2))*overallScale);
//                float scale = (scale1 + scale2*0.3f)/2f;
//                spriteLight.setScale(scale,1);
//            }
            while (accumulator > 0.02f) {
                accumulator -= 0.02f;
                float overallScale = 3f;
                spriteLight.rotate(-(((float)Math.pow((2 * (1.5f - (spriteLight.getScaleX() / overallScale))), 2)) / 2f));
                float angle = (float)Math.toRadians(spriteLight.getRotation());
                float a = 1.1f * overallScale;
                float b = 0.3f * overallScale;
                float cos = (float)Math.cos(angle);
                float sin = (float)Math.sin(angle);
                float radius = (a * b) / (float)Math.sqrt((b * cos) * (b * cos) + (a * sin) * (a * sin));
                spriteLight.setScale(radius, 1f);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if(buildAnim.isEmpty()){
            spriteLight.draw(batch);
        }
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
