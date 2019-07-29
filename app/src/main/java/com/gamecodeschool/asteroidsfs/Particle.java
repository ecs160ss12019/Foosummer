package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;

public class Particle extends SpaceObject {

    Particle(PointF pos, double angle, float velocityMagnitude, float hitRadius) {
        super(pos, angle, velocityMagnitude, hitRadius);
    }

//    Override
//    public void update(float fps) {
//        // Move the particle
//        mPosition.x += mVelocity.x;
//        mPosition.y += mVelocity.y;
//    }
//
//    public void setPosition(PointF position) {
//        mPosition.x = position.x;
//        mPosition.y = position.y;
//    }
//
//    public PointF getPosition() {
//        return mPosition;
//    }
}
