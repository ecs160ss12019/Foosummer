package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;

public class Shooter extends Opponent {

   // private boolean updatePosition = false;

   // public void updateOppPosition(boolean status){ updatePosition = status;}

    private long laserTimer = 0; // Everytime this is > 2900ms, we shoot.
    private final long SHOOT_INTERVAL = 2900;
    private double addAngle = 200;


    public Shooter (PointF position, double angle, float velocityMag, float hitRadius) {
        super(position, angle, velocityMag, hitRadius);
    }

    @Override
    public void update(long time, Display display){
        super.update(time, display);

        if(updatePosition){
            angle += addAngle;
            updatePosition = false;
        }

    }

    public Laser shoot(long timeIncrement, ObjectFactory fac, PointF playerPos) {

        getX = playerPos.x - position.x;
        getY = playerPos.y - position.y;
        angle = (float)Math.atan2(getY, getX);

        laserTimer += timeIncrement;
        if (laserTimer > SHOOT_INTERVAL) {
            laserTimer = 0;

            return fac.getOpponentLaser(new PointF(position.x, position.y), (float)angle, 1);
        }

        return null;
    }

}
