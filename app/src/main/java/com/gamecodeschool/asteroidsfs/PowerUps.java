package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;
import java.util.Random;

enum PowerUp {
    FIRE_RATE,
    SHIELD;
}

public class PowerUps extends SpaceObject{
    private PowerUp type;

    public PowerUps(PointF pos, float size){
        super(pos, 0, 0, size);
        this.type = getRandPowerUp();
    }

    @Override
    public void update(long fps, Display display){
        super.update(fps, display);
    }

    // randomly selects from enum PowerUp
    // to assign the PowerUp type being
    // dropped (in constructor)
    private PowerUp getRandPowerUp(){
        Random rand = new Random();
        return PowerUp.values()[rand.nextInt(PowerUp.values().length)];
    }

    public PowerUp getPowerUpType(){
        return this.type;
    }

}
