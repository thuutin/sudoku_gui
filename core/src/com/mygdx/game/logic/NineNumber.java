package com.mygdx.game.logic;

import aima.core.search.csp.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tin on 4/17/15.
 */
public class NineNumber {

    private List<Variable> variables;
    private int[] numbers = new int[9];
    private int[] backup;
    private int index;

    public NineNumber(int index, int[] ints){
        backup = new int[ints.length];
        System.arraycopy(ints, 0, backup, 0, ints.length);
        numbers = ints;
        this.index = index;
    }

    public int[] getRow(int row){
        row = row * 3;
        return copyArray(row++, row++, row);
    }

    public int[] getColumn(int col){
        return copyArray(col, col + 3, col + 6);
    }

    private int[] copyArray(int... ints){
        int[] result = new int[ints.length];
        int j = 0;
        for (int i : ints){
            result[j] = numbers[i];
            j++;
        }
        return result;
    }

    public int[] getNumbers(){
        return numbers;
    }

    public List<Variable> getVariables(){
        if(variables == null) {
            variables = new ArrayList<Variable>();
            for (int i = 0; i < numbers.length; i++) {
                if (numbers[i] == 0) {
                    variables.add(new Variable(String.format("%d%d", index, i )));
                }
            }
        }
        return variables;
    }

    public void setNumber(int value, int position){
        if (numbers[position] == 0){
            numbers[position] = value;
        } else {
            throw new UnsupportedOperationException("Can not change pre-defined variable");
        }
    }

    public int getIndex() {
        return index;
    }

    public boolean ensureNoConflict(int value){
         for (int i : numbers){
             if( i == value){
                 return false;
             }
         }
        return true;
    }

    public void reset(){
        System.arraycopy(backup, 0, numbers, 0, backup.length);
    }
}
