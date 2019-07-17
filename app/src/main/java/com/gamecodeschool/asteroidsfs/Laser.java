package com.gamecodeschool.asteroidsfs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Laser extends SpaceObject {

    public Laser(float xPosition, float yPosition, float width, float height, float xVelocity, float yVelocity) {
        super(xPosition, yPosition, width, height, xVelocity, yVelocity);
    }


    @Override
    public void draw(Canvas myCanvas) {
        Paint myPaint = new Paint();
        myPaint.setColor(Color.argb(255, 75, 180, 250));
        myCanvas.drawRect(super.getRect(), myPaint);
    }


    // Update the laser position (called each frame/loop)
    // Move the asteroid based on the velocity and current frame rate (mFPS)
    public void update(long fps) {
        // Move the top left corner
        super.getRect().left = super.getRect().left + (super.getXVelocity() / fps) ;
        super.getRect().top = super.getRect().top + (super.getYVelocity() / fps) ;
        // Match up the bottom right corner based on the size of the ball
        super.getRect().right = super.getRect().left + super.getWidth();
        super.getRect().bottom = super.getRect().top + super.getHeight();
    }


    public void increaseVelocity() {
        // increase the speed by 10%
        super.setXVelocity(super.getXVelocity() * 1.1f);
        super.setYVelocity(super.getYVelocity() * 1.1f);
    }
}