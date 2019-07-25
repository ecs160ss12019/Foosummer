package com.gamecodeschool.asteroidsfs;

import android.content.Context;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;

/* 
 * The CollisionEngine's main role is detecting the collision between objects.
 * - ATM: the engine runs n^2 algorithm for comparing arraylists.
 * - The reason why we use brute force is that our object count should not be big enough
 *      to have significant negative performance.
 * The CollisionEngine's second role is to faciliate collision action between two objects.
 */
public class CollisionEngine {
    int totalHits = 0;

    /*
        We go through run through all object pairs that can be collided.
        player laser - asteroid
        player laser - opponent
        enemy laser - player
        asteroid - player
        powerup - player 
        These should cover the basic cases of collision within the game.
        FIXME: Using n2 algorithm for now unless this gives us a sig performance hit.
    */
    public void checkCollision(SObjectsCollection collection, GameProgress gProg) {
        for(int i = 0; i < collection.mPlayerLasers.size(); i++) {
            for(int k = 0; k < collection.mAsteroids.size(); k++) {
                Asteroid temp = collection.mAsteroids.get(k);
                if(SpaceObject.collisionCheck(collection.mPlayerLasers.get(i), temp)) {
                    Log.d("Collision", "asteroid size " + temp.getSize());
                    collection.mAsteroids.addAll(temp.collisionAction());
                    collection.mAsteroids.remove(k);
                    collection.mPlayerLasers.remove(i);
                    i--;
                    break;
                }
            }
        }
    }


    // Sources: https://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
    // https://stackoverflow.com/questions/3134683/android-toast-in-a-thread

    //Declare timer
    // CountDownTimer cTimer = null;

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


    // //cancel timer
    // public void cancelTimer() {
    //     if(cTimer!=null)
    //         cTimer.cancel();
    // }


    // public void startTimer(final Context context, final SObjectsCollection gamePcs) {
    //     Handler handler = new Handler(Looper.getMainLooper());
    //     handler.post(new Runnable() {
    //         public void run() {
    //             cTimer = new CountDownTimer(3000, 100) {
    //                 int i = 0;

    //                 public void onTick(long millisUntilFinished) {
    //                     i++;
    //                     Log.d("CollisionEngine", "grace period implemented " + i);
    //                 }
    //                 public void onFinish() {
    //                     //totalHits += 1;
    //                     // decrement player's life
    //                     //gamePcs.gameProgress.decLife();
    //                     Log.d("CollisionEngine", "grace period done!");

    //                    /* Log.d("CollisionEngine", "total hits from inside onFinish " + totalHits);*/
    //                 }
    //             };
    //             cTimer.start();
    //         }
    //     });
    // }
}

