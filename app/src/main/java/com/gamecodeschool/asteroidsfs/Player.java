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


//		configHitboxSize();
//		configHitboxLocation();
		//currCoords.x = pos.x
		//currCoords.y = pos.y
		//hitboxDim.x = playerLength

		// Intialize mRect (hitbox) based on the size and position
		mRect = new RectF(pos.x, pos.y,
				pos.x + playerLength - SCALE_TO_CENTER,
				pos.y + playerLength - SCALE_TO_CENTER);
//			Log.e("player: ", "value of hitboxDim.x: " + hitboxDim.x);
//		    Log.e("player: ", "value of mRect.left: " + mRect.left);
//			Log.e("player: ", "value of mRect.right: " + mRect.right);
//			Log.e("player: ", "value of mRect.top: " + mRect.top);
//			Log.e("player: ", "value of mRect.bottom: " + mRect.bottom);

//		setPlayerCenter();
	}

//	void configHitboxLocation(){
//		// start player ship location at center
//		// of the screen
//		this.currCoords = new PointF(maxCoords.x / 2, maxCoords.y / 2);
//	}

//	void configHitboxSize(){
//		// Configure the size of the player's
//		// hitbox based on the screen resolution
//		this.hitboxDim = new PointF(maxCoords.x / 25, maxCoords.y / 25);
//	}

	// Update the Player- Called each frame/loop
	// Update arguments within the AsteroidsGame class
	@Override
	public void update(long timeElapsed, Display display ) {
		if(moveState == true) {
			computePlayerVelocity();
		}
		super.update(timeElapsed, display);
		rotatePlayer();
//		movePlayer(timeElapsed);
		this.mRect.offsetTo(position.x, position.y);
	}

	public Matrix configMatrix(Point bitmapDim, int blockSize){
		this.playerMatrix.setRotate((float)super.angle,
				bitmapDim.x / 2, bitmapDim.y / 2);

		this.playerMatrix.postTranslate((super.position.x) - blockSize,
				(position.y) - blockSize);
		return this.playerMatrix;
	}


	public RectF getHitbox() {return mRect;}

	public Matrix getMatrix() {return this.playerMatrix;}

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
			angle += 5;
		} else {
			this.angle = angle;
		}
	}

// 	void movePlayer(long timeElapsed) {

// 		if (moveState == true) {
// 			computePlayerVelocity();
// 			mRect.offset(newPos.x, newPos.y);

// 			// remove this to reincorporate drag.... this is a bit clunky
// 			if(newPos.x > 0 && currVelocity.x < 0){newPos.x = -VELOCITY_RATE;}
// 			if(newPos.x < 0 && currVelocity.x > 0){newPos.x = VELOCITY_RATE;}
// 			if(newPos.y > 0 && currVelocity.y < 0){newPos.y = -VELOCITY_RATE;}
// 			if(newPos.y < 0 && currVelocity.y > 0){newPos.y = VELOCITY_RATE;}

// 			// adds velocity to offset to the new position of the Player (hitbox)
// 			newPos.x += currVelocity.x/timeElapsed;
// 			newPos.y += currVelocity.y/timeElapsed;
// //			mRect.offsetTo();

// 			// setPlayerCenter();
// 			wrapAroundPlayer();
// //			Log.e("boundary: ","max_X: " + maxCoords.x);
// //			Log.e("boundary: ", "max_Y: " + maxCoords.y);

// //			Log.e("player: ", "movementMagnitude: " + movementMagnitude);
// //			Log.d("player: ", "angle: " + angle);
// //			Log.d("player: ", "value of currVelocity.x: " + currVelocity.x);
// //			Log.d("player: ", "value of currVelocity.y: " + currVelocity.y);
// //			Log.d("player: ", "value of newPos.x: " + newPos.x);
// //			Log.d("player: ", "value of newPos.y: " + newPos.y);

// //			Log.d("player: ", "value of mRect.left: " + mRect.left);
// //			Log.d("player: ", "value of mRect.right: " + mRect.right);
// //			Log.d("player: ", "value of mRect.LRdiff: " + (mRect.right-mRect.left));
// //			Log.d("player: ", "value of mRect.top: " + mRect.top);
// //			Log.d("player: ", "value of mRect.bottom: " + mRect.bottom);
// //			Log.d("player: ", "value of mRect.BTdiff: " + (mRect.bottom-mRect.top));
// //			Log.d("player: ", "value of shipCenter.x: " + centerCoords.x);
// //			Log.d("player: ", "value of shipCenter.y: " + centerCoords.y);
// 		}
// 		else{
// 			this.currVelocity.x = 0;
// 			this.currVelocity.y = 0;
// 			this.newPos.x = 0;
// 			this.newPos.y = 0;
// 			movementMagnitude = 0;
// 		}
// 	}

//	void wrapAroundPlayer(){
//		// wrap around for the Player ship
//		if (mRect.right < 0)
//			mRect.left = maxCoords.x-WRAP_AROUND_OFFSET;
//		mRect.right = mRect.left + WRAP_AROUND_OFFSET;
//		if (mRect.left > maxCoords.x)
//			mRect.left =  0;
//		mRect.right = mRect.left + WRAP_AROUND_OFFSET;
//		if (mRect.bottom < 0)
//			mRect.top = maxCoords.y-WRAP_AROUND_OFFSET;
//		mRect.bottom = mRect.top + WRAP_AROUND_OFFSET;
//		if (mRect.top > maxCoords.y)
//			mRect.top = 0;
//		mRect.bottom = mRect.top + WRAP_AROUND_OFFSET;
//	}

	void computePlayerVelocity(){
		if(super.velMagnitude < VELOCITY_RATE) {

			super.velMagnitude += VELOCITY_RATE;
		}
//		if(movementMagnitude > MAX_VELOCITY){
//			this.currVelocity.x = MAX_VELOCITY * (float) Math.cos(angle * RAD_TO_DEG);
//			this.currVelocity.y = MAX_VELOCITY * (float) Math.sin(angle * RAD_TO_DEG);
//		}
//		else{
//			this.currVelocity.x = movementMagnitude * (float) Math.cos(angle * RAD_TO_DEG);
//			this.currVelocity.y = movementMagnitude * (float) Math.sin(angle * RAD_TO_DEG);
//		}
	}

	// void setPlayerCenter(){
	// 	centerCoords = new PointF((int)(mRect.left+0.5*(mRect.right-mRect.left)),
	// 			(int)(mRect.top+0.5*(mRect.bottom-mRect.top)));
	// }







	// commented out until implementation.
	//FIXME TODO: Instantiate a new SpaceObject with a laser magnitude, and copy over current position and angle! We should have laser radius stored somewhere..
	public Laser shoot(long timeIncrement, ObjectFactory fac) {
		laserTimer += timeIncrement;
		if (laserTimer > SHOOT_INTERVAL) {
			laserTimer = 0;
			//FIXME TODO: The last int, 1, is a temporary place in for laser damage variable stored in player. This should be able to go up w/ upgrade (maybe)
			return fac.getPlayerLaser(new PointF(super.position.x, super.position.y),(angle * Math.PI / 180), 1);
		}

		return null;
	}

	public long getTimer() {return laserTimer; }


}
