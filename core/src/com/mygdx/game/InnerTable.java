package com.mygdx.game;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Variable;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.logic.MyConstraints;
import com.mygdx.game.utils.Utils;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by tin on 6/6/15.
 */
public class InnerTable extends Table {

    String tag;
    static Random random = new Random();
    Thread thread;
    public final ReentrantLock lock = new ReentrantLock();
    private Label[] labels = new Label[9];

    public InnerTable(String num, Skin skin) {
        super(skin);

        int[] numberForThisCell = Utils.getSudokuInstance()[Integer.parseInt(num)];
        //setFillParent(true);
//        setDebug(true);
        this.tag = num;
        for (int i = 0; i < 9; i++){
            final NumberLabel l = new NumberLabel(String.valueOf(numberForThisCell[i]), getSkin(), "arial");
            labels[i] = l;
            l.setAlignment(Align.center);
            add(l).size(MyGdxGame.NUMBER_SIZE, MyGdxGame.NUMBER_SIZE);
            if (i % 3 == 2) row();
        }

        addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof OnCellChange){
                    OnCellChange onCellChange = (OnCellChange) event;
                    int[] data = onCellChange.getInts();
                    for(int i = 0; i < 9; i++){
                        String s = String.valueOf(data[i]);
                        labels[i].fire(new NumberLabel.OnNumberChange(s));
                    }
                    return true;
                }
                return false;
            }
        });
    }


    public void updateValues(Assignment assignment) {
        for (Variable v : assignment.getVariables()){
            String index = v.getName().substring(0, 1);
            if (index.equals(tag)){
                String s = v.getName().substring(1,2);
                int i = Integer.parseInt(s);
                int value = (Integer) assignment.getAssignment(v);
                //System.out.print(value);

                value = random.nextInt();
                lock.lock();

                    //labels[i].setText(String.valueOf(value));

                lock.unlock();

            }
        }
    }

    public static class OnCellChange extends Event{
        private int[] ints;

        public OnCellChange(int[] ints){
            this.ints = ints;
        }

        public int[] getInts(){
            return ints;
        }
    }

}
