package com.mygdx.game;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Variable;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.utils.Utils;
import sun.text.normalizer.UTF16;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by tin on 6/6/15.
 */
public class Board extends Table {

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
        addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {

                if (event instanceof OnBoardChange) {
                    OnBoardChange e = (OnBoardChange) event;
                    int[][] ints = e.getInts();
                    fireCellsEvent(ints);
                    return true;
                }
                return false;
            }
        });
    }

    public void fireCellsEvent(int[][] ints) {
        for(int i = 0; i < 9; i++){
            cells[i].fire(new InnerTable.OnCellChange(ints[i]));
        }
    }

    public void performUIUpdate(Assignment ass){
        int[][] def = Utils.convertAssignmentToIntArray(ass);
        this.fire(new OnBoardChange(def));
    }

    @Override
    public void act(float delta) {

            //MyGdxGame.check = false;
        BlockingQueue queue = MyGdxGame.queue;
        Message msg;
        while ((msg = (Message) queue.poll()) != null) {
            performUIUpdate(msg.getMsg());
        }
        synchronized (MyGdxGame.reentrantLock){
            MyGdxGame.stop = false;
        }
        super.act(delta);
    }

    public static class OnBoardChange extends Event{

        private int[][] ints;

        public OnBoardChange(int[][] ints){
            this.ints = ints;
        }

        public int[][] getInts(){
            return ints;
        }
    }


}
