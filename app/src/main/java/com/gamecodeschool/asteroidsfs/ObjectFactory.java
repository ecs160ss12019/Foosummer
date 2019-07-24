package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;

import java.util.Random;

/*
 * This object takes care of spawning new objects.
 * The main use will be on every level. All objects are spawned at beginning of level. (for now)
 */
public class ObjectFactory {
        final private int asteroidSizeFactor;
        final private int maxAngle = 360;
        final private Display screen;
        final private float defaultVelocity; // Default is 10 seconds to cross width of screen.
        final private float defaultLaserVelocity;
        final private Zone zone1; // Area in between 25% to 100% of screen
        final private Zone zone2; // Area in between 50% to 100% of screen

        final private float TIME = 20; // time it should take to cross screen in seconds
        final private float LASER_TIME = 4; // Default seconds it takes for laser to cross screen width.
        final private float MS_PER_S = 1000; // 1000 milliseconds per 1 second
        final private int MAX_ASTEROID_SIZE_LEVEL = 3;
        static final public int DIVISION_FACTOR = 25;
        final private int LASER_SIZE_FACTOR = 2;
        final private float LASER_VEL_FACTOR = 3;
        final private double zone1MinMultiplier = 0.25;
        final private double zone2MinMultiplier = 0.50;

        private float currentVelocityMagnitutde;
        private Random rand = new Random();
        SpaceObjectType objType;


        // When this object is first made for the game engine. The screen 
        ObjectFactory(Display display) {
                asteroidSizeFactor = display.width / DIVISION_FACTOR;
                screen = display;
                defaultVelocity = ((float)display.width) / TIME / MS_PER_S; // speed factor calculation

                zone1 = new Zone((int)(display.width * zone1MinMultiplier), (int)(display.height * zone1MinMultiplier),
                                display.width, display.height);
                zone2 = new Zone((int)(display.width * zone2MinMultiplier), (int)(display.height * zone2MinMultiplier),
                                display.width, display.height);
                

                currentVelocityMagnitutde = defaultVelocity;
                defaultLaserVelocity = ((float)display.width) / LASER_TIME / MS_PER_S;
        }


        // Switch Object getter. Chose enum switch, supposedly this is the fastest.
        public SpaceObject getSpaceObject(SpaceObjectType type) {
                double angle = rand.nextInt(maxAngle) * Math.PI / 180;

                switch(type) {
                        case ASTEROID:
                                PointF point = new PointF(
                                        (float)(rand.nextInt(zone2.xDiff()) + zone2.minX),
                                        (float)(rand.nextInt(zone2.yDiff()) + zone2.minY));
                                int sizeMultiplier = rand.nextInt(MAX_ASTEROID_SIZE_LEVEL) + 1;

                                return new Asteroid(angle,
                                        point,
                                        currentVelocityMagnitutde,
                                        sizeMultiplier * asteroidSizeFactor, sizeMultiplier);
                        // case LASER:
                        case OPPONENT:

                                return new Opponent(new PointF(screen.width, screen.height), rand.nextInt(zone2.xDiff()) + zone2.minX,
                                        rand.nextInt(zone2.yDiff() + zone2.minY) + zone2.minY,
                                        (float)(currentVelocityMagnitutde*Math.cos(angle)), (float)(currentVelocityMagnitutde*Math.sin(angle)));

                //        case POWERUP:
                //            return new PowerUps(rand.nextInt(zone1.xDiff()) + zone1.minY,
                //                        rand.nextInt(zone1.yDiff()) + zone1.minY,
                //                    screen.width/DIVISION_FACTOR, screen.height/DIVISION_FACTOR, 3,
                //                    (float)(currentVelocityMagnitutde * Math.cos(angle)),
                //                    (float)(currentVelocityMagnitutde * Math.sin(angle)));

                }
                //FIXME have to run some sort of Null point exception.
                return null;
        }

        public PowerUps getSpaceObject(SpaceObjectType type, int hits) {
                double angle = rand.nextInt(maxAngle) * Math.PI / 180;

                return new PowerUps(rand.nextInt(zone1.xDiff()) + zone1.minY, 
                        rand.nextInt(zone1.yDiff()) + zone1.minY,
                        screen.width/DIVISION_FACTOR, screen.height/DIVISION_FACTOR, hits,
                        (float)(currentVelocityMagnitutde * Math.cos(angle)),
                        (float)(currentVelocityMagnitutde * Math.sin(angle)));
        }

        public Laser getPlayerLaser(PointF playerPos, double playerAngle, int dmg) {
                SpaceObject temp = new SpaceObject(playerPos, playerAngle, defaultLaserVelocity, 
                                                screen.width / DIVISION_FACTOR / LASER_SIZE_FACTOR);
                return new Laser(temp, dmg);
        }


        // ------------------- Begins Variable Controls ------------------------
        public void addSpeed(float speecIncrement) {
        currentVelocityMagnitutde += speecIncrement;
}

        public void reset() {
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
