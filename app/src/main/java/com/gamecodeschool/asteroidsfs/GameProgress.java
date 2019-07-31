package com.gamecodeschool.asteroidsfs;


import android.util.Log;
import android.widget.Space;
import android.graphics.PointF;
import java.util.Random;

/* *
 * GameProgress's responsibilities
 * - Track user score, life, level, and any other progress related data.
 * - The dsatas are reset when required (starting a new game)
 * - We should store things such as score constants on this object.
 */
public class GameProgress {
    final private int initialScore = 0;
    final private int initialLife = 100;
    final private int initialLevel = 1;
    final private int initialNumOpps = 1;
    final private int initialNumAsteroids = 2;
    final private int initialNumPowerUps = 1;


    // track user score and lives
    private int myScore = initialScore;
    private int highScore = initialScore;
    private int myLives = initialLife; // abstract this to UserShip class?
    private int level = initialLevel; // we increment each time the player clears a level.
    private boolean gameOver = false;
    private int numOpps = initialNumOpps;
    private int numAsteroids = initialNumAsteroids;
    private int numPowerUps = initialNumPowerUps;
    private Random rand = new Random();


    final private int baseScore = 50; // This is the score multiplier for each hostile object player destroys.
    final public int OppMultiplier = 5;

    public int getLevel() {
        return level;
    }

    public int getMyScore() {
        return myScore;
    }

    public int getMyLives() {
        return myLives;
    }

    public boolean getGameStatus() {
        return gameOver;
    }

    // resets by setting our game progress variable to initial lvl.
    public void reset(SObjectsCollection gamePcs, ObjectFactory factory, SpaceObjectType objType) {
        myScore = initialScore;
        myLives = initialLife;
        level = initialLevel;
        numOpps = initialNumOpps;
        numAsteroids = initialNumAsteroids;
        gameOver = false;
        generateEnemies(level, gamePcs, factory, objType);
    }

    public void decLife() {
        myLives -= 1;

        if (myLives < 1) {
            setHighScore();
            gameOver = true;
//            reset();
        } else {
            gameOver = false;
        }
//        Log.d("GameProgress", "myLives after decrementing: " + myLives);
    }


    /*
        We will take an argument regarding score multiplier.
        Then we update by adding the multiplied basescore into our score.
     */
    public void updateScore(int scoreMultiplier) {
        myScore += baseScore * scoreMultiplier;
    }

    public void startNextLevel(SObjectsCollection gamePcs,
                               ObjectFactory factory, SpaceObjectType objType) {
        // call this function when all asteroids and opponents are destroyed
        // increment level counter: currLevel ++;
        level++;
        generateEnemies(level, gamePcs, factory, objType);


        // asteroidsElim == false;
        // oppsElim == false;
    }

    public void generateEnemies(int level, SObjectsCollection gamePcs,
                                ObjectFactory factory, SpaceObjectType objType) {
        // number of opponents and asteroids
        // for the corresponding level
        // numAsteroids = currLevel * (numAsteroids multiplier)
        // numOpps = currLevel * (numOpps multiplier)
        if (level > initialLevel) {
            numAsteroids += 2;
        }
        if (level % 3 == 0) {
            numOpps += 1;
        }
        // add boosts every 5 levels?

        for (int i = 0; i < numAsteroids; i++) {
            gamePcs.mAsteroids.add((Asteroid) factory.getSpaceObject(objType.ASTEROID));
        }


        int numShooters = rand.nextInt(numOpps);
        int numSuiciders = numOpps - numShooters;
        for (int i = 0; i < numOpps; i++) {
            if(level < 3){
                SpaceObject opp = factory.getSpaceObject(objType.OPPONENT);
                gamePcs.mOpponents.add((Opponent) opp);
            }
            else if(Math.random() > 0.35){
                SpaceObject opp = factory.getSpaceObject(objType.OPPONENT);
                gamePcs.mOpponents.add((Opponent) opp);
            }
            else {gamePcs.mSuiciders.add((Suicider) factory.getSpaceObject(objType.SUICIDER));}
            //gamePcs.mSuiciders.add((Suicider) factory.getSpaceObject(objType.SUICIDER));
        }

    }
//    public int getNumAsteroids(){
//        return numAsteroids;
//    }

    private void setHighScore() {
        if(myScore > highScore){
            highScore = myScore;
        }
    }

    public int getHighScore(){return highScore;}

}
