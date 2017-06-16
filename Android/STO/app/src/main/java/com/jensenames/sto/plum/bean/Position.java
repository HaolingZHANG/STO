package com.jensenames.sto.plum.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Position {

    private int[] neighborIds;

    private static Position[] positions;

//        7
//    5       6
//        4
//    2       3
//        1
//
//        0

    public static ArrayList<Integer> getRoute(int count) {
        positions = newInstance();
        ArrayList<Integer> totalPath = new ArrayList<>(count);
        totalPath.add(0);
        totalPath.add(1);
        for(int i = 2; i < count - 3; i++) {
            int lastId = totalPath.get(totalPath.size() - 2);
            int currId = totalPath.get(totalPath.size() - 1);

            ArrayList<Integer> next = new ArrayList<>();
            for(Integer canNext : positions[currId].neighborIds)
                if(canNext != lastId && canNext != 0)
                    if(totalPath.size() != count - 4 || canNext != 7)
                        next.add(canNext);
            totalPath.add(next.get(new Random().nextInt(next.size())));
        }
        if(totalPath.get(totalPath.size() - 1) != 4) {
            totalPath.add(4);
        } else {
            if(new Random().nextBoolean())
                totalPath.add(2);
            else
                totalPath.add(3);
        }
        totalPath.add(1);
        totalPath.add(0);
        System.out.println(Arrays.toString(totalPath.toArray()));
        return totalPath;
    }

    private static Position[] newInstance() {
        if(positions == null) {
            positions = new Position[8];
            for(int i = 0; i < 8; i++) {
                positions[i] = new Position();
                switch (i) {
                    case 0:
                        positions[i].neighborIds = new int[]{1};
                        break;
                    case 1:
                        positions[i].neighborIds = new int[]{0, 2, 3, 4};
                        break;
                    case 2:
                        positions[i].neighborIds = new int[]{1, 4, 5};
                        break;
                    case 3:
                        positions[i].neighborIds = new int[]{1, 4, 6};
                        break;
                    case 4:
                        positions[i].neighborIds = new int[]{1, 2, 3, 5, 6, 7};
                        break;
                    case 5:
                        positions[i].neighborIds = new int[]{2, 4, 7};
                        break;
                    case 6:
                        positions[i].neighborIds = new int[]{3, 4, 7};
                        break;
                    case 7:
                        positions[i].neighborIds = new int[]{4, 5, 6};
                }
            }
            return positions;
        } else
            return positions;
    }

    public static void clear() {
        positions = null;
    }

    private Position() {
        //External applications are not allowed
    }
}