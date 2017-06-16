package com.jensenames.sto.adied.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public abstract class TrainLayout extends LinearLayout implements View.OnClickListener {

    public TrainLayout(Context context) {
        super(context);
    }

    public TrainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void start();

    public abstract void stop();

    public abstract void mark();
}
