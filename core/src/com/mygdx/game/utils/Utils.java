package com.mygdx.game.utils;

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
}
