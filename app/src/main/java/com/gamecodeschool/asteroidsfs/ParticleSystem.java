package com.gamecodeschool.asteroidsfs;

import static com.gamecodeschool.asteroidsfs.GameConfig.EXPLOSION_INTERVAL;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import java.util.ArrayList;
import java.util.Random;


/*
 * The ParticleSystem class is used a factory to generate a number of particles.
 * This class is used when simulating an explosion in a collision.
 */

public class ParticleSystem {

    long mDuration;

    ArrayList<Particle> mParticles;
    Random random = new Random();
    boolean mIsRunning = false;

    public void init(int numParticles, Display display) {
        mParticles = new ArrayList<>();
        // Create the particles
        for (int i = 0; i < numParticles; i++) {
            PointF position = new PointF(
                                        (float)(random.nextInt(display.width)),
                                        (float)(random.nextInt(display.height)));
            float angle = (random.nextInt(360)) ;
            angle = angle * 3.14f / 180.f;
            float speed = (float) (random.nextFloat()*0.25);
            mParticles.add(new Particle(position, angle, speed, 0));
        }
    }

    // Update particles only for a specific amount of time
    // In this case, for one second.
    public void update(long time) {
        mDuration += time;

        for(Particle p : mParticles)
            p.update(time);

        if (mDuration > EXPLOSION_INTERVAL)
            mIsRunning = false;
    }


    public void emitParticles(PointF startPosition) {
        mIsRunning = true;
        mDuration = 0;

        for(Particle p : mParticles)
            p.setPosition(startPosition);
    }

    public void draw(Canvas canvas, Paint paint) {
        for (Particle p : mParticles) {
            // draw system of particles in green
            paint.setColor(Color.argb(255,30,255,5));
            canvas.drawRect(p.getPosition().x, p.getPosition().y,
                            p.getPosition().x+5, p.getPosition().y+5, paint);
        }
    }


}
