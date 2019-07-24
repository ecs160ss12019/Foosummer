package com.gamecodeschool.asteroidsfs;

import android.content.Context;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
// these imports deal with ArrayList class in java


class AsteroidsGame extends SurfaceView implements Runnable{
    private final int NUM_BLOCKS_WIDE = 40;
    int blockSize; // FIXME TODO SUGGESTION: Tuck this into SObjectsCollection, might not be a necessary fix.

    // Toggle for debugging
    static final boolean DEBUGGING = false;

    // Drawing objects
    private SurfaceHolder myHolder;
    private long timeElapsed;
    // Number of milliseconds in a second
    private final int MILLIS_IN_SECOND = 1000;
    /* 
        JSC: Let's eventually replace screen resolution 
        with this object variable (that contains the screen x y size)
    */
    private Display display; 
    // Number of hits to destroy PowerUps
    private int hitsLeft= 3;

    // Here is the Thread and two control variables
    private Thread myGameThread = null;
    // This volatile variable can be accessed
    // from inside and outside the thread
    private volatile boolean nowPlaying;
    private boolean nowPaused = true;


    // GAME OBJECTS
    private GameProgress gameProgress;
    private ObjectFactory factory;
    private GameView gameView;
    private SObjectsCollection gamePcs;

    SpaceObjectType objType; // Enum used for object creation.


    public AsteroidsGame(Context context, int x, int y) {
        // calls parent class constructor of SurfaceView
        super(context);
        display = new Display(x, y);


        blockSize = x / NUM_BLOCKS_WIDE;

        // Initialize the objects
        // ready for drawing with
        // getHolder is a method of Surfaceview
        myHolder = getHolder();
        gameView = new GameView(context, myHolder, display);
        factory = new ObjectFactory(display);
        gameProgress = new GameProgress();
        gamePcs = new SObjectsCollection(display);
        gamePcs.mBlockSize = blockSize; // FIXME Need to get other blocksizes tucked away for this eventually.

        startNewGame();
    }






    /*
        When we start the game we reset the game state such as level
        initial meteor count.
        and clear all lasers and enemy space ships.
    */
    private void startNewGame() {
//        // FIXME: Change 3 to asteroid count variable that can be changed.
        gameProgress.reset();
        factory.reset();
        for(int i = 0; i < 5; i++) {
            gamePcs.mAsteroids.add((Asteroid)factory.getSpaceObject(objType.ASTEROID));
        }

        for(int i = 0; i < 3; i++) {
            gamePcs.mMineralPowerUps.add((PowerUps)factory.getSpaceObject(objType.POWERUP, 3));
        }
    }






    @Override
    public void run() {
        while(nowPlaying) {
            //What time is it now at the start of the loop?
            long frameStartTime = System.currentTimeMillis();

            if(!nowPaused){
                if(timeElapsed > 0) {
                    update();
                    gameView.draw(gamePcs);
                }
                    

                // check for collision between player and asteroids
                // Asteroid myAsteroid = gamePcs.mAsteroidsget(i);
                // boolean asteroidPlayerHit = detectCollision(gamePcs.mPlayer.getRect(), myAsteroid.getHitbox());
                // i++;
                // if(i > 4){
                //     i = 0;
                // }


                /*
                Log.d("ADebugTag", "collision detected: " + hit);
                Log.d("ADebugTag", "value of i: " + i);
                */

                //sprint 2
                // asteroid hit player's ship - decrement player's hitpoints
                //if(asteroidPlayerHit){}

                // check for collision between player and police laser
                // check for collision between player's laser and powerup
                // check for collision between player's laser and asteroids
                //detectCollisions();
            }

            // How long did this frame/loop take?
            // Store the answer in timeThisFrame
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            timeElapsed = timeThisFrame;

        }
    }






    private void update() {
        // PLAYER
        gamePcs.mPlayer.update(timeElapsed, getContext(), blockSize, display.width, display.height);

        // ASTEROIDS
        for(int i = 0 ; i < gamePcs.mAsteroids.size() ; i++) {
            gamePcs.mAsteroids.get(i).update(timeElapsed, display);
        }

        // PLAYER LASER we call different update since this has a boolean attached to it.
        for(int i = 0; i < gamePcs.mPlayerLasers.size(); i++) {
            if(gamePcs.mPlayerLasers.get(i).updateL(timeElapsed, display)) {
                gamePcs.mPlayerLasers.remove(i);
                i--;
            }
        }

        //POWER UPS
        // PowerUp position - currently stationary
        for(int i = 0; i < gamePcs.mMineralPowerUps.size(); i++) {
            gamePcs.mMineralPowerUps.get(i).update(timeElapsed, display.width, display.height);
        }
    }


    // Handle all the screen touches
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        // This switch block replaces the
        // if statement from the Sub Hunter game
        switch (motionEvent.getAction() &
                MotionEvent.ACTION_MASK) {

            // The player has put their finger on the screen
            case MotionEvent.ACTION_DOWN:

                // If the game was paused unpause
                nowPaused = false;

                // If finger pressed on right side of screen
                // then the ship will accelerate
                if(motionEvent.getX() > display.width / 2){
                    // call method that will accelerate ship
                    gamePcs.mPlayer.setMoveState(true);
                }

                // If finger pressed on left side of screen...
                if(motionEvent.getX() < display.width / 2){
                    // If finger pressed on upper left of screen
                    // then the ship will rotate counter-clockwise
                    if(motionEvent.getY() < display.height / 2){
                        // rotate ship counter-clockwise
                        gamePcs.mPlayer.setRotationState(1);
                    }
                    else{
                        // rotate ship clockwise
                        gamePcs.mPlayer.setRotationState(2);
                    }
                }

                break;

            // The player lifted their finger
            // from anywhere on screen.
            case MotionEvent.ACTION_UP:
                if(motionEvent.getX() > display.width / 2){
                    // stop position
                    gamePcs.mPlayer.setMoveState(false);
                }

                if(motionEvent.getX() < display.width / 2){
                    // stop rotation / fix orientation
                    gamePcs.mPlayer.setRotationState(0);
                }

                break;
        }
        return true;
    }




    public void resume(){
        nowPlaying = true;

        // Initialize the instance of Thread
        myGameThread = new Thread(this);

        // Start the thread
        myGameThread.start();
    }






    public void pause() {
        // Set nowPlaying to false
        // Stopping the thread isn't
        // always instant
        nowPlaying = false;
        try {
            myGameThread.join();
        } catch (InterruptedException e){
            Log.e("Error:", "joining thread");
        }
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
    public boolean detectCollision(RectF objectA, RectF objectB) {
            return RectF.intersects(objectA, objectB);
    }
}
