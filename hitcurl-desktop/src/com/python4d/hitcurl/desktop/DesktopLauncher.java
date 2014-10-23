package com.python4d.hitcurl.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.python4d.hitcurl.HitcurL;
import com.python4d.hitcurl.game.Constants;

public class DesktopLauncher {
	private static boolean bCreateAtlas = true;

	public static void main(String[] arg) {

		if (bCreateAtlas == true) {
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			TexturePacker.processIfModified(settings, "./images",
					"../hitcurl-android/assets/images", "hitcurl.atlas");
		}
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		cfg.title = "HitcurL";

		cfg.width = (int) Constants.VIEWPORT_WIDTH;
		cfg.height = (int) Constants.VIEWPORT_HEIGHT;

		new LwjglApplication(new HitcurL(new DesktopGoogleServices(), "alpha"), cfg);
	}
}
