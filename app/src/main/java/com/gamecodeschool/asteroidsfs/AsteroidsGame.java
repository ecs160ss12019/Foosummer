package com.gamecodeschool.asteroidsfs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;
import android.graphics.Canvas;

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
    private boolean nowPaused = false;


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

    // determines if user presses screen to restart after game over
    private boolean userRestart = false;

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
        mTouchHandler = new TouchHandler(display);
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
        // is there a cleaner way to remove all elements from all respective arraylists?
        mParticleSystem.mParticles.removeAll(mParticleSystem.mParticles);
        gamePcs.mMineralPowerUps.removeAll(gamePcs.mMineralPowerUps);
        gamePcs.mAsteroids.removeAll(gamePcs.mAsteroids);
        gamePcs.mOpponents.removeAll(gamePcs.mOpponents);
        gamePcs.mPlayerLasers.removeAll(gamePcs.mPlayerLasers);
        gamePcs.mOpponentLasers.removeAll(gamePcs.mOpponentLasers);
        gamePcs.mPlayer = (Player)factory.getSpaceObject(objType.PLAYER);
        mTouchHandler.setPlayerRef(gamePcs.mPlayer);
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
                    audio.playClick(audio.sounds, 0);
                }
                mCollision.checkCollision(gamePcs, gameProgress, mParticleSystem);
                if(gameProgress.getGameStatus()){
                    gameOver();
                    // after this func, need to clear all elements in arraylists
//                    gameProgress.reset(gamePcs, factory, objType);

                }
                if(mCollision.checkEnemiesRemaining()){
                    gameProgress.startNextLevel(gamePcs, factory, objType);
                    mCollision.resetEnemies();
                }
            }
            // on pause..
            else if(userPause){
                audio.pause();
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
        // synchronize call to touch handler for player's angle calculation.
        mTouchHandler.requestAngleUpdate();
        // EXPLOSION
        mParticleSystem.update(gameClock.getTimeElapsed() , display);

        // shooting action each update. (FIXME: PUT THIS SHOOT RESULT AFTER PLAYER UPDATE..)
        Laser shootResult = gamePcs.mPlayer.shoot(gameClock.getTimeElapsed(), factory);

        if(DEBUGGING) {
            Log.d("update: ", "Shoot time total: " + gamePcs.mPlayer.getTimer());
        }

        if(shootResult != null) {
            gamePcs.mPlayerLasers.add(shootResult);
        }

        // PLAYER
//        if()
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
        Receive touch event.
        Three possible events:
            - Enable Mobility
            - Rotation based on pixel drag per update cycle.
            - Pause menu area touched.
    */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d("TouchEvent:", event.actionToString(event.getAction()) + " Index: " + event.getActionIndex());
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: // First touch point
            case MotionEvent.ACTION_POINTER_DOWN: // Additional touch pointer initiated
                mTouchHandler.inputEvent(event);
                break;
            case MotionEvent.ACTION_MOVE: // A pointer has moved.
                mTouchHandler.updateRotation(event); // Only thing we care in touch event when it comes to move is rotate.
                break;
            case MotionEvent.ACTION_POINTER_UP:  // A touch pointer has been lifted.
            case MotionEvent.ACTION_UP: // Last pointer up action
                mTouchHandler.removeEvent(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                mTouchHandler.reset(); // This case exist for just incase a cancel action is received
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

    private void gameOver(){
        audio.pause();
        gameView.drawGameOver(gameProgress);
        gameClock.frameStop();
        while(!userRestart){
            gameClock.frameStart();
            if(userRestart){
                break;
            }
            gameClock.frameStop();
        }
        // pause here until user presses screen to resume for a new game..
        // nest "startNewGame()" into if statement conditioned on pause until touch
        userRestart = false;
        startNewGame();
    }
}

