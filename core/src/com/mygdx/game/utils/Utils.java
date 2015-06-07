package com.mygdx.game.utils;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Variable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tin on 4/18/15.
 */
public class Utils {
    public static int[] convertRowColumnToCellPosition(int row, int column){
        int[] cellPos = new int[2];
        cellPos[0] = (row / 3) * 3 + column / 3;
        cellPos[1] = (row % 3) * 3 + column % 3;
        return cellPos;
    }

    public static int[][] getSudokuInstance(){
        int[][] def = {
                {2, 3, 7, 0, 0, 0, 5, 0, 1},
                {5, 1, 0, 0, 0, 8, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 6, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 0, 5, 0, 0, 0, 0},
                {0, 0, 0, 4, 0, 0, 0, 6, 5},
                {7, 0, 6, 0, 0, 0, 9, 0, 8},
        };
        return def;
    }

    public static int[][] convertAssignmentToIntArray(Assignment assignment){
        int[][] def = getSudokuInstance();

        List<Variable> vars = assignment.getVariables();
        for (Variable v : vars){
            int value  = (int) assignment.getAssignment(v);
            int i = Integer.parseInt(v.getName().substring(0, 1));
            int j = Integer.parseInt(v.getName().substring(1, 2));
            def[i][j] = value;
        }
        return def;
    }

}
