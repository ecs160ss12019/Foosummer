package com.gamecodeschool.asteroidsfs;

import android.graphics.RectF;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import static org.junit.Assert.assertTrue;

/*
    We leave the collision handling and which objects to compare to the algorithm.
    If the two object's rectf collides, it should return true. If not, should return false.
 */

public class collisionTest {
    // asteroids.add(new Asteroid(asteroidXPosition,
    // asteroidYPosition,
    // asteroidWidth,
    // asteroidHeight,
    // asteroidXVelocity,
    // asteroidYVelocity));
    @Test
    public void testCollision() {
        Asteroid A = new Asteroid(0, 0, 10, 10, 10, 0); // This asteroid moves toward B
        Asteroid B = new Asteroid(30, 0, 10, 10, 0, 0); // This asteroid stands still

        for(int i = 0; i < 3; i++) {
            A.update(1, 1000, 1000);
        }

        // After 3 incremented position update. A and B should collide.
        assertTrue(RectF.intersects(A.getRect(), B.getRect()));
    }
}