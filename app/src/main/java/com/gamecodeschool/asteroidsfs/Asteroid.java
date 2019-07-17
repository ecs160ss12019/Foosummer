package com.gamecodeschool.asteroidsfs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.Random;

public class Asteroid extends SpaceObject {
    private boolean hit;

    /*Define different size asteroids*/

    // The constructor is called in AsteroidsGame:
    // asteroid[i] = new Asteroid(xPosition, yPosition, width, height, xVelocity, yVelocity);
    public Asteroid(float xPosition, float yPosition, float width, float height, float xVelocity, float yVelocity) {
        super(xPosition, yPosition, width, height, xVelocity, yVelocity);
        this.hit = false;
    }


    // Draw asteroid
    @Override
    public void draw(Canvas myCanvas) {
        Paint myPaint = new Paint();
        myPaint.setColor(Color.argb(255, 205, 160, 245));
        myCanvas.drawRect(super.getRect(), myPaint);
    }


    public void increaseVelocity() {
        // increase the speed by 10%
        super.setXVelocity(super.getXVelocity() * 1.1f);
        super.setYVelocity(super.getYVelocity() * 1.1f);
    }


    // Detect collision with a laser object
    // Has the laser hit the asteroid?
    private void detectCollisions(RectF laserPosition){
        hit = RectF.intersects(laserPosition, super.getRect());
    }

    // Large asteroid breaks into medium pieces
    // medium piece breaks into small pieces
    void disintegrate(){

    }

}

