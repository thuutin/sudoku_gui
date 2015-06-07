package com.mygdx.game;

import aima.core.search.csp.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.logic.MyConstraints;
import com.mygdx.game.logic.NineNumber;
import com.mygdx.game.logic.Sudoku;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {

	public final static int BOARD_SIZE = 600;
	public final static int CELL_SIZE = BOARD_SIZE/3;
	public final static int NUMBER_SIZE = BOARD_SIZE/9;

	private Stage stage;
	private Skin skin;

	@Override
	public void create () {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		FileHandle fileHandle = new FileHandle("/Users/tin/IdeaProjects/gdx/core/assets/uiskin.json");
		skin = new Skin(fileHandle);

		Table table = new Board(skin);
		table.setFillParent(true);
		table.setSize(BOARD_SIZE, BOARD_SIZE);
		table.setDebug(true);
		stage.addActor(table);

		new Thread(new Runnable() {
			@Override
			public void run() {
				setUpAIAgent((Board) table);
			}
		}).start();
		//stage.addActor(new ImageButton(skin));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
	}

	private void setUpAIAgent(MyConstraints.OnAssignmentChange client){
		final Sudoku sudoku = new Sudoku();
		final Constraint constraint = new MyConstraints(sudoku);
		((MyConstraints) constraint).setUIClient(client);
		//final BacktrackingStrategy bs = new BacktrackingStrategy();
		final ImprovedBacktrackingStrategy bs = new ImprovedBacktrackingStrategy(true, false, false, false);

		List<Variable> variableList = sudoku.getVariables();
		CSP csp = new CSP(variableList);

		for ( Variable v : variableList){
			ArrayList<Object> arrayList = new ArrayList<Object>(0);
			for(int value = 0; value < 10; value++) {
				if (check(sudoku, v, value) ){
					arrayList.add(value);
				}
			}
			Domain domain = new Domain(arrayList);
			csp.setDomain(v, domain);
		}

		csp.addConstraint(constraint);
		Assignment a = bs.solve(csp);
		System.out.println(a);
	}

	public static boolean check(Sudoku sudoku, Variable v, int value) {

		int[] colNums = sudoku.getColumn(MyConstraints.whichColumnAreYouIn(v));
		for (int i : colNums) {
			if (i == value) {
				return false;
			}
		}

		int[] rowNums = sudoku.getRow(MyConstraints.whichRowAreYouIn(v));
		for (int i : rowNums) {
			if (i == value) {
				return false;
			}
		}

		int cell = MyConstraints.whichCellAreYouIn(v);
		NineNumber c = sudoku.getCells()[cell];
		if (!c.ensureNoConflict(value)) {
			return false;
		}
		return true;
	}

}


