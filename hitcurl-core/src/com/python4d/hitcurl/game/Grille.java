package com.python4d.hitcurl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Grille extends Table {

	private static final String TAG = Grille.class.getName();
	private String[] val = new String[64];

	public Grille(int n) {
		super();
		this.setSkin(Assets.instance.getSkin());
		this.setFillParent(false);
		this.left().bottom();
		if (Constants.DEBUG)
			this.debugTable();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				this.getVal()[i * 8 + j] = Constants.levels.get(n)[i].substring(j, j + 1);
				if (this.getVal()[i * 8 + j].equals(new String("*"))) {
					Image img = new Image(Assets.instance.cubes.noirAtlas);
					img.setName(Constants.levels.get(n)[i].substring(j, j + 1));
					this.add(img).minSize(Constants.SIZE_CUBE);
				}
				else {
					Image img = new Image(Assets.instance.cubes.blancAtlas);
					img.setName(Constants.levels.get(n)[i].substring(j, j + 1));
					this.add(img).minSize(Constants.SIZE_CUBE);
				}
			}
			this.row();
		}

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.debug(TAG,
						"clicked(grille local/stage/parent) #"
								+ new Vector2(x, y) + localToStageCoordinates(new Vector2(x, y)) + localToParentCoordinates(new Vector2(x, y)));
			}
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	/**
	 * @return the val
	 */
	public String[] getVal() {
		return val;
	}

	/**
	 * @param val
	 *            the val to set
	 */
	public void setVal(String[] val) {
		this.val = val;
	}

}
