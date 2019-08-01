package com.gamecodeschool.asteroidsfs;

import static com.gamecodeschool.asteroidsfs.GameConfig.SHOOT_INTERVAL;

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
    private double addAngle = 200;
    private double shootAngle;
    private double angleToPlayer;
    private SpaceObjectType oppType;

    public void updateOppPosition(boolean status) {
        updatePosition = status;
    }

    @Override
    public void update(long time, Display display){
        super.update(time, display);

        if(updatePosition){
            Log.e("opponent", "angle is " + angle);

           // this.angle += addAngle;

            angle = angle + 200;
            updatePosition = false;

            Log.e("opponent", " updated angle is " + angle);
        }

    }


    public Opponent(PointF position, double angle, float velocityMag, float hitRadius) {
        super(position, angle, velocityMag, hitRadius);
    }

    Laser attack(long timeIncrement, ObjectFactory fac, PointF playerPos) {
        getX = playerPos.x - position.x;
        getY = playerPos.y - position.y;
        angleToPlayer = (float)Math.atan2(getY, getX);

        switch (this.oppType) {
            case SHOOTER:
                laserTimer += timeIncrement;
                if (laserTimer > SHOOT_INTERVAL) {
                    laserTimer = 0;
                    shootAngle = angleToPlayer;
                    return fac.getOpponentLaser(new PointF(position.x, position.y), (float)shootAngle, 1);
                }
                break;


            case SUICIDER:
                angle = angleToPlayer;
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

