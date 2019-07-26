package com.gamecodeschool.asteroidsfs;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

public class Opponent extends SpaceObject{
    private int health;

    private long laserTimer = 0; // Everytime this is > 2900ms, we shoot.
    private final long SHOOT_INTERVAL = 2900;


    public Opponent(PointF position, double angle, float velocityMag, float hitRadius, int health) {
        super(position, angle, velocityMag, hitRadius);
        this.health = health;
    }

    @Override
    public void update(long time, Display display){
        super.update(time, display);

        // do something to deal with player's position?

    }


    public Laser shoot(long timeIncrement, ObjectFactory fac, Player player) {
        Point playerCoords = player.getCenterCoords();
/*
        PointF firingLocation = new PointF() ;

        if(playerCoords.x > position.x) { // player is to the right of opponent
            firingLocation.x = position.x+; // shoot in direction of player
        }else if(playerCoords.x < position.x){// player is to the left of opponent
            firingLocation.x =
        }else { // player is on the same x-axis as opponent
            firingLocation.x = mLocation.x
                    + (mObjectWidth / 8f) - (laserLength);
        }
        // Move the height down a bit of ship height from origin
        firingLocation.y = mLocation.y + (mObjectHeight / 1.28f);
        return firingLocation;
    }
*/
        //Log.d("OpponentDebug","playerCoords.x: " + playerCoords.x);

        laserTimer += timeIncrement;
        if (laserTimer > SHOOT_INTERVAL) {
            laserTimer = 0;

            //return fac.getOpponentLaser(new PointF(playerCoords.x, playerCoords.y),(360 * Math.PI / 180), 1);
            return fac.getOpponentLaser(new PointF(position.x, position.y),(360 * Math.PI / 180), 1);

        }

        return null;
    }

    //public long getTimer() {return laserTimer; }
}
