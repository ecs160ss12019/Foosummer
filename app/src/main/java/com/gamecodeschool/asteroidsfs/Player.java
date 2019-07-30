package com.gamecodeschool.asteroidsfs;

import static com.gamecodeschool.asteroidsfs.GameConfig.MAX_DEG;
import static com.gamecodeschool.asteroidsfs.GameConfig.MIN_DEG;
import static com.gamecodeschool.asteroidsfs.GameConfig.ROTATE_RATE;
import static com.gamecodeschool.asteroidsfs.GameConfig.VELOCITY_RATE;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.graphics.Point;

//extends SpaceObject
public class Player extends SpaceObject{

	private RectF mRect;
	protected Matrix playerMatrix = new Matrix();
	// 0 = stopped, 1 = clockwise, 2 = counter-clockwise
	private int[] rotationStates = { 0, 1, 2 };
	private int rotateState;
	// true if player is moving, false if player is stationary
	private boolean moveState;
	// Vars required for timed shooting.
	private final int boxLength;
	private final PointF resetPos;
	private PowerMods pow = new PowerMods();

//	PointF pos, double angle, float velocityMagnitude, float hitCircleSize
	Player(PointF pos, float playerLength) {
		super(pos, 0, 0, playerLength);
		resetPos = new PointF(pos.x, pos.y);
		boxLength = (int) playerLength;

		// Intialize mRect (hitbox) based on the size and position
		mRect = new RectF(pos.x - playerLength,
				pos.y - playerLength,
				pos.x + playerLength,
				pos.y + playerLength);

	}


	// Update the Player- Called each frame/loop
	// Update arguments within the AsteroidsGame class
	@Override
	public void update(long timeElapsed, Display display ) {
		if(moveState == true) {
			computePlayerVelocity();
		} else {
			velMagnitude = 0; // reset speed when not moving.
		}
		super.update(timeElapsed, display); // default movement behavior.
		rotatePlayer();
		this.mRect.offsetTo(position.x - boxLength, position.y - boxLength);
	}

	// Rotates and moves the matrix to match the current angle the player is facing.
	public Matrix configMatrix(Point bitmapDim, int blockSize){
		this.playerMatrix.setRotate((float)(angle * 180/Math.PI),
				bitmapDim.x / 2, bitmapDim.y / 2);

		this.playerMatrix.postTranslate((position.x) - hitRadius - GameConfig.PLAYER_SHIP_PADDING / 2,
				(position.y) - hitRadius - GameConfig.PLAYER_SHIP_PADDING / 2);
		return this.playerMatrix;
	}


	public RectF getHitbox() {return mRect;}

	public Matrix getMatrix() {return playerMatrix;}

	// State control regarding movement and rotation.
	void setMoveState(boolean playerMove) {moveState = playerMove;}
	void setRotationState(int playerRotate) {rotateState = rotationStates[playerRotate];}

	// Rotates player's current angle. (of the parent class)
	void rotatePlayer(){

		if(rotateState == 1){
			if(angle < MIN_DEG){
				angle = MAX_DEG;
			}
			angle -= ROTATE_RATE;
		} else if (rotateState == 2) {
			if (angle > MAX_DEG) {
				angle = MIN_DEG;
			}
			angle += ROTATE_RATE;
		}
	}

	// Increments playher velocity until max rate (set at 20 * the rate)
	void computePlayerVelocity(){
		if(velMagnitude < VELOCITY_RATE * 20) {
			velMagnitude += VELOCITY_RATE;
		}
	}

	// Returns player back to center of the screen.
	public void resetPos() {
		position.x = resetPos.x;
		position.y = resetPos.y;
	}

	public PointF getPosition(){
		return new PointF(position.x, position.y);
	}

	// Shoots if condition is met. Returns null otherwise.
	public Laser shoot(long timeIncrement, ObjectFactory fac) {
		return pow.shootCondition(timeIncrement) ? fac.getPlayerLaser() : null ;
	}

	// returns current shoot interval timer stored within the PowerMods
	public long getTimer() {return pow.currentShootInterval; }


}

/* 
 *	This class will define what powerups the player has picked up.
 * 	Things that can be picked up...
 * 	- Player ship shoots volley every default interval
 * 	- Shooting speed - this can go up? Might not do this.
 *  - Invincibility time frame. When 0, the ship cannot be hit.
 */
class PowerMods {
	// final constants that define default parameters.
	final long DEFAULT_SHOOT_INTERVAL = 500; // in ms
	final long DEFAULT_VOLLEY_INTERVAL = 150; // Time for additional shot!
	final long DEFAULT_INVINCIBILITY_TIMER = 5000; // in ms so 5s
	final int DEFAULT_VOLLEY = 1;
	final int MAX_SHOTS_PER_VOLLEY = 3; // 

	long currentShootInterval;
	int currentVolleyCounter; // Current counter to additional
	int currentShotsPerVolley; // Starts with 1 until max of 4.
	int volleyTimer = 0; // The timer for additional volley.
	long elapsedInvincibilityTime;
	boolean invincible; // marks if the ship can be hit or not.
	// initialize with default initial states.
	PowerMods() {
		currentShootInterval     = DEFAULT_SHOOT_INTERVAL;
		currentVolleyCounter     = DEFAULT_VOLLEY; // player default shoots once per volley.
		elapsedInvincibilityTime = DEFAULT_INVINCIBILITY_TIMER;
	}

	// Everytime time interval is increased. Generate laser and shoot.
	public shootCondition(long timeIncrement) {
		boolean retVal = false;
		currentShootInterval += timeIncrement;
		if(currentShootInterval >= DEFAULT_SHOOT_INTERVAL) {
			currentShootInterval = 0;
			retVal = true;
		}

		if() {

		}
		return retVal;
	}

	// We can increment current shots per volley till we reach max upgrade.
	public boolean powerUpShots() {
		if(currentVolleyCounter < MAX_SHOTS_PER_VOLLEY)
			currentVolleyCounter++;
	}

	// When player lose life, all upgrade settings are reset.
	public void reset() {
		currentShootInterval     = DEFAULT_SHOOT_INTERVAL;
		currentVolleyCounter     = DEFAULT_VOLLEY;
		elapsedInvincibilityTime = DEFAULT_INVINCIBILITY_TIMER;
	}
}
