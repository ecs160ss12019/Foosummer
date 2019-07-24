package com.gamecodeschool.asteroidsfs;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.graphics.Point;
import java.util.ArrayList;

public class Player {

	private RectF mRect;
	private float mLength;
	private float mHeight;
	private float maxXCoord;
	private float maxYCoord;
	private float dx;
	private float dy;
	private float mXVelocity;
	private float mYVelocity;
//	private float mShipWidth;
//	private float mShipHeight;
	private int lives = 3;
	private int score = 0;
	private float degree;
	private Point centerCoords;
	private float movementMagnitude;
	protected Matrix playerMatrix = new Matrix();
	// 0 = stopped, 1 = clockwise, 2 = counter-clockwise
	private int[] rotationStates = { 0, 1, 2 };
	private int rotateState;
	// true if player is moving, false if player is stationary
	private boolean moveState;



	Player(int screenX, int screenY) {
		int centeredScaleFactor = 15;
		// max resolution of screen
		maxXCoord = screenX;
		maxYCoord = screenY;

		// Configure the size of the player's
		// hitbox based on the screen resolution
		mLength = screenX / 25;
		mHeight = screenY / 25;

		// start player ship location at center
		// of the screen
		float mXCoord = screenX / 2;
		float mYCoord = screenY / 2;

		// Intialize mRect (hitbox) based on the size and position
		mRect = new RectF(mXCoord, mYCoord, mXCoord + mLength - centeredScaleFactor,
				mYCoord + mLength - centeredScaleFactor);
//			Log.e("player: ", "value of mLength: " + mLength);
//		    Log.e("player: ", "value of mRect.left: " + mRect.left);
//			Log.e("player: ", "value of mRect.right: " + mRect.right);
//			Log.e("player: ", "value of mRect.top: " + mRect.top);
//			Log.e("player: ", "value of mRect.bottom: " + mRect.bottom);

		centerCoords = new Point((int) (mRect.left + 0.5 * (mRect.right - mRect.left)),
				(int) (mRect.top + 0.5 * (mRect.bottom - mRect.top)));

		// Initialize speed of Player to 0
		mXVelocity = 0;
		mYVelocity = 0;
		dx = 0;
		dy = 0;
		movementMagnitude = 0;


	}


	// Update the Player- Called each frame/loop
	// Update arguments within the AsteroidsGame class
	void update(long timeElapsed) {
		rotatePlayer();
		movePlayer(timeElapsed);
	}

	public Matrix configMatrix(Point bitmapDim, int blockSize){
		this.playerMatrix.setRotate(this.getDegree(),
				bitmapDim.x / 2, bitmapDim.y / 2);

		this.playerMatrix.postTranslate((centerCoords.x) - blockSize,
				(centerCoords.y) - blockSize);
		return this.playerMatrix;
	}

	public Point getCenterCoords() {return this.centerCoords;}

	public float getDegree() {return this.degree;}

	public RectF getHitbox() {return mRect;}



	public float getPlayerLength() {return this.mLength;}

	public float getPlayerHeight() {return this.mHeight;}

	public Matrix getMatrix() {return this.playerMatrix;}

	void setMoveState(boolean playerMove) {moveState = playerMove;}

	void setRotationState(int playerRotate) {rotateState = rotationStates[playerRotate];}

	void rotatePlayer(){
		float maxDeg = 360;
		float minDeg = 0;
		float rotationRate = 3;

		if(rotateState == 1){
			if(degree < minDeg){
				degree = maxDeg;
			}
			degree -= rotationRate;
		} else if (rotateState == 2) {
			if (degree > maxDeg) {
				degree = minDeg;
			}
			degree += rotationRate;
		}
	}

	void movePlayer(long timeElapsed) {
		float velocityRate = 0.2f;
		if (moveState == true) {
			computePlayerVelocity(velocityRate);

			// remove this to reincorporate drag.... this is a bit clunky
			if(dx > 0 && mXVelocity < 0){dx = -velocityRate;}
			if(dx < 0 && mXVelocity > 0){dx = velocityRate;}
			if(dy > 0 && mYVelocity < 0){dy = -velocityRate;}
			if(dy < 0 && mYVelocity > 0){dy = velocityRate;}

			// adds velocity to offset to the new position of the Player (hitbox)
			dx += mXVelocity/timeElapsed;
			dy += mYVelocity/timeElapsed;
			mRect.offset(dx, dy);

			setPlayerCenter();
			wrapAroundPlayer();
//			Log.e("boundary: ","max_X: " + maxXCoord);
//			Log.e("boundary: ", "max_Y: " + maxYCoord);

//			Log.e("player: ", "movementMagnitude: " + movementMagnitude);
//			Log.d("player: ", "degree: " + degree);
//			Log.d("player: ", "value of mXVelocity: " + mXVelocity);
//			Log.d("player: ", "value of mYVelocity: " + mYVelocity);
//			Log.d("player: ", "value of dx: " + dx);
//			Log.d("player: ", "value of dy: " + dy);

//			Log.d("player: ", "value of mRect.left: " + mRect.left);
//			Log.d("player: ", "value of mRect.right: " + mRect.right);
//			Log.d("player: ", "value of mRect.LRdiff: " + (mRect.right-mRect.left));
//			Log.d("player: ", "value of mRect.top: " + mRect.top);
//			Log.d("player: ", "value of mRect.bottom: " + mRect.bottom);
//			Log.d("player: ", "value of mRect.BTdiff: " + (mRect.bottom-mRect.top));
//			Log.d("player: ", "value of shipCenter.x: " + centerCoords.x);
//			Log.d("player: ", "value of shipCenter.y: " + centerCoords.y);
		}
		else{
			this.mXVelocity = 0;
			this.mYVelocity = 0;
			this.dx = 0;
			this.dy = 0;
			movementMagnitude = 0;
		}
	}

	void wrapAroundPlayer(){
		float wrapAroundOffset = 96;
		// wrap around for the Player ship
		if (mRect.right < 0)
			mRect.left = maxXCoord-wrapAroundOffset;
		mRect.right = mRect.left + wrapAroundOffset;
		if (mRect.left > maxXCoord)
			mRect.left =  0;
		mRect.right = mRect.left + wrapAroundOffset;
		if (mRect.bottom < 0)
			mRect.top = maxYCoord-wrapAroundOffset;
		mRect.bottom = mRect.top + wrapAroundOffset;
		if (mRect.top > maxYCoord)
			mRect.top = 0;
		mRect.bottom = mRect.top + wrapAroundOffset;
	}

	void computePlayerVelocity(float velocityRate){
		float maxVelocity = 1;
		double radToDeg = 0.0174533;
		movementMagnitude += velocityRate;
		if(movementMagnitude > maxVelocity){
			this.mXVelocity = maxVelocity * (float) Math.cos(degree * radToDeg);
			this.mYVelocity = maxVelocity * (float) Math.sin(degree * radToDeg);
		}
		else{
			this.mXVelocity = movementMagnitude * (float) Math.cos(degree * radToDeg);
			this.mYVelocity = movementMagnitude * (float) Math.sin(degree * radToDeg);
		}
	}

	void setPlayerCenter(){
		centerCoords = new Point((int)(mRect.left+0.5*(mRect.right-mRect.left)),
				(int)(mRect.top+0.5*(mRect.bottom-mRect.top)));
	}








	public void shoot(int x, int y) {

	}
}
