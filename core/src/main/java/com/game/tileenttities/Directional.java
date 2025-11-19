package com.game.tileenttities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public interface Directional {
    Vector2 getDirection();
    void setDirection(Vector2 direction);
    default float getAngle(Vector2 direction){
        return MathUtils.atan2(direction.y, direction.x) * MathUtils.radiansToDegrees;
    }
}
