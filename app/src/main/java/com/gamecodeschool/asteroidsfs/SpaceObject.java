package com.gamecodeschool.asteroidsfs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class SpaceObject {

    private RectF mRect;        // Give access to precise position and size of object
    private float xVelocity;
    private float yVelocity;
    private float width;
    private float height;


    public SpaceObject(float xPosition, float yPosition, float width, float height, float xVelocity, float yVelocity) {
        this.mRect = new RectF(xPosition, yPosition, xPosition+width,yPosition+height);
        this.width = width;
        this.height = height;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }


    // Return a reference to mRect to AsteroidsGame
    public RectF getRect() {
        return mRect;
    }
    public float getWidth() {
        return width;
    }
    public void setWidth(float width) {
        this.width = width;
    }
    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }
    public float getXVelocity() {
        return xVelocity;
    }
    public void setXVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }
    public float getYVelocity() {
        return yVelocity;
    }
    public void setYVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }


    // Draw object
    public void draw(Canvas myCanvas) {
        Paint myPaint = new Paint();
        myPaint.setColor(Color.argb(255, 255, 255, 255));
        myCanvas.drawRect(mRect, myPaint);
    }


    // Update the object's position (called each frame/loop)
    // Move the object based on the velocity and current frame rate (mFPS)
    public void update(long fps, int x, int y){
        // Move the top left corner
        mRect.left = mRect.left + (xVelocity / fps) ;
        mRect.top = mRect.top + (yVelocity / fps) ;

        // If asteroid travels off the screen -> wrap around
        if (mRect.left < 0)
            mRect.left = x;
        if (mRect.left > x)
            mRect.left = 0;
        if (mRect.top < 0)
            mRect.top = y;
        if (mRect.top > y)
            mRect.top = 0;

        // Match up the bottom right corner based on the size of the ball
        mRect.right = mRect.left + width;
        mRect.bottom = mRect.top + height;
    }

}
