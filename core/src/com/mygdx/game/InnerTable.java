package com.mygdx.game;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Variable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.logic.MyConstraints;

/**
 * Created by tin on 6/6/15.
 */
public class InnerTable extends Table implements MyConstraints.OnAssignmentChange {

    String tag;
    private Label[] labels = new Label[9];

    public InnerTable(String num, Skin skin) {
        super(skin);
        //setFillParent(true);
//        setDebug(true);
        this.tag = num;
        for (int i = 0; i <9; i++){
            Label l = new Label(String.valueOf(0), getSkin());
            labels[i] = l;
            l.setAlignment(Align.center);
            float scaleX = labels[i].getWidth() * 3;
            l.setFontScale(MyGdxGame.NUMBER_SIZE/scaleX);
            l.setSize(MyGdxGame.NUMBER_SIZE, MyGdxGame.NUMBER_SIZE);
            add(l)
                    .size(MyGdxGame.NUMBER_SIZE, MyGdxGame.NUMBER_SIZE);
            if (i %3 == 2) row();
        }
    }

    @Override
    public void performUIUpdate(Assignment assignment) {
        for (Variable v : assignment.getVariables()){
            String index = v.getName().substring(0, 1);
            if (index.equals(tag)){
                String s = v.getName().substring(1,2);
                int i = Integer.parseInt(s);
                Integer value = (Integer) assignment.getAssignment(v);
                labels[i].setText(String.valueOf(value));
            }
        }
    }
}
