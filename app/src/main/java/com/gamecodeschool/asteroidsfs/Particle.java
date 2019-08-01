package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;


/*
 * The Particle class represents a particle moving in space.
 * Thus, it extends SpaceObject to inherit its attributes. It is used to
 * generate a number of particle to simulate an explosion.
 */

public class Particle extends SpaceObject {

    Particle(PointF pos, double angle, float velocityMagnitude, float hitRadius) {
        super(pos, angle, velocityMagnitude, hitRadius);
    }

    // Updates the position of he particle based on time only (not screen size),
    // because it should not wrap around the screen.
    public void update(long time) {
        position.x += velMagnitude * Math.cos(angle) * time;
        position.y += velMagnitude * Math.sin(angle) * time;
    }


    public void setPosition(PointF position) {
        super.position.x = position.x;
        super.position.y = position.y;
    }
}
