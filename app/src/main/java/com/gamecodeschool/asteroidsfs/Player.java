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
	private long laserTimer = 0; // Everytime this is > 500ms, we shoot.
	private double rotationIncrement; // t
	private final long SHOOT_INTERVAL = 500;
	private final int boxLength;
	private final PointF resetPos;

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
		super.update(timeElapsed, display);
		rotatePlayer();
		this.mRect.offsetTo(position.x - boxLength, position.y - boxLength);
	}

	public Matrix configMatrix(Point bitmapDim, int blockSize){
		this.playerMatrix.setRotate((float)(angle * 180/Math.PI),
				bitmapDim.x / 2, bitmapDim.y / 2);

		this.playerMatrix.postTranslate((position.x) - hitRadius - GameConfig.PLAYER_SHIP_PADDING / 2,
				(position.y) - hitRadius - GameConfig.PLAYER_SHIP_PADDING / 2);
		return this.playerMatrix;
	}


	public RectF getHitbox() {return mRect;}

	public Matrix getMatrix() {return playerMatrix;}

	void setMoveState(boolean playerMove) {moveState = playerMove;}

	private void rotatePlayer(){
		angle = (angle + rotationIncrement) % MAX_DEG; // Modulus prevents overflow of continuous increment.
	}

	private void computePlayerVelocity(){
		if(velMagnitude < VELOCITY_RATE * 20) {
			velMagnitude += VELOCITY_RATE;
		}
	}

	public void resetPos() {
		position.x = resetPos.x;
		position.y = resetPos.y;
	}

	public PointF getPosition(){
		return new PointF(position.x, position.y);
	}

	// commented out until implementation.
	//FIXME TODO: Instantiate a new SpaceObject with a laser magnitude, and copy over current position and angle! We should have laser radius stored somewhere..
	public Laser shoot(long timeIncrement, ObjectFactory fac) {
		laserTimer += timeIncrement;
		if (laserTimer > SHOOT_INTERVAL) {
			laserTimer = 0;

			//Log.d("PlayerDebug ", "centerCoords.x: " + centerCoords.x);

			//FIXME TODO: The last int, 1, is a temporary place in for laser damage variable stored in player. This should be able to go up w/ upgrade (maybe)
			return fac.getPlayerLaser(new PointF(position.x, position.y), angle, 1);
		}

		return null;
	}

	public void updateRotation(double newRate) {
		rotationIncrement = newRate;
	}

	public long getTimer() {return laserTimer; }


}
