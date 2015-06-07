package com.mygdx.game.desktop;

import aima.core.search.csp.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.MyConstraints;
import com.mygdx.game.logic.NineNumber;
import com.mygdx.game.logic.Sudoku;

import java.util.ArrayList;
import java.util.List;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Artifitial Intelligent Assignment";
		config.height = config.width = MyGdxGame.BOARD_SIZE;

		MyGdxGame myGame = new MyGdxGame();
		new LwjglApplication(myGame, config);
	}
}
