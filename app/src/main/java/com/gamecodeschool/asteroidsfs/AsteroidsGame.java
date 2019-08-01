package com.gamecodeschool.asteroidsfs;

import static com.gamecodeschool.asteroidsfs.GameConfig.NUM_BLOCKS_WIDE;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


class AsteroidsGame extends SurfaceView implements Runnable{

    int blockSize;

    // Toggle for debugging
    static final boolean DEBUGGING = false;

    // Drawing objects
    private SurfaceHolder myHolder;
    private Display display;

    // The Thread and two control variables
    private Thread myGameThread = null;
    // This volatile variable can be accessed
    // from inside and outside the thread
    private volatile boolean nowPlaying;
    private boolean nowPaused = false;

    // Game objects
    private GameProgress gameProgress;
    private ObjectFactory factory;
    private GameView gameView;
    private CollisionEngine mCollision;
    private SObjectsCollection gamePcs;
    private Audio audio;
    private TouchHandler mTouchHandler;
    private ParticleSystem mParticleSystem;

    // System clock
    private GameClock gameClock;

    // Enum used for object creation.
    SpaceObjectType objType;
    // Used to distinguish opponent types in object factory
    SpaceObjectType oppType;

    // Distinguishes user pause vs "pause" when initializing the game
    private boolean userPause = false;
    // determines when user has chosen a ship to start
    private boolean userStart = false;
    // determines if user presses screen to restart after game over
    private boolean userRestart = false;


    public AsteroidsGame(Context context, int x, int y) {
        // Calls parent class constructor of SurfaceView
        super(context);
        display = new Display(x, y);

        blockSize = x / NUM_BLOCKS_WIDE;

        // Initialize the objects ready for start of game
        // getHolder is a method of Surfaceview
        myHolder = getHolder();
        gameView = new GameView(context, myHolder, display);
        factory = new ObjectFactory(display);
        gameProgress = new GameProgress();
        gamePcs = new SObjectsCollection(display);
        mCollision = new CollisionEngine();
        mParticleSystem = new ParticleSystem ();
        mParticleSystem.init(1000, display);
        mTouchHandler = new TouchHandler(display);
        gameClock = new GameClock();
        gamePcs.mBlockSize = blockSize;
        audio = new Audio(context);
        startNewGame();
    }


    /*
        When we start the game we reset the game state such as level
        initial meteor count.
        and clear all lasers and enemy space ships.
    */
    private void startNewGame() {

        mParticleSystem.mParticles.removeAll(mParticleSystem.mParticles);
        gamePcs.mMineralPowerUps.removeAll(gamePcs.mMineralPowerUps);
        gamePcs.mAsteroids.removeAll(gamePcs.mAsteroids);
        gamePcs.mOpponents.removeAll(gamePcs.mOpponents);
        gamePcs.mPlayerLasers.removeAll(gamePcs.mPlayerLasers);
        gamePcs.mOpponentLasers.removeAll(gamePcs.mOpponentLasers);
        gamePcs.mSuiciders.removeAll(gamePcs.mSuiciders);
        gamePcs.mShooters.removeAll(gamePcs.mShooters);

        gamePcs.mPlayer = (Player)factory.getSpaceObject(objType.PLAYER);

        mTouchHandler.setPlayerRef(gamePcs.mPlayer);
        gameProgress.reset(gamePcs, factory, oppType);
        factory.reset();
    }


    @Override
    public void run() {
        while(nowPlaying) {
            // What time is it now at the start of the loop?
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
                }
                // Return true if all enemies are destroyed then advance to next level
                if(mCollision.checkEnemiesRemaining()){
                    gameProgress.startNextLevel(gamePcs, factory, objType);
                    mCollision.resetEnemies();
                }
            }
            // Pause music and game frame on user pause
            else if(userPause){
                audio.pause();
                gameView.draw(gamePcs, gameProgress, userPause, mParticleSystem);
                gameClock.frameStop();

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
    }


    private void update() {

        // Synchronize call to touch handler for player's angle calculation.
        mTouchHandler.requestAngleUpdate();

        // EXPLOSION
        mParticleSystem.update(gameClock.getTimeElapsed());

        // PLAYER
        gamePcs.mPlayer.update(gameClock.getTimeElapsed(), display);
        gamePcs.mPlayer.configMatrix(gameView.getBitmapDim(), blockSize);

        // PLAYER LASER
        Laser shootResult = gamePcs.mPlayer.shoot(gameClock.getTimeElapsed(), factory);

        if(DEBUGGING) {
            Log.d("update: ", "Shoot time total: " + gamePcs.mPlayer.getTimer());
        }

        if(shootResult != null) {
            gamePcs.mPlayerLasers.add(shootResult);
        }

        // PLAYER LASER we call a different update since this has a boolean attached to it.
        for(int i = 0; i < gamePcs.mPlayerLasers.size(); i++) {
            if(gamePcs.mPlayerLasers.get(i).updateL(gameClock.getTimeElapsed(), display)) {
                gamePcs.mPlayerLasers.remove(i);
                i--;
            }
        }

        // ASTEROIDS
        for(int i = 0 ; i < gamePcs.mAsteroids.size() ; i++) {
            gamePcs.mAsteroids.get(i).update(gameClock.getTimeElapsed(), display);
        }

        // POWER UPS
        if(mCollision.dropPowerUp){
            gamePcs.mMineralPowerUps.add(factory.getPowerUp(mCollision.getDropPos()));
        }
        for(int i = 0; i < gamePcs.mMineralPowerUps.size(); i++) {
            gamePcs.mMineralPowerUps.get(i).update(gameClock.getTimeElapsed(), display);
        }

        // OPPONENT
        Laser oppShootResult;

        for(int i = 0; i < gamePcs.mOpponents.size(); i++) {
            oppShootResult = gamePcs.mOpponents.get(i).attack(gameClock.getTimeElapsed(),
                    factory, gamePcs.mPlayer.getPosition());

            if(oppShootResult != null) {
                gamePcs.mOpponentLasers.add(oppShootResult);

                // Shooter moves away in a different angle after each shot
                gamePcs.mOpponents.get(i).updateOppPosition(true);
            }

            // Update position of both Shooters and Suiciders
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
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: // First touch point
            case MotionEvent.ACTION_POINTER_DOWN: // Additional touch pointer initiated
                nowPaused = mTouchHandler.inputEvent(event, nowPaused);
                userPause = nowPaused; // Synchronize userPause and nowPause after initial start.

                if(gameProgress.getGameStatus()) // Allows user to touch and restart
                    userRestart = true;
                break;
            case MotionEvent.ACTION_MOVE: // A pointer has moved.
                mTouchHandler.updateRotation(event); // Only thing we care in touch event when it comes to move is rotate.
                break;
            case MotionEvent.ACTION_POINTER_UP:  // A touch pointer has been lifted.
            case MotionEvent.ACTION_UP: // Last pointer up action
                mTouchHandler.removeEvent(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                mTouchHandler.reset(); // This case exist for just in case a cancel action is received
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
        // Pause here until user presses screen to resume for a new game..
        // nest "startNewGame()" into if statement conditioned on pause until touch
        userRestart = false;
        startNewGame();
    }

    private void startMenu() {
        audio.pause();
        gameView.drawShipMenu();
        gameClock.frameStop();
        while(!userStart) {
            gameClock.frameStart();
            if(userStart) break;
            gameClock.frameStop();
        }
        // pause here until user presses screen to resume for a new game..
        // nest "startNewGame()" into if statement conditioned on pause until touch
        userStart = false;
    }
}

