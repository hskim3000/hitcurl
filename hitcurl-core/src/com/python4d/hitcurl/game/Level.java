package com.python4d.hitcurl.game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Level {
	public static final String TAG = Level.class.getName();

	public enum LetterInGame {
		h, i, t, c, u, r, l
	};

	public Rectangle BoundsOfGame;
	public Stage stage;
	Letter[] letters = new Letter[7];
	CubesGroup[] lettres = new CubesGroup[7];
	private Grille grille;
	public int level;
	private Vector2 posGrille;
	public boolean isComplete;

	public Level(Stage stage, int level) {
		this.stage = stage;
		this.level = level;
		init(level);

	}

	// Initialise à partir d'un fichier la grille de jeu
	private void init(int level) {
		// Création de la grille
		grille = new Grille(level);
		stage.addActor(grille);

		// ScreenFactor = 1.5f * wordController.screenFactor;
		grille.setTransform(true);
		grille.setPosition(stage.getWidth() / 2 - grille.getMinWidth() / 2f,
				stage.getHeight() / 2 - grille.getMinHeight() / 2f);
		grille.pack();
		posGrille = new Vector2(grille.getX(), grille.getY());
		Gdx.app.debug(TAG, "posion grille=" + posGrille);

		// Création des lettres en utilisant des cubes de couleurs Class
		// CubesGroups
		lettres[0] = new CubesGroup("h");
		lettres[1] = new CubesGroup("i");
		lettres[2] = new CubesGroup("t");
		lettres[3] = new CubesGroup("c");
		lettres[4] = new CubesGroup("u");
		lettres[5] = new CubesGroup("r");
		lettres[6] = new CubesGroup("l");
		for (CubesGroup l : lettres) {
			l.setTransform(true);
			l.getColor().a = 0.8f;
			stage.addActor(l);
		}

		float milieuGrilleX = (grille.getX() + grille.getX() + grille.getWidth()) / 2;

		float posYGrilleHaute = grille.getY() + grille.getHeight();
		lettres[0].setInitPos(new Vector2(milieuGrilleX - Constants.SIZE_CUBE * 3 - lettres[0].getOriginX(), posYGrilleHaute));
		lettres[0].setPosition(lettres[0].getInitPos().x, lettres[0].getInitPos().y);
		lettres[1].setInitPos(new Vector2(milieuGrilleX - Constants.SIZE_CUBE * 1 - lettres[1].getOriginX(), posYGrilleHaute));
		lettres[1].setPosition(lettres[1].getInitPos().x, lettres[1].getInitPos().y);
		lettres[2].setInitPos(new Vector2(milieuGrilleX + Constants.SIZE_CUBE - lettres[2].getOriginX(), posYGrilleHaute));
		lettres[2].setPosition(lettres[2].getInitPos().x, lettres[2].getInitPos().y);
		lettres[3].setInitPos(new Vector2(milieuGrilleX + Constants.SIZE_CUBE * 3.5f - lettres[3].getOriginX(), posYGrilleHaute));
		lettres[3].setPosition(lettres[3].getInitPos().x, lettres[3].getInitPos().y);

		float posYGrilleBasse = grille.getY() - Constants.SIZE_CUBE * 3;
		lettres[4].setInitPos(new Vector2(milieuGrilleX - Constants.SIZE_CUBE * 4f, posYGrilleBasse));
		lettres[4].setPosition(lettres[4].getInitPos().x, lettres[4].getInitPos().y);
		lettres[5].setInitPos(new Vector2(milieuGrilleX, posYGrilleBasse));
		lettres[5].setPosition(lettres[5].getInitPos().x, lettres[5].getInitPos().y);
		lettres[6].setInitPos(new Vector2(milieuGrilleX + Constants.SIZE_CUBE * 3, posYGrilleBasse));
		lettres[6].setPosition(lettres[6].getInitPos().x, lettres[6].getInitPos().y);

	}

	public void update(float deltaTime) {
		for (CubesGroup l : lettres) {
			if (l.isClicked()) {
				l.setClicked(false);
				float nbPixelCube = Constants.SIZE_CUBE;
				float deltaMiddleX = l.getOriginX() * l.getScaleX() - l.getOriginX();
				float deltaMiddleY = l.getOriginY() * l.getScaleY() - l.getOriginY();
				if (l.getX() > posGrille.x + deltaMiddleX - nbPixelCube / 2
						&& l.getY() > posGrille.y + deltaMiddleY - nbPixelCube / 2
						&& l.getX() < (posGrille.x + (grille.getWidth() - l.getWidth()) + nbPixelCube * 0.5f)
						&& l.getY() < (posGrille.y + (grille.getHeight() - l.getHeight()) + nbPixelCube * 0.5f)) {

					int nb_blocsx = (int) ((l.getX() - posGrille.x - deltaMiddleX + nbPixelCube / 2) / nbPixelCube);
					int nb_blocsy = (int) ((l.getY() - posGrille.y - deltaMiddleY + nbPixelCube / 2) / nbPixelCube);

					l.setPosition(posGrille.x + deltaMiddleX + nb_blocsx * nbPixelCube, posGrille.y + deltaMiddleY + nb_blocsy * nbPixelCube);

					l.setInGrille(true);

					Gdx.app.debug(TAG, "delta=" + new Vector2(deltaMiddleX, deltaMiddleY)
							+ "\n  nb_bloc =\t" + new Vector2(nb_blocsx, nb_blocsy)
							+ "\n  nbPixelCube =\t" + nbPixelCube
							+ "\n  PosGrille =\t" + posGrille
							+ "\n  LetterPosXY =\t" + new Vector2(l.getX(), l.getY())
							+ "\n  LetterWidth =\t" + new Vector2(l.getWidth(), l.getHeight()));

					isComplete = IsComplete();
				} else
					l.setInGrille(false);
			}
		}
	}

	public boolean IsComplete() {
		Actor act = new Actor();
		int nb = 0;
		boolean complete = true;
		for (Actor actor : grille.getChildren()) {
			act = grille.getStage().hit((actor.getX() + 1) + posGrille.x, (1 + actor.getY()) + posGrille.y, true);

			if (act.getName() != null) {
				if (!act.getName().endsWith(grille.getVal()[nb]) || (!act.getName().equals("*") && act.getName().equals(grille.getVal()[nb]))) {
					complete = false;
					break;
				}
				Gdx.app.debug(TAG, "hit1 " + new Vector2(actor.getX(), actor.getY()) + " GrilleInStage(" + nb + ")=\t" + act
						+ " =>> Grille val= " + grille.getVal()[nb] + "\tGrille Complete? " + complete);

				if (grille.getVal()[nb].equals("*") && !act.getName().endsWith("*"))
					act.addAction(
							sequence(scaleBy(0.1f, 0.1f, 0.5f),
									scaleBy(-0.1f, -0.1f, 0.5f)));
			}
			nb++;
		}
		Gdx.app.debug(TAG, "Grille Complete? " + complete);
		return complete;
	}

	public void SavePosition(int niveau) {
		Preferences prefs = Gdx.app.getPreferences("LEVEL" + niveau);

		for (CubesGroup l : lettres) {
			prefs.putFloat(l.getName() + "x", l.getX());
			prefs.putFloat(l.getName() + "y", l.getY());
			prefs.putFloat(l.getName() + "rot", l.getRotation());

		}
		prefs.putBoolean("isComplete", isComplete);
		prefs.flush();
	}

	public void RestorePosition(int niveau) {
		Preferences prefs = Gdx.app.getPreferences("LEVEL" + niveau);

		for (CubesGroup l : lettres) {
			float pos = 0.0f;
			pos = prefs.getFloat(l.getName() + "x", 0);
			if (pos != 0.0f)
				l.setX(pos);
			pos = prefs.getFloat(l.getName() + "y", 0);
			if (pos != 0f)
				l.setY(pos);
			l.setRotation(prefs.getFloat(l.getName() + "rot", 0));
		}
		isComplete = prefs.getBoolean("isComplete", false);
	}

}
