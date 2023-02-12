package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.MainGame;
import com.mygdx.game.extra.Utils;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setWindowedMode(Utils.SCREEN_WIDTH, Utils.SCREEN_HEIGHT);
		config.setForegroundFPS(60);
		config.setTitle("PacmanACD");
		new Lwjgl3Application(new MainGame(), config);
	}
}
