package com.gamecodeschool.asteroidsfs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
// these imports deal with ArrayList class in java


class AsteroidsGame extends SurfaceView implements Runnable{
    Canvas canvas;
    Paint paint;


    private final int NUM_BLOCKS_WIDE = 40;
    int blockSize; // FIXME TODO SUGGESTION: Tuck this into SObjectsCollection, might not be a necessary fix.

    // Toggle for debugging
    static final boolean DEBUGGING = false;

    // Drawing objects
    private SurfaceHolder myHolder;
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
    private Audio audio;
    private TouchHandler mTouchHandler;
    //Temporarily here
    ParticleSystem mParticleSystem;

    // SYSTEM CLOCK
    private GameClock gameClock;

    SpaceObjectType objType; // Enum used for object creation.

    private CollisionEngine mCollision;

    // distinguishes user pause vs "pause" when initializing the game
    boolean userPause = false;

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
        mCollision = new CollisionEngine();
        //Temporarily here
        mParticleSystem = new ParticleSystem ();
        mParticleSystem.init(1000, display);
        mTouchHandler = new TouchHandler(display, gamePcs.mPlayer);
        gameClock = new GameClock();
        gamePcs.mBlockSize = blockSize; // FIXME Need to get other blocksizes tucked away for this eventually.
        audio = new Audio(context);
        startNewGame();
    }




    /*
        When we start the game we reset the game state such as level
        initial meteor count.
        and clear all lasers and enemy space ships.
    */
    private void startNewGame() {
//        // FIXME: Change 3 to asteroid count variable that can be changed.
        gamePcs.mPlayer = (Player)factory.getSpaceObject(objType.PLAYER);
        gameProgress.reset(gamePcs, factory, objType);
        factory.reset();


    }






    @Override
    public void run() {
        while(nowPlaying) {
            //What time is it now at the start of the loop?
            gameClock.frameStart();

            if(!nowPaused){
                if(gameClock.getTimeElapsed() > 0) {
                    update();
                    gameView.draw(gamePcs, gameProgress, userPause, mParticleSystem);
                }
                mCollision.checkCollision(gamePcs, gameProgress, mParticleSystem);
                if(gameProgress.getGameStatus()){
                    gameOver();
//                    gameProgress.reset(gamePcs, factory, objType);

                }
                if(mCollision.checkEnemiesRemaining()){
                    gameProgress.startNextLevel(gamePcs, factory, objType);
                    mCollision.resetEnemies();
                }
            }
            // on pause..
            else if(userPause){
                gameView.draw(gamePcs, gameProgress, userPause, mParticleSystem);
//                nowPlaying = false;
                gameClock.frameStop();
//                Log.e("run: ", "nowPlaying is false: " + nowPlaying);
                while(userPause){
                    gameClock.frameStart();
                    if(!userPause){
                        break;
                    }
                    gameClock.frameStop();
                }

            }

            // How long did this frame/loop take?
            // Store the answer in timeThisFrame
            gameClock.frameStop();
        }
//        Log.e("run:", "userPause: " + userPause);
    }



    private void update() {

        // EXPLOSION
        mParticleSystem.update(gameClock.getTimeElapsed() , display);

        // shooting action each update.
        Laser shootResult = gamePcs.mPlayer.shoot(gameClock.getTimeElapsed(), factory);

        if(DEBUGGING) {
            Log.d("update: ", "Shoot time total: " + gamePcs.mPlayer.getTimer());
        }

        if(shootResult != null) {
            gamePcs.mPlayerLasers.add(shootResult);
        }

        // PLAYER
        gamePcs.mPlayer.update(gameClock.getTimeElapsed(), display);
        gamePcs.mPlayer.configMatrix(gameView.getBitmapDim(), blockSize);


        // ASTEROIDS
        for(int i = 0 ; i < gamePcs.mAsteroids.size() ; i++) {
            gamePcs.mAsteroids.get(i).update(gameClock.getTimeElapsed(), display);
        }

        // PLAYER LASER we call different update since this has a boolean attached to it.
        for(int i = 0; i < gamePcs.mPlayerLasers.size(); i++) {
            if(gamePcs.mPlayerLasers.get(i).updateL(gameClock.getTimeElapsed(), display)) {
                gamePcs.mPlayerLasers.remove(i);
                i--;
            }
        }

        //POWER UPS

        if(mCollision.dropPowerUp){
            gamePcs.mMineralPowerUps.add(factory.getPowerUp(mCollision.getDropPos()));
        }
        // PowerUp position - currently stationary
        for(int i = 0; i < gamePcs.mMineralPowerUps.size(); i++) {
            gamePcs.mMineralPowerUps.get(i).update(gameClock.getTimeElapsed(), display);
        }

        // OPPONENT
        Laser oppShootResult;

        for(int i = 0; i < gamePcs.mOpponents.size(); i++) {

            oppShootResult = gamePcs.mOpponents.get(i).shoot(gameClock.getTimeElapsed(), factory, gamePcs.mPlayer.getPosition());

            if(oppShootResult != null) {
                gamePcs.mOpponentLasers.add(oppShootResult);

                // update the position of opponent at this index
                gamePcs.mOpponents.get(i).updateOppPosition(true);
            }

            gamePcs.mOpponents.get(i).update(gameClock.getTimeElapsed(), display);
        }

        // OPPONENT LASER
        for(int i = 0; i < gamePcs.mOpponentLasers.size(); i++) {
            if(gamePcs.mOpponentLasers.get(i).updateL(gameClock.getTimeElapsed(), display)) {
                gamePcs.mOpponentLasers.remove(i);
                i--;
            }
        }


    }



    /*
        Receive touch event here.
        Parse the motionEvent and send it to handler.
    */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: // First touch point.
                
                break;
            case MotionEvent.ACTION_POINTER_DOWN: // Additional touch pointer initiated
                break;
            case MotionEvent.ACTION_POINTER_UP:  // A touch pointer has been lifted.
                break;
            case MotionEvent.ACTION_UP: // Last pointer up action
                break;
            case MotionEvent.ACTION_MOVE: // A pointer has moved.
                break;
        }
    }




    public void resume(){
        nowPlaying = true;

        // Initialize the instance of Thread
        myGameThread = new Thread(this);

        // Start the thread
        myGameThread.start();
        audio.playClick();
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
        audio.pause();
    }

    private void gameOver(){
        // Draw some huge white text
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(blockSize * 10);

        canvas.drawText("Game over!", blockSize * 4,
                blockSize * 14, paint);

        // Draw some text to prompt restarting
        paint.setTextSize(blockSize * 2);
        canvas.drawText("Tap screen to start again",
                blockSize * 8,
                blockSize * 18, paint);

        startNewGame();
    }




















    // Handle all the screen touches
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int index = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(index);
        int action = motionEvent.getActionMasked();
        PointF pauseRadius = new PointF(2497, 116);

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

                // need to consider pause for multi-touch also... need to test on Android
                if(motionEvent.getX() > pauseRadius.x && motionEvent.getY() < pauseRadius.y && nowPaused == false){
                    nowPaused = true;
                }
                else {
                    nowPaused = false;

                }

                userPause = nowPaused;


                // If finger pressed on right side of screen
                // then the ship will accelerate
                if(motionEvent.getX() > display.width / 2){
                    // call method that will accelerate ship
                    gamePcs.mPlayer.setMoveState(true);
                }
                else if(motionEvent.getX() < display.width / 2){
                    downX = motionEvent.getRawX();
                    lastMoveX = downX;
                }


                break;

            case MotionEvent.ACTION_MOVE:
                moveX = motionEvent.getRawX();
                float xDiff = Math.abs(moveX - downX);
                lastMoveX = moveX;
                Log.e("ACTION_MOVE:" , "moveX:"+moveX+", xDiff:"+xDiff+
                        ", downX: " + downX);
                if(downX > moveX){
                    gamePcs.mPlayer.setRotationState(1);
                }
                else if(downX < moveX){
                    gamePcs.mPlayer.setRotationState(2);
                }
//                if (xDiff > mTouchSlop){
//                    return true;
//                }

                break;

            case MotionEvent.ACTION_UP:
                if(motionEvent.getX() < display.width / 2){
                    gamePcs.mPlayer.setRotationState(0);
                }
                else if( motionEvent.getX() > display.width / 2){
                    gamePcs.mPlayer.setMoveState(false);
                }
                break;

//            case MotionEvent.ACTION_POINTER_DOWN:
//                index = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
//                pointerId = motionEvent.getPointerId(index);
////                Log.e("Controlls", "Action Pointer DOWN "+ pointerId);
////                Log.e("Controlls", "Coordinates "+ motionEvent.getX(index) + " "+  motionEvent.getY(index));
//
//                //
//                // HANDLE PAUSE HERE TOO.. NEED ANDROID TO TEST
//                // nowPaused = false;
//                //
//                //
//
//                if(motionEvent.getX(0) < display.width / 2){
//                    if(motionEvent.getY(0) < display.height / 2){
//                        // rotate ship counter-clockwise
//                        gamePcs.mPlayer.setRotationState(1);
////                        Log.e("Controlls", "ROTATE SECOND HERE TOP"+ pointerId);
//                    }
//                    else if (motionEvent.getY(0) > display.height / 2){
//                        // rotate ship clockwise
//                        gamePcs.mPlayer.setRotationState(2);
////                        Log.e("Controlls", "ROTATE SECOND HERE BOT "+ pointerId);
//                    }
//                }
//                else if(motionEvent.getX(0) > display.width ){
//                    gamePcs.mPlayer.setMoveState(true);
//                }
//
//                if( motionEvent.getX(1) < display.width / 2){
//                    // If finger pressed on upper left of screen
//                    // then the ship will rotate counter-clockwise
//                    if(motionEvent.getY(1) < display.height / 2){
//                        // rotate ship counter-clockwise
//                        gamePcs.mPlayer.setRotationState(1);
////                        Log.e("Controlls", "ROTATE SECOND HERE TOP"+ pointerId);
//                    }
//                    else if (motionEvent.getY(1) > display.height / 2){
//                        // rotate ship clockwise
//                        gamePcs.mPlayer.setRotationState(2);
////                        Log.e("Controlls", "ROTATE SECOND HERE BOT "+ pointerId);
//                    }
//                }
//                else if(motionEvent.getX(1) > display.width / 2){
//                    gamePcs.mPlayer.setMoveState(true);
//                }
//
//                break;



//            // The player lifted their finger
//            // from anywhere on screen.
//            case MotionEvent.ACTION_UP:
//
//                index = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
//                pointerId = motionEvent.getPointerId(index);
////                Log.d("Controlls", "Action UP "+ pointerId);
////                Log.d("Controlls", "Coordinates "+ motionEvent.getX(index) + " "+  motionEvent.getY(index));
//
//                if(motionEvent.getX() > display.width / 2){
//                    // stop position
//                    gamePcs.mPlayer.setMoveState(false);
//                }
//                if(motionEvent.getX() < display.width / 2){
//
//                    // stop rotation / fix orientation
//                    gamePcs.mPlayer.setRotationState(0);
//                }
//
//                break;

//            case MotionEvent.ACTION_POINTER_UP: // when order of touch and release is the same
//                index = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
//                pointerId = motionEvent.getPointerId(index);
////                Log.d("Controlls", "Action Pointer UP "+ pointerId);
////                Log.d("Controlls", "Coordinates "+ motionEvent.getX(index) + " "+  motionEvent.getY(index));
//
//                if((pointerId == 0 || pointerId == 1) &&
//                        ((motionEvent.getX(0) < display.width / 2) ||
//                                ((motionEvent.getX(1) < display.width / 2))))
//                {
//                    gamePcs.mPlayer.setRotationState(0);
//                }
//
//                if(pointerId == 1 && motionEvent.getX(1) < display.width / 2){
//                    gamePcs.mPlayer.setRotationState(0);
//                }
//                if(pointerId == 1 && motionEvent.getX(1) > display.width / 2){
//                    gamePcs.mPlayer.setMoveState(false);
//                }
//                if(pointerId == 0 && motionEvent.getX(0) < display.width / 2){
//                    gamePcs.mPlayer.setRotationState(0);
//                }
//                if(pointerId == 0 && motionEvent.getX(0) > display.width / 2){
//                    gamePcs.mPlayer.setMoveState(false);
//                }
//
//                break;

        }
        return true;
    }

}

