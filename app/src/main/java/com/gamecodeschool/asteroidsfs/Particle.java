package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;

public class Particle extends SpaceObject {

    Particle(PointF pos, double angle, float velocityMagnitude, float hitRadius) {
        super(pos, angle, velocityMagnitude, hitRadius);
    }


    public void update(long time) {
        // UPDATING NEW POSITION VARIABLE.
        position.x += velMagnitude * Math.cos(angle) * time;
        position.y += velMagnitude * Math.sin(angle) * time;
    }


    public void setPosition(PointF position) {
        super.position.x = position.x;
        super.position.y = position.y;
    }
}
