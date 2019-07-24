package com.gamecodeschool.asteroidsfs;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class Opponent extends SpaceObject{
    private int size;
    private boolean hit;
    private Bitmap bitmap;

    private RectF mRect;
    private float mLength;
    private float mHeight;
    private float mXCoord;
    private float mYCoord;
    private float maxXCoord;
    private float maxYCoord;
    private float mXVelocity;
    private float mYVelocity;
    private float movementMagnitude;
    private float mPlayerSpeed;
    float xVelocity;
    float yVelocity;

    public Opponent(PointF position, float width, float height, float velocityX, float velocityY) {
        super(position, width, height, velocityX, velocityY);
        this.hit = false;

        mLength = width / 25;
        mHeight = height / 25;
        mXCoord = width / 2;
        mYCoord = (height / 2);
        mPlayerSpeed = width / 2;

        //mXVelocity = 0;
        //mYVelocity = 0;
        movementMagnitude = 0;
        xVelocity = velocityX;
        yVelocity = velocityY;

        mRect = new RectF(mXCoord, mYCoord, mXCoord + mLength - 15, mYCoord + mLength - 15);
    }

    public RectF getRect() {
        return mRect;
    }

    public void update(long fps, int x, int y){
        //movementMagnitude * (float) Math.cos(degree * 0.0174533);

        // Move the top left corner
        mRect.left = mRect.left + (mXVelocity/fps);
        mRect.top = mRect.top + (mYVelocity/fps);

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
        mRect.right = mRect.left + mLength;
        mRect.bottom = mRect.top + mHeight;
    }


    // Draw opponent w/ picture
    public void draw(Canvas canvas, Bitmap OpponentBitmap) {
        Paint myPaint = new Paint();
        canvas.drawBitmap(OpponentBitmap, 100, 100, myPaint);
    }

}
