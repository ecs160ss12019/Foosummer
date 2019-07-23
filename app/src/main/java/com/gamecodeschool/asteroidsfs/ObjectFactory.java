package com.gamecodeschool.asteroidsfs;

import java.util.Random;

/*
 * This object takes care of spawning new objects.
 * The main use will be on every level. All objects are spawned at beginning of level. (for now)
 */
public class ObjectFactory {
        final private int maxAngle = 360;
        final private Display screen;
        final private float defaultVelocity; // Default is 10 seconds to cross width of screen.
        final private Zone zone1; // Area in between 25% to 100% of screen
        final private Zone zone2; // Area in between 50% to 100% of screen
        final private int objectSizeFactor;

        final private float TIME = 10; // time it should take to cross screen in seconds
        final private float MS_PER_S = 1000; // 1000 milliseconds per 1 second
        final private int MAX_ASTEROID_SIZE_LEVEL = 3;
        final private int DIVISION_FACTOR = 25;
        final private double zone1MinMultiplier = 0.25;
        final private double zone2MinMultiplier = 0.50;

        private float currentVelocityMagnitutde;
        private Random rand = new Random();
        SpaceObjectType objType;


        // When this object is first made for the game engine. The screen 
        ObjectFactory(Display display) {
                screen = display;       
                defaultVelocity = ((float)display.width) / TIME / MS_PER_S; // speed factor calculation

                zone1 = new Zone((int)(display.width * zone1MinMultiplier), (int)(display.height * zone1MinMultiplier),
                                display.width, display.height);
                zone2 = new Zone((int)(display.width * zone2MinMultiplier), (int)(display.height * zone2MinMultiplier),
                                display.width, display.height);
                
                objectSizeFactor = display.width / DIVISION_FACTOR;

                currentVelocityMagnitutde = defaultVelocity;
        }


        // Switch Object getter. Chose enum switch, supposedly this is the fastest.
        public SpaceObject getSpaceObject(SpaceObjectType type) {
                switch(type) {
                        // case PLAYER:
                        case ASTEROID:
                                
                                return new Asteroid(rand.nextInt(maxAngle),
                                        rand.nextInt(zone2.xDiff()) + zone2.minX,
                                        rand.nextInt(zone2.yDiff() + zone2.minY) + zone2.minY,
                                        currentVelocityMagnitutde,
                                        rand.nextInt(MAX_ASTEROID_SIZE_LEVEL) * objectSizeFactor);
                        // case LASER:
                        // case OPPONENT:
                        // case POWERUP:

                }
                //FIXME have to run some sort of Null point exception.
                return null;
        }


        // ------------------- Begins Variable Controls ------------------------
        public void addSpeed(float speecIncrement) {
        currentVelocityMagnitutde += speecIncrement;
}

        public void resetSpeed() {
                currentVelocityMagnitutde = defaultVelocity;
        }

        // ------------------- Ends Variable Controls --------------------------
}

// Class used for spawn range.
class Zone {
        // defines minimal rectangle areas where an object may spawn.
        int minX;
        int minY;
        int maxX;
        int maxY;

        Zone(int minWidth, int minHeight, int maxWidth, int maxHeight) {
            minX = minWidth;
            minY = minHeight;
            maxX = maxWidth;
            maxY = maxHeight;
        }

        public int xDiff() {
                return maxX - minX;
        }

        public int yDiff() {
                return maxY - minY;
        }
}
