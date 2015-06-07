package com.mygdx.game;

import aima.core.search.csp.*;
import com.mygdx.game.logic.NineNumber;
import com.mygdx.game.logic.MyConstraints;
import com.mygdx.game.logic.Sudoku;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static boolean finalCheck(Sudoku s){
        int[] model = new int[] {1,2,3,4,5,6,7,8,9};
        for (int i = 0; i < 9; i++){
            int[] temp = s.getColumn(i).clone();
            Arrays.sort(temp);
            boolean a = Arrays.equals(temp, model);

            temp = s.getRow(i).clone();
            Arrays.sort(temp);
            boolean b = Arrays.equals(temp, model);

            temp = s.getCells()[i].getNumbers().clone();
            Arrays.sort(temp);
            boolean c = Arrays.equals(temp, model);
            if ( !a || !b || !c ){
                return false;
            }
        }
        return true;
    }

    private static boolean apply(Assignment assignment, Sudoku s){
        List<Variable> variables = assignment.getVariables();

        for (Variable v : variables){
            int value = (Integer) assignment.getAssignment(v);
            int[] colNums = s.getColumn(MyConstraints.whichColumnAreYouIn(v));
            for( int i : colNums){
                if (i == value){
                    return false;
                }
            }

            int[] rowNums = s.getRow(MyConstraints.whichRowAreYouIn(v));
            for( int i : rowNums ){
                if (i == value){
                    return false;
                }
            }

            // Add to The Cell (included checking)
            int cell = MyConstraints.whichCellAreYouIn(v);
            int pos = Integer.parseInt(v.getName().substring(1, 2));
            NineNumber c = s.getCells()[cell];
            if (!c.ensureNoConflict(value)){

                return false;

            } else {
                c.setNumber(value, pos);
            }

        }
        return true;
    }
}