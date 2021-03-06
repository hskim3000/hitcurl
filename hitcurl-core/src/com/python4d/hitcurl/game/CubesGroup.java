package com.python4d.hitcurl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;

public class CubesGroup extends Group {

	private static final String TAG = CubesGroup.class.getName();
	private float cubesize;
	private boolean isDragging = false;
	private Vector2 DragStart = new Vector2(0, 0);
	private static float Dragdelta = 10.0f;
	private Vector2 size = new Vector2(0, 0);
	private boolean clicked = false;
	private boolean inGrille = false;
	private Vector2 initPos;
	private Skin skin;

	/** @see #CubesGroup(String name, Object[][] objects) */
	public CubesGroup(String name) {
		this(name, Constants.lettres.get(name));
	}

	/**
	 * Crée un Group Acteur composé de cube suivant objects
	 * {@link Constants#lettres lettres}
	 * 
	 * @author damien
	 */
	public CubesGroup(String name, Object[][] objects) {
		skin = Assets.getAssetManager().get(Constants.SKIN_OBJECTS, Skin.class);

		if (Constants.DEBUG)
			this.debugAll();
		this.setName(name + "-*");
		this.setTouchable(Touchable.childrenOnly);
		cubesize = Constants.SIZE_CUBE;
		Vector2 xy;
		Image img;
		for (Object[] cube : objects) {
			// Est-ce que le cube à plusieurs images, si oui on crée un Animated
			// Actor
			if (((String[]) cube[1]).length > 1) {
				Array<AtlasRegion> tmp = new Array<AtlasRegion>();
				for (int i = 1; i < ((String[]) cube[1]).length; i++) {
					tmp.add((AtlasRegion) skin.getRegion(((String[]) cube[1])[i]));
				}
				img = new AnimatedActor(new Animation((float) Math.random() * .15f + .15f, tmp, Animation.PlayMode.NORMAL));
			}
			// Sinon on crée une simple image avec la première image du cube
			else
				img = new Image(skin, ((String[]) cube[1])[0]);

			xy = (Vector2) cube[0];
			img.setName("cube-" + name);
			img.setPosition(xy.x * cubesize, xy.y * cubesize);
			img.setTouchable(Touchable.enabled);
			size.x = Math.max(size.x, xy.x * cubesize);
			size.y = Math.max(size.y, xy.y * cubesize);
			this.addActor(img);
		}
		this.setWidth(size.x + cubesize);
		this.setHeight(size.y + cubesize);
		this.setInitPos(new Vector2(0, 0));

		// pour la rotation prendre un pivot simple pour la rotation par rapport
		// à la grille...*/
		this.setOrigin(Math.min(getWidth() / 2, getHeight() / 2), Math.min(getWidth() / 2, getHeight() / 2));

		this.addListener((new DragListener() {

			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (!isDragging) {
					toFront();
					DragStart.set(localToStageCoordinates(new Vector2(
							getTouchDownX(), getTouchDownY())));
				}
				isDragging = true;
				for (Actor a : getChildren().items) {
					if (a != null && a instanceof AnimatedActor)
						((AnimatedActor) a).getAnim().setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
				}
				Vector2 v = localToStageCoordinates((new Vector2(x, y)));

				// On ramène l'objet au centre de rotation pour le faire une
				// rotation inverse et on remet l'objet à sa place suivant le
				// click (touchdown)
				// http://forums.futura-sciences.com/mathematiques-superieur/308920-rotation-dun-objet-un-repere-autour-dun-axe-de-cet-objet.html
				Vector2 tv = new Vector2(getTouchDownX(), getTouchDownY());
				Vector2 ov = new Vector2(getOriginX(), getOriginY());

				tv.sub(ov);
				tv.rotate(getRotation());
				tv.add(ov);

				v.sub(tv);
				float zoom = ((OrthographicCamera) getStage().getCamera()).zoom;

				Vector3 vw = getStage().getCamera().project(new Vector3(v.x, getStage().getHeight() - v.y, zoom));
				if (vw.x < 0)
					vw.x = 0;

				if (vw.y < getHeight() / zoom)
					vw.y = getHeight() / zoom;

				if (vw.x > (getStage().getWidth() - getWidth() / zoom))
					vw.x = (getStage().getWidth() - getWidth() / zoom);

				if (vw.y > (getStage().getHeight() - getHeight() / zoom))
					vw.y = (getStage().getHeight() - getHeight() / zoom);

				Vector3 coord = getStage().getCamera().unproject(vw);
				setPosition(coord.x, coord.y);
				Gdx.app.debug(TAG, "clicked(cube:" + getName() + ") #" + localToStageCoordinates(new Vector2(0, 0)) + " et " + v);
			}

		}));

		// Rotation 2D
		// x' = x cos f - y sin f
		// y' = y cos f + x sin f

		this.addListener(new ClickListener(-1) {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (!isDragging
						|| DragStart.dst2(localToStageCoordinates(new Vector2(
								x, y))) < Dragdelta * getScaleX()) {
					if (event.getButton() == 0)
						setRotation(getRotation() + 90);
					else
						setRotation(getRotation() - 90);
				} else
					DragStart.set(localToStageCoordinates(new Vector2(x, y)));
				isDragging = false;
				for (Actor a : getChildren().items) {
					if (a != null && a instanceof AnimatedActor)
						((AnimatedActor) a).getAnim().setPlayMode(Animation.PlayMode.NORMAL);
				}
				Gdx.app.debug(
						TAG,
						"clicked(cube:" + getName() + ") #"
								+ localToStageCoordinates(new Vector2(0, 0))
								+ " drag?"
								+ isDragging
								+ " =>"
								+ DragStart
								+ " dist="
								+ DragStart
										.dst2(localToStageCoordinates(new Vector2(
												x, y))) + " rotation="
								+ getRotation() % 360);
				clicked = true;

			}

		});
	}

	@Override
	public void setScale(float scaleXY) {
		super.setScale(scaleXY);

	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public boolean isClicked() {
		return clicked;
	}

	public boolean isInGrille() {
		return inGrille;
	}

	public void setInGrille(boolean inGrille) {
		this.inGrille = inGrille;
	}

	/**
	 * @return the initPos
	 */
	public Vector2 getInitPos() {
		return initPos;
	}

	/**
	 * @param initPos
	 *            the initPos to set
	 */
	public void setInitPos(Vector2 initPos) {
		this.initPos = initPos;
	}
}
