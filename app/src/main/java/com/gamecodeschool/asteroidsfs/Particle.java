package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;

public class Particle extends SpaceObject {

    Particle(PointF pos, double angle, float velocityMagnitude, float hitRadius) {
        super(pos, angle, velocityMagnitude, hitRadius);
    }


    // Default position update based on time.
    public void update(long time, final Display screen) {
        // UPDATING NEW POSITION VARIABLE.
        position.x += velMagnitude * Math.cos(angle) * time;
        position.y += velMagnitude * Math.sin(angle) * time;

        if(position.x < 0) {
            position.x = screen.width;
        }
        else if(position.y < 0) {
            position.y = screen.height;
        }
        else if(position.x > screen.width) {
            position.x = 0;
        }
        else if(position.y > screen.height) {
            position.y = 0;
        }
    }


    public void setPosition(PointF position) {
        super.position.x = position.x;
        super.position.y = position.y;
    }
}
