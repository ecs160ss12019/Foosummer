package com.gamecodeschool.asteroidsfs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.Random;

public class PowerUps {
    private RectF mRect;
    float width, height, xVelocity, yVelocity;
    int hitsLeft;

    public PowerUps(float xPosition, float yPosition, float width, float height, int hitsLeft,
                    float xVelocity, float yVelocity) {
        this.mRect = new RectF(xPosition, yPosition, xPosition + width, yPosition + height);
        this.width = width;
        this.height = height;
        this.hitsLeft = hitsLeft;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;


//    public void move(int screenWidth, int screenHeight){
//        something with radius and x.y
//
    }
    public RectF getRect() {
        return mRect;
    }

    public void update(long fps, int x, int y){
        // Move the top left corner
        mRect.left = mRect.left + (xVelocity/fps);
        mRect.top = mRect.top + (yVelocity/fps);

        // If the powerup travels off the screen -> wrap around
        if (mRect.left < 0) {
            mRect.left = x;
        }
        if (mRect.left > x) {
            mRect.left = 0;
        }
        if (mRect.top < 0) {
            mRect.top = y;
        }
        if (mRect.top > y) {
            mRect.top = 0;
        }

        // Match up the bottom right corner based on the size of the ball
        mRect.right = mRect.left + width;
        mRect.bottom = mRect.top + height;
    }

    public void draw(Canvas myCanvas) {
        Paint myPaint = new Paint();
        myPaint.setColor(Color.argb(255, 205, 210, 120));
        myCanvas.drawRect(mRect, myPaint);
    }




    public int getHitsLeft(){
        //checks hitpoints to determine whether it is destroyed
        return hitsLeft;
    }


    public void shootFaster(Player spaceShip){
        //do something to alter shooting speed
    }


    public void moreLives(Player spaceShip){
        //spaceShip.setLives += 1 or something
    }
}
