package com.jensenames.sto.record.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jensenames.sto.R;

public class HistoryView extends LinearLayout {

    public HistoryView(Context context) {
        super(context);
        initView();
    }

    public HistoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_history, this);
    }

    public void setHead(String[] head) {
        ((TextView)findViewById(R.id.time)).setText(head[0]);
        ((TextView)findViewById(R.id.train_id)).setText(head[1]);
        ((TextView)findViewById(R.id.degree)).setText(head[2]);
        ((TextView)findViewById(R.id.complete_count)).setText(head[3]);
        ((TextView)findViewById(R.id.wrong_count)).setText(head[4]);
    }

    public void setData(String stoData) {
        String[] data = stoData.split(",");
        ((TextView)findViewById(R.id.time)).setText(data[0].replace(" ", "\n"));
        ((TextView)findViewById(R.id.complete_count)).setText(data[3]);
        ((TextView)findViewById(R.id.wrong_count)).setText(data[4]);
        switch (data[1]) {
            case "1":((TextView)findViewById(R.id.train_id)).setText(getResources().getString(R.string.calculate_run_balance));break;
            case "2":
                ((TextView)findViewById(R.id.train_id)).setText(getResources().getString(R.string.plum_bleep_test));
                ((TextView)findViewById(R.id.wrong_count)).setText("-");break;
            case "3":((TextView)findViewById(R.id.train_id)).setText(getResources().getString(R.string.legend_bleep_test));
        }
        switch (data[2]) {
            case "1":((TextView)findViewById(R.id.degree)).setText(getResources().getString(R.string.degree_1_short));break;
            case "2":((TextView)findViewById(R.id.degree)).setText(getResources().getString(R.string.degree_2_short));break;
            case "3":((TextView)findViewById(R.id.degree)).setText(getResources().getString(R.string.degree_3_short));break;
            case "4":((TextView)findViewById(R.id.degree)).setText(getResources().getString(R.string.degree_4_short));
        }
    }
}
