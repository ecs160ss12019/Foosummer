package com.gamecodeschool.asteroidsfs;

import org.junit.Test;

import java.util.Random;

import static junit.framework.TestCase.assertTrue;


public class ZoneTester {
    Zone zone1 = new Zone(1000, 1000, 2000, 2000);

    // We run about 100 times and test if the result is within range.
    // should be 0-500 1500-2000 for both x and y.
    @Test
    public void testZoneRange() {
        for(int i = 0; i < 100; i++) {
            int tempX = zone1.randomX();
            int tempY = zone1.randomY();
            // test for each loops.
            assertTrue((tempX >= 0 && tempX <= 500) || (tempX >= 1500 && tempX <= 2000));
            assertTrue((tempY >= 0 && tempY <= 500) || (tempY >= 1500 && tempY <= 2000));
        }
    }

}
