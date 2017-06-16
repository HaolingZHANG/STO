package com.jensenames.sto.adied.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.jensenames.sto.R;

public class StandardView extends ImageView {

    public StandardView(Context context) {
        super(context);
        init();
    }

    public StandardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {
        if(getResources().getConfiguration().locale.getCountry().contains("CN"))
            this.setImageResource(R.mipmap.standard_cn);
        else
            this.setImageResource(R.mipmap.standard_en);
    }
}
