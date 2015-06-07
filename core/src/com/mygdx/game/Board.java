package com.mygdx.game;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Variable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.logic.MyConstraints;

import java.util.List;

/**
 * Created by tin on 6/6/15.
 */
public class Board extends Table implements MyConstraints.OnAssignmentChange {

    InnerTable[] cells = new InnerTable[9];
    Assignment mCurrentAssignment = null;

    public Board(Skin skin) {
        this(skin, null);
    }

    public Board(Skin skin, Assignment assignment){
        super(skin);

        //this.mCurrentAssignment = assignment;
        for (int i = 0; i < 9; i++){
            cells[i] = new InnerTable(String.valueOf(i), getSkin());
            add(cells[i])
                    .size(MyGdxGame.CELL_SIZE, MyGdxGame.CELL_SIZE);
            if (i %3 == 2) row();
        }
    }

    @Override
    public void performUIUpdate(Assignment assignment) {
        if (mCurrentAssignment == null)
            for (MyConstraints.OnAssignmentChange mInterface : cells)
                mInterface.performUIUpdate(assignment);
        else {
            //int[] change = compareAssignment(assignment, mCurrentAssignment);
        }
        //mCurrentAssignment = assignment;
    }

    private int[] compareAssignment(Assignment a1, Assignment a2){
        if (a1.getVariables().size() < a2.getVariables().size())
            return compareAssignment(a2, a1);

        for (int i = 0; i < a1.getVariables().size(); i++){
            Variable v1 = a1.getVariables().get(i);
            Variable v2 = a2.getVariables().get(i);
            if (v1.getName().equals(v2)){
                System.out.println(String.format("true"));
            }
        }
        return null;

    }


}
