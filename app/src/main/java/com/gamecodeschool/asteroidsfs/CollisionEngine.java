package com.gamecodeschool.asteroidsfs;

import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

public class CollisionEngine {
/*
    // check for collision between objects and act accordingly
    void collisionControl(RectF objectA, RectF objectB){
        int i = 0;


        // check for collision between player and asteroids
        Asteroid myAsteroid = asteroids.get(i);
        boolean asteroidPlayerHit = myCollision.detectCollision(myShip.getRect(), myAsteroid.getRect());
        i++;
        if(i > 4){i = 0;}

        Log.d("ADebugTag", "collision detected: " + asteroidPlayerHit);

        // asteroid hit player's ship - decrement player's hit points
        if(asteroidPlayerHit){myShip.updateHitPoints(5);}

        Log.d("ADebugTag", "player hit points: " + myShip.getHitPoints());

        // check for collision between player and police laser
        boolean playerOppLaserHit = detectCollision(myShip.getRect(), opponentShip.getRect());

        if(playerOppLaserHit){myShip.updateHitPoints(5);}


        // check for collision between player's laser and power ups
        PowerUps myPowerUp = mineralPowerUps[i];
        boolean laserPowerUpHit = myCollision.detectCollision(myShip.getRect(), myPowerUp.getRect());
        i++;
        if(i > 2){i = 0;}

        if(laserPowerUpHit){
            //power up activates
        }

        // check for collision between player's laser and asteroids

    }
*/
    /*
        We go through run through all object pairs that can be collided.
        meteor - player's laser.
        meteor - player
        enemy - player
        enemy laser - player
        enemy - player's laser

        These should cover the basic cases of collision within the game.
    */

    public boolean detectCollision(RectF objectA, RectF objectB) {
        return RectF.intersects(objectA, objectB);
    }

}

