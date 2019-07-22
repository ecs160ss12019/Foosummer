package com.gamecodeschool.asteroidsfs;

import android.graphics.RectF;
/*
	Player is a spacecraft that is able to shoot.
*/


public class Player {

	private int screenX;
	private int screenY;
	private RectF mRect;
	private float mLength;
	private float mHeight;
	private float mXCoord;
	private float mYCoord;
	private float mXVelocity;
	private float mYVelocity;
	private float mShipWidth;
	private float mShipHeight;
	private float hitPoints = 20;
	private int lives = 3;
	private int score = 0;
	private float mPlayerSpeed;

	private boolean hit;

	Player(int screenX, int screenY) {

		// Configure the size of the player's
		// ship based on the screen resolution
		mLength = screenX / 13;
		mHeight = screenY / 6;

		// start player ship location at center
		// of the screen
		mXCoord = screenX / 2;
		mYCoord = (screenY / 2);

		// Intialize mRect based on the size and position
		mRect = new RectF(mXCoord, mYCoord,
				mXCoord + mLength,
				mYCoord + mHeight);

		// Configure the speed of the ship
		// This code means the ship can cover the
		// width of the screen in 2 seconds
		mPlayerSpeed = screenX / 2;
	}



	public RectF getRect(){
		return mRect;
	}

	float getRectLeft() {return mRect.left;}

	float getRectTop() {return mRect.top;}

	void updateHitPoints(float decrVal) {
		if (decrVal > 0) // some type of input validation here?
		hitPoints -= decrVal;
		if (hitPoints == 0){
			// call to AsteroidsGame to print game over
			// reset ship location
		}

	}

	float getHitPoints() {return hitPoints;} // for debugging


	// Update the bat- Called each frame/loop
	void update(long fps) {

	}

	public float getPlayerLength(){
		return this.mLength;
	}

	public float getPlayerHeight(){
		return this.mHeight;
	}
//	public Player() {
//		this(1,1,1,1,false);
//	}
//	public Player(double pos_x, double pos_y) {
//		this(pos_x,pos_y,1,1,false);
//	}
//	public Player(double pos_x, double pos_y, double velocity_x, double velocity_y, boolean hit) {
//		super(pos_x, pos_y, velocity_x, velocity_y);
//		this.hit = hit;
//	}

	public void shoot() {
		Laser laser;
		//laser = new Laser();
		//Laser laser = new Laser(mRect.left, mRect.top, screenY/100, screenY/100, -(screenY/5), (screenY/5));
	}

}
