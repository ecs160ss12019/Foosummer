package com.gamecodeschool.asteroidsfs;

import static com.gamecodeschool.asteroidsfs.GameConfig.MAX_DEG;
import static com.gamecodeschool.asteroidsfs.GameConfig.MIN_DEG;
import static com.gamecodeschool.asteroidsfs.GameConfig.MAX_VELOCITY;
import static com.gamecodeschool.asteroidsfs.GameConfig.RAD_TO_DEG;
import static com.gamecodeschool.asteroidsfs.GameConfig.ROTATE_RATE;
import static com.gamecodeschool.asteroidsfs.GameConfig.SCALE_TO_CENTER;
import static com.gamecodeschool.asteroidsfs.GameConfig.VELOCITY_RATE;
import static com.gamecodeschool.asteroidsfs.GameConfig.WRAP_AROUND_OFFSET;



import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.graphics.Point;
import java.util.Timer;

import java.util.ArrayList;

public class Player {

	private RectF mRect;
	private float mLength;
	private float mHeight;
	private float maxXCoord;
	private float maxYCoord;
	private float mXCoord;
	private float mYCoord;
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



	// Vars required for timed shooting.
	private long laserTimer = 0; // Everytime this is > 500ms, we shoot.
	private final long SHOOT_INTERVAL = 500;


	Player(int screenX, int screenY) {
		// max resolution of screen
		maxXCoord = screenX;
		maxYCoord = screenY;

		configHitboxSize();
		configHitboxLocation();



		// Intialize mRect (hitbox) based on the size and position
		mRect = new RectF(mXCoord, mYCoord, mXCoord + mLength - SCALE_TO_CENTER,
				mYCoord + mLength - SCALE_TO_CENTER);
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

	void configHitboxLocation(){
		// start player ship location at center
		// of the screen
		this.mXCoord = maxXCoord / 2;
		this.mYCoord = maxYCoord / 2;
	}

	void configHitboxSize(){
		// Configure the size of the player's
		// hitbox based on the screen resolution
		this.mLength = maxXCoord / 25;
		this.mHeight = maxYCoord / 25;
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

		if(rotateState == 1){
			if(degree < MIN_DEG){
				degree = MAX_DEG;
			}
			degree -= ROTATE_RATE;
		} else if (rotateState == 2) {
			if (degree > MAX_DEG) {
				degree = MIN_DEG;
			}
			degree += 5;
		} else {
			this.degree = degree;
		}
	}

	void movePlayer(long timeElapsed) {

		if (moveState == true) {
			computePlayerVelocity();

			// remove this to reincorporate drag.... this is a bit clunky
			if(dx > 0 && mXVelocity < 0){dx = -VELOCITY_RATE;}
			if(dx < 0 && mXVelocity > 0){dx = VELOCITY_RATE;}
			if(dy > 0 && mYVelocity < 0){dy = -VELOCITY_RATE;}
			if(dy < 0 && mYVelocity > 0){dy = VELOCITY_RATE;}

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
		// wrap around for the Player ship
		if (mRect.right < 0)
			mRect.left = maxXCoord-WRAP_AROUND_OFFSET;
		mRect.right = mRect.left + WRAP_AROUND_OFFSET;
		if (mRect.left > maxXCoord)
			mRect.left =  0;
		mRect.right = mRect.left + WRAP_AROUND_OFFSET;
		if (mRect.bottom < 0)
			mRect.top = maxYCoord-WRAP_AROUND_OFFSET;
		mRect.bottom = mRect.top + WRAP_AROUND_OFFSET;
		if (mRect.top > maxYCoord)
			mRect.top = 0;
		mRect.bottom = mRect.top + WRAP_AROUND_OFFSET;
	}

	void computePlayerVelocity(){
		movementMagnitude += VELOCITY_RATE;
		if(movementMagnitude > MAX_VELOCITY){
			this.mXVelocity = MAX_VELOCITY * (float) Math.cos(degree * RAD_TO_DEG);
			this.mYVelocity = MAX_VELOCITY * (float) Math.sin(degree * RAD_TO_DEG);
		}
		else{
			this.mXVelocity = movementMagnitude * (float) Math.cos(degree * RAD_TO_DEG);
			this.mYVelocity = movementMagnitude * (float) Math.sin(degree * RAD_TO_DEG);
		}
	}

	void setPlayerCenter(){
		centerCoords = new Point((int)(mRect.left+0.5*(mRect.right-mRect.left)),
				(int)(mRect.top+0.5*(mRect.bottom-mRect.top)));
	}







	// commented out until implementation.
	//FIXME TODO: Instantiate a new SpaceObject with a laser magnitude, and copy over current position and angle! We should have laser radius stored somewhere..
	public Laser shoot(long timeIncrement, ObjectFactory fac) {
		laserTimer += timeIncrement;
		if (laserTimer > SHOOT_INTERVAL) {
			laserTimer = 0;
			//FIXME TODO: The last int, 1, is a temporary place in for laser damage variable stored in player. This should be able to go up w/ upgrade (maybe)
			return fac.getPlayerLaser(new PointF(centerCoords.x, centerCoords.y),(degree * Math.PI / 180), 1);
		}

		return null;
	}

	public long getTimer() {return laserTimer; }


}
