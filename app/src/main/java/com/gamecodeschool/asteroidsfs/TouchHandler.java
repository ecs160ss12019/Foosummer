package com.gamecodeschool.asteroidsfs;

import android.util.Log;
import android.view.MotionEvent;

/*
 * TouchHandler will receive input from the onTouchEvent.
 * All Player related movements and actions with information
 * 	received from on touch event is all handled here.
 */
public class TouchHandler {
	Player playerRef;
	Display display;
	int rotationPointerId;

	float oldX = 0;
	float newX = 0;

	final private int INVALID = -1;
	final private int NO_ROTATION = 0;
	final private double ROTATION_CONSTANT; // Radian / pixel constant

	TouchHandler(Display display) {
		this.display = display;
		ROTATION_CONSTANT = GameConfig.MAX_DEG / (display.width / 3); // full rotation is 1/4th of display width
	}
	public void playerRotation() {}
	public void playerVelocity() {}
	public void pauseEvent() {}


	public void requestAngleUpdate() {
		if(rotationPointerId != INVALID) {
			float deltaX = newX - oldX;
			playerRef.updateRotation(deltaX * ROTATION_CONSTANT);
			oldX = newX;
		} else {
			playerRef.updateRotation(NO_ROTATION);
		}
	}

	public void reset() {
		rotationPointerId = INVALID;
		oldX = 0;
		newX = 0;
	}

	public void setPlayerRef(Player P) {
		playerRef = P;
	}
	public void setRotation(MotionEvent event) {
		if(rotationPointerId == INVALID) {
			rotationPointerId = event.getPointerId(event.getActionIndex());
			oldX = event.getX(event.getActionIndex()); // set the original point of the event.
		}
	}

	// Check if current event matches rotation ID and if it does, allow it to set new index.
	public void updateRotation(MotionEvent event) {
		if(rotationPointerId == event.getPointerId(event.getActionIndex())) {
			newX = event.getX(event.getActionIndex());
		}
	}


}
