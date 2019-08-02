package com.gamecodeschool.asteroidsfs;

import static com.gamecodeschool.asteroidsfs.GameConfig.SHOOT_INTERVAL;

import android.graphics.PointF;

/* Initial opponents - Shooters, shoot at the player
 * Higher level opponents - Suiciders, have no lasers and
 * go on a suicide mission to destroy Player
 */

public class Opponent extends SpaceObject {
    // Gets the X and Y component between Opponent and Player
    protected float getX;
    protected float getY;

    private long laserTimer = 0; // Every time this is > SHOOT_INTERVAL, Opponent shoots.
    private double shootAngle;
    private double angleToPlayer;
    private boolean updatePosition = false;
    private SpaceObjectType oppType;

    // Called from AsteroidsGame after each Shooter's shot
    // to initiate Shooter's movement pattern
    public void updateOppPosition(boolean status) {
        updatePosition = status;
    }

    @Override
    public void update(long time, Display display){
        super.update(time, display);

        // After Shooter shoots a laser, it moves away
        if(updatePosition){
            angle += 200; // Ideal angle change after each shot
            updatePosition = false;
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
                    // Update the laser's shoot angle
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

    // Is the opponent of type Shooter or Suicider?
    public void setOppType(SpaceObjectType oppType) {
        this.oppType = oppType;
    }

    public SpaceObjectType getOppType(){
        return this.oppType;
    }

}

