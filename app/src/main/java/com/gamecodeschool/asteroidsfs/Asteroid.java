package com.gamecodeschool.asteroidsfs;

    import android.graphics.PointF;
    import java.util.ArrayList;

public class Asteroid extends SpaceObject {
    private int size;       //Define different size asteroids

    // Using this constructor does not generate a RectF in the SpaceObject.
    public Asteroid(double angle, PointF pos, float velocityMagnitude, float asteroidSize) {
        super(pos, angle, velocityMagnitude, asteroidSize);
    }

}