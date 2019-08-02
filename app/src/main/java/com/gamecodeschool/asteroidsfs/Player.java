package com.gamecodeschool.asteroidsfs;

import static com.gamecodeschool.asteroidsfs.GameConfig.MAX_DEG;
import static com.gamecodeschool.asteroidsfs.GameConfig.VELOCITY_RATE;
import static com.gamecodeschool.asteroidsfs.GameConfig.DEFAULT_INVINCIBILITY_RESPAWN_TIME;
import static com.gamecodeschool.asteroidsfs.GameConfig.DEFAULT_SHOOT_THRESHOLD;
import static com.gamecodeschool.asteroidsfs.GameConfig.DEFAULT_INVINCIBILITY_DURATION;
import static com.gamecodeschool.asteroidsfs.GameConfig.DEFAULT_THRESHOLD_DECREMENT;
import static com.gamecodeschool.asteroidsfs.GameConfig.MINIMUM_SHOOT_INTERVAL;


import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Point;

public class Player extends SpaceObject{
	private static Player instance = null;
	private RectF mRect;
	protected Matrix playerMatrix = new Matrix();
	private PowerMods pow = new PowerMods();
	// true if player is moving, false if player is stationary
	private boolean moveState;
	// Vars required for timed shooting.
	private double rotationIncrement; // t
	private final int boxLength;
	private final PointF resetPos;
	private boolean shieldInvincibility;
	private boolean respawnInvincibility;
	private long respawnCountdown;

	// If instance is null, return a newly constructed player pointer.
	public static Player getInstance(Display dis, float length) {
		instance = (instance == null) ?
					new Player(new PointF(dis.width/2, dis.height/2), length) : instance;
		return instance;
	}

	private Player(PointF pos, float playerLength) {
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

		// using Players hitbox to assign translation for movement.
		this.mRect.offsetTo(position.x - boxLength, position.y - boxLength);
	}

	// Rotates and moves the matrix to match the current angle the player is facing.
	// (coordinates the matrix to manipulate the bitmap of the ship)
	public Matrix configMatrix(Point bitmapDim, int blockSize){
		this.playerMatrix.setRotate((float)(angle * 180/Math.PI),
				bitmapDim.x / 2, bitmapDim.y / 2);

		this.playerMatrix.postTranslate((position.x) - hitRadius - GameConfig.PLAYER_SHIP_PADDING / 2,
				(position.y) - hitRadius - GameConfig.PLAYER_SHIP_PADDING / 2);

		return this.playerMatrix;
	}

	private void computePlayerVelocity(){
		if(velMagnitude < VELOCITY_RATE * 20) {
			velMagnitude += VELOCITY_RATE;
		}
	}

	// Returns player back to center of the screen.
	public void resetPos() {
		position.x = resetPos.x;
		position.y = resetPos.y;
		pow.disablePowerUps();

		// Start timer for respawn invincibility
		respawnCountdown += DEFAULT_INVINCIBILITY_RESPAWN_TIME;
	}

	// Shoots if condition is met. Returns null otherwise.
	public Laser shoot(long timeIncrement, ObjectFactory fac) {
		// if Player just respawned, respawn
		// invincibility state disables shooting
		if(respawnInvincibility){
			return null;
		}else{
			return pow.shootCondition(timeIncrement) ? fac.getPlayerLaser(getPosition(), angle, 1) : null;
		}
	}

	// Determines if Player is currently in Invincible state
	private void isInvincible(long timeElapsed){
		if(pow.shieldEnabled(timeElapsed)){
			shieldInvincibility = true;
		}
		else if (justRespawned(timeElapsed)){
			respawnInvincibility = true;
		}
		else{

			// if Player is neither in respawn invincibility
			// or shield invincibility states
			deactivateInvincibility();
		}
	}

	// Determines the type of power up and assigns
	// the appropriate power up functionality
	public void receivePowerUp(PowerUp powerUpType){
		switch(powerUpType){
			case SHIELD:
				pow.activateShield();
				break;

			case FIRE_RATE:
				pow.increaseFireRate();
				break;
		}
	}

	// Count down timer for respawn invincibility on respawn
	private boolean justRespawned(long timeDecrement){
		respawnCountdown -= timeDecrement;
		if(respawnCountdown < 0){
			respawnCountdown = 0;
			return false;
		}
		return true;
	}

	private void deactivateInvincibility(){
		shieldInvincibility = false;
		respawnInvincibility = false;
	}

	public RectF getHitbox() {return mRect;}
	public Matrix getMatrix() {return playerMatrix;}
	public PointF getPosition(){ return new PointF(position.x, position.y); }
	public boolean getShieldState(){return shieldInvincibility;}
	public boolean getRespawnState(){return respawnInvincibility;}

	// returns current shoot interval timer stored within the PowerMods
	public long getTimer() {return pow.currentShootThreshold; }

	// State control regarding movement and rotation.
	public void setMoveState(boolean playerMove) {moveState = playerMove;}

	// Modulus prevents overflow of continuous increment.
	private void rotatePlayer(){ angle = (angle + rotationIncrement) % MAX_DEG; }
	public void updateRotation(double newRate) { rotationIncrement = newRate; }

}

/* 
 *	This class will define what powerups the player has picked up.
 * 	Things that can be picked up...
 * 	- Player ship shoots volley every default interval
 * 	- Shooting speed - this can go up? Might not do this.
 *  - Invincibility time frame. When 0, the ship cannot be hit.
 */

class PowerMods {
	long currentShootThreshold;
	long currentElapsedTime;
	long invincibilityCountDown;
	// initialize with default initial states.
	PowerMods() {
		currentShootThreshold    = DEFAULT_SHOOT_THRESHOLD;
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

	// if invincibility power up is picked up,
	// increment the invincibility shield duration
	public void activateShield(){
		invincibilityCountDown += DEFAULT_INVINCIBILITY_DURATION;
	}

	// determines if shield invincibility is enabled
	// by decrementing the countdown until the
	// shield power up duration ends (when count down equals 0)
	public boolean shieldEnabled(long timeDecrement){
		invincibilityCountDown -= timeDecrement;
		if(invincibilityCountDown < 0){
			invincibilityCountDown = 0;
			return false;
		}
		return true;
	}

	// When player loses life, all upgrade settings are reset.
	public void disablePowerUps(){
		currentShootThreshold    = DEFAULT_SHOOT_THRESHOLD;
	}

}
