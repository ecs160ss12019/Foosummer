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
        myCanvas.drawRect(super.getHitbox(), myPaint);
    }


    // Update the laser position (called each frame/loop)
    // Allow laser to travel off the screen
    public void update(long fps) {
        // Move the top left corner
        super.getHitbox().left = super.getHitbox().left + (super.getVelocityX() / fps) ;
        super.getHitbox().top = super.getHitbox().top + (super.getVelocityY() / fps) ;
        // Match up the bottom right corner based on the size of the ball
        super.getHitbox().right = super.getHitbox().left + super.getWidth();
        super.getHitbox().bottom = super.getHitbox().top + super.getHeight();
    }


    public void increaseVelocity() {
        // increase the speed by 10%
        super.setVelocityX(super.getVelocityX() * 1.1f);
        super.setVelocityY(super.getVelocityY() * 1.1f);
    }
}