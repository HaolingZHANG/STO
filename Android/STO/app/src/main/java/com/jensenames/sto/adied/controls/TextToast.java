package com.jensenames.sto.adied.controls;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class TextToast {
    private static String oldDescription;

    private static Toast toast = null;

    private static long oneTime = 0;

    public synchronized static void showTextToast(final String description, final Context context) {
        if(toast == null) {
            toast = Toast.makeText(context, description, Toast.LENGTH_SHORT);
            show(description);

            oneTime = System.currentTimeMillis();
        } else {
            long twoTime = System.currentTimeMillis();

            if(description.equals(oldDescription)) {
                if ((twoTime - oneTime) > Toast.LENGTH_LONG)
                    show(description);
            } else {
                oldDescription = description;
                show(description);
            }
            oneTime = twoTime;
        }
    }

    private static void show(final String description) {
        toast.setText(description);
        ((TextView) toast.getView().findViewById(Resources.getSystem().getIdentifier("message", "id", "android"))).setGravity(Gravity.CENTER);
        toast.show();
    }
}
