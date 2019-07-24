package com.gamecodeschool.asteroidsfs;

    import android.graphics.PointF;
    import java.util.ArrayList;
import java.util.Random;

public class Asteroid extends SpaceObject {
    private int size;       //Define different size asteroids
    private final int DEFAULT_SPLIT_ANGLE = 30; // DEFAULT MAX SPLIT POSSIBLE ANGLE

    // Using this constructor does not generate a RectF in the SpaceObject.
    public Asteroid(double angle, PointF pos, float velocityMagnitude, float asteroidSize) {
        super(pos, angle, velocityMagnitude, asteroidSize);
    }

    public Asteroid(SpaceObject copy, int newSize, int theta) {
        super(copy);
        size = newSize;
        super.angle += theta * Math.PI / 180;
    }

    public ArrayList<Asteroids> collisionAction() {
        ArrayList<Asteroids> temp = new ArrayList<Asteroids>();
        if(size > 1) {
            Random r = new Random();
            // Creates asteroid of smaller size that splits 
            //at randomized angle of at max 30 different from original.
            temp.add(new Asteroid(this, size - 1, r.nextInt(DEFAULT_SPLIT_ANGLE)));
            temp.add(new Asteroid(this, size - 1, -(r.nextInt(DEFAULT_SPLIT_ANGLE))));
        }
        
        return temp;
    }

}