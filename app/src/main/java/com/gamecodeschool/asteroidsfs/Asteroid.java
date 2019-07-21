package com.gamecodeschool.asteroidsfs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import java.util.Random;

public class Asteroid extends SpaceObject {
    private int size;       //Define different size asteroids
    private boolean hit;
    private Bitmap bitmap;


    public Asteroid(float positionX, float positionY, float width, float height, float velocityX, float velocityY) {
        super(positionX, positionY, width, height, velocityX, velocityY);
        this.hit = false;
    }

    // Using this constructor does not generate a RectF in the SpaceObject.
    public Asteroid(int angle, int xPos, int yPos, float velocityMagnitude, float asteroidSize) {
        super((float)xPos, (float)yPos, angle, velocityMagnitude, asteroidSize);
        this.hit = false;
//        bitmap = GameView.createAsteroidBitmap((int)asteroidSize*2);
    }


    // Draw asteroid
    @Override
    public void draw(Canvas myCanvas) {
        Paint myPaint = new Paint();
        myPaint.setColor(Color.argb(255, 205, 160, 245));
        myCanvas.drawRect(super.getHitbox(), myPaint);
    }
    public void draw(Canvas canvas, Bitmap asteroidsBitmap) {
        Paint myPaint = new Paint();
        canvas.drawBitmap(asteroidsBitmap, 100, 100, myPaint);
    }


    public void increaseVelocity() {
        // increase the speed by 10%
        super.setVelocityX(super.getVelocityX() * 1.1f);
        super.setVelocityY(super.getVelocityY() * 1.1f);
    }


    // Detect collision with a laser object
    // Has the laser hit the asteroid?
    private void detectCollisions(RectF laserPosition){
        hit = RectF.intersects(laserPosition, super.getHitbox());
    }

    // Large asteroid breaks into medium pieces
    // medium piece breaks into small pieces
    void disintegrate() {
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
}

