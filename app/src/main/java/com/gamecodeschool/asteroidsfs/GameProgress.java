package com.gamecodeschool.asteroidsfs;


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

    // track user score and lives
    private int myScore = initialScore;
    private int myLives = initialLife; // abstract this to UserShip class?
    private int level = initialLevel; // we increment each time the player clears a level.

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

    // resets by setting our game progress variable to initial lvl.
    public void reset() {
        myScore = initialScore;
        myLives = initialLife;
        level = initialLevel;
    }

    // This function returns false when player life is <= 0
    // We know the game is over if this function returns false.
    public boolean decLife() {
        myLives -= 1;
        if (myLives > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /*
        We will take an argument regarding score multiplier.
        Then we update by adding the multiplied basescore into our score.
     */
    public void updateScore(int scoreMultiplier) {
        myScore += baseScore * scoreMultiplier;
    }

}
