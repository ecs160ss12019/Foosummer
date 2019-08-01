package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;
import java.util.ArrayList;
import java.util.Random;

/*
 * The Asteroid class represents an asteroid moving in space.
 * Thus, it extends SpaceObject to inherit all moving functionality.
 * The asteroid class extra functionality such as increasing its speed,
 * used for asteroids that were disintegrated, and collision detection.
 */

public class Asteroid extends SpaceObject {
    private int size; // The Size multiplier of asteroid
    private final int DEFAULT_SPLIT_ANGLE = 30; // DEFAULT MAX SPLIT POSSIBLE ANGLE

    // Using this constructor does not generate a RectF in the SpaceObject.
    Asteroid(double angle, PointF pos, float velocityMagnitude, float asteroidSize, int size) {
        super(pos, angle, velocityMagnitude, asteroidSize);
        this.size = size;
    }

    // This constructor is used to make a copy of the asteroid w/ a different size and direction.
    // It is used when the player hits an asteroid and it disintegrates into two smaller copies
    // of itself.
    Asteroid(SpaceObject copy, int newSize, int theta) {
        super(copy);
        size = newSize;
        angle += theta * Math.PI / 180; // Theta is in degree, converts to radian for unit consistency
        hitRadius = hitRadius * newSize / (newSize + 1); // Reduce hit box for size reduction
    }

    void increaseVelocity() {
        // increase the speed by 50%
        velMagnitude = velMagnitude * 1.5f;
    }

    /*
        Asteroid returns ArrayList of Asteroid upon collision. Smaller asteroids are created with
        randomized angle with limited range. +- 30 degree angles.
    */
    public ArrayList<Asteroid> collisionAction() {
        ArrayList<Asteroid> temp = new ArrayList<Asteroid>();
        if(size > 1) {
            Random r = new Random();
            // Creates asteroid of smaller size that splits 
            //at randomized angle of at max 30 different from original.
            temp.add(new Asteroid(this, size - 1, r.nextInt(DEFAULT_SPLIT_ANGLE)));
            temp.add(new Asteroid(this, size - 1, -(r.nextInt(DEFAULT_SPLIT_ANGLE))));
            temp.get(0).increaseVelocity();
            temp.get(1).increaseVelocity();
        }

        return temp;
    }

    public int getSize() { return size; }
}