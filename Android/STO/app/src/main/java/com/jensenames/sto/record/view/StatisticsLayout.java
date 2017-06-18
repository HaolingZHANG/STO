package com.jensenames.sto.record.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jensenames.sto.R;
import com.jensenames.sto.record.bean.Publicity;

public class StatisticsLayout extends LinearLayout {

    public StatisticsLayout(Context context) {
        super(context);
        init();
    }

    public StatisticsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.view_grid, this);
        GridLayout layout = (GridLayout) findViewById(R.id.grid);

        String[][] datas = dataChange(Publicity.getTotalPublicitys());

        for(int i = 0; i < 12; i++) {
            ((TextView) (layout.getChildAt(i + 18))).setText(datas[i][0]);
            if(i > 3 && i < 8) {
                //no data, because of no judge!
                ((TextView) (layout.getChildAt(i + 31))).setText("-");
                ((TextView) (layout.getChildAt(i + 44))).setText("-");
            } else {
                ((TextView) (layout.getChildAt(i + 31))).setText(datas[i][1]);
                ((TextView) (layout.getChildAt(i + 44))).setText(datas[i][2]);
            }
        }
    }

    private static String[][] dataChange(Publicity[] publicities) {
        String[][] datas = new String[12][3];

        for(int i = 0; i < publicities.length ; i++) {
            for(int j = 0; j < publicities[i].getFinishs().length; j++) {
                if(publicities[i].getFinishs()[j] != 0)
                    datas[i * 4 + j][0] = String.valueOf(publicities[i].getFinishs()[j]);
                else
                    datas[i * 4 + j][0] = "0";
            }

            for(int j = 0; j < publicities[i].getPerfects().length; j++) {
                if(publicities[i].getPerfects()[j] != 0)
                    datas[i * 4 + j][1] = String.valueOf(publicities[i].getPerfects()[j]);
                else
                    datas[i * 4 + j][1] = "0";
            }

            for(int j = 0; j < publicities[i].getErrorRates().length; j++) {
                if (publicities[i].getErrorRates()[j] != 0)
                    datas[i * 4 + j][2] = String.valueOf(publicities[i].getErrorRates()[j]) + "%";
                else
                    datas[i * 4 + j][2] = "0%";
            }

        }
        return datas;
    }
}
