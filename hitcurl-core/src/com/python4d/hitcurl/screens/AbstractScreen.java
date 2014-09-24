package com.python4d.hitcurl.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.python4d.hitcurl.game.Assets;
import com.python4d.hitcurl.game.Constants;

public abstract class AbstractScreen implements Screen {
	protected Game game;
	protected int deltawait = Constants.TEMPO_TOUCHE;
	public float screenFactor;

	public AbstractScreen(Game game) {
		this.game = game;
		this.screenFactor = Gdx.graphics.getWidth() / Constants.VIEWPORT_WIDTH;
	}

	public abstract void render(float deltaTime);

	public abstract void resize(int width, int height);

	public abstract void show();

	public abstract void hide();

	public abstract void pause();

	public void resume() {
		Assets.instance.init(new AssetManager());
	}

	public void dispose() {
		Assets.instance.dispose();
	}

}
