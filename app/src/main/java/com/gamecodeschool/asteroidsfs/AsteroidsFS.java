package com.gamecodeschool.asteroidsfs;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

// main
public class AsteroidsFS extends Activity {
    private AsteroidsGame myAsteroidsGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        myAsteroidsGame = new AsteroidsGame(this, size.x, size.y);
        setContentView(myAsteroidsGame);
    }

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

