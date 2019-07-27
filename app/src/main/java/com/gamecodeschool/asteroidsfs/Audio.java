package com.gamecodeschool.asteroidsfs;

import android.media.MediaPlayer;
import android.content.Context;


public class Audio {

    MediaPlayer mp;
    Context context;

    public Audio(Context ct) {
        this.context = ct;
    }

    public void playClick() {
        mp = MediaPlayer.create(context, R.raw.the_imperial_march);
        mp.start();
    }

    public void pause(){
        mp.pause();
    }
}
