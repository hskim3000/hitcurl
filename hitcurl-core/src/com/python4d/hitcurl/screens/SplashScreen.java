package com.python4d.hitcurl.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.python4d.hitcurl.game.Assets;
import com.python4d.hitcurl.game.Constants;
import com.python4d.hitcurl.game.CubesGroup;
import com.python4d.hitcurl.game.TextActor;

public class SplashScreen extends AbstractScreen {

	private static final String TAG = SplashScreen.class.getName();
	private Stage stage;
	private Skin skin;
	private Image imgBackground;
	// debug
	private boolean debugEnabled = true;

	// The global field
	ShapeRenderer debugRenderer;
	private float FirstHeight;
	private int FirstWidth;

	public SplashScreen(Game game) {
		super(game);

	}

	private void rebuildStage() {
		skin = new Skin(Gdx.files.internal(Constants.SKIN_OBJECTS));
		// build all layers
		Table tableLayer = new Table(skin);
		tableLayer.setFillParent(true);
		imgBackground = new Image(skin, "tetris");
		CubesGroup cube = new CubesGroup("cube", new Object[][] { { new Vector2(0, 0), Assets.instance.cubes.jauneAtlas } });
		tableLayer.add(imgBackground);
		if (Constants.DEBUG)
			tableLayer.debugAll();
		TextActor textActor = new TextActor(new BitmapFont(), "(c) Python4D & PP");
		textActor.setColor(Color.YELLOW);
		tableLayer.row();
		tableLayer.add(textActor).center();

		stage.addActor(tableLayer);
		stage.addActor(cube);
		cube.addAction(forever(
				sequence(moveBy(0, stage.getHeight() - Constants.SIZE_CUBE, 1.0f),
						moveBy(stage.getWidth() - Constants.SIZE_CUBE, 0, 1.0f * stage.getWidth() / stage.getHeight()),
						moveBy(0, -stage.getHeight() + Constants.SIZE_CUBE, 1.0f),
						moveBy(-stage.getWidth() + Constants.SIZE_CUBE, 0, 1.0f * stage.getWidth() / stage.getHeight()))));

	}

	@Override
	public void render(float deltaTime) {
		if (deltawait-- < 0 && Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			deltawait = 10;
		}
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(deltaTime);
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, (int) ((float) width / (float) FirstWidth * (float) height));
		Gdx.app.debug(TAG, "Screen Width & Height # " + stage.getViewport().getScreenWidth() + " & " + stage.getViewport().getScreenHeight());
		Gdx.app.debug(TAG, "World Width & Height # " + stage.getViewport().getWorldWidth() + " & " + stage.getViewport().getWorldHeight());

	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);
		FirstWidth = Gdx.graphics.getWidth();
		FirstHeight = Gdx.graphics.getHeight();
		rebuildStage();
	}

	// Hide est appelé par Game au changemement de Screen
	@Override
	public void hide() {
		stage.dispose();
		skin.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

}
