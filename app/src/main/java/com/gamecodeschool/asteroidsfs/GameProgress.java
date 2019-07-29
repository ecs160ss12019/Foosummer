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
    final private int initialLife = 3;
    final private int initialLevel = 1;
    final private int initialNumOpps = 1;
    final private int initialNumAsteroids = 2;
    final private int initialNumPowerUps = 1;




    // track user score and lives
    private int myScore = initialScore;
    private int myLives = initialLife; // abstract this to UserShip class?
    private int level = initialLevel; // we increment each time the player clears a level.
    private boolean gameOver = false;
    private int numOpps = initialNumOpps;
    private int numAsteroids = initialNumAsteroids;
    private int numPowerUps = initialNumPowerUps;


    final private int baseScore = 50; // This is the score multiplier for each hostile object player destroys.

    public int getLevel() {
        return level;
    }

    public int getMyScore() {
        return myScore;
    }

    public int getMyLives() {
        return myLives;
    }

    public boolean getGameStatus() {return gameOver;}

    // resets by setting our game progress variable to initial lvl.
    public void reset(SObjectsCollection gamePcs, ObjectFactory factory, SpaceObjectType objType) {
        myScore = initialScore;
        myLives = initialLife;
        level = initialLevel;
        numOpps = initialNumOpps;
        numAsteroids = initialNumAsteroids;
        generateEnemies(level, gamePcs, factory, objType);
    }


    public void decLife() {
        myLives -= 1;

        if (myLives <= 0) {
            gameOver = true;
//            reset();
        }
        else {
            gameOver = false;
        }
        Log.d("GameProgress", "myLives after decrementing: " + myLives);


    }




    /*
        We will take an argument regarding score multiplier.
        Then we update by adding the multiplied basescore into our score.
     */
    public void updateScore(int scoreMultiplier) { myScore += baseScore * scoreMultiplier; }

    public void startNextLevel(SObjectsCollection gamePcs,
                               ObjectFactory factory, SpaceObjectType objType){
        // call this function when all asteroids and opponents are destroyed
        // increment level counter: currLevel ++;
        level ++;
        generateEnemies(level, gamePcs, factory, objType);


        // asteroidsElim == false;
        // oppsElim == false;
    }

    public void generateEnemies(int level, SObjectsCollection gamePcs,
                                ObjectFactory factory, SpaceObjectType objType){
        // number of opponents and asteroids
        // for the corresponding level
        // numAsteroids = currLevel * (numAsteroids multiplier)
        // numOpps = currLevel * (numOpps multiplier)
        if(level > initialLevel) {
            numAsteroids += 2;
        }
        if(level % 3 == 0){
            numOpps += 1;
        }
        // add boosts every 5 levels?

        for(int i = 0; i < numAsteroids; i++) {
            gamePcs.mAsteroids.add((Asteroid)factory.getSpaceObject(objType.ASTEROID));
        }
        for(int i = 0; i < numOpps; i++) {

            // modify the opponent coordinates to spawn away from player
            SpaceObject temp = factory.getSpaceObject(objType.OPPONENT);
            PointF playerPos = gamePcs.mPlayer.getPosition();

            // try to randomize this more!!
            temp.position.x += gamePcs.mPlayer.getPosition().x;
            temp.position.y += gamePcs.mPlayer.getPosition().y;


            gamePcs.mOpponents.add((Opponent)temp);
        }


    }


//    public int getNumAsteroids(){
//        return numAsteroids;
//    }

}
