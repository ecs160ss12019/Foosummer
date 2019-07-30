package com.gamecodeschool.asteroidsfs;

    import android.graphics.PointF;
    import android.widget.Space;

    import java.util.ArrayList;
import java.util.Random;

public class Asteroid extends SpaceObject {
    private int size;       //Define different size asteroids
    private final int DEFAULT_SPLIT_ANGLE = 30; // DEFAULT MAX SPLIT POSSIBLE ANGLE

    // Using this constructor does not generate a RectF in the SpaceObject.
    Asteroid(double angle, PointF pos, float velocityMagnitude, float asteroidSize, int size) {
        super(pos, angle, velocityMagnitude, asteroidSize);
        this.size = size;
    }

    Asteroid(SpaceObject copy, int newSize, int theta) {
        super(copy);
        size = newSize;
        angle += theta * Math.PI / 180;
        hitRadius = hitRadius * newSize / (newSize + 1);
    }

    void increaseVelocity() {
        // increase the speed by 50%
        velMagnitude = velMagnitude * 1.5f;
    }

    public ArrayList<Asteroid> collisionAction() {
        ArrayList<Asteroid> temp = new ArrayList<Asteroid>();
        if(size > 1) {
            Random r = new Random();
            // Creates asteroid of smaller size that splits 
            //at randomized angle of at max 30 different from original.
            temp.add(new Asteroid(this, size - 1, r.nextInt(DEFAULT_SPLIT_ANGLE)));
            temp.add(new Asteroid(this, size - 1, -(r.nextInt(DEFAULT_SPLIT_ANGLE))));
            temp.get(0).increaseVelocity();
            temp.get(1).increaseVelocity();
        }

        return temp;
    }

    public int getSize() { return size; }
}