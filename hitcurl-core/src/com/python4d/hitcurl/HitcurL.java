package com.python4d.hitcurl;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.python4d.hitcurl.game.Assets;
import com.python4d.hitcurl.screens.SplashScreen;

public class HitcurL extends Game {

	// a libgdx helper class that logs the current FPS each second
	private boolean paused;
	private static final String TAG = HitcurL.class.getName();

	@Override
	public void create() {
		// Set Libgdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log(TAG, "Creating game");
		// Load assets
		Assets.instance.init(new AssetManager());
		Gdx.input.setCatchBackKey(true);
		setScreen(new SplashScreen(this));
	}

}
