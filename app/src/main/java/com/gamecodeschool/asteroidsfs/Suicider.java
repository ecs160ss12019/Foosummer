package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;

public class Suicider extends Opponent {


    public Suicider(PointF position, double angle, float velocityMag, float hitRadius) {
        super(position, angle, velocityMag, hitRadius);
    }

    public void launchSuicideShip(PointF playerPos) {

        getX = playerPos.x - position.x;
        getY = playerPos.y - position.y;
        angle = (float)Math.atan2(getY, getX);
        velMagnitude += velMagnitude/10000;

    }


}
