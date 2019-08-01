package com.gamecodeschool.asteroidsfs;

import android.graphics.PointF;
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
	PointF pauseRadius = new PointF(2497, 116); // FIXME: For now this is hardcoded.

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
	// Simply pass pause if touch type is not Pause, if Pause. Flip value
	public boolean inputEvent(MotionEvent event, boolean pause) {
		int newestIndex = event.getActionIndex();
		TouchClass classificationResult = getClassification(new PointF(event.getX(newestIndex),
													event.getY(newestIndex)));
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
				pause = (pause == true) ? false : true;
				break;
		}
		return pause;
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
	private TouchClass getClassification(PointF input) {
		return input.x < display.width / 2 ? TouchClass.ROTATION : rightsideTouchClass(input);
	}	

	// We keep the ID of what we're tracking if current tracking motion is invalid.
	private void move(MotionEvent event) {
		if(moveId == INVALID) {
			moveId = event.getPointerId(event.getActionIndex()); // get a constant marker for the id.
			playerRef.setMoveState(true);
		}
	}
	
	// FIXME: Complete this later to accomodate pause.
	private TouchClass rightsideTouchClass(PointF input) {
		return (input.x > pauseRadius.x && input.y < pauseRadius.y) ? TouchClass.PAUSE : TouchClass.MOVE; // for now it only returns move. Add position comparison for pause menu!!
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
