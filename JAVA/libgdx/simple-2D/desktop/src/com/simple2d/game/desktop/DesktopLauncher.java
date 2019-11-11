package com.simple2d.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.simple2d.game.Simple2DGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//Simple2DGame game = new Simple2DGame();
		//config.width = game.V_WIDTH * 3;
		//config.height = game.V_HEIGHT * 3;
		new LwjglApplication(new Simple2DGame(), config);
	}
}
