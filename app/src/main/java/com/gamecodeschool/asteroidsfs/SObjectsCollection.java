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
        ArrayList<Suicider> mSuiciders;
        ArrayList<Shooter> mShooters;
        ArrayList<Laser> mOpponentLasers;


        SObjectsCollection(Display display) {
                mAsteroids       = new ArrayList<Asteroid>();
                mPlayerLasers    = new ArrayList<Laser>();
                mMineralPowerUps = new ArrayList<PowerUps>();
                mOpponents       = new ArrayList<Opponent>();
                mOpponentLasers = new ArrayList<Laser>();
                mSuiciders = new ArrayList<Suicider>();
                mShooters = new ArrayList<Shooter>();
        }
}
