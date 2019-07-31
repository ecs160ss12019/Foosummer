package com.gamecodeschool.asteroidsfs;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.graphics.PointF;

import java.util.ArrayList;

import java.util.Random;

import android.graphics.PointF;


/* 
 * The CollisionEngine's main role is detecting the collision between objects.
 * - ATM: the engine runs n^2 algorithm for comparing arraylists.
 * - The reason why we use brute force is that our object count should not be big enough
 *      to have significant negative performance.
 * The CollisionEngine's second role is to faciliate collision action between two objects.
 */
public class CollisionEngine {
    int totalHits = 0;
    boolean asteroidsEliminated = false;
    boolean oppsEliminated = false;
//    Random powerUpSeed = new Random();
    double asteroidDropProbability = 0.10;
    double oppponentDropProbability = 0.20;
    boolean dropPowerUp = false;
    private PointF powerUpPos;
//    GameProgress playerStatus = new GameProgress();

    /*
        We go through run through all object pairs that can be collided.
        player laser - asteroid
        player laser - opponent
        enemy laser - player
        asteroid - player
        powerup - player 
        These should cover the basic cases of collision within the game.
        FIXME: Separate these into separate private methods for readability!!!
    */
    public void checkCollision(SObjectsCollection collection, GameProgress gProg, ParticleSystem ps) {
        dropPowerUp = false;

        // player vs asteroid.
        playerAsteroidCollision(collection.mPlayer, collection.mAsteroids, gProg);
        playerEnemyCollision(collection.mPlayer, collection.mOpponents, gProg);
        PLaserEnemyCollision(collection.mPlayerLasers, collection.mOpponents, gProg, ps);
        PLaserAsteroidCollision(collection.mPlayerLasers, collection.mAsteroids, gProg);
        oLaserPlayerCollision(collection.mOpponentLasers, collection.mPlayer, gProg);
        playerPowerUpCollision(collection.mPlayer, collection.mMineralPowerUps, gProg);
    }

    // See if player collided with any of the asteroids.
    private void playerAsteroidCollision(Player P, ArrayList<Asteroid> aList, GameProgress gp) {
        for(int i = 0; i < aList.size(); i++) {
            Asteroid temp = aList.get(i);
            if(SpaceObject.collisionCheck(P, temp)) {
                P.resetPos();
                // add subtract life logic here and possible start grace period count down.
                gp.decLife();

                aList.addAll(temp.collisionAction());
                aList.remove(i);
                i--;
                if(aList.size() == 0){
                    asteroidsEliminated = true;
                }
                break;
            }
        }
    }

    private void playerEnemyCollision(Player P, ArrayList<Opponent> oList, GameProgress gp){
        for(int i = 0; i < oList.size(); i++) {
            Opponent temp = oList.get(i);
            if(SpaceObject.collisionCheck(P, temp)) {
                P.resetPos();
                // add subtract life logic here and possible start grace period count down.
                // should the enemy ship be destroyed on collision with Player ship?
                gp.decLife();

                oList.remove(i);
                i--;
                if(oList.size() == 0){
                    oppsEliminated = true;
                }
                break;
            }
        }
    }

    private void playerPowerUpCollision(Player P, ArrayList<PowerUps> puList, GameProgress gp){
        for(int i = 0; i < puList.size(); i++) {
            PowerUps temp = puList.get(i);
            if(SpaceObject.collisionCheck(P, temp)) {
                // add power up feature on collision

                //FIXME:
                P.receivePowerUp(PowerUp.FIRE_RATE);
//                P.receivePowerUp(PowerUp.SHIELD);
                puList.remove(i);
                i--;
                break;
            }
        }
    }

    private void PLaserEnemyCollision(ArrayList<Laser> pList, ArrayList<Opponent> oList, GameProgress gp, ParticleSystem ps) {
        for(int i = 0; i < pList.size(); i++) {
            for(int k = 0; k < oList.size(); k++) {
                Opponent temp = oList.get(k);
                if(SpaceObject.collisionCheck(pList.get(i), temp)) {
                    ps.emitParticles(
                            new PointF(
                                    temp.getPosition().x,
                                    temp.getPosition().y

                            )
                    );

                    pList.remove(i);
                    //FIXME: Need to add score logic here!
                    // For now enemy dies in 1 hit.
                    gp.updateScore(gp.OppMultiplier);

                    didPowerUpDrop(oppponentDropProbability,
                            new PointF(temp.getBitmapX(), temp.getBitmapY()));

                    oList.remove(k);
                    i--;
                    k--;
                    if(oList.size() == 0){
                        oppsEliminated = true;
                    }
                    break;
                }
            }
        }
    }

    private void PLaserAsteroidCollision(ArrayList<Laser> pList, ArrayList<Asteroid> aList, GameProgress gp){
        for(int i = 0; i < pList.size(); i++) {
            for(int k = 0; k < aList.size(); k++) {
                Asteroid temp = aList.get(k);
                if(SpaceObject.collisionCheck(pList.get(i), temp)) {



                    gp.updateScore(temp.getSize());

                    didPowerUpDrop(asteroidDropProbability,
                            new PointF(temp.getBitmapX(), temp.getBitmapY()));

                    aList.addAll(temp.collisionAction());
                    aList.remove(k);
                    pList.remove(i);
                    i--;
                    if(aList.size() == 0){
                        asteroidsEliminated = true;
                    }
                    break;
                }
            }
        }
    }

    private void oLaserPlayerCollision(ArrayList<Laser>oLaserList, Player P, GameProgress gp) {
        for(int i = 0; i < oLaserList.size(); i++) {
            if(SpaceObject.collisionCheck(oLaserList.get(i), P)) {
                P.resetPos();
                // add subtract life logic here and possible grace period count down.
                gp.decLife();

                oLaserList.remove(i);
                i--;
                break;
            }
        }
    }

    public boolean checkEnemiesRemaining(){
        if(oppsEliminated == true && asteroidsEliminated == true) { return true; }
        else{ return false; }
    }

    public void resetEnemies(){
        oppsEliminated = false;
        asteroidsEliminated = false;
    }

    private void didPowerUpDrop(double prob, PointF pos){
        if(prob >= Math.random()){
            dropPowerUp = true;
            setDropPos(pos);
        }
    }

    private void setDropPos(PointF pos){
        powerUpPos = pos;
    }

    public PointF getDropPos() {return this.powerUpPos;}



    // FIXME for now these code will not be used, but left in there as a possible future usage if we need some sort of timer function 
    // FIXME for the collision endgine.
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

