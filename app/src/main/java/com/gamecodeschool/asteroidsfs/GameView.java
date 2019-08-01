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

import java.util.ArrayList;

// make this an interface

public class  GameView {
        final private int LaserSizeFactor = 2;
        private boolean toggleTransparency= false;

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
        int[] spaceship2Drawables = {
                R.drawable.spaceship2_0,
                R.drawable.spaceship2_1,
        };
        int[] spaceship3Drawables = {
                R.drawable.spaceship3_0,
                R.drawable.spaceship3_1,
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
//                R.drawable.asteroidmedium_2,
//                R.drawable.asteroidmedium_3,
//                R.drawable.asteroidmedium_4,
                R.drawable.asteroidmedium_5,
                R.drawable.asteroidmedium_6,
//                R.drawable.asteroidmedium_7,
//                R.drawable.asteroidmedium_8,
//                R.drawable.asteroidmedium_9,
                R.drawable.asteroidmedium_10,
                R.drawable.asteroidmedium_11,
//                R.drawable.asteroidmedium_12,
//                R.drawable.asteroidmedium_13
//                R.drawable.asteroidmedium_14,
                R.drawable.asteroidmedium_15,
                R.drawable.asteroidmedium_16,
//                R.drawable.asteroidmedium_17,
//                R.drawable.asteroidmedium_18,
//                R.drawable.asteroidmedium_19,
                R.drawable.asteroidmedium_20,
                R.drawable.asteroidmedium_21,
//                R.drawable.asteroidmedium_22,
//                R.drawable.asteroidmedium_23
//                R.drawable.asteroidmedium_24,
                R.drawable.asteroidmedium_25,
                R.drawable.asteroidmedium_26,
//                R.drawable.asteroidmedium_27,
//                R.drawable.asteroidmedium_28,
                R.drawable.asteroidmedium_29,
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
        Bitmap mOpponent2Bitmap;
        Bitmap mPlayerLaserBM;
        Bitmap yellowPowerUpBM;
        Bitmap redPowerUpBM;
        Bitmap bluePowerUpBM;
        Bitmap greenPowerUpBM;
        Bitmap mOpponentLaserBM;
        Bitmap pauseButtonBM;
        Bitmap gameOverBM;
        Bitmap[] mBackGroundGif;
        Bitmap[] spaceshipGIF;
        Bitmap[] spaceship2GIF;
        Bitmap[] spaceship3GIF;
        ArrayList<Bitmap[]> ships;
        Bitmap[] mAsteroidSmallGif;
        Bitmap[] mAsteroidMediumGif;
        Bitmap[] mAsteroidLargeGif;
        int b, ss, s, m, l = 0;



        GameView(Context context, SurfaceHolder surfHolder, Display screen) {
                int asteroidSizeFactor = screen.width / ObjectFactory.DIVISION_FACTOR;
                int shipSize = screen.width / ObjectFactory.shipScaleFactor;
                ourContext = context;
                myHolder = surfHolder;
                myPaint = new Paint();
                screenRes = new PointF(screen.width, screen.height);
                ships = new ArrayList<>();
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
                mBackGroundGif = createGIF(backgroundDrawables, screen.width,
                                                screen.height, true);

                // SPACESHIP BITMAP
                spaceshipGIF = createGIF(spaceshipDrawables,
                                        shipSize + GameConfig.PLAYER_SHIP_PADDING,
                                        shipSize + GameConfig.PLAYER_SHIP_PADDING,
                                        true);
                spaceship2GIF = createGIF(spaceship2Drawables,
                                        shipSize + GameConfig.PLAYER_SHIP_PADDING,
                                        shipSize + GameConfig.PLAYER_SHIP_PADDING,
                                        true);
                spaceship3GIF = createGIF(spaceship3Drawables,
                                        shipSize + GameConfig.PLAYER_SHIP_PADDING,
                                        shipSize + GameConfig.PLAYER_SHIP_PADDING,
                                        true);
                ships.add(spaceshipGIF);
                ships.add(spaceship2GIF);
                ships.add(spaceship3GIF);

                // ASTEROID SMALL BITMAP
                mAsteroidSmallGif = createGIF(asteroidSmallDrawables, asteroidSizeFactor,
                                                asteroidSizeFactor, false);
                // ASTEROID MEDIUM BITMAP
                mAsteroidMediumGif = createGIF(asteroidMediumDrawables, asteroidSizeFactor*2,
                                                asteroidSizeFactor*2, false);
                // ASTEROID LARGE BITMAP
                mAsteroidLargeGif = createGIF(asteroidLargeDrawables, asteroidSizeFactor*3,
                                                asteroidSizeFactor*3, false);

                // Player laser bitmap creation. For now, let's make lasers half the asteroid size.
                mPlayerLaserBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.plaser);
                mPlayerLaserBM = Bitmap.createScaledBitmap(mPlayerLaserBM, asteroidSizeFactor / LaserSizeFactor,
                        asteroidSizeFactor / LaserSizeFactor, false);

                mOpponentBitmap = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.ufo3);
                mOpponentBitmap = Bitmap.createScaledBitmap(mOpponentBitmap, shipSize*2, shipSize, false);

                mOpponent2Bitmap = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.ufo2);
                mOpponent2Bitmap = Bitmap.createScaledBitmap(mOpponent2Bitmap, shipSize*2, shipSize, false);

                mOpponentLaserBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.laser_red);
                mOpponentLaserBM = Bitmap.createScaledBitmap(mOpponentLaserBM, asteroidSizeFactor / LaserSizeFactor,
                        asteroidSizeFactor / LaserSizeFactor, false);

                yellowPowerUpBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.yellowpowerup);
                yellowPowerUpBM = Bitmap.createScaledBitmap(yellowPowerUpBM, asteroidSizeFactor, asteroidSizeFactor, false);

                bluePowerUpBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.bluepowerup);
                bluePowerUpBM = Bitmap.createScaledBitmap(bluePowerUpBM, asteroidSizeFactor, asteroidSizeFactor, false);

                pauseButtonBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.pausebutton);
                pauseButtonBM = Bitmap.createScaledBitmap(pauseButtonBM, asteroidSizeFactor, asteroidSizeFactor, false);

                gameOverBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.gameovermenu1);
                gameOverBM = Bitmap.createScaledBitmap(gameOverBM, screen.width, screen.height, false );

        }

        private Bitmap[] createGIF(int[] drawables, int dstWidth, int dstHeight, boolean filter) {
            Bitmap[] tempGIF = new Bitmap[drawables.length];
            for(int i = 0; i < drawables.length ; i++) {
                tempGIF[i] = BitmapFactory.decodeResource(ourContext.getResources(), drawables[i]);
                tempGIF[i] = Bitmap.createScaledBitmap(tempGIF[i], dstWidth, dstHeight, filter);
            }
            return tempGIF;
        }

        public Point getBitmapDim() {
                Point bitMapDim = new Point(shipBitmap.getWidth(), shipBitmap.getHeight());
                return bitMapDim;
        }

        // Draw the game objects and the HUD.
        // Receives SObjectsCollection packet that contains objects to be rendered by GameView.
        void draw(SObjectsCollection render, GameProgress gProg, boolean userPause, ParticleSystem ps) {
                if (myHolder.getSurface().isValid()) {
                        // Lock the canvas (graphics memory) ready to draw
                        myCanvas = myHolder.lockCanvas();
                        if(!userPause){
                                // Choose a color to paint with
                                myPaint.setColor(Color.argb(255, 75, 180, 250));
                                drawBackground();

                                if(AsteroidsGame.DEBUGGING){ printDebugging(render); }

                                // Methods are ordered alphabetically below draw()
                                drawAsteroids(render);
                                drawPlayer(render, spaceshipGIF);
                                myPaint.setAlpha(255);
                                drawOpponent(render, gProg);
                                //drawSuicider(render, gProg);
                                drawLasers(render, gProg);
                                drawPowerUps(render);
                                drawParticleExplosion(ps);
                                drawPauseButton();
                                drawHUD(gProg);

//                                Log.e("GameView", "CURRENT GAME LEVEL: " + gProg.getLevel());

                        }
                        else{ drawPauseMenu(); }


                        // Display the drawing on screen
                        // unlockCanvasAndPost is a method of SurfaceView
                        myHolder.unlockCanvasAndPost(myCanvas);
                }
        }

        private void drawAsteroids(SObjectsCollection render) {
                // ASTEROIDS
                for (int i = 0; i < render.mAsteroids.size(); i++) {
                        switch (render.mAsteroids.get(i).getSize()) {
                                case 1:
                                        // DRAW SMALL ASTEROID GIF
                                        myCanvas.drawBitmap(mAsteroidSmallGif[s++], render.mAsteroids.get(i).getBitmapX(), render.mAsteroids.get(i).getBitmapY(), myPaint);
                                        if (s == mAsteroidSmallGif.length)
                                                s = 0;
                                        break;
                                case 2:
                                        // DRAW MEDIUM ASTEROID GIF
                                        myCanvas.drawBitmap(mAsteroidMediumGif[m++], render.mAsteroids.get(i).getBitmapX(), render.mAsteroids.get(i).getBitmapY(), myPaint);
                                        if (m == mAsteroidMediumGif.length)
                                                m = 0;
                                        break;
                                case 3:
                                        // DRAW LARGE ASTEROID GIF
                                        myCanvas.drawBitmap(mAsteroidLargeGif[l++], render.mAsteroids.get(i).getBitmapX(), render.mAsteroids.get(i).getBitmapY(), myPaint);
                                        if (l == mAsteroidLargeGif.length)
                                                l = 0;
                                        break;

                        }
//                        Log.e("GameView", "CURRENT GAME LEVEL: " + gProg.getLevel());
                }
        }

        private void drawBackground(){
                // Fills the screen with background "space" image
                myCanvas.drawBitmap(mBackGroundGif[b++], 0, 0, myPaint);
                //myCanvas.drawBitmap(mBackGround, 0, 0, myPaint);
                if(b == mBackGroundGif.length)
                        b = 0;
        }

        private void drawHUD(GameProgress gProg){
                // Choose the font size
                myPaint.setTextSize(screenRes.x / 40);

                // Draws Game HUD
                myCanvas.drawText("Score: " + gProg.getMyScore() + " Lives: " + gProg.getMyLives(), screenRes.x / 75 ,
                        screenRes.x / 50, myPaint);
        }

        private void drawLasers(SObjectsCollection render, GameProgress gProg){
                // PLAYER LASERS
                for(int i = 0; i < render.mPlayerLasers.size(); i++) {
                        myCanvas.drawBitmap(mPlayerLaserBM, render.mPlayerLasers.get(i).getBitmapX(),
                                render.mPlayerLasers.get(i).getBitmapY(), myPaint);
                }

                // OPPONENT LASERS
                for(int i = 0; i < render.mOpponentLasers.size(); i++) {
                        myCanvas.drawBitmap(mOpponentLaserBM, render.mOpponentLasers.get(i).getBitmapX(),
                                render.mOpponentLasers.get(i).getBitmapY(), myPaint);

                }

        }

        private void drawOpponent(SObjectsCollection render, GameProgress gProg){
                // OPPONENT

                for (int i = 0; i < render.mOpponents.size(); i++) {
                        switch(render.mOpponents.get(i).getOppType()) {
                                case SHOOTER:

                                        myCanvas.drawBitmap(mOpponentBitmap, render.mOpponents.get(i).getBitmapX(),
                                                render.mOpponents.get(i).getBitmapY(), myPaint);
                                        break;
                                case SUICIDER:

                                        myCanvas.drawBitmap(mOpponent2Bitmap, render.mOpponents.get(i).getBitmapX(),
                                                render.mOpponents.get(i).getBitmapY(), myPaint);
                                        break;

                        }

                }
        }

        private void drawParticleExplosion(ParticleSystem ps){
                if(ps.mIsRunning == true)
                        ps.draw(myCanvas, myPaint);
        }

        // draw the pause button
        private void drawPauseButton(){ myCanvas.drawBitmap(pauseButtonBM, 2500, 10, myPaint); }

        private void drawPowerUps(SObjectsCollection render){
                // add switch case for power ups..?
                // POWER UPS
//                switch (powerUpType)

                for (int i = 0; i < render.mMineralPowerUps.size(); i++) {
                        switch(render.mMineralPowerUps.get(i).getPowerUpType()){
                                case FIRE_RATE:
                                        myCanvas.drawBitmap(yellowPowerUpBM, render.mMineralPowerUps.get(i).getBitmapX(),
                                                render.mMineralPowerUps.get(i).getBitmapY(), myPaint);
                                        break;

                                case SHIELD:
                                        myCanvas.drawBitmap(bluePowerUpBM, render.mMineralPowerUps.get(i).getBitmapX(),
                                                render.mMineralPowerUps.get(i).getBitmapY(), myPaint);
                                        break;
                        }

                }
        }

        private void drawPlayer(SObjectsCollection render, Bitmap[] shipGIF){
                if(render.mPlayer.getShieldState()){
                        myCanvas.drawArc(render.mPlayer.getHitbox(), 0, 360, true, myPaint);
                        myCanvas.drawBitmap(shipGIF[ss++], render.mPlayer.getMatrix(), myPaint);
                        if(ss == shipGIF.length)
                                ss = 0;
                }
                else if(render.mPlayer.getRespawnState()){
                        if(toggleTransparency){
                                myPaint.setAlpha(0);
                                toggleTransparency = false;
                        }
                        else{
                                myPaint.setAlpha(255);
                                toggleTransparency = true;
                        }
                        myCanvas.drawBitmap(shipGIF[ss++], render.mPlayer.getMatrix(), myPaint);
                        if(ss == shipGIF.length)
                                ss = 0;
                }
                else{
                        myCanvas.drawBitmap(shipGIF[ss++], render.mPlayer.getMatrix(), myPaint);
                        if(ss == shipGIF.length)
                                ss = 0;
                }
        }

//        private void drawSuicider(SObjectsCollection render, GameProgress gProg){
//                for(int i = 0; i < render.mSuiciders.size(); i++){
//                        myCanvas.drawBitmap(mOpponent2Bitmap, render.mSuiciders.get(i).getBitmapX(),
//                                render.mSuiciders.get(i).getBitmapY(), myPaint);
//                }
//        }


        void drawShipMenu() {
                if (myHolder.getSurface().isValid()) {
                        myCanvas = myHolder.lockCanvas();
                        myCanvas.drawARGB(255, 0, 0, 0);

                        // Draw spaceship options
                        float thirdWidth = screenRes.x/3;
                        float halfHeight = screenRes.y/2;
                        myCanvas.drawBitmap(spaceshipGIF[ss++], thirdWidth/2, halfHeight, myPaint);
                        myCanvas.drawBitmap(spaceship2GIF[ss++], thirdWidth/2 + thirdWidth, screenRes.y/2, myPaint);
                        myCanvas.drawBitmap(spaceship3GIF[ss++], thirdWidth/2 + thirdWidth + thirdWidth, screenRes.y/2, myPaint);
                        if(ss == spaceship2GIF.length)
                                ss = 0;
                        
                        // Prompt user to choose a spaceship
                        myPaint.setColor(Color.argb(255, 255, 255, 255));
                        myPaint.setTextSize(screenRes.x / 20);
                        myCanvas.drawText("Choose Your Spaceship",
                                                                        (screenRes.x / 4) - 30,
                                                                        (screenRes.y / 2) + 500,
                                                                        myPaint);
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

                        myCanvas.drawText("PAUSED", (screenRes.x / 2) - 225, screenRes.y / 2, myPaint);
                        myCanvas.drawText("PRESS ANYWHERE TO RESUME", screenRes.x/7,  (screenRes.y / 2) + 150, myPaint);
//                        // unlockCanvasAndPost is a method of SurfaceView
//                        myHolder.unlockCanvasAndPost(myCanvas);
//                }
        }

        void drawGameOver(GameProgress gProg){
                if (myHolder.getSurface().isValid()) {
                        myCanvas = myHolder.lockCanvas();
//                        myCanvas.drawARGB(255, 100, 100, 100);
                        myCanvas.drawBitmap(gameOverBM, 0, 0, myPaint);
                        myPaint.setColor(Color.argb(255, 255, 255, 255));
                        myPaint.setTextSize(screenRes.x / 20);
//                        myCanvas.drawText("Game over!", (screenRes.x / 2) - 250, screenRes.y / 2, myPaint);
                        // Draw some text to prompt restarting
                        //                myPaint.setTextSize(blockSize * 2);
                        myCanvas.drawText("Tap anywhere to restart",
                                (screenRes.x / 4) - 30, (screenRes.y / 2) + 500, myPaint);


                        myPaint.setColor(Color.argb(100, 244, 232, 104));
                        myPaint.setTextSize(screenRes.x / 40);
                        myCanvas.drawText("HIGH SCORE: " + gProg.getHighScore(),
                                (screenRes.x / 3) + 200, (screenRes.y / 2) + 675, myPaint);

                        myCanvas.drawText("YOUR SCORE: " + gProg.getMyScore(),
                                (screenRes.x / 3) + 190, (screenRes.y / 2) + 600, myPaint);

                        myHolder.unlockCanvasAndPost(myCanvas);
                }
        }

        private void printDebugging(SObjectsCollection render){
                Log.e("draw: ", "value of shiphitbox.left: " + render.mPlayer.getHitbox().left);
                Log.e("draw: ", "value of shiphitbox.right: " + render.mPlayer.getHitbox().right);
                Log.e("draw: ", "value of shiphitbox.top: " + render.mPlayer.getHitbox().top);
                Log.e("draw: ", "value of shiphitbox.bottom: " + render.mPlayer.getHitbox().bottom);
                Log.d("draw:", "value of shipcenter.x: " + render.mPlayer.getPosition().x);
                Log.d("draw:", "value of shipcenter.y: " + render.mPlayer.getPosition().y);
                Log.d("draw:", "value of shipbitmap.height: " + shipBitmap.getHeight());
                Log.d("draw:", "value of shipbitmap.width: " + shipBitmap.getWidth());
        }

}
