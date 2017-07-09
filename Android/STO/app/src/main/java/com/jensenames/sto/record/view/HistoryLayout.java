package com.jensenames.sto.record.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.jensenames.sto.R;
import com.jensenames.sto.record.bean.Score;
import com.jensenames.sto.record.operate.ScoreRecord;

public class HistoryLayout extends ScrollView {

    public HistoryLayout(Context context) {
        super(context);
        init();
    }

    public HistoryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.removeAllViews();
        LinearLayout layout = new LinearLayout(getContext());
        layout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setOrientation(LinearLayout.VERTICAL);

        HistoryView view1 = new HistoryView(getContext());
        view1.setHead(new String[] {
                getResources().getString(R.string.train_time),
                getResources().getString(R.string.train_name),
                getResources().getString(R.string.train_degree),
                getResources().getString(R.string.total_count),
                getResources().getString(R.string.wrong_count)
        });
        layout.addView(view1);

        if (ScoreRecord.getTotalScores().size() == 0) {
            HistoryView view2 = new HistoryView(getContext());
            view2.setHead(new String[] {
                    "-","-","-","-","-"
            });
            layout.addView(view2);
        } else
            for(Score score : ScoreRecord.getTotalScores()) {
                HistoryView view2 = new HistoryView(getContext());
                view2.setData(score.toString());
                layout.addView(view2);
            }
        this.addView(layout);
    }
}
