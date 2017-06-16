package com.jensenames.sto.record.bean;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Score {

    private String time;
    private int trainId;
    private int degree;
    private int total;
    private int wrong;

    @SuppressLint("SimpleDateFormat")
    public Score(int trainId, int degree, int total, int wrong) {
        this.time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        this.trainId = trainId;
        this.degree = degree;
        this.total = total;
        this.wrong = wrong;
    }

    public Score(String stoData) {
        String[] data = stoData.split(",");
        this.time = data[0];
        this.trainId = Integer.parseInt(data[1]);
        this.degree = Integer.parseInt(data[2]);
        this.total = Integer.parseInt(data[3]);
        this.wrong = Integer.parseInt(data[4]);
    }

    int getTrainId() {
        return trainId;
    }

    int getDegree() {
        return degree;
    }

    int getTotal() {
        return total;
    }

    int getWrong() {
        return wrong;
    }

    @Override
    public String toString() {
        return time + "," + trainId + "," + degree + "," + total + "," + wrong;
    }
}
