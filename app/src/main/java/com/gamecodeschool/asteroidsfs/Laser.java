package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;

public class Laser extends SpaceObject {
    int damage; // the damage that this thing can do.

    public Laser(SpaceObject parent, int damageVal) {
        super(parent);
        damage = damageVal;
    }

    // This one will return false if out of bound.
    // True means it's still inside screen space.
    public boolean update(long time, final Display screen)  {
        boolean retVal = false;
        position.x += velMagnitude * Math.cos(angle) * time;
        position.y += velMagnitude * Math.sin(angle) * time;

        
        if(super.position.x < 0 || super.position.y < 0 || super.position.x > screen.width || 
                                        super.position.y > screen.height) {
            retVal = true;
        }

        return retVal;
    }
}
