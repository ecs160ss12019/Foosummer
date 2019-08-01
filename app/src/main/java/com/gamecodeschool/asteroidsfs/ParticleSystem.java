package com.gamecodeschool.asteroidsfs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Random;

public class ParticleSystem {

    long mDuration;

    ArrayList<Particle> mParticles;
    Random random = new Random();
    boolean mIsRunning = false;
    private final long EXPLOSION_INTERVAL = 1000;

    //Maybe replace with a call to getObject() in ObjectFactory
    public void init(int numParticles, Display display){
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
            paint.setColor(Color.argb(255,255,255,255));
            canvas.drawRect(p.getPosition().x, p.getPosition().y,
                            p.getPosition().x+5, p.getPosition().y+5, paint);
        }
    }


}
