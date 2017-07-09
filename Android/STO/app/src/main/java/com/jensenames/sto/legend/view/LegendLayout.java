package com.jensenames.sto.legend.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jensenames.sto.adied.leger.bean.LegerTime;
import com.jensenames.sto.adied.leger.service.SoundPrompt;
import com.jensenames.sto.windows.MainActivity;
import com.jensenames.sto.R;
import com.jensenames.sto.legend.bean.Legend;
import com.jensenames.sto.record.operate.ScoreRecord;
import com.jensenames.sto.record.bean.Degree;
import com.jensenames.sto.adied.controls.TextToast;
import com.jensenames.sto.adied.controls.TrainLayout;
import com.jensenames.sto.record.bean.Score;

import java.util.ArrayList;
import java.util.Random;

public class LegendLayout extends TrainLayout {

    private TextView countDown;
    private TextView explain;
    private TextView last;

    private ImageView answerA;
    private ImageView answerB;
    private ImageView answerC;
    private ImageView answerD;

    private int count;

    private int wrong;
    private int right;

    private int singleTripCount;

    private int currRightAnswer;
    private String[] currAnswer;

    private Thread thread;

    private boolean finish;

    public LegendLayout(Context context) {
        super(context);
        init(context);
    }

    public LegendLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.layout_legend, this);
        countDown = (TextView) findViewById(R.id.countDown);
        explain = (TextView) findViewById(R.id.explain);
        last = (TextView) findViewById(R.id.last);

        answerA = (ImageView) findViewById(R.id.legendA);
        answerB = (ImageView) findViewById(R.id.legendB);
        answerC = (ImageView) findViewById(R.id.legendC);
        answerD = (ImageView) findViewById(R.id.legendD);

        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);

        currAnswer = new String[4];
        finish = false;
    }

    public void setCount(int count) {
        last.setText(String.valueOf(count * 2));
        this.count = count * 2 * Degree.getDegree();
        countDown.setText(String.valueOf(Degree.getDegree()));
    }

    @Override
    public void start() {
        singleTripCount = 0;
        wrong = 0;
        currRightAnswer = -1;
        next();
        thread = new Thread(new Runnable() {

            private int i = 1;

            private Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    singleTripCount = 0;
                    if(MainActivity.isProcess()) {
                        MainActivity.call();
                        last.setText(String.valueOf(Integer.parseInt(String.valueOf(last.getText())) - 1));
                    }
                    countDown.setText(String.valueOf(Integer.parseInt(String.valueOf(countDown.getText())) - 1));
                }
            };

            @Override
            public void run() {
                if(Integer.parseInt(String.valueOf(countDown.getText())) > 0 && MainActivity.isProcess()) {

                    int intervalTime = LegerTime.getIntervalTime(i, 0);
                    SoundPrompt.ring();
                    if(intervalTime != -1)
                        handler.postDelayed(this, intervalTime);
                }

                handler.sendMessageAtTime(new Message(), 0);
                if(Integer.parseInt(String.valueOf(countDown.getText())) == 0) {
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
            e.printStackTrace();
            if(finish) {
                TextToast.showTextToast(getResources().getString(R.string.complete_field) + Degree.getDegreeName(getContext()) +
                        getResources().getString(R.string.difficulty_field) + "-" +
                        getResources().getString(R.string.total_field) + count + getResources().getString(R.string.count_field) + "," +
                        getResources().getString(R.string.wrong_field) + wrong + getResources().getString(R.string.count_field) + "！", getContext());
                mark();
            }
        }
        MainActivity.clearProcess();
        MainActivity.getStaticActivity().returnEnter();
    }

    @Override
    public void mark() {
        ScoreRecord.write(new Score(3, Degree.getDegree(), count, wrong), getContext());
    }

    @SuppressLint("NewApi")
    private void next() {
        ArrayList<Legend> currLegends = new ArrayList<>();
        while(currLegends.size() <= 4) {
            int i = new Random().nextInt(5);
            int j = new Random().nextInt(Legend.signsLength[i] - 1);
            Legend currLegend = Legend.newInstance(getContext()).get(i).get(j);
            boolean same = false;
            for(Legend legend : currLegends)
                if(legend.getName().equals(currLegend.getName())) {
                    same = true;
                    break;
                }
            if(!same)
                currLegends.add(currLegend);
        }
        currRightAnswer = new Random().nextInt(3);
        for(int i = 0; i < 4; i++)
            currAnswer[i] = currLegends.get(i).getName();
        explain.setText(currLegends.get(currRightAnswer).getName());

        if (Build.VERSION.SDK_INT >= 21) {
            answerA.setImageDrawable(getResources().getDrawable(currLegends.get(0).getSourceId(), getContext().getTheme()));
            answerB.setImageDrawable(getResources().getDrawable(currLegends.get(1).getSourceId(), getContext().getTheme()));
            answerC.setImageDrawable(getResources().getDrawable(currLegends.get(2).getSourceId(), getContext().getTheme()));
            answerD.setImageDrawable(getResources().getDrawable(currLegends.get(3).getSourceId(), getContext().getTheme()));
        } else {
            answerA.setImageDrawable(VectorDrawableCompat.create(getResources(), currLegends.get(0).getSourceId(), getContext().getTheme()));
            answerB.setImageDrawable(VectorDrawableCompat.create(getResources(), currLegends.get(1).getSourceId(), getContext().getTheme()));
            answerC.setImageDrawable(VectorDrawableCompat.create(getResources(), currLegends.get(2).getSourceId(), getContext().getTheme()));
            answerD.setImageDrawable(VectorDrawableCompat.create(getResources(), currLegends.get(3).getSourceId(), getContext().getTheme()));
        }
    }

    @Override
    public void onClick(View view) {
        if(MainActivity.isProcess()) {
            if(singleTripCount < Degree.getDegree()) {
                singleTripCount++;
                switch (view.getId()) {
                    case R.id.legendA:
                        if (currRightAnswer != 0) {
                            wrong++;
                            TextToast.showTextToast(getResources().getString(R.string.choose_field) + currAnswer[0] +
                                    getResources().getString(R.string.right_field) + (currRightAnswer + 1) +
                                    getResources().getString(R.string.wrong_field_2) + wrong +
                                    getResources().getString(R.string.count_field), getContext());
                        } else
                            right++;
                        judgeAndSetNext();
                        break;
                    case R.id.legendB:
                        if (currRightAnswer != 1) {
                            wrong++;
                            TextToast.showTextToast(getResources().getString(R.string.choose_field) + currAnswer[1] +
                                    getResources().getString(R.string.right_field) + (currRightAnswer + 1) +
                                    getResources().getString(R.string.wrong_field_2) + wrong +
                                    getResources().getString(R.string.count_field), getContext());
                        } else
                            right++;
                        judgeAndSetNext();
                        break;
                    case R.id.legendC:
                        if (currRightAnswer != 2) {
                            wrong++;
                            TextToast.showTextToast(getResources().getString(R.string.choose_field) + currAnswer[2] +
                                    getResources().getString(R.string.right_field) + (currRightAnswer + 1) +
                                    getResources().getString(R.string.wrong_field_2) + wrong +
                                    getResources().getString(R.string.count_field), getContext());
                        } else
                            right++;
                        judgeAndSetNext();
                        break;
                    case R.id.legendD:
                        if (currRightAnswer != 3) {
                            wrong++;
                            TextToast.showTextToast(getResources().getString(R.string.choose_field) + currAnswer[3] +
                                    getResources().getString(R.string.right_field) + (currRightAnswer + 1) +
                                    getResources().getString(R.string.wrong_field_2) + wrong +
                                    getResources().getString(R.string.count_field), getContext());
                        } else
                            right++;
                        judgeAndSetNext();
                }
            } else
                TextToast.showTextToast(getResources().getString(R.string.next_field), getContext());
        }
    }

    private void judgeAndSetNext() {
        if(right + wrong < count) {
            next();
            int temp = Integer.parseInt(String.valueOf(countDown.getText()));
            if(temp > 1)
                countDown.setText(String.valueOf(temp - 1));
            else
                countDown.setText(String.valueOf(Degree.getDegree()));
        } else {
            TextToast.showTextToast(getResources().getString(R.string.complete_field) + Degree.getDegreeName(getContext()) +
                    getResources().getString(R.string.difficulty_field) + "-" +
                    getResources().getString(R.string.total_field) + count + getResources().getString(R.string.count_field) + "," +
                    getResources().getString(R.string.wrong_field) + wrong + getResources().getString(R.string.count_field) + "！", getContext());
            mark();
            stop();
        }
    }
}
