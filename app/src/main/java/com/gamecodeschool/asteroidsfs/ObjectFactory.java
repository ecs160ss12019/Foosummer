package com.gamecodeschool.asteroidsfs;

import static com.gamecodeschool.asteroidsfs.GameConfig.MAX_ANGLE;
import static com.gamecodeschool.asteroidsfs.GameConfig.TIME;
import static com.gamecodeschool.asteroidsfs.GameConfig.LASER_TIME;
import static com.gamecodeschool.asteroidsfs.GameConfig.OPPONENT_TIME;
import static com.gamecodeschool.asteroidsfs.GameConfig.SUICIDER_TIME;
import static com.gamecodeschool.asteroidsfs.GameConfig.MS_PER_S;
import static com.gamecodeschool.asteroidsfs.GameConfig.MAX_ASTEROID_SIZE_LEVEL;
import static com.gamecodeschool.asteroidsfs.GameConfig.DIVISION_FACTOR;
import static com.gamecodeschool.asteroidsfs.GameConfig.LASER_SIZE_FACTOR;
import static com.gamecodeschool.asteroidsfs.GameConfig.ZONE_1_MIN_MULTIPLIER;
import static com.gamecodeschool.asteroidsfs.GameConfig.ZONE_2_MIN_MULTIPLIER;
import static com.gamecodeschool.asteroidsfs.GameConfig.SHIP_SCALE_FACTOR;
import static com.gamecodeschool.asteroidsfs.GameConfig.OPPONENT_HIT_RADIUS;
import static com.gamecodeschool.asteroidsfs.GameConfig.POWERUP_HIT_RADIUS;

import android.graphics.PointF;

import java.util.Random;

/*
 * ObjectFactory stores constants and some minimal calculations necessary to create objects.
 * This object also uses Zone object to handle randomized spawn spots for certain objects such as
 *      Asteroid and Opponents. This helps us have randomized spawn and to keep the spawned objects
 *      away from the player in initial level.
 * This object provides a centralized place to call for object creation.
 */
public class ObjectFactory {
        final private int asteroidSizeFactor;
        final private Display screen;
        final private float defaultVelocity; // Default is 10 seconds to cross width of screen.
        final private float defaultLaserVelocity;
        final private Zone zone1; // Area in between 25% to 100% of screen
        final private Zone zone2; // Area in between 50% to 100% of screen

        private float currentVelocityMagnitude;
        private Random rand = new Random();
        private float opponentVelocity;
        private float suiciderVelocity;

        private PointF defaultShipSize;


        // Upon creation, initialize parameters necessary to scale size of objects and other related
        // constants for the game.
        ObjectFactory(Display display) {
                asteroidSizeFactor = display.width / DIVISION_FACTOR;
                screen = display;
                defaultVelocity = ((float)display.width) / TIME / MS_PER_S; // speed factor calculation

                // Spawnable zones for non-player classes.
                zone1 = new Zone(display, ZONE_1_MIN_MULTIPLIER);
                zone2 = new Zone(display, ZONE_2_MIN_MULTIPLIER);
                

                currentVelocityMagnitude = defaultVelocity;
                defaultLaserVelocity = ((float)display.width) / LASER_TIME / MS_PER_S;
                opponentVelocity = ((float)display.width) / OPPONENT_TIME / MS_PER_S;
                suiciderVelocity = ((float)display.width) / SUICIDER_TIME / MS_PER_S;

                defaultShipSize = new PointF(screen.width / SHIP_SCALE_FACTOR,
                        screen.height / SHIP_SCALE_FACTOR);

        }


        // Switch based object getter. For Objects that only uses parameters known to the Factory.
        public SpaceObject getSpaceObject(SpaceObjectType type) {
                double angle = rand.nextInt(MAX_ANGLE) * Math.PI / 180;

                switch(type) {
                        case ASTEROID:
                                PointF point = new PointF(zone2.randomX(), zone2.randomY());
                                int sizeMultiplier = rand.nextInt(MAX_ASTEROID_SIZE_LEVEL) + 1;
                                return new Asteroid(angle,
                                                point,
                                                currentVelocityMagnitude,
                                                sizeMultiplier * asteroidSizeFactor / 2, sizeMultiplier);

                        case SHOOTER:
                                return new Opponent(new PointF(zone2.randomX(), zone2.randomY()),
                                                rand.nextInt(MAX_ANGLE) * Math.PI/180,
                                                opponentVelocity, OPPONENT_HIT_RADIUS);


                        case POWERUP:
                                return new PowerUps(new PointF(zone1.randomX(), zone1.randomY()), POWERUP_HIT_RADIUS);


                        case SUICIDER:
                                return new Suicider(new PointF(zone2.randomX(), zone2.randomY()),
                                        rand.nextInt(MAX_ANGLE) * Math.PI/180,
                                        suiciderVelocity, OPPONENT_HIT_RADIUS);
                }
                return null;
        }


        // Below are methods for generating specific objects with different parameters.
        public PowerUps getPowerUp(PointF pos){
                return new PowerUps(pos, 50);
        }

        public Laser getPlayerLaser(PointF playerPos, double playerAngle, int dmg) {
                SpaceObject temp = new SpaceObject(playerPos, playerAngle, defaultLaserVelocity, 
                                                screen.width / DIVISION_FACTOR / LASER_SIZE_FACTOR / 2);
                return new Laser(temp, dmg);
        }

        //want to shoot in direction of player
        public Laser getOpponentLaser(PointF oppPos, float playerAngle, int dmg) {
                SpaceObject temp = new SpaceObject(oppPos, playerAngle, defaultLaserVelocity,
                        screen.width / DIVISION_FACTOR / LASER_SIZE_FACTOR / 2);
                return new Laser(temp, dmg);
        }

        public float getPlayerLength() {
                return defaultShipSize.x / 2;
        }


        // ------------------- Begins Variable Controls ------------------------

        public void reset() {
                currentVelocityMagnitude = defaultVelocity;
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

        Zone(Display display, float multiplier) {
                minX = (int)(display.width * multiplier);
                minY = (int)(display.height *multiplier);
                maxX = display.width;
                maxY = display.height;
        }

        public int xDiff() {
                return maxX - minX;
        }

        // Returns horizontal position that is certain percentage of screen away from the center.
        public int randomX() {
                Random r = new Random();
                int result = r.nextInt(xDiff());

                return (result <= xDiff() / 2) ? result : (result + minX);
        }
        
        public int yDiff() {
                return maxY - minY;
        }

        // Returns vertical position that is certain percentage of screen away from center.
        public int randomY() {
                Random r = new Random();
                int result = r.nextInt(yDiff());

                return (result <= yDiff() / 2) ? result : (result + minY);
        }
}
