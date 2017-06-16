package com.jensenames.sto.record.bean;


import android.content.Context;

import com.jensenames.sto.R;

public class Degree {

    public static final int NORMAL = 1;
    public static final int DIFFICULT = 2;
    public static final int INFERNAL = 3;
    public static final int FATE = 4;

    private static int degree = 0;

    public static void initDegree(int degree) {
        Degree.degree = degree;
    }

    public static int getDegree() {
        return degree;
    }

    public static String getDegreeName(Context context) {
        switch (degree) {
            case 1:return context.getResources().getString(R.string.degree_1);
            case 2:return context.getResources().getString(R.string.degree_2);
            case 3:return context.getResources().getString(R.string.degree_3);
            case 4:return context.getResources().getString(R.string.degree_4);
        }
        return "";
    }
}
