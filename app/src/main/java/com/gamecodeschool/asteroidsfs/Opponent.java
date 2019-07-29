package com.gamecodeschool.asteroidsfs;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

public class Opponent extends SpaceObject{
    private int health;
    private float getX;
    private float getY;
    private float shootAngle;

    private long laserTimer = 0; // Everytime this is > 2900ms, we shoot.
    private final long SHOOT_INTERVAL = 2900;


    public Opponent(PointF position, double angle, float velocityMag, float hitRadius, int health) {
        super(position, angle, velocityMag, hitRadius);
        this.health = health;
    }

    @Override
    public void update(long time, Display display){
        super.update(time, display);
    }


    public Laser shoot(long timeIncrement, ObjectFactory fac, PointF playerPos) {

        getX = playerPos.x - position.x;
        getY = playerPos.y - position.y;
        shootAngle = (float)Math.atan2(getY, getX);

        laserTimer += timeIncrement;
        if (laserTimer > SHOOT_INTERVAL) {
            laserTimer = 0;

            return fac.getOpponentLaser(new PointF(position.x, position.y), shootAngle, 1);
        }

        return null;
    }

    public float getShootAngle() {return shootAngle;}
}
