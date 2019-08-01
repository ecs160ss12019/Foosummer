package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;
import android.util.Log;

public class Opponent extends SpaceObject {
    protected float getX;
    protected float getY;
    protected boolean updatePosition = false;
    private long laserTimer = 0; // Every time this is > 2900ms, opponent shoots.
    private final long SHOOT_INTERVAL = 2900;
    private double addAngle = 200; // Ideal angle change after each shot
    private double shootAngle;
    private double angleToPlayer;
    private SpaceObjectType oppType;

    public void updateOppPosition(boolean status) {
        updatePosition = status;
    }

    @Override
    public void update(long time, Display display){
        super.update(time, display);

        // After Shooter shoots a laser, it moves away
        if(updatePosition){
            angle += addAngle;
            updatePosition = false;
        }

    }


    public Opponent(PointF position, double angle, float velocityMag, float hitRadius) {
        super(position, angle, velocityMag, hitRadius);
    }

    // Lower level and onward opponents - Shooters, shoot at the player
    // Higher level opponents - Suiciders, have no lasers and
    // are on a suicide mission to destroy player
    Laser attack(long timeIncrement, ObjectFactory fac, PointF playerPos) {
        getX = playerPos.x - position.x;
        getY = playerPos.y - position.y;
        angleToPlayer = (float)Math.atan2(getY, getX);

        switch (this.oppType) {
            case SHOOTER:
                laserTimer += timeIncrement;
                if (laserTimer > SHOOT_INTERVAL) {
                    laserTimer = 0;
                    // Update the laser's shoot angle, not this Shooter's actual angle position
                    shootAngle = angleToPlayer;
                    return fac.getOpponentLaser(new PointF(position.x, position.y), (float)shootAngle, 1);
                }
                break;
            case SUICIDER:
                // Always moves in the direction of the player
                angle = angleToPlayer;
                velMagnitude += velMagnitude/10000;
                break;
        }
        return null;
    }

    // Shooter or Suicider?
    public void setOppType(SpaceObjectType oppType) {
        this.oppType = oppType;
    }

    public SpaceObjectType getOppType(){
        return this.oppType;
    }

}

