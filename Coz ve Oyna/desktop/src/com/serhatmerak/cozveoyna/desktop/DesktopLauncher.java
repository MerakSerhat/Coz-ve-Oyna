package com.serhatmerak.cozveoyna.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.serhatmerak.cozveoyna.GameMain;
import com.serhatmerak.cozveoyna.helpers.GameInfo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameInfo.WIDTH;
		config.height = GameInfo.HEIGHT;
		config.resizable = false;
//		config.fullscreen = true;
		config.title = "Coz ve Oyna";
		config.addIcon("desktop_icon.png", Files.FileType.Internal);
		new LwjglApplication(new GameMain(), config);
	}
}
