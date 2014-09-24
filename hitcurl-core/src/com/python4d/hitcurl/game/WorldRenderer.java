package com.python4d.hitcurl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable {
	private static final String TAG = WorldRenderer.class.getName();
	public OrthographicCamera camera, cameraHUD;
	private SpriteBatch batch;

	// On récupére l'adresse de worldController pour pouvoir dessiner les objets
	// définis/dessinés dans level
	private WorldController worldController;

	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}

	private void init() {
		batch = (SpriteBatch) worldController.stage.getBatch();
		cameraHUD = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	// ==========================================================RENDER
	public void render() {
		renderWorld(batch);
		renderGui(batch);
	}

	private void renderGui(SpriteBatch batch) {

		worldController.hud.act(Gdx.graphics.getDeltaTime());
		worldController.hud.draw();
	}

	private void renderWorld(SpriteBatch batch) {
		worldController.stage.act(Gdx.graphics.getDeltaTime());
		worldController.stage.draw();
	}

	// ======================================================RESIZE
	public void resize(int width, int height) {
		// resize the stage
		worldController.stage.getViewport().update(width, height, true);
		((OrthographicCamera) worldController.stage.getCamera()).zoom = 1 / (worldController.stage.getWidth() / (Constants.SIZE_CUBE * 12f));
		worldController.hud.getViewport().update(width, height, true);

		Gdx.app.debug(TAG,
				"RESIZE => Width & Height #" + Gdx.graphics.getWidth() + " & "
						+ Gdx.graphics.getHeight());
	}

	@Override
	public void dispose() {
	}
}