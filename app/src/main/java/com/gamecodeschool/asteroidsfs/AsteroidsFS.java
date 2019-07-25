package com.gamecodeschool.asteroidsfs;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Display;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

// main
public class AsteroidsFS extends Activity{
    private AsteroidsGame myAsteroidsGame;
//    AnimationDrawable outespaceAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        myAsteroidsGame = new AsteroidsGame(this, size.x, size.y);
        setContentView(myAsteroidsGame);

//
//        ImageView imageView = (ImageView) findViewById(R.id.outerspace_image);
//        imageView.setBackgroundResource(R.drawable.outerspace);
//        outespaceAnimation = (AnimationDrawable) imageView.getBackground();


    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        outespaceAnimation.start();
//    }

    @Override
    protected void onResume(){
        super.onResume();

        myAsteroidsGame.resume();
    }

    @Override
    protected void onPause(){
        super.onPause();

        myAsteroidsGame.pause();
    }


}
