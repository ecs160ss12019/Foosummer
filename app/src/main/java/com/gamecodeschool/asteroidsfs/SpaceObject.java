package com.gamecodeschool.asteroidsfs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.PointF;

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

    public SpaceObject(PointF pos, double angle, float velocityMagnitude, float hitCircleSize) {
        // BEGIN LEGACY CODE. Needs to be phased out for later stage.
        position = pos;
        velMagnitude = velocityMagnitude;
        hitRadius = hitCircleSize;
        this.angle = angle;
    }

    public PointF getPosition() {
        return position;
    }
    public float getVelocityX() {
        return (float)(velMagnitude * Math.cos(angle));
    }
    public float getVelocityY() {
        return (float)(velMagnitude * Math.sin(angle));
    }
    // Commented out since this might not be
//    public void setVelocityX(float velocityX) {
//        this.velocityX = velocityX;
//    }
//    public void setVelocityY(float velocityY) {
//        this.velocityY = velocityY;
//    }
    public float getHitRadius() {
        return hitRadius;
    }

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

    /* An example of how a collision detection for this base object would work.
     * This is done through radial sum of two objects and comparing that to the distance of two objects.
     */
    // static public boolean detectCollision(SpaceObject A, SpaceObject B) {
    //     int distance = (int)Math.sqrt(Math.pow((A.getPosition().x - B.getPosition().x), 2) 
    //                     + Math.pow((A.getPosition().y - B.getPosition().y), 2));

    //     // The objects overlap if their distance apart is less than sum of their radius.
    //     return distance <= (int)(A.getHitRadius() + B.getHitRadius());
    // }
}
