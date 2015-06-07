package com.mygdx.game.logic;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
import com.sun.deploy.util.ArrayUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by tin on 4/17/15.
 */
public class Sudoku {

    private NineNumber[] cells = new NineNumber[9];

    public Sudoku(){
        //int[] a = {2, 0, 7, 0, 0, 0, 5, 0, 1};
        cells[0] = new NineNumber(0, new int[] {2, 3, 7, 0, 0, 0, 5, 0, 1});
        cells[1] = new NineNumber(1, new int[] {5, 1, 0, 0, 0, 8, 0, 0, 0});
        cells[2] = new NineNumber(2, new int[] {0,0,0,0,2,0,6,0,0});

        cells[3] = new NineNumber(3, new int[] {0,0,0,0,0,0,0,0,5});
        cells[4] = new NineNumber(4, new int[] {0,0,0,0,0,0,0,0,0});
        cells[5] = new NineNumber(5, new int[] {4,0,0,0,0,0,0,0,0});

        cells[6] = new NineNumber(6, new int[] {0,0,3,0,5,0,0,0,0});
        cells[7] = new NineNumber(7, new int[] {0,0,0,4,0,0,0,6,5});
        cells[8] = new NineNumber(8, new int[] {7,0,6,0,0,0,9,0,8});
    }

    public Sudoku(NineNumber[] cells){
        this.cells = cells;
    }

    public int[] getRow(int row){
        int whichCell = row/3;
        whichCell = whichCell * 3;
        int[] a = cells[whichCell++].getRow(row % 3);
        int[] b = cells[whichCell++].getRow(row % 3);
        int[] c = cells[whichCell].getRow(row % 3);

        int[] result = new int[9];
        for (int i = 0; i < a.length; i++){
            result[i] = a[i];
            result[i+3] = b[i];
            result[i+6] = c[i];
        }
        return result;
    }

    public int[] getColumn(int col){
        int whichCell = col/3;
        int[] a = cells[whichCell].getColumn(col % 3);
        int[] b = cells[whichCell+3].getColumn(col % 3);
        int[] c = cells[whichCell+6].getColumn(col % 3);

        int[] result = new int[9];
        for (int i = 0; i < a.length; i++){
            result[i] = a[i];
            result[i+3] = b[i];
            result[i+6] = c[i];
        }
        return result;
    }

    public List<Variable> getVariables(){
        List<Variable> variables = new ArrayList<Variable>();
        for (NineNumber c : cells){
            variables.addAll(c.getVariables());
        }
        return variables;
    }

    public NineNumber[] getCells(){
        return cells;
    }

    @Override
    public String toString() {
        return String.format("%d", getNum());
    }

    public int getNum(){
        int num = 0;
        for ( NineNumber c : cells){
            for (int i : c.getNumbers()){
                if ( i== 0){
                    num++;
                }
            }
        }
        return num;
    }


    public void reset(){
        for (NineNumber c : cells){
            c.reset();
        }
    }


}
