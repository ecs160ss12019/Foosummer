package com.gamecodeschool.asteroidsfs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.SurfaceHolder;
import android.content.Context;

// make this an interface

public class GameView {
        final private int LaserSizeFactor = 2;

        private SurfaceHolder myHolder;
        private Canvas myCanvas;
        private Paint myPaint;
        private Context ourContext;
        //Matrix shipMatrix = new Matrix();

        // Bitmaps that is contained within the gameview.
        Bitmap mAsteroid1;
        Bitmap mAsteroid2;
        Bitmap mAsteroid3;
        Bitmap shipBitmap;
        Bitmap mBackGround;
        Bitmap mOpponentBitmap;
        Bitmap mPlayerLaserBM;

        GameView(Context context, SurfaceHolder surfHolder, Display screen) {
                int asteroidSizeFactor = screen.width / ObjectFactory.DIVISION_FACTOR;
                ourContext = context;
                myHolder = surfHolder;
                myPaint = new Paint();
                // Preload bitmaps for asteroids and make 3 different scale ones.
                Bitmap asteroidBMP = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.asteroid);
                mAsteroid1 = Bitmap.createScaledBitmap(asteroidBMP, asteroidSizeFactor * 1,
                        asteroidSizeFactor * 1, false);
                mAsteroid2 = Bitmap.createScaledBitmap(asteroidBMP, asteroidSizeFactor * 2,
                        asteroidSizeFactor * 2, false);
                mAsteroid3 = Bitmap.createScaledBitmap(asteroidBMP, asteroidSizeFactor * 3,
                        asteroidSizeFactor * 3, false);

                shipBitmap = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.sqspaceship);
                // // Modify the bitmaps to face the ship
                // // in the correct direction
                shipBitmap = Bitmap.createScaledBitmap(shipBitmap, 128, 128, true);
                mBackGround = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.outerspacebackground1);
                // Player laser bitmap creation. For now, let's make lasers half the asteroid size.
                mPlayerLaserBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.plaser);
                mPlayerLaserBM = Bitmap.createScaledBitmap(mPlayerLaserBM, asteroidSizeFactor / LaserSizeFactor,
                                                asteroidSizeFactor / LaserSizeFactor, false);
                shipBitmap.setHasAlpha(true);

                mOpponentBitmap = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.opponent);
                mOpponentBitmap = Bitmap.createScaledBitmap(mOpponentBitmap, 100, 100, false);
        }

        public Point getBitmapDim(){
                Point bitMapDim = new Point(shipBitmap.getWidth(), shipBitmap.getHeight());
                return bitMapDim;
        }

        // Draw the game objects and the HUD.
        // Receives SObjectsCollection packet that contains objects to be rendered by GameView.
        void draw(SObjectsCollection render) {
                // include position of ship (updating move location to be drawn)
                if (myHolder.getSurface().isValid()) {
                        // Lock the canvas (graphics memory) ready to draw
                        myCanvas = myHolder.lockCanvas();

                        // Fills the screen with background "space" image
                        myCanvas.drawBitmap(mBackGround, 0, 0, myPaint);

                        // Choose a color to paint with
                        myPaint.setColor(Color.argb(255, 75, 180, 250));


                        if(AsteroidsGame.DEBUGGING == true) {
                                Log.e("draw: ", "value of shiphitbox.left: " + render.mPlayer.getHitbox().left);
                                Log.e("draw: ", "value of shiphitbox.right: " + render.mPlayer.getHitbox().right);
                                Log.e("draw: ", "value of shiphitbox.top: " + render.mPlayer.getHitbox().top);
                                Log.e("draw: ", "value of shiphitbox.bottom: " + render.mPlayer.getHitbox().bottom);
                                Log.d("draw:", "value of shipcenter.x: " + render.mPlayer.getPosition().x);
                                Log.d("draw:", "value of shipcenter.y: " + render.mPlayer.getPosition().y);
//                                Log.d("draw:", "value of blockSize: " + render.mBlockSize);
                                Log.d("draw:", "value of shipbitmap.height: " + shipBitmap.getHeight());
                                Log.d("draw:", "value of shipbitmap.width: " + shipBitmap.getWidth());
                        }

                        // draw the ship and its hitbox
                        myCanvas.drawRect(render.mPlayer.getHitbox(), myPaint);
                        myCanvas.drawBitmap(shipBitmap, render.mPlayer.getMatrix(), myPaint);

                        // LASERS
                        for(int i = 0; i < render.mPlayerLasers.size(); i++) {
                                myCanvas.drawBitmap(mPlayerLaserBM, render.mPlayerLasers.get(i).getBitmapX(),
                                                render.mPlayerLasers.get(i).getBitmapY(), myPaint);
                        }
                        //
                        // // ASTEROIDS
//                        myPaint.setColor(Color.red(250));
                        for (int i = 0; i < render.mAsteroids.size(); i++) {
                                switch(render.mAsteroids.get(i).getSize()) {
                                case 1:
                                        myCanvas.drawBitmap(mAsteroid1, render.mAsteroids.get(i).getBitmapX(),
                                                render.mAsteroids.get(i).getBitmapY(), myPaint);
                                        break;
                                case 2:
                                        myCanvas.drawBitmap(mAsteroid2, render.mAsteroids.get(i).getBitmapX(),
                                                render.mAsteroids.get(i).getBitmapY(), myPaint);
                                        break;
                                case 3:
                                        myCanvas.drawBitmap(mAsteroid3, render.mAsteroids.get(i).getBitmapX(),
                                                render.mAsteroids.get(i).getBitmapY(), myPaint);
                                        break;
                                }
                        }
                        //
                        // // POWER UPS
                        for(int i = 0; i < render.mMineralPowerUps.size(); i++){
                                render.mMineralPowerUps.get(i).draw(myCanvas, myPaint);
                        }

                        // // OPPONENT
                       // Log.d("GameView", "render.mOpponents.size() " + render.mOpponents.size());
                        for (int i = 0; i < render.mOpponents.size(); i++) {
                                myCanvas.drawBitmap(mOpponentBitmap, render.mOpponents.get(i).getBitmapX(),
                                        render.mOpponents.get(i).getBitmapY(), myPaint);
                        }
                        // Choose the font size
                        // myPaint.setTextSize(fontSize);


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
