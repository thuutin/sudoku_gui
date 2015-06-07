package com.mygdx.game.logic;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
import com.mygdx.game.Message;
import com.mygdx.game.MyGdxGame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by tin on 4/17/15.
 */
public class MyConstraints implements Constraint {
    private int num = 61;
    private Sudoku sudoku;

    private OnAssignmentChange mUIclient;

    public MyConstraints(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    public void setUIClient(OnAssignmentChange client){
        this.mUIclient = client;
    }

    @Override
    public List<Variable> getScope() {
        return sudoku.getVariables();
    }

    @Override
    public boolean isSatisfiedWith (Assignment assignment) {
        //Sudoku s = copySudoku();
        try {
            sudoku.reset();
            return applyAssignment(assignment);
        } catch (InterruptedException e){
            e.printStackTrace();

        }
        System.out.println("FATAL, PROGRAM WRONG");
        return false;
    }

    private boolean applyAssignment (Assignment assignment) throws InterruptedException{

        long i = Thread.currentThread().getId();
        MyGdxGame.queue.put(new Message(assignment));
        synchronized (MyGdxGame.reentrantLock){
            MyGdxGame.stop = true;
        }
        while(MyGdxGame.stop){
            Thread.currentThread().sleep(MyGdxGame.INTERVAL);
        }
        List<Variable> variables = assignment.getVariables();
        Hashtable<Integer, ArrayList<Integer>> columns = new Hashtable<Integer, ArrayList<Integer>>(0);
        Hashtable<Integer, ArrayList<Integer>> rows = new Hashtable<Integer, ArrayList<Integer>>(0);
        Hashtable<Integer, ArrayList<Integer>> cells = new Hashtable<Integer, ArrayList<Integer>>(0);

        for (Variable v : variables){
            Integer value = (Integer) assignment.getAssignment(v);
            int col = whichColumnAreYouIn(v);
            int row = whichRowAreYouIn(v);
            int cell = whichCellAreYouIn(v);

            if(!columns.keySet().contains(col)){
                ArrayList<Integer> arrayList = new ArrayList<Integer>(1);
                arrayList.add(value);
                columns.put(col, arrayList);
            } else {
                ArrayList<Integer> arrayList = columns.get(col);
                if (arrayList.contains(value)){
                    return false;
                } else {
                    arrayList.add(value);
                }
            }

            if(!rows.keySet().contains(row)){
                ArrayList<Integer> arrayList = new ArrayList<Integer>(1);
                arrayList.add(value);
                rows.put(row, arrayList);
            } else {
                ArrayList<Integer> arrayList = rows.get(row);
                if (arrayList.contains(value)){
                    return false;
                } else {
                    arrayList.add(value);
                }
            }

            if(!cells.keySet().contains(cell)){
                ArrayList<Integer> arrayList = new ArrayList<Integer>(1);
                arrayList.add(value);
                cells.put(cell, arrayList);
            } else {
                ArrayList<Integer> arrayList = cells.get(cell);
                if (arrayList.contains(value)){
                    return false;
                } else {
                    arrayList.add(value);
                }
            }


//            int[] colNums = s.getColumn(whichColumnAreYouIn(v));
//            for( int i : colNums){
//                if (i == value){
//                    return false;
//                }
//            }
//
//            int[] rowNums = s.getRow(whichRowAreYouIn(v));
//            for( int i : rowNums ){
//                if (i == value){
//                    return false;
//                }
//            }
//
//            // Add to The Cell (included checking)
//            int cell = whichCellAreYouIn(v);
//            int pos = Integer.parseInt(v.getName().substring(1, 2));
//            Cell c = s.getCells()[cell];
//            if (!c.ensureNoConflict(value)){
//                return false;
//            } else {
//                c.setNumber(value, pos);
//            }

        }

        return true;
    }


    public static int whichCellAreYouIn(Variable v){
        return Integer.parseInt(v.getName().substring(0, 1));
    }

    public static int whichRowAreYouIn(Variable v){
        int cell =  whichCellAreYouIn(v);
        int positionInCell = Integer.parseInt(v.getName().substring(1, 2));
        int div = positionInCell / 3;
        int cellCol = cell / 3;
        return cellCol * 3 + div;
    }

    public static int whichColumnAreYouIn(Variable v){
        int cell =  whichCellAreYouIn(v);
        int positionInCell = Integer.parseInt(v.getName().substring(1, 2));
        int mod = positionInCell % 3;
        int cellCol = cell % 3;
        return cellCol * 3 + mod;
    }

    private Sudoku copySudoku(){
        NineNumber[] cells = sudoku.getCells();
        NineNumber[] newCells = new NineNumber[cells.length];
        for( NineNumber c : cells){
            int[] oldNums = c.getNumbers();
            int[] newNums = new int[oldNums.length];
            System.arraycopy(oldNums, 0, newNums, 0, oldNums.length);
            newCells[c.getIndex()] = new NineNumber(c.getIndex(), newNums);
        }
        return new Sudoku(newCells);
    }

    public interface OnAssignmentChange {
        void performUIUpdate(Assignment assignment);
    }
}
