package com.sidescroller.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sidescroller.game.SideScrollerMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 480;
		//config.foregroundFPS = 60;
		new LwjglApplication(new SideScrollerMain(), config);
	}
}
