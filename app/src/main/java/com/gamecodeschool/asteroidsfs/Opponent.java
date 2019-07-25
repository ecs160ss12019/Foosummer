package com.gamecodeschool.asteroidsfs;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class Opponent extends SpaceObject{
    private int health;

    public Opponent(PointF position, double angle, float velocityMag, float hitRadius, int health) {
        super(position, angle, velocityMag, hitRadius);
        this.health = health;
    }

    @Override
    public void update(long fps, Display display){
        super.update(fps, display);
        // do something to deal with player's position?
    }
}
