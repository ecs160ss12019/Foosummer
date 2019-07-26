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

import java.lang.reflect.Array;
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
        int[] drawables = {
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
                R.drawable.outerspace_74
        };
        Bitmap[] mBackGround = new Bitmap[drawables.length];
        int k = 0;


        GameView(Context context, SurfaceHolder surfHolder, Display screen) {
                ourContext = context;
                myHolder = surfHolder;
                myPaint = new Paint();
                mAsteroids = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.asteroid);
                mAsteroids = Bitmap.createScaledBitmap(mAsteroids, 100, 100, false);

                shipBitmap = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.sqspaceship);
                // // Modify the bitmaps to face the ship
                // // in the correct direction
                shipBitmap = Bitmap.createScaledBitmap(shipBitmap, 128, 128, true);
                for(int i= 0 ; i < drawables.length ; i++) {
                        mBackGround[i] = BitmapFactory.decodeResource(ourContext.getResources(), drawables[i]);
                        mBackGround[i] = Bitmap.createScaledBitmap(mBackGround[i], screen.width, screen.height, true);
                }
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

                        myCanvas.drawBitmap(mBackGround[k++], 0, 0, myPaint);
                        if(k == drawables.length)
                                k =0;
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
//                        myPaint.setColor(Color.red(250));
                        for (int i = 0; i < render.mAsteroids.size(); i++) {
                                myCanvas.drawBitmap(mAsteroids, render.mAsteroids.get(i).getBitmapX(),
                                                render.mAsteroids.get(i).getBitmapY(), myPaint);

//                                myCanvas.drawCircle(render.mAsteroids.get(i).getPosition().x,
//                                        render.mAsteroids.get(i).getPosition().y,
//                                        20.0f, myPaint);

                        }
                        //
                        // // POWER UPS
                        for(int i = 0; i < render.mMineralPowerUps.size(); i++){
                                render.mMineralPowerUps.get(i).draw(myCanvas, myPaint);
                        }

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
