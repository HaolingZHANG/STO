package com.jensenames.sto.adied.leger.bean;

public class LegerTime {

    private static final int[] frequencyHierarchy = new int[] {
            7, 15, 23, 32, 41, 51, 61,
            72, 83, 94, 106, 118, 131, 144,
            157, 171, 185, 200, 215, 231, 247
    };

    private static final int[] intervalTime = new int[] {
            8740, 8000, 7580, 7200, 6860, 6550, 6260,
            6000, 5760, 5540, 5330, 5140, 4970, 4800,
            4650, 4500, 4360, 4240, 4110, 4000, 3890
    };

    public static int getIntervalTime(int currentCount, int operationTime) {
        for(int i = 0; i < frequencyHierarchy.length; i++)
            if(currentCount < frequencyHierarchy[i])
                return intervalTime[i] + operationTime;
        return -1;
    }
}
