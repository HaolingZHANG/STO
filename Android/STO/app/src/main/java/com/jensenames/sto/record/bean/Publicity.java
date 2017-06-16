package com.jensenames.sto.record.bean;

import com.jensenames.sto.record.operate.ScoreRecord;

public class Publicity {

    private int[] finishs;
    private int[] perfects;
    private int[] errorRates;

    public static Publicity[] getTotalPublicitys() {

        double[] tempM = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] tempD = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        Publicity[] totalPublicitys = new Publicity[]{new Publicity(), new Publicity(), new Publicity()};
        for(Score score : ScoreRecord.getTotalScores()) {
            totalPublicitys[score.getTrainId() - 1].finishs[score.getDegree() - 1]++;
            if(score.getWrong() == 0)
                totalPublicitys[score.getTrainId() - 1].perfects[score.getDegree() - 1]++;
            tempM[(score.getTrainId() - 1) * 4 + score.getDegree() - 1] += score.getWrong();
            tempD[(score.getTrainId() - 1) * 4 + score.getDegree() - 1] += score.getTotal();
        }
        for(int i = 0; i < totalPublicitys.length; i++)
            for(int j = 0; j < totalPublicitys[i].errorRates.length; j++) {
                if(tempD[i * 4 + j] != 0)
                    totalPublicitys[i].errorRates[j] = (int) (tempM[i * 4 + j] / tempD[i * 4 + j] * 100);
                else
                    totalPublicitys[i].errorRates[j] = 0;
            }
        return totalPublicitys;
    }

    private Publicity() {
        this.finishs = new int[] {0, 0, 0, 0};
        this.perfects = new int[] {0, 0, 0, 0};
        this.errorRates = new int[] {0, 0, 0, 0};
    }

    public int[] getFinishs() {
        return finishs;
    }

    public int[] getPerfects() {
        return perfects;
    }

    public int[] getErrorRates() {
        return errorRates;
    }
}
