package com.gamecodeschool.asteroidsfs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.SurfaceHolder;
import android.content.Context;
import android.graphics.PointF;

// make this an interface

public class GameView {
        final private int LaserSizeFactor = 2;

        private SurfaceHolder myHolder;
        private Canvas myCanvas;
        private Paint myPaint;
        private Context ourContext;
        private PointF screenRes;
        //Matrix shipMatrix = new Matrix();
        int[] backgroundDrawables = {
                R.drawable.outerspace_0,
//                R.drawable.outerspace_1,
//                R.drawable.outerspace_2,
//                R.drawable.outerspace_3,
//                R.drawable.outerspace_4,
//                R.drawable.outerspace_5,
//                R.drawable.outerspace_6,
//                R.drawable.outerspace_7,
//                R.drawable.outerspace_8,
//                R.drawable.outerspace_9,
//                R.drawable.outerspace_10,
//                R.drawable.outerspace_11,
//                R.drawable.outerspace_12,
//                R.drawable.outerspace_13,
//                R.drawable.outerspace_14,
//                R.drawable.outerspace_15,
//                R.drawable.outerspace_16,
//                R.drawable.outerspace_17,
//                R.drawable.outerspace_18,
//                R.drawable.outerspace_19,
//                R.drawable.outerspace_20,
//                R.drawable.outerspace_21,
//                R.drawable.outerspace_22,
//                R.drawable.outerspace_23,
//                R.drawable.outerspace_24,
//                R.drawable.outerspace_25,
//                R.drawable.outerspace_26,
//                R.drawable.outerspace_27,
//                R.drawable.outerspace_28,
//                R.drawable.outerspace_29,
//                R.drawable.outerspace_30,
//                R.drawable.outerspace_31,
//                R.drawable.outerspace_32,
//                R.drawable.outerspace_33,
//                R.drawable.outerspace_34,
//                R.drawable.outerspace_35,
//                R.drawable.outerspace_36,
//                R.drawable.outerspace_37,
//                R.drawable.outerspace_38,
//                R.drawable.outerspace_39,
//                R.drawable.outerspace_40,
//                R.drawable.outerspace_41,
//                R.drawable.outerspace_42,
//                R.drawable.outerspace_43,
//                R.drawable.outerspace_44,
//                R.drawable.outerspace_45,
//                R.drawable.outerspace_46,
//                R.drawable.outerspace_47,
//                R.drawable.outerspace_48,
//                R.drawable.outerspace_49,
//                R.drawable.outerspace_50
        };
        int[] spaceshipDrawables = {
                R.drawable.spaceship_0,
                R.drawable.spaceship_1,
        };
        int[] asteroidSmallDrawables = {
                R.drawable.asteroidsmall_0,
                R.drawable.asteroidsmall_1,
//                R.drawable.asteroidsmall_2,
//                R.drawable.asteroidsmall_3,
//                R.drawable.asteroidsmall_4,
                R.drawable.asteroidsmall_5,
                R.drawable.asteroidsmall_6,
//                R.drawable.asteroidsmall_7,
//                R.drawable.asteroidsmall_8,
//                R.drawable.asteroidsmall_9,
                R.drawable.asteroidsmall_10,
                R.drawable.asteroidsmall_11,
//                R.drawable.asteroidsmall_12,
//                R.drawable.asteroidsmall_13
//                R.drawable.asteroidsmall_14,
                R.drawable.asteroidsmall_15,
                R.drawable.asteroidsmall_16,
//                R.drawable.asteroidsmall_17,
//                R.drawable.asteroidsmall_18,
//                R.drawable.asteroidsmall_19,
                R.drawable.asteroidsmall_20,
                R.drawable.asteroidsmall_21,
//                R.drawable.asteroidsmall_22,
//                R.drawable.asteroidsmall_23
//                R.drawable.asteroidsmall_24,
                R.drawable.asteroidsmall_25,
                R.drawable.asteroidsmall_26,
//                R.drawable.asteroidsmall_27,
//                R.drawable.asteroidsmall_28,
//                R.drawable.asteroidsmall_29,
        };
        int[] asteroidMediumDrawables = {
                R.drawable.asteroidmedium_0,
                R.drawable.asteroidmedium_1,
//                R.drawable.asteroidsmall_2,
//                R.drawable.asteroidsmall_3,
//                R.drawable.asteroidsmall_4,
                R.drawable.asteroidmedium_5,
                R.drawable.asteroidmedium_6,
//                R.drawable.asteroidsmall_7,
//                R.drawable.asteroidsmall_8,
//                R.drawable.asteroidsmall_9,
                R.drawable.asteroidmedium_10,
                R.drawable.asteroidmedium_11,
//                R.drawable.asteroidsmall_12,
//                R.drawable.asteroidsmall_13
//                R.drawable.asteroidsmall_14,
                R.drawable.asteroidmedium_15,
                R.drawable.asteroidmedium_16,
//                R.drawable.asteroidsmall_17,
//                R.drawable.asteroidsmall_18,
//                R.drawable.asteroidsmall_19,
                R.drawable.asteroidmedium_20,
                R.drawable.asteroidmedium_21,
//                R.drawable.asteroidsmall_22,
//                R.drawable.asteroidsmall_23
//                R.drawable.asteroidsmall_24,
                R.drawable.asteroidmedium_25,
                R.drawable.asteroidmedium_26,
//                R.drawable.asteroidsmall_27,
//                R.drawable.asteroidsmall_28,
//                R.drawable.asteroidsmall_29,
        };
        int[] asteroidLargeDrawables = {
                R.drawable.asteroidlarge_0,
                R.drawable.asteroidlarge_1,
                R.drawable.asteroidlarge_2,
                R.drawable.asteroidlarge_3,
//                R.drawable.asteroidlarge_4,
                R.drawable.asteroidlarge_5,
                R.drawable.asteroidlarge_6,
                R.drawable.asteroidlarge_7,
                R.drawable.asteroidlarge_8,
//                R.drawable.asteroidlarge_9,
                R.drawable.asteroidlarge_10,
                R.drawable.asteroidlarge_11,
                R.drawable.asteroidlarge_12,
                R.drawable.asteroidlarge_13,
//                R.drawable.asteroidlarge_14,
                R.drawable.asteroidlarge_15,
                R.drawable.asteroidlarge_16,
                R.drawable.asteroidlarge_17,
                R.drawable.asteroidlarge_18,
//                R.drawable.asteroidlarge_19,
                R.drawable.asteroidlarge_20,
                R.drawable.asteroidlarge_21,
                R.drawable.asteroidlarge_22,
                R.drawable.asteroidlarge_23,
//                R.drawable.asteroidlarge_24,
                R.drawable.asteroidlarge_25,
                R.drawable.asteroidlarge_26,
                R.drawable.asteroidlarge_27,
                R.drawable.asteroidlarge_28,
//                R.drawable.asteroidlarge_29,
        };



        Bitmap mAsteroid1;
        Bitmap mAsteroid2;
        Bitmap mAsteroid3;
        Bitmap shipBitmap;
        Bitmap mOpponentBitmap;
        Bitmap mPlayerLaserBM;
        Bitmap yellowPowerUpBM;
        Bitmap redPowerUpBM;
        Bitmap bluePowerUpBM;
        Bitmap greenPowerUpBM;
        Bitmap mOpponentLaserBM;
        Bitmap pauseButtonBM;
        Bitmap pauseMenuBM;
        Bitmap[] mBackGroundGif = new Bitmap[backgroundDrawables.length];
        Bitmap[] spaceshipGIF = new Bitmap[spaceshipDrawables.length];
        Bitmap[] mAsteroidSmallGif = new Bitmap[asteroidSmallDrawables.length];
        Bitmap[] mAsteroidMediumGif = new Bitmap[asteroidMediumDrawables.length];
        Bitmap[] mAsteroidLargeGif = new Bitmap[asteroidLargeDrawables.length];
        int b, ss, s, m, l = 0;



        GameView(Context context, SurfaceHolder surfHolder, Display screen) {
                int asteroidSizeFactor = screen.width / ObjectFactory.DIVISION_FACTOR;
                int shipSize = screen.width / ObjectFactory.shipScaleFactor;
                ourContext = context;
                myHolder = surfHolder;
                myPaint = new Paint();
                screenRes = new PointF(screen.width, screen.height);
                // Preload bitmaps for asteroids and make 3 different scale ones.
//                Bitmap asteroidBMP = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.asteroid);
//                Bitmap asteroidSmallBMP = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.asteroidsmall_0);
//                Bitmap asteroidMediumBMP = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.asteroidmedium_0);
//                Bitmap asteroidLargeBMP = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.asteroidlarge_0);
//                mAsteroid1 = Bitmap.createScaledBitmap(asteroidSmallBMP, asteroidSizeFactor * 1,
//                                asteroidSizeFactor , false);
//                mAsteroid2 = Bitmap.createScaledBitmap(asteroidMediumBMP, asteroidSizeFactor * 2,
//                                asteroidSizeFactor * 2, false);
//                mAsteroid3 = Bitmap.createScaledBitmap(asteroidLargeBMP, asteroidSizeFactor * 3,
//                                asteroidSizeFactor * 3, false);



                shipBitmap = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.spaceship_0);
                // // Modify the bitmaps to face the ship
                // // in the correct direction
                shipBitmap = Bitmap.createScaledBitmap(shipBitmap, shipSize + GameConfig.PLAYER_SHIP_PADDING, 
                                                                shipSize + GameConfig.PLAYER_SHIP_PADDING,
                                                                true);


                // BACKGROUND BITMAP
                for(int i = 0; i < backgroundDrawables.length ; i++) {
                        mBackGroundGif[i] = BitmapFactory.decodeResource(ourContext.getResources(), backgroundDrawables[i]);
                        mBackGroundGif[i] = Bitmap.createScaledBitmap(mBackGroundGif[i], screen.width, screen.height, true);
                }
                //mBackGround = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.outerspacebackground1);


                // SPACESHIP BITMAP
                for(int i = 0; i < spaceshipDrawables.length ; i++) {
                        spaceshipGIF[i] = BitmapFactory.decodeResource(ourContext.getResources(), spaceshipDrawables[i]);
                        spaceshipGIF[i] = Bitmap.createScaledBitmap(spaceshipGIF[i], shipSize + GameConfig.PLAYER_SHIP_PADDING,
                                                                        shipSize + GameConfig.PLAYER_SHIP_PADDING, true);
                }


                // ASTEROID SMALL BITMAP
                for(int i = 0; i < asteroidSmallDrawables.length ; i++) {
                        mAsteroidSmallGif[i] = BitmapFactory.decodeResource(ourContext.getResources(), asteroidSmallDrawables[i]);
                        mAsteroidSmallGif[i] = Bitmap.createScaledBitmap(mAsteroidSmallGif[i], asteroidSizeFactor*1, asteroidSizeFactor*1, false);
                }
                // ASTEROID MEDIUM BITMAP
                for(int i = 0; i < asteroidMediumDrawables.length ; i++) {
                        mAsteroidMediumGif[i] = BitmapFactory.decodeResource(ourContext.getResources(), asteroidMediumDrawables[i]);
                        mAsteroidMediumGif[i] = Bitmap.createScaledBitmap(mAsteroidMediumGif[i], asteroidSizeFactor*2, asteroidSizeFactor*2, false);
                }
                // ASTEROID LARGE BITMAP
                for(int i = 0; i < asteroidLargeDrawables.length ; i++) {
                        mAsteroidLargeGif[i] = BitmapFactory.decodeResource(ourContext.getResources(), asteroidLargeDrawables[i]);
                        mAsteroidLargeGif[i] = Bitmap.createScaledBitmap(mAsteroidLargeGif[i], asteroidSizeFactor*3, asteroidSizeFactor*3, false);
                }

                // Player laser bitmap creation. For now, let's make lasers half the asteroid size.
                mPlayerLaserBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.plaser);
                mPlayerLaserBM = Bitmap.createScaledBitmap(mPlayerLaserBM, asteroidSizeFactor / LaserSizeFactor,
                        asteroidSizeFactor / LaserSizeFactor, false);

                shipBitmap.setHasAlpha(true);

                mOpponentBitmap = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.ufo3);
                mOpponentBitmap = Bitmap.createScaledBitmap(mOpponentBitmap, shipSize*2, shipSize, false);

                mOpponentLaserBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.olaser);
                mOpponentLaserBM = Bitmap.createScaledBitmap(mOpponentLaserBM, asteroidSizeFactor / LaserSizeFactor,
                        asteroidSizeFactor / LaserSizeFactor, false);

                yellowPowerUpBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.yellowpowerup);
                yellowPowerUpBM = Bitmap.createScaledBitmap(yellowPowerUpBM, asteroidSizeFactor, asteroidSizeFactor, false);

                mOpponentLaserBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.olaser);
                mOpponentLaserBM = Bitmap.createScaledBitmap(mOpponentLaserBM, asteroidSizeFactor / LaserSizeFactor,
                asteroidSizeFactor / LaserSizeFactor, false);

                pauseButtonBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.pausebutton);
                pauseButtonBM = Bitmap.createScaledBitmap(pauseButtonBM, asteroidSizeFactor, asteroidSizeFactor, false);

                pauseMenuBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.pausemenu);
//                pauseMenuBM = Bitmap.createScaledBitmap(pauseMenuBM, )

        }

        public Point getBitmapDim() {
                Point bitMapDim = new Point(shipBitmap.getWidth(), shipBitmap.getHeight());
                return bitMapDim;
        }

        // Draw the game objects and the HUD.
        // Receives SObjectsCollection packet that contains objects to be rendered by GameView.
        void draw(SObjectsCollection render, GameProgress gProg, boolean userPause, ParticleSystem ps) {
                // include position of ship (updating move location to be drawn)
                if (myHolder.getSurface().isValid()) {
                        // Lock the canvas (graphics memory) ready to draw
                        myCanvas = myHolder.lockCanvas();
                        if(!userPause){


                                // Fills the screen with background "space" image
                                myCanvas.drawBitmap(mBackGroundGif[b++], 0, 0, myPaint);
                                //myCanvas.drawBitmap(mBackGround, 0, 0, myPaint);
                                if(b == mBackGroundGif.length)
                                        b = 0;

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

                                // draw the pause button
                                myCanvas.drawBitmap(pauseButtonBM, 2500, 10, myPaint);



                                // PLAYER SHIP
                                //myCanvas.drawRect(render.mPlayer.getHitbox(), myPaint);
                                //myCanvas.drawBitmap(shipBitmap, render.mPlayer.getMatrix(), myPaint);
                                myCanvas.drawBitmap(spaceshipGIF[ss++], render.mPlayer.getMatrix(), myPaint);
                                if(ss == spaceshipGIF.length)
                                        ss = 0;


                                // LASERS
                                for(int i = 0; i < render.mPlayerLasers.size(); i++) {
                                        myCanvas.drawBitmap(mPlayerLaserBM, render.mPlayerLasers.get(i).getBitmapX(),
                                                render.mPlayerLasers.get(i).getBitmapY(), myPaint);
                                }

                                for(int i = 0; i < render.mOpponentLasers.size(); i++) {
                                        myCanvas.drawBitmap(mOpponentLaserBM, render.mOpponentLasers.get(i).getBitmapX(),
                                                render.mOpponentLasers.get(i).getBitmapY(), myPaint);
                                }


                                // ASTEROIDS
                                for (int i = 0; i < render.mAsteroids.size(); i++) {
                                        switch(render.mAsteroids.get(i).getSize()) {
                                                case 1:
                                                        // DRAW SMALL ASTEROID GIF
                                                        myCanvas.drawBitmap(mAsteroidSmallGif[s++], render.mAsteroids.get(i).getBitmapX(), render.mAsteroids.get(i).getBitmapY(), myPaint);
                                                        if(s == mAsteroidSmallGif.length)
                                                                s = 0;
                                                        break;
                                                case 2:
                                                        // DRAW MEDIUM ASTEROID GIF
                                                        myCanvas.drawBitmap(mAsteroidMediumGif[m++], render.mAsteroids.get(i).getBitmapX(), render.mAsteroids.get(i).getBitmapY(), myPaint);
                                                        if(m == mAsteroidMediumGif.length)
                                                                m = 0;
                                                        break;
                                                case 3:
                                                        // DRAW LARGE ASTEROID GIF
                                                        myCanvas.drawBitmap(mAsteroidLargeGif[l++], render.mAsteroids.get(i).getBitmapX(), render.mAsteroids.get(i).getBitmapY(), myPaint);
                                                        if(l == mAsteroidLargeGif.length)
                                                                l = 0;
                                                        break;
                                        }
                                }






                                //
                                // // POWER UPS
                                for(int i = 0; i < render.mMineralPowerUps.size(); i++){
//                                render.mMineralPowerUps.get(i).draw(myCanvas, myPaint);
                                        myCanvas.drawBitmap(yellowPowerUpBM, render.mMineralPowerUps.get(i).getBitmapX(),
                                                render.mMineralPowerUps.get(i).getBitmapY(), myPaint);
                                }

                                // // OPPONENT
                                // Log.d("GameView", "render.mOpponents.size() " + render.mOpponents.size());
                                for (int i = 0; i < render.mOpponents.size(); i++) {
                                        myCanvas.drawBitmap(mOpponentBitmap, render.mOpponents.get(i).getBitmapX(),
                                                render.mOpponents.get(i).getBitmapY(), myPaint);
                                }
                                // Choose the font size
                                myPaint.setTextSize(screenRes.x / 40);

//                              Draw the HUD
                                myCanvas.drawText("Score: " + gProg.getMyScore() + " Lives: " + gProg.getMyLives(), screenRes.x / 75 ,
                                        screenRes.x / 50, myPaint);


                                // particle system explosion
                                // if(ps.mIsRunning == true){
                                //      ps.draw()
                                //      ps.mIsRunning = false;
                                //{
                                if(ps.mIsRunning == true){
                                        ps.draw(myCanvas, myPaint);
                                }


                        }
                        else{ drawPauseMenu(); }





                        // Display the drawing on screen
                        // unlockCanvasAndPost is a method of SurfaceView
                        myHolder.unlockCanvasAndPost(myCanvas);
                }

        }

        void drawPauseMenu(){
//                if (myHolder.getSurface().isValid()) {
//                        // Lock the canvas (graphics memory) ready to draw
//                        myCanvas = myHolder.lockCanvas();
//                        myCanvas.drawBitmap(pauseMenuBM, 0, 0, myPaint);
                        myCanvas.drawARGB(150, 0, 0, 0);

                        // Choose a color to paint with
                        myPaint.setColor(Color.argb(255, 75, 180, 250));
                        // Choose the font size
                        myPaint.setTextSize(screenRes.x / 20);

                        // Draw the HUD
                        myCanvas.drawText("PAUSED", (screenRes.x / 2) - 225, screenRes.y / 2, myPaint);
                        myCanvas.drawText("PRESS ANYWHERE TO RESUME", screenRes.x/7,  (screenRes.y / 2) + 150, myPaint);
//                        // unlockCanvasAndPost is a method of SurfaceView
//                        myHolder.unlockCanvasAndPost(myCanvas);
//                }
        }

}
