package com.gamecodeschool.asteroidsfs;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

public class Opponent extends SpaceObject {
    protected float getX;
    protected float getY;
    protected boolean updatePosition = false;
    private long laserTimer = 0; // Everytime this is > 2900ms, we shoot.
    private final long SHOOT_INTERVAL = 2900;
    private double addAngle = 200;
    private SpaceObjectType oppType;


    public void updateOppPosition(boolean status) {
        updatePosition = status;
    }

    public Opponent(PointF position, double angle, float velocityMag, float hitRadius) {
        super(position, angle, velocityMag, hitRadius);
    }

    Laser attack(long timeIncrement, ObjectFactory fac, PointF playerPos, SpaceObjectType type) {
        getX = playerPos.x - position.x;
        getY = playerPos.y - position.y;
        angle = (float)Math.atan2(getY, getX);
        switch (type) {
            case SHOOTER:
                laserTimer += timeIncrement;
                if (laserTimer > SHOOT_INTERVAL) {
                    laserTimer = 0;
                    angle += addAngle;
                    return fac.getOpponentLaser(new PointF(position.x, position.y), (float)angle, 1);
                }
                break;


            case SUICIDER:
                velMagnitude += velMagnitude/10000;
                break;
        }
        return null;
    }

    public void setOppType(SpaceObjectType oppType) {
        this.oppType = oppType;
    }

    public SpaceObjectType getOppType(){
        return this.oppType;
    }

}

