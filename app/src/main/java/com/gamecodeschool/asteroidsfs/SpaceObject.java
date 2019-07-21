package com.gamecodeschool.asteroidsfs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class SpaceObject {

    private RectF hitbox;        // Give access to precise position and size of object
    private float velocityX;
    private float velocityY;
    private float width;
    private float height;

    // new variables to control circular hit box. Only really need position and object radius.
    private float posX;
    private float poxY;
    private float hitBoxRadius;


    public SpaceObject(float positionX, float positionY, float width, float height, float velocityX, float velocityY) {
        float halfWidth = width/2;
        float halfHeight = height/2;
        this.hitbox = new RectF(positionX-halfWidth, positionY-halfHeight, positionX+halfWidth,positionY+halfHeight);
        this.width = width;
        this.height = height;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public SpaceObject(float positionX, float positionY, int angle, float velocityMagnitude, float hitCircleSize) {
        float sideLength = hitCircleSize / 2;
        hitbox = new RectF(positionX-sideLength, positionY-sideLength, positionX+sideLength,positionY+sideLength);
        width = hitCircleSize;
        height = width;
        velocityX = velocityMagnitude * (float) Math.cos(angle);
        velocityY = velocityMagnitude * (float) Math.sin(angle);
    }


    public RectF getHitbox() {
        return hitbox;
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
    public float getVelocityX() {
        return velocityX;
    }
    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }
    public float getVelocityY() {
        return velocityY;
    }
    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }


    // Draw object
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 255, 255, 255));
        canvas.drawRect(hitbox, paint);
    }


    // Update the object's position (called each frame/loop)
    // Move the object based on the velocity and current frame rate (mFPS)
    public void update(long fps, int x, int y){
        // Move the top left corner
        hitbox.left = hitbox.left + (velocityX / fps) ;
        hitbox.top = hitbox.top + (velocityY / fps) ;

        // If object travels off the screen -> wrap around
        if (hitbox.left < 0)
            hitbox.left = x;
        if (hitbox.left > x)
            hitbox.left = 0;
        if (hitbox.top < 0)
            hitbox.top = y;
        if (hitbox.top > y)
            hitbox.top = 0;

        // Match up the bottom right corner based on the size of the ball
        hitbox.right = hitbox.left + width;
        hitbox.bottom = hitbox.top + height;
    }

    // Uploaded 
    public void update(long time, final Display screen) {
        hitbox.left = hitbox.left + (velocityX * time) ;
        hitbox.top = hitbox.top + (velocityY * time) ;

        // If object travels off the screen -> wrap around
        if (hitbox.left < 0)
            hitbox.left = screen.width;
        if (hitbox.left > screen.width)
            hitbox.left = 0;
        if (hitbox.top < 0)
            hitbox.top = screen.height;
        if (hitbox.top > screen.height)
            hitbox.top = 0;

        // Match up the bottom right corner based on the size of the ball
        hitbox.right = hitbox.left + width;
        hitbox.bottom = hitbox.top + height;
    }

}
