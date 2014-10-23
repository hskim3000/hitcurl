package com.python4d.hitcurl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.python4d.hitcurl.HitcurL;
import com.python4d.hitcurl.game.Assets;
import com.python4d.hitcurl.game.Constants;
import com.python4d.hitcurl.game.Toast;

public abstract class AbstractScreen implements Screen {
	public static final String TAG = AbstractScreen.class.getName();
	protected HitcurL game;
	protected int deltawait = Constants.TEMPO_TOUCHE;
	public float screenFactor;
	public Toast myToast;

	public AbstractScreen(HitcurL game) {
		this.game = game;
		myToast = new Toast(2, 0);
		screenFactor = Gdx.graphics.getWidth() / Constants.VIEWPORT_WIDTH;
	}

	public void render(float deltaTime) {
		if (Assets.getAssetManager().update()) {

			if (myToast != null)
				myToast.toaster();
		}
		// display loading information
		// float progress = Assets.getAssetManager().getProgress();
		// Gdx.app.error(TAG, "progress assets" + progress);
		// progress(return the % of loading assets finished ) use for displaying
		// the progressbar

	}

	public abstract void resize(int width, int height);

	public abstract void show();

	public abstract void hide();

	public abstract void pause();

	public void resume() {
		// Assets.instance.init(new AssetManager());
	}

	public void dispose() {
		Assets.instance.dispose();
	}

}
