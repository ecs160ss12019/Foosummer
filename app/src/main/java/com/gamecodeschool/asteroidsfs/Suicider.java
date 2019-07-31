package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;

public class Suicider extends SpaceObject{
    private int health;
    private float getX;
    private float getY;


    public Suicider(PointF position, double angle, float velocityMag, float hitRadius, int health) {
        super(position, angle, velocityMag, hitRadius);
        this.health = health;
    }
/*
    @Override
    public void update(long time, Display display){
        super.update(time, display);

        if(updatePosition){
            angle = shootAngle+addAngle;
            updatePosition = false;
        }

    }*/

    public void launchSuicideShip(PointF playerPos) {

        getX = playerPos.x - position.x;
        getY = playerPos.y - position.y;
        angle = (float)Math.atan2(getY, getX);
        velMagnitude += velMagnitude/10000;

    }
}
