package com.python4d.hitcurl.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.python4d.hitcurl.game.Constants.ColorCube;
import com.python4d.hitcurl.screens.GameScreen;

public class Letter extends Image {

	private static final String TAG = Letter.class.getName();
	private boolean isDragging = false;
	private Vector2 DragStart = new Vector2(0, 0);
	private static float Dragdelta = 10.0f;
	
	public Letter(AtlasRegion LetterTexture) {
		super(LetterTexture);
//		if (Constants.DEBUG)
//			this.debug();
		this.setOrigin(getWidth() / 2, getHeight() / 2);

		this.setTouchable(Touchable.enabled);
		this.addListener((new DragListener() {

			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (!isDragging) {
					toFront();
					DragStart.set(localToStageCoordinates(new Vector2(
							getTouchDownX(), getTouchDownY())));
				}
				isDragging = true;
				Vector2 v = localToStageCoordinates((new Vector2(x, y)));
				
				// On ramène l'objet au centre de rotation pour le faire une
				// rotation inverse et on remet l'objet à sa place suivant le
				// click (touchdown)
				// http://forums.futura-sciences.com/mathematiques-superieur/308920-rotation-dun-objet-un-repere-autour-dun-axe-de-cet-objet.html
				Vector2 tv = new Vector2(getTouchDownX(), getTouchDownY());
				Vector2 ov = new Vector2(getOriginX(), getOriginY());
				
				tv.sub(ov);
				tv.scl(getScaleX(), getScaleY());
				tv.rotate(getRotation());
				tv.add(ov);

				v.sub(tv);
				setPosition(v.x, v.y);
			}

		}));

		// Rotation 2D
		// x' = x cos f - y sin f
		// y' = y cos f + x sin f

		this.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.debug(
						TAG,
						"clicked(int screenX, int screenY, int pointer, int button) #"
								+ localToStageCoordinates(new Vector2(x, y))
								+ " drag?"
								+ isDragging
								+ " =>"
								+ DragStart
								+ " dist="
								+ DragStart.dst2(localToStageCoordinates(new Vector2(x, y)))
								+ " rotation="+getRotation()%360);

				if (!isDragging
						|| DragStart.dst2(localToStageCoordinates(new Vector2(
								x, y))) < Dragdelta * getScaleX()) {
					setRotation(getRotation() + 90);
				} else
					DragStart.set(localToStageCoordinates(new Vector2(x, y)));
				isDragging = false;
			}

		});

	}

	public void update(float deltaTime) {

	}

}
