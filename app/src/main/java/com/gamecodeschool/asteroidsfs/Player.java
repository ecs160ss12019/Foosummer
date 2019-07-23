package com.gamecodeschool.asteroidsfs;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.ArrayList;

public class Player {

	private RectF mRect;
	private float mLength;
	private float mHeight;
	private float mXCoord;
	private float mYCoord;
	private float maxXCoord;
	private float maxYCoord;
	private float mXVelocity;
	private float mYVelocity;
	float dx;
	float dy;
	private float mShipWidth;
	private float mShipHeight;
	private int lives = 3;
	private int score = 0;
	private float mPlayerSpeed;
	private int degree;
	private Point centerCoords;
	private float movementMagnitude;
	protected Matrix playerMatrix = new Matrix();

	private boolean hit;
	// 0 = stopped, 1 = clockwise, 2 = counter-clockwise
	private int rotationStates[] = { 0, 1, 2 };
	private int rotateState;

	// true if player is moving, false if player is stationary
	private boolean moveState;

	// A bitmap for each direction the ship can face
	private Bitmap mBitmapHeadUp;
	private Bitmap mBitmapHeadCurrent;

	//Laser Gun
	ArrayList<Laser> lasers;

	Player(int screenX, int screenY) {
		// max resolution of screen
		maxXCoord = screenX;
		maxYCoord = screenY;

		// Configure the size of the player's
		// hitbox based on the screen resolution
		mLength = screenX / 30;
		mHeight = screenY / 30;

		// start player ship location at center
		// of the screen
		mXCoord = screenX / 2;
		mYCoord = screenY / 2;

		// Intialize mRect (hitbox) based on the size and position
		mRect = new RectF(mXCoord, mYCoord, mXCoord + mLength - 15, mYCoord + mLength - 15);
			Log.e("player: ", "value of mLength: " + mLength);
		    Log.e("player: ", "value of mRect.left: " + mRect.left);
			Log.e("player: ", "value of mRect.right: " + mRect.right);
			Log.e("player: ", "value of mRect.top: " + mRect.top);
			Log.e("player: ", "value of mRect.bottom: " + mRect.bottom);

		centerCoords = new Point((int) (mRect.left + 0.5 * (mRect.right - mRect.left)),
				(int) (mRect.top + 0.5 * (mRect.bottom - mRect.top)));

		// Initialize speed of Player to 0
		mXVelocity = 0;
		mYVelocity = 0;
		movementMagnitude = 0;


		lasers = new ArrayList<Laser>();
	}

	public RectF getHitbox() {
		return mRect;
	}
	public ArrayList<Laser> getLasers(){
		return lasers;
	}

	// Update the Player- Called each frame/loop
	// Update arguments within the AsteroidsGame class
	void update(long timeElapsed, Context ourContext, int blockSize, int x, int y) {
		if(rotateState == 1){
			if(degree < 0){
				degree = 360;
			}
			degree -= 3;
		} else if (rotateState == 2) {
			if (degree > 360) {
				degree = 0;
			}
			degree += 3;
		} else {
			degree = degree;
		}

		if (moveState == true) {
			movementMagnitude += 0.2f;
			if(movementMagnitude > 1){
				this.mXVelocity = 1 * (float) Math.cos(degree * 0.0174533);
				this.mYVelocity = 1 * (float) Math.sin(degree * 0.0174533);
			}
			else{
				this.mXVelocity = movementMagnitude * (float) Math.cos(degree * 0.0174533);
				this.mYVelocity = movementMagnitude * (float) Math.sin(degree * 0.0174533);
			}

			// remove this to reincorporate drag.... this is a bit clunky
			if(dx > 0 && mXVelocity < 0){
				dx = -0.2f;
			}
			if(dx < 0 && mXVelocity > 0){
				dx = 0.2f;
			}
			if(dy > 0 && mYVelocity < 0){
				dy = -0.2f;
			}
			if(dy < 0 && mYVelocity > 0){
				dy = 0.2f;
			}

			this.dx += mXVelocity/timeElapsed;
			this.dy += mYVelocity/timeElapsed;
			// mRect.offset(this.dx, this.dy);
			mRect.offset(this.dx, this.dy);

			centerCoords = new Point((int)(mRect.left+0.5*(mRect.right-mRect.left)),
					(int)(mRect.top+0.5*(mRect.bottom-mRect.top)));

			if (mRect.left < 0)
				mRect.left = x-100;
				mRect.right = mRect.left + 96;
			if (mRect.left > x)
				mRect.left =  0+100;
				mRect.right = mRect.left + 96;
			if (mRect.top < 0)
				mRect.top = y-100;
				mRect.bottom = mRect.top + 96;
			if (mRect.top > y)
				mRect.top = 0+100;
				mRect.bottom = mRect.top + 96;

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

	public Bitmap getBitMap() {
		return this.mBitmapHeadCurrent;
	}

//	public Matrix configMatrix(){
//		this.playerMatrix.setRotate(this.getDegree(),
//				shipBitmap.getWidth() / 2, shipBitmap.getHeight() / 2);
//
//		this.playerMatrix.postTranslate((render.mPlayer.getCenterCoords().x) - render.mBlockSize,
//				(render.mPlayer.getCenterCoords().y) - render.mBlockSize);
//	}

	public Point getCenterCoords() {
		return this.centerCoords;
	}

	public int getDegree() {
		return this.degree;
	}

	public float getPlayerLength() {
		return this.mLength;
	}

	public float getPlayerHeight() {
		return this.mHeight;
	}

	public Matrix getMatrix(){
		return this.playerMatrix;
	}

	void setMoveState(boolean playerMove) {
		moveState = playerMove;
	}

	void setRotationState(int playerRotate) {
		rotateState = rotationStates[playerRotate];
	}








	public void shoot(int x, int y) {
		lasers.add(new Laser(new PointF(x/2,y/2),y/100, y/100, -(y/5), (y/5)));

//		for(int i = 0; i < lasers.size(); i++) {
//			lasers.get(i).update(fps, x, y);
//		}
	}
}
