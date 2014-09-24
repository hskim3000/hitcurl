package com.python4d.hitcurl.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.python4d.hitcurl.game.Constants;
import com.python4d.hitcurl.game.WorldController;
import com.python4d.hitcurl.game.WorldRenderer;

public class GameScreen extends AbstractScreen {
	private static final String TAG = GameScreen.class.getName();
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	private boolean paused = false;
	public float screenFactor;

	public GameScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(float deltaTime) {
		if (deltawait-- < 0
				&& (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input
						.isKeyPressed(Keys.ESCAPE))) {
			deltawait = Constants.TEMPO_TOUCHE;
			backToMenu();
		}
		// Do not update game world when paused.
		if (!paused) {
			// Update game world by the time that has passed
			// since last rendered frame.
			worldController.update(deltaTime);
		}
		// Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f,
				0xff / 255.0f);
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		worldRenderer.render();
	}

	private void backToMenu() {
		// switch to menu screen
		game.setScreen(new SplashScreen(game));
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void show() {
		// <?
		// Visiblement il faut remettre directement par une commande bas niveau
		// le Viewport au dimension actuelle
		// peut-être une explication ici
		// https://github.com/libgdx/libgdx/issues/1661
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// ?>
		screenFactor = Gdx.graphics.getWidth() / Constants.VIEWPORT_WIDTH;
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
		Gdx.input.setCatchBackKey(true);

	}

	@Override
	public void hide() {
		worldRenderer.dispose();
		Gdx.input.setCatchBackKey(false);

	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		super.resume();
		// Only called on Android!
		paused = false;
	}

}
