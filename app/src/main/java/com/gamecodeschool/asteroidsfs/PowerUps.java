package com.gamecodeschool.asteroidsfs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.Random;

enum PowerUp {
    FIRE_RATE,
    SHIELD;
}

public class PowerUps extends SpaceObject{
//    private RectF mRect;
//    float width, height, xVelocity, yVelocity;
//    int hitsLeft;
    private float size;
    private PointF pos;
    private PowerUp type;

    public PowerUps(PointF pos, float size){
        super(pos, 0, 0, size);
        this.size = size;
        this.type = getRandPowerUp();
//        this.pos = pos;

    }

    @Override
    public void update(long fps, Display display){
        super.update(fps, display);

        // do something to deal with player's position?
    }

    private PowerUp getRandPowerUp(){
        Random rand = new Random();
        return PowerUp.values()[rand.nextInt(PowerUp.values().length)];
    }

    public PowerUp getPowerUpType(){
        return this.type;
    }

}
