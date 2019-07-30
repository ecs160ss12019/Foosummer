package com.gamecodeschool.asteroidsfs;

import android.media.MediaPlayer;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class Audio {

    MediaPlayer mp;
    ArrayList<MediaPlayer> sounds;


    public Audio(Context ct) {
        sounds = new ArrayList<MediaPlayer>();
        sounds.add(MediaPlayer.create(ct, R.raw.the_imperial_march));
        sounds.add(MediaPlayer.create(ct, R.raw.fail));
        sounds.add(MediaPlayer.create(ct, R.raw.asteroid_explosion));
        sounds.add(MediaPlayer.create(ct, R.raw.laser_powerup));
        sounds.add(MediaPlayer.create(ct, R.raw.shield_powerup));
    }

    public void playClick(ArrayList<MediaPlayer> song, int idx) {
        mp = song.get(idx);
        mp.start();
    }

    public void pause(){
        mp.pause();
    }
}
