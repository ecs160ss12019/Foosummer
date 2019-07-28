package com.gamecodeschool.asteroidsfs;

public class GameClock {
    // variables
    private long frameStartTime;
    private long timeThisFrame;
    private long timeElapsed;


    // methods
    public void frameStart(){
        //What time is it now at the start of the loop?
        frameStartTime = System.currentTimeMillis();
    }

    public void frameStop(){
        // How long did this frame/loop take?
        // Store the answer in timeThisFrame
        timeThisFrame = System.currentTimeMillis() - frameStartTime;
        timeElapsed = timeThisFrame;
    }

    public long getTimeElapsed(){
        return timeElapsed;
    }
}
