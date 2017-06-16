package com.jensenames.sto.calculate.bean;


import java.util.Random;

public class Expression {

    private int termOne;
    private int termTwo;
    private int termThr;

    private int[] results = new int[4];

    private int rightPosition;

    private int operatorChange;

    public Expression() {
        termOne = new Random().nextInt(10);
        termTwo = new Random().nextInt(10);
        termThr = new Random().nextInt(100);
        operatorChange = new Random().nextInt(2);

        int answer;
        if(operatorChange == 0)
            answer = termOne * termTwo + termThr;
        else
            answer = termOne * termTwo - termThr;

        randomOrder(getTotalAnswer(answer));
    }

    public int[] getResults() {
        return results;
    }

    public boolean isRight(int touchPosition) {
        return rightPosition == touchPosition;
    }

    public int getRightAnswer() {
        return results[rightPosition];
    }

    private int[] getTotalAnswer(int rightAnswer) {
        int wrong1 = rightAnswer + 1;
        int wrong2 = rightAnswer + 10;
        int wrong3 = rightAnswer - 10;
        return new int[] {wrong1, wrong2, wrong3, rightAnswer};
    }

    private void randomOrder(int[] originOrder) {
        int rightAnswer = originOrder[3];
        int count = originOrder.length;
        int randCount = 0;
        int randomPosition = 0;
        do {
            int range = count - randCount;
            int originPosition = new Random().nextInt(range);
            results[randomPosition++] = originOrder[originPosition];
            randCount++;
            originOrder[originPosition] = originOrder[range - 1];
        } while (randCount < count);

        for(int i = 0; i < 4; i++)
            if(results[i] == rightAnswer)
                rightPosition = i;
    }

    @Override
    public String toString() {
        return operatorChange == 0 ? termOne + "×" + termTwo + "+" + termThr + "=" : termOne + "×" + termTwo + "-" + termThr + "=";
    }
}
