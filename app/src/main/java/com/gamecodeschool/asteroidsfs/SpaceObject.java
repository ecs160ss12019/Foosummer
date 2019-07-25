package com.gamecodeschool.asteroidsfs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.PointF;
import android.util.Log;

/* 
 * The SpaceObject is a moving object in space. We need position to track this object on screen.
 * The position is center of object with float (x,y) coordinate. We would prefer
 *       int but float works better
 *      with some of the other android API.
 * We have overall speed magnitude (doesn't have direction)
 *      component x and y can be generated by multiplying with angle(radian) to 
 *      get (x,y) component of the vector.
 * The instead of using RectF as suggested by text, we shall use radial sum vs distance 
 *      of object comparison.
 */
public class SpaceObject {
    // These must be protected since Spaceships (player / enemy) can modify their movements.
    protected PointF position;
    protected float velMagnitude;
    protected float hitRadius;
    protected double angle; // in radians!

    public SpaceObject(PointF pos, double angle, float velocityMagnitude, float hitRadius) {
        // BEGIN LEGACY CODE. Needs to be phased out for later stage.
        position = pos;
        velMagnitude = velocityMagnitude;
        this.hitRadius = hitRadius;
        this.angle = angle;
    }

    public SpaceObject(SpaceObject cpy) {
        position = cpy.position;
        velMagnitude = cpy.velMagnitude;
        hitRadius = cpy.hitRadius;
        angle = cpy.angle;
    }

    public float getVelocityX() {
        return (float)(velMagnitude * Math.cos(angle));
    }
    public float getVelocityY() {
        return (float)(velMagnitude * Math.sin(angle));
    }
    
    public PointF getPosition() {return position;}
    public float getHitRadius() {return hitRadius;}

    // Following two getters returns coordinate for top left corner for bitmap. When used together.
    public float getBitmapX() {
        return position.x - hitRadius;
    }
    public float getBitmapY() {
        return position.y - hitRadius;
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
}
