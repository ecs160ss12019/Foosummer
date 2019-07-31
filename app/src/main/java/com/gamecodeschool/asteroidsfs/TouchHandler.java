package com.gamecodeschool.asteroidsfs;

/*
 * TouchHandler will receive input from the onTouchEvent.
 * All Player related movements and actions with information
 * 	received from on touch event is all handled here.
 */
public class TouchHandler {
	Player playerRef;
	Display display;
	float oldX;
	float newX;
	
	TouchHandler(Display display, Player P) {
		playerRef = P;
		this.display = display;
	}
	public void playerRotation() {}
	public void playerVelocity() {}
	public void pauseEvent() {}
}
