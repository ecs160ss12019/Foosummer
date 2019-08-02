package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;

/*
    This GameConfig class contains all static
    final constants within our code
 */


public class GameConfig {
    // PLAYER CLASS (including PowerMods)
    static final double MAX_DEG = 6.28318530718; // RADIAN VALUE FOR 360
    static final float VELOCITY_RATE = 0.02f;
    static final int PLAYER_SHIP_PADDING = 30; // pixel padding. this  / 2 is padding on each applied side.
    static final long DEFAULT_INVINCIBILITY_RESPAWN_TIME = 3000;
    static final long DEFAULT_SHOOT_THRESHOLD = 500; // in ms
    static final long DEFAULT_THRESHOLD_DECREMENT = 50; // Time for additional shot!
    static final int MINIMUM_SHOOT_INTERVAL = 100;
    static final long DEFAULT_INVINCIBILITY_DURATION = 10000;

    // ASTEROIDS CLASS
    static final int DEFAULT_SPLIT_ANGLE = 30; // DEFAULT MAX SPLIT POSSIBLE ANGLE

    // ASTEROIDS_GAME CLASS
    static final int NUM_BLOCKS_WIDE = 40;

    // COLLISION_ENGINE CLASS
    static final double ASTEROID_DROP_PROB = 0.10;
    static final double OPPONENT_DROP_PROB = 0.20;

    // GAME_PROGRESS CLASS
    static final int INITIAL_SCORE = 0;
    static final int INITIAL_LIFE = 3;
    static final int INITIAL_LEVEL = 1;
    static final int INITIAL_NUM_OPPONENTS = 1;
    static final int INITIAL_NUM_ASTEROIDS = 2;
    static final int INITIAL_MIN_SCORE_ADD_LIFE = 2500;

    // GAMEVIEW CLASS
    static final int LASER_SIZE_FACTOR = 2;
    static final PointF PAUSE_BUTTON_SCALE = new PointF(100, 10);

    // OBJECT_FACTORY CLASS
    static final int MAX_ANGLE = 360;
    static final float TIME = 20; // time it should take to cross screen in seconds
    static final float LASER_TIME = 4; // Default seconds it takes for laser to cross screen width.
    static final float OPPONENT_TIME = 20;
    static final float SUICIDER_TIME = 15;
    static final float MS_PER_S = 1000; // 1000 milliseconds per 1 second
    static final int MAX_ASTEROID_SIZE_LEVEL = 3;
    static final int DIVISION_FACTOR = 25;
    static final float ZONE_1_MIN_MULTIPLIER = 0.25f;
    static final float ZONE_2_MIN_MULTIPLIER = 0.50f;
    static final int SHIP_SCALE_FACTOR = 20;
    static final float OPPONENT_HIT_RADIUS = 100;
    static final float POWERUP_HIT_RADIUS = 50;

    // OPPONENT CLASS
    static final long SHOOT_INTERVAL = 2900;

    // PARTICLE_SYSTEM CLASS
    static final long EXPLOSION_INTERVAL = 1000;

    // TOUCH_HANDLER CLASS
    static final int INVALID = -1;
    static final int NO_ROTATION = 0;
}
