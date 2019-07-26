package com.gamecodeschool.asteroidsfs;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

public class Opponent extends SpaceObject{
    private int health;

    private Point centerCoords;
    private float degree;
    private long laserTimer = 0; // Everytime this is > 500ms, we shoot.
    private final long SHOOT_INTERVAL = 10;

    public float getDegree() {return this.degree;} // do I need this?
    public Point getCenterCoords() {return this.centerCoords;}


    public Opponent(PointF position, double angle, float velocityMag, float hitRadius, int health) {
        super(position, angle, velocityMag, hitRadius);
        this.health = health;
    }

    @Override
    public void update(long time, Display display){
        super.update(time, display);

        position.x += velMagnitude * Math.cos(angle) * time/2;
        position.y += velMagnitude * Math.sin(angle) * time/2;



        // do something to deal with player's position?

    }

    public Laser shoot(long timeIncrement, ObjectFactory fac) {
        laserTimer += timeIncrement;
        if (laserTimer > SHOOT_INTERVAL) {
            laserTimer = 0;
            //FIXME TODO: The last int, 1, is a temporary place in for laser damage variable stored in player. This should be able to go up w/ upgrade (maybe)
            return fac.getPlayerLaser(new PointF(position.x, position.y),(degree * Math.PI / 180), 1);
        }

        return null;
    }

    public long getTimer() {return laserTimer; }
}
