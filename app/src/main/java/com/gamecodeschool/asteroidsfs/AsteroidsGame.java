package com.gamecodeschool.asteroidsfs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap;
// these imports deal with ArrayList class in java
import java.util.ArrayList;
import java.util.*;
import java.util.Random;
import android.graphics.Bitmap;

class AsteroidsGame extends SurfaceView implements Runnable{
    private final int NUM_BLOCKS_WIDE = 40;
    int blockSize;


    // Toggle for debugging
    private final boolean DEBUGGING = true;

    // Drawing objects
    private SurfaceHolder myHolder;
//    private Canvas myCanvas;
//    private Paint myPaint;

    // Frames per second
    private long myFPS;
    private long timeElapsed;
    // Number of milliseconds in a second
    private final int MILLIS_IN_SECOND = 1000;

    // Screen resolution
    private int screenX;
    private int screenY;
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

//    Matrix shipMatrix = new Matrix();

    // GAME OBJECTS
    private GameProgress gameProgress;

    private ObjectFactory factory;
    SpaceObjectType objType;

//    private Space mySpace;

    public Player myShip;
//    private OpponentShip npcShip; 
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Laser> myLasers;
//    private Laser npcLaser; // vector of lasers associated per npc ship?
    private PowerUps mineralPowerUps[]; // vector of mineral powerups
//    private Drawable mCustomImage;

    private GameView gameView;
    // temp Context

    public AsteroidsGame(Context context, int x, int y) {
        // calls parent class constructor of SurfaceView
        super(context);


        blockSize = x / NUM_BLOCKS_WIDE;


        this.screenX = x;
        this.screenY = y;


        // Initialize the objects
        // ready for drawing with
        // getHolder is a method of Surfaceview
        myHolder = getHolder();
//        myPaint = new Paint();

        gameView = new GameView(context, myHolder);

        // Initialize the objects

        myShip = new Player(screenX, screenY);

        // Asteroids = new Asteroids()

        myLasers = new ArrayList<Laser>();

        // Initialize asteroids
        asteroids = new ArrayList<Asteroid>();

        // Initialize powerups - eventually have them scale with levels?
        // currently hardcoded to 1 for now
        // ill change it to spawn upon a certain point threshold or timed later
        mineralPowerUps = new PowerUps[1];
        Random rn = new Random();
        for(int i = 0; i < mineralPowerUps.length; i++){
            mineralPowerUps[i] = new PowerUps(rn.nextInt(screenX), rn.nextInt(screenY),
                    screenY / 50, screenY / 50, hitsLeft, -(screenY/8), (screenY/8));
        }
      

        display = new Display(x, y);
        gameProgress = new GameProgress();
        factory = new ObjectFactory(display);


        // enemyShip = new ...()
        // myLaser = new ..()
        // enemyLaser = new ..()

        startNewGame();

    }






    /*
        When we start the game we reset the game state such as level
        initial meteor count.
        and clear all lasers and enemy space ships.
    */
    private void startNewGame() {
//        // FIXME: Change 3 to asteroid count variable that can be changed.
        for(int i = 0; i < 3; i++) {
            asteroids.add((Asteroid)factory.getSpaceObject(objType.ASTEROID));
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
                    gameView.draw(myShip.getHitbox(), blockSize, myShip.getDegree(),
                            myShip.getCenterCoords(), asteroids, myLasers, mineralPowerUps);
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

            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            timeElapsed = timeThisFrame;

        }
    }






    private void update() {
        myShip.update(timeElapsed, getContext(), blockSize);

        for(int i = 0; i < myLasers.size(); i++) {
            myLasers.get(i).update(timeElapsed, display);
        }

        for(int i = 0 ; i < asteroids.size() ; i++) {
            asteroids.get(i).update(timeElapsed, display);
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
                if(motionEvent.getX() > screenX / 2){
                    // call method that will accelerate ship
                    myShip.setMoveState(true);
                }

                // If finger pressed on left side of screen...
                if(motionEvent.getX() < screenX / 2){
                    // If finger pressed on upper left of screen
                    // then the ship will rotate counter-clockwise
                    if(motionEvent.getY() < screenY / 2){
                        // rotate ship counter-clockwise
                        myShip.setRotationState(1);
                    }
                    else{
                        // rotate ship clockwise
                        myShip.setRotationState(2);
                    }
                }

                break;

            // The player lifted their finger
            // from anywhere on screen.
            case MotionEvent.ACTION_UP:
                if(motionEvent.getX() > screenX / 2){
                    // stop position
                    myShip.setMoveState(false);
                }

                if(motionEvent.getX() < screenX / 2){
                    // stop rotation / fix orientation
                    myShip.setRotationState(0);
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
