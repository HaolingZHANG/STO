package com.jensenames.sto.calculate.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.jensenames.sto.windows.MainActivity;
import com.jensenames.sto.R;
import com.jensenames.sto.calculate.bean.Expression;
import com.jensenames.sto.record.operate.ScoreRecord;
import com.jensenames.sto.record.bean.Degree;
import com.jensenames.sto.adied.controls.TextToast;
import com.jensenames.sto.adied.controls.TrainLayout;
import com.jensenames.sto.record.bean.Score;

public class CalculateLayout extends TrainLayout {

    private TextView countDown;

    private TextView expressionShow;

    private TextView answerA;
    private TextView answerB;
    private TextView answerC;
    private TextView answerD;

    private int count;
    private int wrong;

    private Expression currExpression;

    public CalculateLayout(Context context) {
        super(context);
        init(context);
    }

    public CalculateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("SetTextI18n")
    private void init(Context context) {
        View.inflate(context, R.layout.layout_calculate, this);
        countDown = (TextView) findViewById(R.id.countDown);
        expressionShow = (TextView) findViewById(R.id.expression_show);

        answerA = (TextView) findViewById(R.id.answerA);
        answerB = (TextView) findViewById(R.id.answerB);
        answerC = (TextView) findViewById(R.id.answerC);
        answerD = (TextView) findViewById(R.id.answerD);

        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);

        answerA.setClickable(false);
        answerB.setClickable(false);
        answerC.setClickable(false);
        answerD.setClickable(false);
    }

    public void setLength(int length) {
        switch (Degree.getDegree()) {
            case Degree.NORMAL:
                count = length / 100;
                TextToast.showTextToast(getResources().getString(R.string.calculate_degree_1_intro), getContext());
                break;
            case Degree.DIFFICULT:
                count = length / 80;
                TextToast.showTextToast(getResources().getString(R.string.calculate_degree_2_intro), getContext());
                break;
            case Degree.INFERNAL:
                count = length / 50;
                TextToast.showTextToast(getResources().getString(R.string.calculate_degree_3_intro), getContext());
                break;
            case Degree.FATE:
                count = length / 40;
                TextToast.showTextToast(getResources().getString(R.string.calculate_degree_2_intro), getContext());
                break;
        }
        wrong = 0;
        countDown.setText(String.valueOf(count));
        expressionShow.setText("");
    }

    @Override
    public void start() {
        answerA.setClickable(true);
        answerB.setClickable(true);
        answerC.setClickable(true);
        answerD.setClickable(true);

        show();
    }

    @Override
    public void stop() {
        MainActivity.getStaticActivity().returnEnter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.answerA:next(0);break;
            case R.id.answerB:next(1);break;
            case R.id.answerC:next(2);break;
            case R.id.answerD:next(3);
        }
    }

    private void next(int touchPosition) {
        if(currExpression.isRight(touchPosition))
            countDown.setText(String.valueOf(Integer.parseInt(String.valueOf(countDown.getText())) - 1));
        else {
            wrong++;
            TextToast.showTextToast(getResources().getString(R.string.choose_field) + touchPosition +
                    getResources().getString(R.string.right_field) + currExpression.getRightAnswer() +
                    getResources().getString(R.string.wrong_field) + wrong +
                    getResources().getString(R.string.count_field), getContext());
        }

        if(Integer.parseInt(String.valueOf(countDown.getText())) == 0) {
            TextToast.showTextToast(getResources().getString(R.string.complete_field) + Degree.getDegreeName(getContext()) +
                    getResources().getString(R.string.difficulty_field) + ":" +
                    getResources().getString(R.string.total_field) + count +
                    getResources().getString(R.string.count_field_2) + "," +
                    getResources().getString(R.string.wrong_field) + wrong +
                    getResources().getString(R.string.count_field_2) + "！", getContext());
            mark();
            stop();
        } else
            show();
    }

    @Override
    public void mark() {
        ScoreRecord.write(new Score(1, Degree.getDegree(), count, wrong), getContext());
    }

    @SuppressLint("SetTextI18n")
    private void show() {
        currExpression = new Expression();
        expressionShow.setText(currExpression.toString());
        int[] results = currExpression.getResults();
        answerA.setText("A:" + results[0]);
        answerB.setText("B:" + results[1]);
        answerC.setText("C:" + results[2]);
        answerD.setText("D:" + results[3]);
    }
}
