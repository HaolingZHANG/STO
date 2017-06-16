package com.jensenames.sto.adied.leger.service;

import android.app.Activity;
import android.media.MediaPlayer;

import com.jensenames.sto.R;

public class SoundPrompt {

    private static MediaPlayer player;

    public static void startRing(Activity activity) {
        player = MediaPlayer.create(activity, R.raw.warning_tone);
        player.setLooping(false);
    }

    public static void ring() {
        player.start();
    }

    public static void stopRing() {
        player = null;
    }
}
