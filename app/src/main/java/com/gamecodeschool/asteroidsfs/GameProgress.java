package com.gamecodeschool.asteroidsfs;


import static com.gamecodeschool.asteroidsfs.GameConfig.INITIAL_SCORE;
import static com.gamecodeschool.asteroidsfs.GameConfig.INITIAL_LIFE;
import static com.gamecodeschool.asteroidsfs.GameConfig.INITIAL_LEVEL;
import static com.gamecodeschool.asteroidsfs.GameConfig.INITIAL_NUM_OPPONENTS;
import static com.gamecodeschool.asteroidsfs.GameConfig.INITIAL_NUM_ASTEROIDS;

/* *
 * GameProgress's responsibilities
 * - Track user score, life, level, and any other progress related data.
 * - The dsatas are reset when required (starting a new game)
 * - We should store things such as score constants on this object.
 */
public class GameProgress {

    // track user score and lives
    private int myScore = INITIAL_SCORE;
    private int highScore = INITIAL_SCORE;
    private int myLives = INITIAL_LIFE;
    private int level = INITIAL_LEVEL; // we increment each time the player clears a level.
    private boolean gameOver = false;
    private int numOpps = INITIAL_NUM_OPPONENTS;
    private int numAsteroids = INITIAL_NUM_ASTEROIDS;

    final private int baseScore = 50; // This is the score multiplier for each hostile object player destroys.
    final public int OppMultiplier = 5;

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
        myScore = INITIAL_SCORE;
        myLives = INITIAL_LIFE;
        level = INITIAL_LEVEL;
        numOpps = INITIAL_NUM_OPPONENTS;
        numAsteroids = INITIAL_NUM_ASTEROIDS;
        gameOver = false;
        generateEnemies(level, gamePcs, factory, objType);
    }

    public void decLife() {
        myLives -= 1;

        if (myLives < 1) {
            setHighScore();
            gameOver = true;
        } else {
            gameOver = false;
        }
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
        // Called when all asteroids and opponents are destroyed
        level++;
        generateEnemies(level, gamePcs, factory, objType);
    }

    public void generateEnemies(int level, SObjectsCollection gamePcs,
                                ObjectFactory factory, SpaceObjectType objType) {
        if (level > INITIAL_LEVEL) {
            numAsteroids += 2;
        }
        if (level % 3 == 0) {
            numOpps += 1;
        }

        for (int i = 0; i < numAsteroids; i++) {
            gamePcs.mAsteroids.add((Asteroid) factory.getSpaceObject(objType.ASTEROID));
        }


        for (int i = 0; i < numOpps; i++) {
            if(level < 3 || Math.random() > 0.35){
                gamePcs.mOpponents.add((Opponent) factory.getSpaceObject(objType.SHOOTER));
                gamePcs.mOpponents.get(i).setOppType(objType.SHOOTER);
            }
            else {
                gamePcs.mOpponents.add((Opponent) factory.getSpaceObject(objType.SUICIDER));
                gamePcs.mOpponents.get(i).setOppType(objType.SUICIDER);
            }
        }


    }

    private void setHighScore() {
        if(myScore > highScore){
            highScore = myScore;
        }
    }

    public int getHighScore(){return highScore;}

}
