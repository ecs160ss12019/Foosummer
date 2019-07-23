package com.gamecodeschool.asteroidsfs;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Bitmap;

public class Asteroid extends SpaceObject {
    private int size;       //Define different size asteroids
    private boolean hit;
    private Bitmap bitmap;

    public Asteroid(PointF position, float width, float height, float velocityX, float velocityY) {
        super(position, width, height, velocityX, velocityY);
        this.hit = false;
    }

    // Using this constructor does not generate a RectF in the SpaceObject.
    public Asteroid(int angle, int xPos, int yPos, float velocityMagnitude, float asteroidSize) {
        super((float)xPos, (float)yPos, angle, velocityMagnitude, asteroidSize);
        this.hit = false;
//        bitmap = GameView.createAsteroidBitmap((int)asteroidSize*2);
    }


    public Bitmap getBitmap() {
        return bitmap;
    }
    public float getHorizontalPos() {
        return super.getHitbox().left;
    }
    public float getVerticalPos() {
        return super.getHitbox().top;
    }

    // Draw asteroid w/ picture
    public void draw(Canvas canvas, Bitmap asteroidsBitmap) {
        Paint myPaint = new Paint();
        canvas.drawBitmap(asteroidsBitmap, 100, 100, myPaint);
    }


    public void increaseVelocity() {
        // increase the speed by 20%
        super.setVelocityX(super.getVelocityX() * 1.2f);
        super.setVelocityY(super.getVelocityY() * 1.2f);
    }


    // Detect collision with a laser object
    // Has the laser hit the asteroid?
    private void detectCollisions(RectF laserPosition){
        hit = RectF.intersects(laserPosition, super.getHitbox());
    }

    // Large asteroid breaks into medium pieces
    // medium piece breaks into small pieces
    void disintegrate () {
        if(size == 2) {

        } else if (size == 1) {

        }
    }

}