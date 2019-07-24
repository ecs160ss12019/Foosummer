package com.gamecodeschool.asteroidsfs;

import android.content.Context;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;

public class CollisionEngine {
    int totalHits = 0;

    public void checkCollision(SObjectsCollection gamePcs, Context context) {

        // check for collision between objects and act accordingly

        // check for collision between player and asteroids
        Asteroid myAsteroid;

        for (int i = 0; i < gamePcs.mAsteroids.size(); i++) {
            myAsteroid = gamePcs.mAsteroids.get(i);


            // asteroid hit player's ship - decrement player's life

//            if (RectF.intersects(gamePcs.mPlayer.getHitbox(), myAsteroid.getPosition())) {
//                startTimer(context, gamePcs); // grace period after a collision - 3 seconds
//
//                Log.d("CollisionEngine", "asteroidPlayerHit: " + RectF.intersects(gamePcs.mPlayer.getHitbox(), myAsteroid.getHitbox()));
//                Log.d("CollisionEngine", "lives left after timer function: " + gamePcs.gameProgress.getMyLives());
//                //Log.d("CollisionEngine", "total hits after timer function: " + totalHits);
//            }
        }

        // check for collision between player and police laser
        //boolean playerOppLaserHit = detectCollision(gamePcs.mPlayer.getHitbox(), opponentShip.getRect());

        //if(playerOppLaserHit){gamePcs.mPlayer.updateHitPoints(5);}


        // check for collision between player's laser and power ups
        /*PowerUps myPowerUp;
        boolean laserPowerUpHit;
        for(int i = 0; i < gamePcs.mMineralPowerUps.length; i++){
            myPowerUp = gamePcs.mMineralPowerUps[i];
            laserPowerUpHit = RectF.intersects(gamePcs.mPlayerLaser.getRect(), myPowerUp.getRect());

            if(laserPowerUpHit){
                //power up activates
            }
        }*/

        // check for collision between player's laser and asteroids


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

    // Sources: https://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
    // https://stackoverflow.com/questions/3134683/android-toast-in-a-thread

    //Declare timer
    CountDownTimer cTimer = null;

/*
    //start timer function
    public void startTimer(final SObjectsCollection gamePcs) {
        cTimer = new CountDownTimer(3000, 1000) {
            int i = 0;
            public void onTick(long millisUntilFinished) {
                i++;
                Log.d("CollisionEngine", "grace period implemented " + i);
            }
            public void onFinish() {
                totalHits += 1;
                // decrement player's life
                gamePcs.gameProgress.decLife();
            }
        };
        cTimer.start();
    }*/


    //cancel timer
    public void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }


    public void startTimer(final Context context, final SObjectsCollection gamePcs) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                cTimer = new CountDownTimer(3000, 100) {
                    int i = 0;

                    public void onTick(long millisUntilFinished) {
                        i++;
                        Log.d("CollisionEngine", "grace period implemented " + i);
                    }
                    public void onFinish() {
                        //totalHits += 1;
                        // decrement player's life
                        //gamePcs.gameProgress.decLife();
                        Log.d("CollisionEngine", "grace period done!");

                       /* Log.d("CollisionEngine", "total hits from inside onFinish " + totalHits);*/
                    }
                };
                cTimer.start();
            }
        });
    }
}

