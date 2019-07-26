package com.gamecodeschool.asteroidsfs;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap;
// these imports deal with ArrayList class in java
import java.util.ArrayList;
import java.util.*;
import java.util.Random;

class AsteroidsGame extends SurfaceView implements Runnable{
    private final int NUM_BLOCKS_WIDE = 40;
    int blockSize;


    // Toggle for debugging
    static final boolean DEBUGGING = false;

    // Drawing objects
    private SurfaceHolder myHolder;
//    private Canvas myCanvas;
//    private Paint myPaint;

    // Frames per second
    private long myFPS;
    private long timeElapsed;
    // Number of milliseconds in a second
    private final int MILLIS_IN_SECOND = 1000;
    /* 
        JSC: Let's eventually replace screen resolution 
        with this object variable (that contains the screen x y size)
    */
    private Display display; 

    // Text size
    private int fontSize = blockSize*10;
    private int fontMargin;

    // track user score and lives
    private int score = 0;
    private int lives = 3; // abstract this to UserShip class?

    private int i = 0;

    private int degree;

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
    SpaceObjectType objType;

//    private Space mySpace;

    public Player myShip;
//    private OpponentShip npcShip; 
    private ArrayList<Asteroid> asteroids;
//    private Laser npcLaser; // vector of lasers associated per npc ship?
    private ArrayList<PowerUps> mineralPowerUps; // vector of mineral powerups
//    private Drawable mCustomImage;

    private GameView gameView;
    private Render mRender = new Render();
    // temp Context


    public AsteroidsGame(Context context, int x, int y) {
        // calls parent class constructor of SurfaceView
        super(context);
        display = new Display(x, y);


        blockSize = x / NUM_BLOCKS_WIDE;

        // Initialize the objects
        // ready for drawing with
        // getHolder is a method of Surfaceview
        myHolder = getHolder();
//        myPaint = new Paint();

        gameView = new GameView(context, myHolder, display);

        // Initialize the objects
        myShip = new Player(display.width, display.height);

        // Initialize asteroids
        asteroids = new ArrayList<Asteroid>();

        mineralPowerUps = new ArrayList<PowerUps>();

        gameProgress = new GameProgress();
        factory = new ObjectFactory(display);
        
        bundleRender();


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
        for(int i = 0; i < 10; i++) {
            asteroids.add((Asteroid)factory.getSpaceObject(objType.ASTEROID));
        }

        for(int i = 0; i < 3; i++) {
            mineralPowerUps.add((PowerUps)factory.getSpaceObject(objType.POWERUP, 3));
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

                    gameView.draw(mRender);
                }
                    

                // check for collision between player and asteroids
                // Asteroid myAsteroid = asteroids.get(i);
                // boolean asteroidPlayerHit = detectCollision(myShip.getRect(), myAsteroid.getHitbox());
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

        myShip.update(timeElapsed);
        myShip.configMatrix(gameView.getBitmapDim(), blockSize);


        // ASTEROIDS
        for(int i = 0 ; i < asteroids.size() ; i++) {
            asteroids.get(i).update(timeElapsed, display);
        }

        //POWER UPS
        // PowerUp position - currently stationary
        for(int i = 0; i < mineralPowerUps.size(); i++) {
            mineralPowerUps.get(i).update(timeElapsed, display.width, display.height);
        }
    }


    // Handle all the screen touches
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int index = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(index);
        int action = motionEvent.getActionMasked();

        int oldX, oldY;
        // This switch block replaces the
        // if statement from the Sub Hunter game
        switch (motionEvent.getAction() &
                MotionEvent.ACTION_MASK) {

            // The player has put their finger on the screen
            case MotionEvent.ACTION_DOWN:
                index = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                pointerId = motionEvent.getPointerId(index);
//                Log.e("Controlls", "Action DOWN "+ pointerId);
//                Log.e("Controlls", "Coordinates "+ motionEvent.getX(index) + " "+  motionEvent.getY(index));
                // If the game was paused unpause
                nowPaused = false;

                // If finger pressed on right side of screen
                // then the ship will accelerate
                if(motionEvent.getX() > display.width / 2){
                    // call method that will accelerate ship
                    myShip.setMoveState(true);
                }
                // If finger pressed on left side of screen...

                else if(motionEvent.getX() < display.width / 2){

                    // If finger pressed on upper left of screen
                    // then the ship will rotate counter-clockwise
                    if(motionEvent.getY() < display.height / 2){
                        // rotate ship counter-clockwise
                        myShip.setRotationState(1);
                    }
                    else{
                        // rotate ship clockwise
                        myShip.setRotationState(2);
                    }
                }

                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                index = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                pointerId = motionEvent.getPointerId(index);
//                Log.e("Controlls", "Action Pointer DOWN "+ pointerId);
//                Log.e("Controlls", "Coordinates "+ motionEvent.getX(index) + " "+  motionEvent.getY(index));
                nowPaused = false;

                if(motionEvent.getX(0) < display.width / 2){
                    if(motionEvent.getY(0) < display.height / 2){
                        // rotate ship counter-clockwise
                        myShip.setRotationState(1);
//                        Log.e("Controlls", "ROTATE SECOND HERE TOP"+ pointerId);
                    }
                    else if (motionEvent.getY(0) > display.height / 2){
                        // rotate ship clockwise
                        myShip.setRotationState(2);
//                        Log.e("Controlls", "ROTATE SECOND HERE BOT "+ pointerId);
                    }
                }
                else if(motionEvent.getX(0) > display.width ){
                    myShip.setMoveState(true);
                }

                if( motionEvent.getX(1) < display.width / 2){
                    // If finger pressed on upper left of screen
                    // then the ship will rotate counter-clockwise
                    if(motionEvent.getY(1) < display.height / 2){
                        // rotate ship counter-clockwise
                        myShip.setRotationState(1);
//                        Log.e("Controlls", "ROTATE SECOND HERE TOP"+ pointerId);
                    }
                    else if (motionEvent.getY(1) > display.height / 2){
                        // rotate ship clockwise
                        myShip.setRotationState(2);
//                        Log.e("Controlls", "ROTATE SECOND HERE BOT "+ pointerId);
                    }
                }
                else if(motionEvent.getX(1) > display.width / 2){
                    myShip.setMoveState(true);
                }

                break;



            // The player lifted their finger
            // from anywhere on screen.
            case MotionEvent.ACTION_UP:

                index = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                pointerId = motionEvent.getPointerId(index);
//                Log.d("Controlls", "Action UP "+ pointerId);
//                Log.d("Controlls", "Coordinates "+ motionEvent.getX(index) + " "+  motionEvent.getY(index));

                if(motionEvent.getX() > display.width / 2){
                    // stop position
                    myShip.setMoveState(false);
                }
                if(motionEvent.getX() < display.width / 2){

                    // stop rotation / fix orientation
                    myShip.setRotationState(0);
                }

                break;

            case MotionEvent.ACTION_POINTER_UP: // when order of touch and release is the same
                index = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                pointerId = motionEvent.getPointerId(index);
//                Log.d("Controlls", "Action Pointer UP "+ pointerId);
//                Log.d("Controlls", "Coordinates "+ motionEvent.getX(index) + " "+  motionEvent.getY(index));

                if((pointerId == 0 || pointerId == 1) &&
                        ((motionEvent.getX(0) < display.width / 2) ||
                        ((motionEvent.getX(1) < display.width / 2))))
                {
                   myShip.setRotationState(0);
                }

                if(pointerId == 1 && motionEvent.getX(1) < display.width / 2){
                    myShip.setRotationState(0);
                }
                if(pointerId == 1 && motionEvent.getX(1) > display.width / 2){
                    myShip.setMoveState(false);
                }
                if(pointerId == 0 && motionEvent.getX(0) < display.width / 2){
                    myShip.setRotationState(0);
                }
                if(pointerId == 0 && motionEvent.getX(0) > display.width / 2){
                    myShip.setMoveState(false);
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

    private void bundleRender(){
        mRender.mPlayer = myShip;
//        mRender.mPlayerLaser = myLasers;
        mRender.mAsteroids = asteroids;
        mRender.mMineralPowerUps = mineralPowerUps;
        mRender.mBlockSize = blockSize;
    }

}
