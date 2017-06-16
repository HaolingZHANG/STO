package com.jensenames.sto.plum.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.jensenames.sto.adied.leger.service.SoundPrompt;
import com.jensenames.sto.windows.MainActivity;
import com.jensenames.sto.R;
import com.jensenames.sto.adied.leger.bean.LegerTime;
import com.jensenames.sto.plum.bean.Position;
import com.jensenames.sto.record.operate.ScoreRecord;
import com.jensenames.sto.record.bean.Degree;
import com.jensenames.sto.adied.controls.TextToast;
import com.jensenames.sto.adied.controls.TrainLayout;
import com.jensenames.sto.record.bean.Score;

import java.util.ArrayList;

public class PlumLayout extends TrainLayout {

    private TextView countDown;
    private PlumView plumView;

    private ArrayList<Integer> pathIds;
    private int degree;
    private int count;

    private Thread thread;

    private boolean finish = false;

    public PlumLayout(Context context) {
        super(context);
        init(context);
    }

    public PlumLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.layout_plum, this);
        countDown = (TextView) findViewById(R.id.countDown);
        plumView = (PlumView) findViewById(R.id.plum_view);
    }

    public void setCount(int count) {
        this.count = count;
        countDown.setText(String.valueOf(count));
        degree = Degree.getDegree();
        pathIds = Position.getRoute(count);
        finish = false;
    }

    @Override
    public void start() {
        thread = new Thread(new Runnable() {

            private int i = 1;
            private int startI = degree * 10;
            private Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    plumView.invalidate();
                    if(MainActivity.isProcess())
                        MainActivity.call();
                    countDown.setText(String.valueOf(Integer.parseInt(String.valueOf(countDown.getText())) - 1));
                }
            };

            @Override
            public void run() {
                if(i < pathIds.size() && MainActivity.isProcess()) {
                    ArrayList<Integer> path = new ArrayList<>();

                    if(i > 0)
                        path.add(pathIds.get(i - 1));
                    path.add(pathIds.get(i));

                    if(i < pathIds.size() - 1)
                        path.add(pathIds.get(i + 1));

                    plumView.setPath(path, i == 1);

                    int intervalTime = LegerTime.getIntervalTime(startI, 0);
                    SoundPrompt.ring();
                    if(intervalTime != -1)
                        handler.postDelayed(this, intervalTime);
                }
                i++;
                startI++;

                handler.sendMessageAtTime(new Message(), 0);
                if(i == pathIds.size() + 1) {
                    finish = true;
                    MainActivity.clearProcess();
                    SoundPrompt.stopRing();
                    stop();
                }
            }
        });
        thread.start();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void stop() {
        try {
            thread.stop();
            thread.interrupt();
        } catch (UnsupportedOperationException | NullPointerException e) {
            if(finish) {
                TextToast.showTextToast(getResources().getString(R.string.complete_field) +
                        Degree.getDegreeName(getContext()) + getResources().getString(R.string.difficulty_field) + "-" +
                        count + getResources().getString(R.string.count_field) + "！", getContext());
                mark();
            }
        }
        MainActivity.clearProcess();
        MainActivity.getStaticActivity().returnEnter();
    }

    @Override
    public void mark() {
        ScoreRecord.write(new Score(2, Degree.getDegree(), count, 0), getContext());
    }

    @Override
    public void onClick(View view) {
        //no click
    }
}
