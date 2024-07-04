package com.spaceshooter;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.spaceshooter.MainGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(144);
		config.setTitle("SpaceShooter");
		config.setWindowedMode(960,644);
		config.setResizable(false);
		new Lwjgl3Application(new MainGame(), config);
	}
}
