package com.gamecodeschool.asteroidsfs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.PointF;

public class SpaceObject {

    private PointF position;
    private RectF hitbox;        // Give access to precise position and size of object
    private float velocityX;
    private float velocityY;
    private float width;
    private float height;

    private float velMagnitude;
    private float hitRadius;
    private double angle; // in radians!


    public SpaceObject(PointF position, float width, float height, float velocityX, float velocityY) {
        this.position = position;
        this.hitbox = new RectF(position.x-width/2, position.y-height/2, position.x+width/2,position.y+height/2);
        this.width = width;
        this.height = height;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public SpaceObject(PointF pos, double angle, float velocityMagnitude, float hitCircleSize) {
        // BEGIN LEGACY CODE. Needs to be phased out for later stage.
        float sideLength = hitCircleSize / 2;
        hitbox = new RectF(pos.x - sideLength, pos.y - sideLength,
                pos.x + sideLength,pos.y + sideLength);
        width = hitCircleSize;
        height = width;
        // END LEGACY CODE;
        
        position = pos;
        velMagnitude = velocityMagnitude;
        hitRadius = hitCircleSize;
        this.angle = angle;
    }


    public RectF getHitbox() {
        return hitbox;
    }
    public PointF getPosition() {
        return position;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
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
    public float getHitRadius() {
        return hitRadius;
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

    public void update(long time, final Display screen) {
        hitbox.left = hitbox.left + (velocityX * time) ;
        hitbox.top = hitbox.top + (velocityY * time) ;

        // UPDATING NEW POSITION VARIABLE.
        position.x += velMagnitude * Math.cos(angle);
        position.y += velMagnitude * Math.sin(angle);

        if(position.x < 0) {
            position.x = screen.height;
        }
        else if(position.y < 0) {
            position.y = screen.height;
        }
        else if(position.x > screen.width) {
            position.x = 0;
        }
        else if(position.y > screen.height) {
            position.y = 0;
        }
        if (hitbox.left < 0)
            hitbox.left = screen.width;
        if (hitbox.left > screen.width)
            hitbox.left = 0;
        if (hitbox.top < 0)
            hitbox.top = screen.height;
        if (hitbox.top > screen.height)
            hitbox.top = 0;
    }

    // This should be used to compared two objects to see if they collided.
    static public boolean detectCollision(SpaceObject A, SpaceObject B) {
        int distance = (int)Math.sqrt(Math.pow((A.getPosition().x - B.getPosition().x), 2) 
                        + Math.pow((A.getPosition().y - B.getPosition().y), 2));

        // The objects overlap if their distance apart is less than sum of their radius.
        return distance <= (int)(A.getHitRadius() + B.getHitRadius());
    }
}
