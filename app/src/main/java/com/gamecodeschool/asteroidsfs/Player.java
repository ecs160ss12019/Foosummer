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
	private long laserTimer = 0; // Everytime this is > 500ms, we shoot.
	private final long SHOOT_INTERVAL = 500;

//	PointF pos, double angle, float velocityMagnitude, float hitCircleSize
	Player(PointF pos, float playerLength) {
		super(pos, 0, 0, playerLength);

		// Intialize mRect (hitbox) based on the size and position
		mRect = new RectF(pos.x, pos.y,
				pos.x + playerLength - SCALE_TO_CENTER,
				pos.y + playerLength - SCALE_TO_CENTER);

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
		this.mRect.offsetTo(position.x, position.y);
	}

	public Matrix configMatrix(Point bitmapDim, int blockSize){
		this.playerMatrix.setRotate((float)(angle * 180/Math.PI),
				bitmapDim.x / 2, bitmapDim.y / 2);

		this.playerMatrix.postTranslate((position.x) - blockSize,
				(position.y) - blockSize);
		return this.playerMatrix;
	}


	public RectF getHitbox() {return mRect;}

	public Matrix getMatrix() {return playerMatrix;}

	void setMoveState(boolean playerMove) {moveState = playerMove;}

	void setRotationState(int playerRotate) {rotateState = rotationStates[playerRotate];}

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

	void computePlayerVelocity(){
		if(velMagnitude < VELOCITY_RATE) {
			velMagnitude += VELOCITY_RATE;
		}
	}

	// commented out until implementation.
	//FIXME TODO: Instantiate a new SpaceObject with a laser magnitude, and copy over current position and angle! We should have laser radius stored somewhere..
	public Laser shoot(long timeIncrement, ObjectFactory fac) {
		laserTimer += timeIncrement;
		if (laserTimer > SHOOT_INTERVAL) {
			laserTimer = 0;
			//FIXME TODO: The last int, 1, is a temporary place in for laser damage variable stored in player. This should be able to go up w/ upgrade (maybe)
			return fac.getPlayerLaser(new PointF(position.x, position.y), angle, 1);
		}

		return null;
	}

	public long getTimer() {return laserTimer; }


}
