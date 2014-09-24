package com.python4d.hitcurl.game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;

public class WorldController extends InputAdapter implements Disposable {
	private static final String TAG = WorldController.class.getName();
	private InputMultiplexer inputMultiplexer;
	public Stage stage, hud;
	private Game game;
	protected Level level;
	public int score = 0;
	public float zoom = 1f;
	private Vector2 LasttouchDown;

	private int niveau = 0;
	private Skin skin;
	private TextButton boutonLevelD, boutonLevelG;
	private Table levelTable;
	private Label labelLevel;
	public float screenFactor;
	private TextActor scoretext;
	private TextActor completetext;
	private TextActor copyright;
	private Table completeTable;

	public WorldController(Game game) {
		this.game = game;
		init();
	}

	// ==================================================INIT
	public void init() {
		stage = new Stage();
		hud = new Stage();
		screenFactor = stage.getWidth() / Constants.VIEWPORT_WIDTH;

		level = new Level(stage, niveau);
		level.RestorePosition(niveau);
		skin = new Skin(Gdx.files.internal(Constants.SKIN_OBJECTS));
		levelTable = new Table();

		levelTable.setFillParent(true);
		completeTable = new Table();
		completeTable.setFillParent(true);
		levelTable.bottom().center();
		if (Constants.DEBUG)
			levelTable.debugAll();
		skin.getFont("fingerpaint").setColor(1, 0, 0, 1);
		skin.getFont("fingerpaint").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		labelLevel = new Label("Level " + niveau, skin);
		labelLevel.setColor(Color.YELLOW);
		labelLevel.setFontScale(screenFactor * 2);
		boutonLevelD = new TextButton("", skin, "BoutonJauneDroit");
		boutonLevelG = new TextButton("", skin, "BoutonJauneGauche");
		scoretext = new TextActor(skin.getFont("sketchfont"), "Score " + niveau);
		scoretext.setScale(screenFactor);
		copyright = new TextActor(skin.getFont("berlin"), "(c) Python4d & PP");
		copyright.setScale(0.5f * screenFactor);
		completetext = new TextActor(skin.getFont("goodgirl"), "Level Completed !");
		completetext.setScale(screenFactor);
		completetext.addAction(forever(sequence(fadeOut(1), fadeIn(1))));
		completeTable.add(completetext).center();
		completetext.setVisible(level.isComplete);

		boutonLevelD.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("button droit", "clicked");
				NextLevel();
			};
		});
		boutonLevelG.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("button droit", "clicked");
				PreviousLevel();
			};
		});
		levelTable.setTransform(true);
		levelTable.add(boutonLevelG).expandX().center();
		levelTable.add(labelLevel).expandX().center();
		levelTable.add(boutonLevelD).expandX().center();
		levelTable.row().expandX();
		levelTable.add(scoretext).center().expandX().pad(10);
		levelTable.add();
		levelTable.add(copyright).right().bottom().pad(3);
		levelTable.bottom().pack();
		hud.addActor(levelTable);
		hud.addActor(completeTable);

		inputMultiplexer = new InputMultiplexer(stage, hud);
		inputMultiplexer.addProcessor(1, this);
		Gdx.input.setInputProcessor(inputMultiplexer);
		LasttouchDown = new Vector2(-1, -1);
	}

	// ==================================================UPDATE
	public void update(float deltaTime) {
		// Test les lettre dans la grille
		level.update(deltaTime);
		completetext.setVisible(level.isComplete);
	}

	private void NextLevel() {
		level.SavePosition(niveau);
		if (++niveau >= Constants.NB_NIVEAU)
			niveau = 0;
		stage.clear();
		level = new Level(stage, niveau);
		level.RestorePosition(niveau);
		completetext.setVisible(level.isComplete);
		labelLevel.setText("Level " + niveau);
	}

	private void PreviousLevel() {

		level.SavePosition(niveau);
		if (--niveau < 0)
			niveau = Constants.NB_NIVEAU - 1;
		stage.clear();
		level = new Level(stage, niveau);
		level.RestorePosition(niveau);
		completetext.setVisible(level.isComplete);
		labelLevel.setText("Level " + niveau);
	}

	@Override
	public boolean scrolled(int amount) {
		Gdx.app.debug(TAG, "scrolled(int amount) #" + amount);
		stage.clear();
		if (amount > 0)
			NextLevel();
		else
			PreviousLevel();

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Gdx.app.debug(TAG,
				"touchDown(int screenX, int screenY, int pointer, int button) #" + screenX + ", " + screenY + ", " + pointer + ", " + button);

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Gdx.app.debug(TAG,
				"touchUp(int screenX, int screenY, int pointer, int button) #" + screenX + ", " + screenY + ", " + pointer + ", " + button
						+ " => Stage" + stage.screenToStageCoordinates(new Vector2(screenX, screenY)));

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Gdx.app.debug(TAG,
				"touchDragged(int screenX, int screenY, int pointer) #" + screenX + ", " + screenY + ", " + pointer);
		return false;
	}

	@Override
	public void dispose() {
		stage.dispose();
		hud.dispose();

	}

}