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
        final private int defaultOpponentHealth = 3;
        final private Zone zone1; // Area in between 25% to 100% of screen
        final private Zone zone2; // Area in between 50% to 100% of screen

        final private float TIME = 20; // time it should take to cross screen in seconds
        final private float LASER_TIME = 4; // Default seconds it takes for laser to cross screen width.
        final private float OPPONENT_TIME = 30;
        final private float MS_PER_S = 1000; // 1000 milliseconds per 1 second
        final private int MAX_ASTEROID_SIZE_LEVEL = 3;
        static final public int DIVISION_FACTOR = 25;
        final private int LASER_SIZE_FACTOR = 2;
        final private float LASER_VEL_FACTOR = 3;
        final private double zone1MinMultiplier = 0.25;
        final private double zone2MinMultiplier = 0.50;

        private float currentVelocityMagnitude;
        private Random rand = new Random();
        private int opponentHealth = 3;
        private float opponentVelocity;
        SpaceObjectType objType;

        private PointF defaultShipSize;
        static final public int shipScaleFactor = 25;


        // When this object is first made for the game engine. The screen 
        ObjectFactory(Display display) {
                asteroidSizeFactor = display.width / DIVISION_FACTOR;
                screen = display;
                defaultVelocity = ((float)display.width) / TIME / MS_PER_S; // speed factor calculation

                zone1 = new Zone((int)(display.width * zone1MinMultiplier), (int)(display.height * zone1MinMultiplier),
                                display.width, display.height);
                zone2 = new Zone((int)(display.width * zone2MinMultiplier), (int)(display.height * zone2MinMultiplier),
                                display.width, display.height);
                

                currentVelocityMagnitude = defaultVelocity;
                defaultLaserVelocity = ((float)display.width) / LASER_TIME / MS_PER_S;
                opponentVelocity = ((float)display.width) / OPPONENT_TIME / MS_PER_S;

                defaultShipSize = new PointF(screen.width / shipScaleFactor,
                        screen.height / shipScaleFactor);

//                defaultPowerUpSize =
        }


        // Switch Object getter. Chose enum switch, supposedly this is the fastest.
        public SpaceObject getSpaceObject(SpaceObjectType type) {
                double angle = rand.nextInt(maxAngle) * Math.PI / 180;

                switch(type) {
                        case PLAYER:
                                return new Player(new PointF(screen.width/2, screen.height/2),
                                                defaultShipSize.x/2);
                        case ASTEROID:
                                PointF point = new PointF(
                                        (float)(rand.nextInt(zone2.xDiff()) + zone2.minX),
                                        (float)(rand.nextInt(zone2.yDiff()) + zone2.minY));
                                int sizeMultiplier = rand.nextInt(MAX_ASTEROID_SIZE_LEVEL) + 1;

                                return new Asteroid(angle,
                                                point,
                                                currentVelocityMagnitude,
                                                sizeMultiplier * asteroidSizeFactor / 2, sizeMultiplier);
                        // case LASER:
                        case OPPONENT:

                                return new Opponent(new PointF(rand.nextInt(zone2.xDiff()) + zone2.minX,
                                                rand.nextInt(zone2.yDiff() + zone2.minY) + zone2.minY),
                                                rand.nextInt(maxAngle) * Math.PI/180,
                                                opponentVelocity, 100,
                                                opponentHealth);

                        case POWERUP:
                            return new PowerUps(new PointF(rand.nextInt(zone2.xDiff()) + zone2.minX,
                                    rand.nextInt(zone2.yDiff() + zone2.minY) + zone2.minY),
                                    50);

                }
                //FIXME have to run some sort of Null point exception.
                return null;
        }

//        public PowerUps getSpaceObject(SpaceObjectType type, int hits) {
//                double angle = rand.nextInt(maxAngle) * Math.PI / 180;
//
//                return new PowerUps(rand.nextInt(zone1.xDiff()) + zone1.minY,
//                        rand.nextInt(zone1.yDiff()) + zone1.minY,
//                        screen.width/DIVISION_FACTOR, screen.height/DIVISION_FACTOR, hits,
//                        (float)(currentVelocityMagnitude * Math.cos(angle)),
//                        (float)(currentVelocityMagnitude * Math.sin(angle)));
//        }

        public Laser getPlayerLaser(PointF playerPos, double playerAngle, int dmg) {
                SpaceObject temp = new SpaceObject(playerPos, playerAngle, defaultLaserVelocity, 
                                                screen.width / DIVISION_FACTOR / LASER_SIZE_FACTOR / 2);
                return new Laser(temp, dmg);
        }

        //want to shoot in direction of player
        public Laser getOpponentLaser(PointF oppPos, double playerAngle, int dmg) {
                SpaceObject temp = new SpaceObject(oppPos, playerAngle, defaultLaserVelocity,
                        screen.width / DIVISION_FACTOR / LASER_SIZE_FACTOR);
                return new Laser(temp, dmg);
        }


        // ------------------- Begins Variable Controls ------------------------
        public void addSpeed(float speecIncrement) {
        currentVelocityMagnitude += speecIncrement;
}

        public void reset() {
                currentVelocityMagnitude = defaultVelocity;
                opponentHealth = defaultOpponentHealth;
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
