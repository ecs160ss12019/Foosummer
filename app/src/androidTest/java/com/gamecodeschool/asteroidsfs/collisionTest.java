package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;
import android.graphics.RectF;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import static org.junit.Assert.assertTrue;

/*
    We leave the collision handling and which objects to compare to the algorithm.
    If the two object's rectf collides, it should return true. If not, should return false.
 */

//FIXME have to redo collision test due to object changes.
public class collisionTest {
    // asteroids.add(new Asteroid(asteroidXPosition,
    // asteroidYPosition,
    // asteroidWidth,
    // asteroidHeight,
    // asteroidXVelocity,
    // asteroidYVelocity));

    Display screen;

    collisionTest(Display screen) {
        this.screen = screen;
    }


    @Test
    public void testCollision() {
        //Asteroid(angle, pos, velocityMagnitude, hitRadius, size)
        Asteroid A = new Asteroid(0, new PointF(50,50), 1, 1, 3); // This asteroid moves toward B
        Asteroid B = new Asteroid(0, new PointF(0,0), 0, 1, 3); // This asteroid stands still

        for(int i = 0; i < 3; i++) {
            A.update(1, screen);
        }

        // After 3 incremented position update. A and B should collide.
        assertTrue(A.collisionCheck(A, B));
    }
}