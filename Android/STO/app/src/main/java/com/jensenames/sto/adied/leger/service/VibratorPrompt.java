package com.jensenames.sto.adied.leger.service;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

public class VibratorPrompt {

    public static void Vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }
}
