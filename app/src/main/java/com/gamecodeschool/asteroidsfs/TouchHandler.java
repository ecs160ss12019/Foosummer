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
 * TouchHandler receives motion event from touchevent function frmo Asteroid.
 * The TouchHandler keeps track of three areas the user touches, that is left half
 * 	of screen for rotation of ship, right half for moving forward, and the top
 * 	right corner for handling pause.
 * This object tracks only one touch event pointer and nothing else in that touch
 * 	zone until the touch zone is lifted.
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
		// Constant used to translate touch distance to rotation
		ROTATION_CONSTANT = GameConfig.MAX_DEG / (display.width / 3); 
		rotationPointerId = INVALID;
		moveId = INVALID;
	}

	
	// Classify the newest event. Then check if they're valid before modifying state.
	// Pause is a simple pass by value until pause action is taken.
	public boolean inputEvent(MotionEvent event, boolean pause) {
		int newestIndex = event.getActionIndex();
		TouchClass classificationResult = getClassification(new PointF(event.getX(newestIndex),
									event.getY(newestIndex))); // Receives new classification based on touch position.
		// Dispatch to util method based on classification.
		switch(classificationResult) {
			case MOVE:
				move(event);
				break;
			case ROTATION:
				rotate(event);
				break;
			case PAUSE:
				pause = (pause) ? false : true; // flips pause values when pause button is touched.
				break;
		}
		return pause;
	}

	/* 
		This request is called from outside by AsteroidGames.
		The call checks if there has been movements on pointer if tracked and translates to degree value.
		If there is no pointer being tracked simply sets rotation to 0.
	 */
	public void requestAngleUpdate() {
		if(rotationPointerId != INVALID) { // Valid Touch Pointer Requires rotation update per game loop cycle.
			// When old x is 0, don't calculate delta to prevent jumpy rotation.
			float deltaX = (oldX != 0) ? (newX - oldX) : 0;
			playerRef.updateRotation(deltaX * ROTATION_CONSTANT);
			oldX = newX; // Overwrites old position with current position for next cycle update.
		} else {
			playerRef.updateRotation(NO_ROTATION); // No Touch Pointer is tracked.
		}
	}

	// reset states of the handler.
	public void reset() {
		rotationPointerId = INVALID;
		moveId = INVALID;
		oldX = 0;
		newX = 0;
	}

	// Required setter to call player's updateRotation(degree) method.
	public void setPlayerRef(Player P) {
		playerRef = P;
	}

	// Check for event ID that are tracked only. And reset to original state prior to that touch type.
	public void removeEvent(MotionEvent event) {
		int actionIndex = event.getActionIndex();

		// Current lifted motion is tracked as move, we reset this and tell player to stop moving.
		if(moveId == event.getPointerId(actionIndex)) {
			moveId = INVALID;
			playerRef.setMoveState(false);
		}

		// Current pointer is tracked rotation. Reset the rotation state
		// Reset is required to stop player from rotation after finger has been lifted.
		if(rotationPointerId == event.getPointerId(actionIndex)) {
			rotationPointerId = INVALID;
			oldX = 0;
			newX = 0;
		}
	}

	// Called on every ACTION_MOVE event. This check if the pointer is tracked and changes tracked horizontal position.
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
		return (input.x < (display.width / 2)) ? TouchClass.ROTATION : rightsideTouchClass(input);
	}	

	// We keep the ID of what we're tracking if current tracking motion is invalid.
	private void move(MotionEvent event) {
		if(moveId == INVALID) {
			moveId = event.getPointerId(event.getActionIndex()); // get a constant marker for the id.
			playerRef.setMoveState(true);
		}
	}
	
	// When right side of the screen is touched, Check if it's in pause area (top right corner)
	private TouchClass rightsideTouchClass(PointF input) {
		return ((input.x > pauseRadius.x) && (input.y < pauseRadius.y)) ? TouchClass.PAUSE : TouchClass.MOVE; // for now it only returns move. Add position comparison for pause menu!!
	}
	
	// Begin tracking new pointer and assign its id (constant) to the tracking variable.
	private void rotate(MotionEvent event) {
		if(rotationPointerId == INVALID) {
			rotationPointerId = event.getPointerId(event.getActionIndex());
		}
	}
}
