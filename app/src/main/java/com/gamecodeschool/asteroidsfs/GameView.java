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

        // Bitmaps that is contained within the gameview.
        int[] backgroundDrawables = {
                R.drawable.outerspace_0,
                R.drawable.outerspace_1,
                R.drawable.outerspace_2,
                R.drawable.outerspace_3,
                R.drawable.outerspace_4,
                R.drawable.outerspace_5,
                R.drawable.outerspace_6,
                R.drawable.outerspace_7,
                R.drawable.outerspace_8,
                R.drawable.outerspace_9,
                R.drawable.outerspace_10,
                R.drawable.outerspace_11,
                R.drawable.outerspace_12,
                R.drawable.outerspace_13,
                R.drawable.outerspace_14,
                R.drawable.outerspace_15,
                R.drawable.outerspace_16,
                R.drawable.outerspace_17,
                R.drawable.outerspace_18,
                R.drawable.outerspace_19,
                R.drawable.outerspace_20,
                R.drawable.outerspace_21,
                R.drawable.outerspace_22,
                R.drawable.outerspace_23,
                R.drawable.outerspace_24,
                R.drawable.outerspace_25,
                R.drawable.outerspace_26,
                R.drawable.outerspace_27,
                R.drawable.outerspace_28,
                R.drawable.outerspace_29,
                R.drawable.outerspace_30,
                R.drawable.outerspace_31,
                R.drawable.outerspace_32,
                R.drawable.outerspace_33,
                R.drawable.outerspace_34,
                R.drawable.outerspace_35,
                R.drawable.outerspace_36,
                R.drawable.outerspace_37,
                R.drawable.outerspace_38,
                R.drawable.outerspace_39,
                R.drawable.outerspace_40,
                R.drawable.outerspace_41,
                R.drawable.outerspace_42,
                R.drawable.outerspace_43,
                R.drawable.outerspace_44,
                R.drawable.outerspace_45,
                R.drawable.outerspace_46,
                R.drawable.outerspace_47,
                R.drawable.outerspace_48,
                R.drawable.outerspace_49,
                R.drawable.outerspace_50,
                R.drawable.outerspace_51,
                R.drawable.outerspace_52,
                R.drawable.outerspace_53,
                R.drawable.outerspace_54,
                R.drawable.outerspace_55,
                R.drawable.outerspace_56,
                R.drawable.outerspace_57,
                R.drawable.outerspace_58,
                R.drawable.outerspace_59,
                R.drawable.outerspace_60,
                R.drawable.outerspace_61,
                R.drawable.outerspace_62,
                R.drawable.outerspace_63,
                R.drawable.outerspace_64,
                R.drawable.outerspace_65,
                R.drawable.outerspace_66,
                R.drawable.outerspace_67,
                R.drawable.outerspace_68,
                R.drawable.outerspace_69,
                R.drawable.outerspace_70,
                R.drawable.outerspace_71,
                R.drawable.outerspace_72,
                R.drawable.outerspace_73,
                R.drawable.outerspace_74};

        int[] asteroidDrawables = {
                R.drawable.asteroid_0,
                R.drawable.asteroid_1,
                R.drawable.asteroid_2,
                R.drawable.asteroid_3,
                R.drawable.asteroid_4,
                R.drawable.asteroid_5,
                R.drawable.asteroid_6,
                R.drawable.asteroid_7,
                R.drawable.asteroid_8,
                R.drawable.asteroid_9,
                R.drawable.asteroid_10,
                R.drawable.asteroid_11,
                R.drawable.asteroid_12,
                R.drawable.asteroid_13,
                R.drawable.asteroid_14,
                R.drawable.asteroid_15,
                R.drawable.asteroid_16,
                R.drawable.asteroid_17,
                R.drawable.asteroid_18,
                R.drawable.asteroid_19,
                R.drawable.asteroid_20,
                R.drawable.asteroid_21,
                R.drawable.asteroid_22,
                R.drawable.asteroid_23,
                R.drawable.asteroid_24,
                R.drawable.asteroid_25,
                R.drawable.asteroid_26,
                R.drawable.asteroid_27,
                R.drawable.asteroid_28,
                R.drawable.asteroid_29};
        Bitmap[] mBackGround = new Bitmap[backgroundDrawables.length];
        Bitmap[] mAsteroid = new Bitmap[asteroidDrawables.length];
        int k,m,n = 0;
        Bitmap mAsteroid1;
        Bitmap mAsteroid2;
        Bitmap mAsteroid3;
        Bitmap shipBitmap;
        Bitmap mOpponentBitmap;
        Bitmap mPlayerLaserBM;
        Bitmap yellowPowerUpBM;
        Bitmap mOpponentLaserBM;


        GameView(Context context, SurfaceHolder surfHolder, Display screen) {
                int asteroidSizeFactor = screen.width / ObjectFactory.DIVISION_FACTOR;
                int shipSize = screen.width / ObjectFactory.shipScaleFactor;
                ourContext = context;
                myHolder = surfHolder;
                myPaint = new Paint();
                screenRes = new PointF(screen.width, screen.height);
                // Preload bitmaps for asteroids and make 3 different scale ones.
                Bitmap asteroidBMP = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.asteroid);
                mAsteroid1 = Bitmap.createScaledBitmap(asteroidBMP, asteroidSizeFactor * 1,
                                asteroidSizeFactor , false);
                mAsteroid2 = Bitmap.createScaledBitmap(asteroidBMP, asteroidSizeFactor * 2,
                                asteroidSizeFactor * 2, false);
                mAsteroid3 = Bitmap.createScaledBitmap(asteroidBMP, asteroidSizeFactor * 3,
                                asteroidSizeFactor * 3, false);

                shipBitmap = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.sqspaceship);
                // // Modify the bitmaps to face the ship
                // // in the correct direction
                shipBitmap = Bitmap.createScaledBitmap(shipBitmap, shipSize + GameConfig.PLAYER_SHIP_PADDING,
                                                            shipSize + GameConfig.PLAYER_SHIP_PADDING,
                                                            true);
                //DRAW BACKGROUND

                for(int i = 0; i < backgroundDrawables.length ; i++) {
                        mBackGround[i] = BitmapFactory.decodeResource(ourContext.getResources(), backgroundDrawables[i]);
                        mBackGround[i] = Bitmap.createScaledBitmap(mBackGround[i], screen.width, screen.height, true);
                }

                // Player laser bitmap creation. For now, let's make lasers half the asteroid size.
                mPlayerLaserBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.plaser);
                mPlayerLaserBM = Bitmap.createScaledBitmap(mPlayerLaserBM, asteroidSizeFactor / LaserSizeFactor,
                        asteroidSizeFactor / LaserSizeFactor, false);

                shipBitmap.setHasAlpha(true);

                mOpponentBitmap = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.opponent);
                mOpponentBitmap = Bitmap.createScaledBitmap(mOpponentBitmap, shipSize, shipSize, false);

                yellowPowerUpBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.yellowpowerup);
                yellowPowerUpBM = Bitmap.createScaledBitmap(yellowPowerUpBM, asteroidSizeFactor, asteroidSizeFactor, false);

                mOpponentLaserBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.olaser);
                mOpponentLaserBM = Bitmap.createScaledBitmap(mOpponentLaserBM, asteroidSizeFactor / LaserSizeFactor,
                asteroidSizeFactor / LaserSizeFactor, false);
        }

        public Point getBitmapDim(){
                Point bitMapDim = new Point(shipBitmap.getWidth(), shipBitmap.getHeight());
                return bitMapDim;
        }

        // Draw the game objects and the HUD.
        // Receives SObjectsCollection packet that contains objects to be rendered by GameView.
        void draw(SObjectsCollection render, GameProgress gProg) {
                // include position of ship (updating move location to be drawn)
                if (myHolder.getSurface().isValid()) {
                        // Lock the canvas (graphics memory) ready to draw
                        myCanvas = myHolder.lockCanvas();

                        // Fills the screen with background "space" image

                        myCanvas.drawBitmap(mBackGround[k++], 0, 0, myPaint);
                        if(k == backgroundDrawables.length)
                                k =0;
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

                        for(int i = 0; i < render.mOpponentLasers.size(); i++) {
                                myCanvas.drawBitmap(mOpponentLaserBM, render.mOpponentLasers.get(i).getBitmapX(),
                                        render.mOpponentLasers.get(i).getBitmapY(), myPaint);
                        }

                        //
                        // // ASTEROIDS
//                        myPaint.setColor(Color.red(250));
                        for (int i = 0; i < render.mAsteroids.size(); i++) {
                                    // GIF
//                                myCanvas.drawBitmap(mAsteroid[m++], render.mAsteroids.get(i).getBitmapX(),
//                                                render.mAsteroids.get(i).getBitmapY(), myPaint);
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
                        if(m == asteroidDrawables.length)
                                m =0;
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

//                       Draw the HUD
                        myCanvas.drawText("Score: " + gProg.getMyScore() + " Lives: " + gProg.getMyLives(), screenRes.x / 75 ,
                                screenRes.x / 50, myPaint);

//                         if(DEBUGGING){
//                         printDebuggingText();
//                         }
                        // Display the drawing on screen
                        // unlockCanvasAndPost is a method of SurfaceView
                        myHolder.unlockCanvasAndPost(myCanvas);
                }

        }

}
