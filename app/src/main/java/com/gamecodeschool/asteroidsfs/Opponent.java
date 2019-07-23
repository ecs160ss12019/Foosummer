package com.gamecodeschool.asteroidsfs;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class Opponent extends SpaceObject{
    private int size;
    private boolean hit;
    private Bitmap bitmap;

    public Opponent(PointF position, float width, float height, float velocityX, float velocityY) {
        super(position, width, height, velocityX, velocityY);
        this.hit = false;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public float getHorizontalPos() {
        return super.getHitbox().left;
    }
    public float getVerticalPos() {
        return super.getHitbox().top;
    }

    // Draw opponent w/ picture
    public void draw(Canvas canvas, Bitmap OpponentBitmap) {
        Paint myPaint = new Paint();
        canvas.drawBitmap(OpponentBitmap, 100, 100, myPaint);
    }

}
