package com.gamecodeschool.asteroidsfs;

import java.util.ArrayList;

/* 
 * For now this will contain things that is necessary for the gameview to draw.
 * This will contain arguments necessary for the GameView to render the game objects.
 */
public class SObjectsCollection {
        Player mPlayer;
        int mBlockSize;
        ArrayList<Asteroid> mAsteroids;
        ArrayList<Laser> mPlayerLasers; // to be implemented once we have enemy ship.
        ArrayList<PowerUps> mMineralPowerUps;
        ArrayList<Opponent> mOpponents;

        SObjectsCollection(Display display) {
                mPlayer          = new Player(display.width, display.height);
                mAsteroids       = new ArrayList<Asteroid>();
                mPlayerLasers    = new ArrayList<Laser>();
                mMineralPowerUps = new ArrayList<PowerUps>();
                mOpponents       = new ArrayList<Opponent>();
        }
}
