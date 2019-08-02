package com.gamecodeschool.asteroidsfs;

import static com.gamecodeschool.asteroidsfs.GameConfig.LASER_SIZE_FACTOR;
import static com.gamecodeschool.asteroidsfs.GameConfig.DIVISION_FACTOR;
import static com.gamecodeschool.asteroidsfs.GameConfig.SHIP_SCALE_FACTOR;
import static com.gamecodeschool.asteroidsfs.GameConfig.PAUSE_BUTTON_SCALE;

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

public class  GameView {
        private boolean toggleTransparency= false;

        private SurfaceHolder myHolder;
        private Canvas myCanvas;
        private Paint myPaint;
        private Context ourContext;
        private PointF screenRes;

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
                R.drawable.asteroidsmall_5,
                R.drawable.asteroidsmall_6,
                R.drawable.asteroidsmall_10,
                R.drawable.asteroidsmall_11,
                R.drawable.asteroidsmall_15,
                R.drawable.asteroidsmall_16,
                R.drawable.asteroidsmall_20,
                R.drawable.asteroidsmall_21,
                R.drawable.asteroidsmall_25,
                R.drawable.asteroidsmall_26,
        };
        
        int[] asteroidMediumDrawables = {
                R.drawable.asteroidmedium_0,
                R.drawable.asteroidmedium_1,
                R.drawable.asteroidmedium_5,
                R.drawable.asteroidmedium_6,
                R.drawable.asteroidmedium_10,
                R.drawable.asteroidmedium_11,
                R.drawable.asteroidmedium_15,
                R.drawable.asteroidmedium_16,
                R.drawable.asteroidmedium_20,
                R.drawable.asteroidmedium_21,
                R.drawable.asteroidmedium_25,
                R.drawable.asteroidmedium_26,
                R.drawable.asteroidmedium_29,
        };
        int[] asteroidLargeDrawables = {
                R.drawable.asteroidlarge_0,
                R.drawable.asteroidlarge_1,
                R.drawable.asteroidlarge_2,
                R.drawable.asteroidlarge_3,
                R.drawable.asteroidlarge_5,
                R.drawable.asteroidlarge_6,
                R.drawable.asteroidlarge_7,
                R.drawable.asteroidlarge_8,
                R.drawable.asteroidlarge_10,
                R.drawable.asteroidlarge_11,
                R.drawable.asteroidlarge_12,
                R.drawable.asteroidlarge_13,
                R.drawable.asteroidlarge_15,
                R.drawable.asteroidlarge_16,
                R.drawable.asteroidlarge_17,
                R.drawable.asteroidlarge_18,
                R.drawable.asteroidlarge_20,
                R.drawable.asteroidlarge_21,
                R.drawable.asteroidlarge_22,
                R.drawable.asteroidlarge_23,
                R.drawable.asteroidlarge_25,
                R.drawable.asteroidlarge_26,
                R.drawable.asteroidlarge_27,
                R.drawable.asteroidlarge_28,
        };


        Bitmap mBackGround;
        Bitmap shipBitmap;
        Bitmap mOpponentBitmap;
        Bitmap mOpponent2Bitmap;
        Bitmap mPlayerLaserBM;
        Bitmap yellowPowerUpBM;
        Bitmap bluePowerUpBM;
        Bitmap mOpponentLaserBM;
        Bitmap pauseButtonBM;
        Bitmap gameOverBM;
        Bitmap[] spaceshipGIF;
        Bitmap[] spaceship2GIF;
        Bitmap[] spaceship3GIF;
        ArrayList<Bitmap[]> ships;
        Bitmap[] mAsteroidSmallGif;
        Bitmap[] mAsteroidMediumGif;
        Bitmap[] mAsteroidLargeGif;
        int ss, s, m, l = 0;



        GameView(Context context, SurfaceHolder surfHolder, Display screen) {
                int asteroidSizeFactor = screen.width / DIVISION_FACTOR;
                int shipSize = screen.width / SHIP_SCALE_FACTOR;
                ourContext = context;
                myHolder = surfHolder;
                myPaint = new Paint();
                screenRes = new PointF(screen.width, screen.height);
                ships = new ArrayList<>();

                shipBitmap = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.spaceship_0);
                // // Modify the bitmaps to face the ship
                // // in the correct direction
                shipBitmap = Bitmap.createScaledBitmap(shipBitmap, shipSize + GameConfig.PLAYER_SHIP_PADDING,
                                                                shipSize + GameConfig.PLAYER_SHIP_PADDING,
                                                                true);

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

                mBackGround = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.outerspace_0);
                mBackGround = Bitmap.createScaledBitmap(mBackGround, screen.width, screen.height, true);

                // Player laser bitmap creation. For now, let's make lasers half the asteroid size.
                mPlayerLaserBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.plaser);
                mPlayerLaserBM = Bitmap.createScaledBitmap(mPlayerLaserBM, asteroidSizeFactor / LASER_SIZE_FACTOR,
                        asteroidSizeFactor / LASER_SIZE_FACTOR, false);

                mOpponentBitmap = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.ufo3);
                mOpponentBitmap = Bitmap.createScaledBitmap(mOpponentBitmap, shipSize*2, shipSize, false);

                mOpponent2Bitmap = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.ufo2);
                mOpponent2Bitmap = Bitmap.createScaledBitmap(mOpponent2Bitmap, shipSize*2, shipSize, false);

                mOpponentLaserBM = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.laser_red);
                mOpponentLaserBM = Bitmap.createScaledBitmap(mOpponentLaserBM, asteroidSizeFactor / LASER_SIZE_FACTOR,
                        asteroidSizeFactor / LASER_SIZE_FACTOR, false);

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
                                drawOpponent(render, gProg);
                                drawLasers(render, gProg);
                                drawPowerUps(render);
                                drawParticleExplosion(ps);
                                drawPauseButton();
                                drawHUD(gProg);

                        }
                        // if user paused the game, draw the pause menu only
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
                }
        }

        private void drawBackground(){
                // Fills the screen with background "space" image
                myCanvas.drawBitmap(mBackGround, 0, 0, myPaint);
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

        private void drawPauseButton(){ myCanvas.drawBitmap(pauseButtonBM,
                screenRes.x - PAUSE_BUTTON_SCALE.x, PAUSE_BUTTON_SCALE.y, myPaint); }

        private void drawPowerUps(SObjectsCollection render){
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
                }
                myCanvas.drawBitmap(shipGIF[ss++], render.mPlayer.getMatrix(), myPaint);
                if(ss == shipGIF.length)
                        ss = 0;
                myPaint.setAlpha(255);
        }

        void drawShipMenu() {
                if (myHolder.getSurface().isValid()) {
                        myCanvas = myHolder.lockCanvas();
                        myCanvas.drawARGB(255, 0, 0, 0);

                        // Draw spaceship options
                        myCanvas.drawBitmap(spaceshipGIF[ss++], 0, 0, myPaint);
                        myCanvas.drawBitmap(spaceship2GIF[ss++], screenRes.x/2, screenRes.y/2, myPaint);
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
                myCanvas.drawARGB(150, 0, 0, 0);
                // Choose a color to paint with
                myPaint.setColor(Color.argb(255, 75, 180, 250));
                // Choose the font size
                myPaint.setTextSize(screenRes.x / 20);
                myCanvas.drawText("PAUSED", (screenRes.x / 2) - 225, screenRes.y / 2, myPaint);
                myCanvas.drawText("PRESS ANYWHERE TO RESUME", screenRes.x/7,  (screenRes.y / 2) + 150, myPaint);
        }

        void drawGameOver(GameProgress gProg){
                if (myHolder.getSurface().isValid()) {
                        myCanvas = myHolder.lockCanvas();
                        myCanvas.drawBitmap(gameOverBM, 0, 0, myPaint);
                        myPaint.setColor(Color.argb(255, 255, 255, 255));
                        myPaint.setTextSize(screenRes.x / 20);
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
