package com.gamecodeschool.asteroidsfs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import android.graphics.RectF;
import android.graphics.Point;

import java.util.ArrayList;

// make this an interface
public class GameView{

    private SurfaceHolder myHolder;
    private Canvas myCanvas;
    private Paint myPaint;
    private Context ourContext;
    Matrix shipMatrix = new Matrix();

    GameView(Context context, SurfaceHolder surfHolder){
        ourContext = context;
        myHolder = surfHolder;
        myPaint = new Paint();
    }


    // Draw the game objects and the HUD
    void draw(RectF myShipHitbox, int blockSize, int myShipDegree, Point shipCenter, ArrayList<Asteroid> asteroids,
              ArrayList<Laser> myLasers, PowerUps[] mineralPowerUps) {
        //include position of ship (updating move location to be drawn)

        if (myHolder.getSurface().isValid()) {
            // Lock the canvas (graphics memory) ready to draw
            myCanvas = myHolder.lockCanvas();


            // Fills the screen with background "space" image
            myCanvas.drawBitmap(BitmapFactory.decodeResource(ourContext.getResources(),
                    R.drawable.outerspacebackground1), 0, 0, null);

            // Choose a color to paint with
            myPaint.setColor(Color.argb
                    (255, 75, 180, 250));


            // Draw the objects
//            myCanvas.drawRect(myShipHitbox, myPaint);
//            myCanvas.drawArc(myShipHitbox.getCirc(), 0, 360, false, myPaint);


            // A bitmap for each direction the ship can face
            Bitmap shipBitmap;


            shipBitmap = BitmapFactory
                    .decodeResource(ourContext.getResources(),
                            R.drawable.sqspaceship);


//            // Modify the bitmaps to face the ship
//            // in the correct direction
            shipBitmap = Bitmap
                    .createScaledBitmap(shipBitmap,
                            blockSize*2, blockSize*2, true);
//
//
//            // set parameters depending on degree orientation vs location of box
//            // shipMatrix.preRotate(myShipDegree);
//            shipBitmap = Bitmap
//                    .createBitmap(shipBitmap,
//                            0, 0, (blockSize*2), (blockSize*2), shipMatrix, true);
            shipMatrix.setRotate(myShipDegree, shipBitmap.getWidth()/2, shipBitmap.getHeight()/2);
//            shipMatrix.setTranslate();
//            shipMatrix.postTranslate((myShipHitbox.left - shipBitmap.getWidth()+blockSize),
////                    (myShipHitbox.top - shipBitmap.getHeight()+blockSize)
////            );
            shipMatrix.postTranslate((shipCenter.x)-blockSize,
                    (shipCenter.y)-blockSize
            );
            shipBitmap.setHasAlpha(true);

//            Log.e("draw: ", "value of shiphitbox.left: " + myShipHitbox.left);
//            Log.e("draw: ", "value of shiphitbox.right: " + myShipHitbox.right);
//            Log.e("draw: ", "value of shiphitbox.top: " + myShipHitbox.top);
//            Log.e("draw: ", "value of shiphitbox.bottom: " + myShipHitbox.bottom);
//            Log.d("draw:", "value of shipcenter.x: " + shipCenter.x);
//            Log.d("draw:", "value of shipcenter.y: " + shipCenter.y);
//            Log.d("draw:", "value of blockSize: " + blockSize);
//            Log.d("draw:", "value of shipbitmap.height: " + shipBitmap.getHeight());
//            Log.d("draw:", "value of shipbitmap.width: " + shipBitmap.getWidth());


            // myShipHitbox func that will return shipBitmap
            myCanvas.drawRect(myShipHitbox, myPaint);
            myCanvas.drawBitmap(shipBitmap, shipMatrix, myPaint);
//            myCanvas.drawBitmap(shipBitmap,

//                    shipMatrix, myPaint);
//            shipMatrix.mapRect(myShipHitbox.getRect());




//            // LASERS
//            // Draw lasers
//            for(int i = 0; i < myLasers.size(); i++) {
//                myLasers.get(i).draw(myCanvas);
//            }
//
//            // ASTEROIDS
//            Bitmap mAsteroids;
//            mAsteroids = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.asteroid);
//            for(int i = 0 ; i < asteroids.size(); i++) {
//                mAsteroids = BitmapFactory.decodeResource(ourContext.getResources(), R.drawable.asteroid);
//                mAsteroids = Bitmap.createScaledBitmap(mAsteroids, (blockSize*2), (blockSize*2), false);
//                myCanvas.drawBitmap(mAsteroids, asteroids.get(i).getHitbox().left,
//                        asteroids.get(i).getHitbox().top, myPaint);
//            }
//
//            // POWER UPS
//            for(int i = 0; i < mineralPowerUps.length; i++){
//                mineralPowerUps[i].draw(myCanvas);
//            }



            // Choose the font size
            //myPaint.setTextSize(fontSize);

            // Draw the HUD
            //myCanvas.drawText("Score: " + score + "   Lives: " + lives, fontMargin , fontSize, myPaint);

//            if(DEBUGGING){
//                printDebuggingText();
//            }
            // Display the drawing on screen
            // unlockCanvasAndPost is a method of SurfaceView
            myHolder.unlockCanvasAndPost(myCanvas);
        }

    }

}
