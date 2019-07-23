package com.gamecodeschool.asteroidsfs;

import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

public class CollisionEngine {

    public void checkCollision(Render render) {
        // check for collision between objects and act accordingly

        // check for collision between player and asteroids
        Asteroid myAsteroid;
        boolean asteroidPlayerHit;
        for (int i = 0; i < render.mAsteroids.size(); i++) {
            myAsteroid = render.mAsteroids.get(i);
            asteroidPlayerHit = RectF.intersects(render.mPlayer.getHitbox(), myAsteroid.getHitbox());

            // asteroid hit player's ship - decrement player's hit points
            if (asteroidPlayerHit) {
                render.mPlayer.updateHitPoints(5);
            }

            Log.d("ADebugTag", "player hit points: " + render.mPlayer.getHitPoints());

            // check for collision between player and police laser
            //boolean playerOppLaserHit = detectCollision(render.mPlayer.getHitbox(), opponentShip.getRect());

            //if(playerOppLaserHit){render.mPlayer.updateHitPoints(5);}


            // check for collision between player's laser and power ups
        /*PowerUps myPowerUp = render.mMineralPowerUps[i];
        boolean laserPowerUpHit = detectCollision(render.mPlayer.getHitbox(), myPowerUp.getRect());
        i++;
        if(i > 2){i = 0;}

        if(laserPowerUpHit){
            //power up activates
        }
*/
            // check for collision between player's laser and asteroids

        }


    /*
        We go through run through all object pairs that can be collided.
        meteor - player's laser.
        meteor - player
        enemy - player
        enemy laser - player
        enemy - player's laser

        These should cover the basic cases of collision within the game.
    */
    }


}

