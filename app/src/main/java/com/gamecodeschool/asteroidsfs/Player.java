package com.gamecodeschool.asteroidsfs;

import static com.gamecodeschool.asteroidsfs.GameConfig.MAX_DEG;
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
	// true if player is moving, false if player is stationary
	private boolean moveState;
	// Vars required for timed shooting.
	private double rotationIncrement; // t
	private final int boxLength;
	private final PointF resetPos;
	private PowerMods pow = new PowerMods();
//	private boolean invincible = false;
	private boolean shieldInvincibility;
	private boolean respawnInvincibility;
	private long respawnCountdown;
	final long DEFAULT_INVINCIBILITY_RESPAWN_TIME = 3000;

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
		isInvincible(timeElapsed);
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

	private void rotatePlayer(){
		angle = (angle + rotationIncrement) % MAX_DEG; // Modulus prevents overflow of continuous increment.
	}

	private void computePlayerVelocity(){
		if(velMagnitude < VELOCITY_RATE * 20) {
			velMagnitude += VELOCITY_RATE;
		}
	}

	// Returns player back to center of the screen.
	// change this to resetPlayer()..
	public void resetPos() {
		position.x = resetPos.x;
		position.y = resetPos.y;
		pow.disablePowerUps();

		// respawn invincibility
		// just lost a life (declife)
		// should i not set respawnstate and just call function to add to respawncountdown
		respawnCountdown += DEFAULT_INVINCIBILITY_RESPAWN_TIME;
	}

	public PointF getPosition(){ return new PointF(position.x, position.y); }





	// Shoots if condition is met. Returns null otherwise.
	public Laser shoot(long timeIncrement, ObjectFactory fac) {
		// if (justRespawned == true...) return null for x seconds
		if(respawnInvincibility){
			return null;
		}else{
			return pow.shootCondition(timeIncrement) ? fac.getPlayerLaser(getPosition(), angle, 1) : null;
		}
	}

	// returns current shoot interval timer stored within the PowerMods
	public long getTimer() {return pow.currentShootThreshold; }

	// void or boolean..
	private void isInvincible(long timeElapsed){
//		if(invincible == true){ return true; }
//		else{ return false; }
		//
		// current logic only accounts for shield invincibility
		if(pow.shieldEnabled(timeElapsed)){
//			return true;
			shieldInvincibility = true;
			// setRespawnState(false)
		}
		else if (justRespawned(timeElapsed)){
			respawnInvincibility = true;
//			setShieldState(false);
		}
		else{
//			return false;
			deactivateInvincibility();
		}

		// else if (justRespawned){ return true && disable laser for x seconds }
		// add else if for respawn invincibility...
	}


	public void receivePowerUp(PowerUp powerUpType){
		switch(powerUpType){
			case SHIELD:
				pow.activateShield();
				break;

			case FIRE_RATE:
				// modify fire rate
				pow.increaseFireRate();
				break;
		}
	}

	public void updateRotation(double newRate) {
		rotationIncrement = newRate;
	}

	private boolean justRespawned(long timeDecrement){
		respawnCountdown -= timeDecrement;
		if(respawnCountdown < 0){
			respawnCountdown = 0;
			return false;
		}
		return true;
	}

//	private void setRespawnState(boolean respawnState){ respawnInvincibility = respawnState;}
//	private void setShieldState(boolean shieldState){ shieldInvincibility = shieldState; }
	private void deactivateInvincibility(){
		shieldInvincibility = false;
		respawnInvincibility = false;
	}
	public boolean getShieldState(){return shieldInvincibility;}
	public boolean getRespawnState(){return respawnInvincibility;}

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
	final long DEFAULT_SHOOT_THRESHOLD = 500; // in ms
	final long DEFAULT_THRESHOLD_DECREMENT = 50; // Time for additional shot!
//	final long DEFAULT_INVINCIBILITY_TIMER = 5000; // in ms so 5s
	final int MINIMUM_SHOOT_INTERVAL = 100;
	final long DEFAULT_INVINCIBILITY_DURATION = 10000;

	long currentShootThreshold;
	long currentElapsedTime;
	long invincibilityCountDown;
	// initialize with default initial states.
	PowerMods() {
		currentShootThreshold    = DEFAULT_SHOOT_THRESHOLD;
//		elapsedInvincibilityTime = DEFAULT_INVINCIBILITY_TIMER;
	}

	// Everytime time interval is increased. Generate laser and shoot.
	public boolean shootCondition(long timeIncrement) {
		boolean retVal = false;
		currentElapsedTime += timeIncrement;
		if(currentElapsedTime >= currentShootThreshold) {
			currentElapsedTime = 0;
			retVal = true;
		}
		return retVal;
	}

	// Power up increases Player fire rate up to max threshold
	public void increaseFireRate(){
		if(currentShootThreshold > MINIMUM_SHOOT_INTERVAL){
			currentShootThreshold -= DEFAULT_THRESHOLD_DECREMENT;
		}
	}

	// case condition from power up picked up
	public void activateShield(){
		invincibilityCountDown += DEFAULT_INVINCIBILITY_DURATION;
	}

	// constantly updated from game engine update()
	public boolean shieldEnabled(long timeDecrement){
		invincibilityCountDown -= timeDecrement;
		if(invincibilityCountDown < 0){
			invincibilityCountDown = 0;
			return false;
			// turn off invincibility
		}
		return true;
	}

	// When player lose life, all upgrade settings are reset.
	public void disablePowerUps(){
		currentShootThreshold    = DEFAULT_SHOOT_THRESHOLD;
//		elapsedInvincibilityTime = DEFAULT_INVINCIBILITY_TIMER;
	}

}
