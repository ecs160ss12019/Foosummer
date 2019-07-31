package com.gamecodeschool.asteroidsfs;

import android.util.Log;
import android.view.MotionEvent;
// Touch Classification based on the touch location.
enum TouchClass {
	MOVE,
	ROTATION,
	PAUSE
}

/*
 * TouchHandler will receive input from the onTouchEvent.
 * All Player related movements and actions with information
 * 	received from on touch event is all handled here
 */
public class TouchHandler {
	Player playerRef;
	Display display;
	int rotationPointerId;
	int moveId;

	float oldX = 0;
	float newX = 0;

	final private int INVALID = -1;
	final private int NO_ROTATION = 0;
	final private double ROTATION_CONSTANT; // Radian / pixel constant

	TouchHandler(Display display) {
		this.display = display;
		ROTATION_CONSTANT = GameConfig.MAX_DEG / (display.width / 3); // full rotation is 1/4th of display width
		rotationPointerId = INVALID;
		moveId = INVALID;
	}

	
	// Classify the newest event. Then check if they're valid before modifying state.
	public void inputEvent(MotionEvent event) {
		int newestIndex = event.getActionIndex();
		TouchClass classificationResult = getClassification(event.getX(newestIndex));
		// Dispatch to util method based on classification.
		switch(classificationResult) {
			case MOVE:
				move(event);
				break;
			case ROTATION:
				rotate(event);
				break;
			case PAUSE:
				pause(event);
		}
	}

	// Based on change to X position since last angle update request. We calculate new rotational radian angle
	public void requestAngleUpdate() {
		if(rotationPointerId != INVALID) {
			// When old x is 0, don't calculate delta to prevent jumpy rotation.
			float deltaX = oldX != 0 ? newX - oldX : 0;
			playerRef.updateRotation(deltaX * ROTATION_CONSTANT);
			oldX = newX;
		} else {
			playerRef.updateRotation(NO_ROTATION);
		}
	}

	// reset states of the handler.
	public void reset() {
		rotationPointerId = INVALID;
		moveId = INVALID;
		oldX = 0;
		newX = 0;
	}

	public void setPlayerRef(Player P) {
		playerRef = P;
	}

	// FIXME: For now we only really care about two touch up events.
	// Check for event ID that are tracked only. And reset to original state prior to that touch type.
	public void removeEvent(MotionEvent event) {
		int actionIndex = event.getActionIndex();
		if(moveId == event.getPointerId(actionIndex)) {
			moveId = INVALID;
			playerRef.setMoveState(false);
		}
		if(rotationPointerId == event.getPointerId(actionIndex)) {
			rotationPointerId = INVALID;
			oldX = 0;
			newX = 0;
		}
	}

	// Check if current event matches rotation ID and if it does, allow it to set new index.
	public void updateRotation(MotionEvent event) {
		if(event.findPointerIndex(rotationPointerId) != INVALID) {
			newX = event.getX(event.findPointerIndex(rotationPointerId));
		}
	}

	// ------------------------------------------------------------------------------------
	// ----------------------------------- UTIL METHODS -----------------------------------
	// ------------------------------------------------------------------------------------

	// Based on the touch position X, get classification of the method.
	private TouchClass getClassification(float x) {
		return x < display.width / 2 ? TouchClass.ROTATION : rightsideTouchClass(x);
	}	

	// We keep the ID of what we're tracking if current tracking motion is invalid.
	private void move(MotionEvent event) {
		if(moveId == INVALID) {
			moveId = event.getPointerId(event.getActionIndex()); // get a constant marker for the id.
			playerRef.setMoveState(true);
		}
	}
	
	// FIXME: Complete this later to accomodate pause.
	private TouchClass rightsideTouchClass(float intputX) {
		return TouchClass.MOVE; // for now it only returns move. Add position comparison for pause menu!!
	}
	
	// Check to see if the event is what we're tracking. Update when rotating.
	private void rotate(MotionEvent event) {
		if(rotationPointerId == INVALID) {
			rotationPointerId = event.getPointerId(event.getActionIndex());
		}
	}

	//FIXME: Implement later!
	private void pause(MotionEvent event) {

	}

}
