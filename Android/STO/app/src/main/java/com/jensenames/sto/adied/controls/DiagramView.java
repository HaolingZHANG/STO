package com.jensenames.sto.adied.controls;

import android.content.Context;
import android.util.AttributeSet;

import com.jensenames.sto.R;

public class DiagramView extends TouchImageView {
    public DiagramView(Context context) {
        super(context);
        init();
    }

    public DiagramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {
        if(getResources().getConfiguration().locale.getCountry().contains("CN"))
            this.setImageResource(R.mipmap.schematic_diagram_cn);
        else
            this.setImageResource(R.mipmap.schematic_diagram_en);
    }
}
