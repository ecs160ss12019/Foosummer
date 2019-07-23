package com.gamecodeschool.asteroidsfs;

import java.util.ArrayList;

/* 
 * For now this will contain things that is necessary for the gameview to draw.
 * This will contain arguments necessary for the GameView to render the game objects.
 */
public class Render {
        Player mPlayer;
        int mBlockSize;
        ArrayList<Asteroid> mAsteroids;
        ArrayList<Laser> mPlayerLaser;
        // ArrayList<Laser> mEnemeyLaser; // to be implemented once we have enemy ship.
        PowerUps mMineralPowerUps[];
}
