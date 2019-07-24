package com.gamecodeschool.asteroidsfs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.content.Context;
import android.graphics.RectF;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.view.View;
import android.app.Activity;

import java.util.ArrayList;

// make this an interface

public class GameView {

        private SurfaceHolder myHolder;
        private Canvas myCanvas;
        private Paint myPaint;
        private Context ourContext;
        //Matrix shipMatrix = new Matrix();

        // Bitmaps that is contained within the gameview.
        Bitmap mAsteroids;
        public Bitmap shipBitmap;
        Bitmap mBackGround;

        GameView(Context context, SurfaceHolder surfHolder) {
                ourContext = context;
                myHolder = surfHolder;
                myPaint = new Paint();
                mAsteroids = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.asteroid);
                mAsteroids = Bitmap.createScaledBitmap(mAsteroids, 100, 100, false);

                shipBitmap = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.sqspaceship);
                // // Modify the bitmaps to face the ship
                // // in the correct direction
                shipBitmap = Bitmap.createScaledBitmap(shipBitmap, 128, 128, true);
                mBackGround = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.outerspacebackground1);
                shipBitmap.setHasAlpha(true);


        }

        public Point getBitmapDim(){
                Point bitMapDim = new Point(shipBitmap.getWidth(), shipBitmap.getHeight());
                return bitMapDim;
        }

        // Draw the game objects and the HUD.
        // Receives Render packet that contains objects to be rendered by GameView.
        void draw(Render render) {
                // include position of ship (updating move location to be drawn)
                if (myHolder.getSurface().isValid()) {
                        // Lock the canvas (graphics memory) ready to draw
                        myCanvas = myHolder.lockCanvas();

                        // Fills the screen with background "space" image
                        myCanvas.drawBitmap(mBackGround, 0, 0, null);

                        // Choose a color to paint with
                        myPaint.setColor(Color.argb(255, 75, 180, 250));


                        if(AsteroidsGame.DEBUGGING == true) {
                                Log.e("draw: ", "value of shiphitbox.left: " + render.mPlayer.getHitbox().left);
                                Log.e("draw: ", "value of shiphitbox.right: " + render.mPlayer.getHitbox().right);
                                Log.e("draw: ", "value of shiphitbox.top: " + render.mPlayer.getHitbox().top);
                                Log.e("draw: ", "value of shiphitbox.bottom: " + render.mPlayer.getHitbox().bottom);
                                Log.d("draw:", "value of shipcenter.x: " + render.mPlayer.getCenterCoords().x);
                                Log.d("draw:", "value of shipcenter.y: " + render.mPlayer.getCenterCoords().y);
//                                Log.d("draw:", "value of blockSize: " + render.mBlockSize);
                                Log.d("draw:", "value of shipbitmap.height: " + shipBitmap.getHeight());
                                Log.d("draw:", "value of shipbitmap.width: " + shipBitmap.getWidth());
                        }

                        // draw the ship and its hitbox
                        myCanvas.drawRect(render.mPlayer.getHitbox(), myPaint);
                        myCanvas.drawBitmap(shipBitmap, render.mPlayer.getMatrix(), myPaint);



                        // // LASERS
                        // // Draw lasers
                        // for(int i = 0; i < myLasers.size(); i++) {
                        // myLasers.get(i).draw(myCanvas);
                        // }
                        //
                        // // ASTEROIDS
                        for (int i = 0; i < render.mAsteroids.size(); i++) {
                                myCanvas.drawBitmap(mAsteroids, render.mAsteroids.get(i).getHitbox().left,
                                                render.mAsteroids.get(i).getHitbox().top, myPaint);
                        }
                        //
                        // // POWER UPS
                        // for(int i = 0; i < mineralPowerUps.length; i++){
                        // mineralPowerUps[i].draw(myCanvas);
                        // }

//                         Choose the font size
//                         myPaint.setTextSize(fontSize);

//                         Draw the HUD
//                         myCanvas.drawText("Score: " + score + " Lives: " + lives, fontMargin ,
//                         fontSize, myPaint);

                        // if(DEBUGGING){
                        // printDebuggingText();
                        // }
                        // Display the drawing on screen
                        // unlockCanvasAndPost is a method of SurfaceView
                        myHolder.unlockCanvasAndPost(myCanvas);
                }

        }

}
