package com.jensenames.sto.legend.bean;

import android.content.Context;

import java.util.ArrayList;

public class Legend {

    private int sourceId;
    private String name;

    private static ArrayList<ArrayList<Legend>> legends;
    public static final int[] signsLength = {15, 9, 18, 9, 27, 6};

    public static ArrayList<ArrayList<Legend>> newInstance(Context context) {
        if(legends == null) {
            legends = new ArrayList<>();
            for(int i = 0; i < 6; i++) {
                ArrayList<Legend> sameTypes = new ArrayList<>();
                for (int j = 0; j < signsLength[i]; j++) {
                    int sign = (i + 1) * 100 + j + 1;
                    sameTypes.add(new Legend(context.getResources().getIdentifier("s" + sign, "drawable", context.getPackageName()),
                            context.getResources().getString(context.getResources().getIdentifier("s" + sign, "string", context.getPackageName()))));
                }
                legends.add(sameTypes);
            }
        }
        return legends;
    }

    private Legend(int sourceId, String name) {
        this.sourceId = sourceId;
        this.name = name;
    }

    public static void clear() {
        legends = null;
    }

    public int getSourceId() {
        return sourceId;
    }

    public String getName() {
        return name;
    }
}
