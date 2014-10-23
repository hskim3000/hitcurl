package com.python4d.hitcurl.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Grille extends Table {

	private static final String TAG = Grille.class.getName();
	private static final int nbCubesTotal = 36;
	private String[] val = new String[64];
	private Random rand = new Random();
	private int n;
	private int nbClue;
	ArrayList<Integer> liste = new ArrayList<Integer>();

	public Grille(int n) {
		this(n, 0);
	}

	public Grille(int n, int nbClue) {
		super();
		this.n = n;
		this.nbClue = nbClue;
		liste = RestoreClueList();
		this.setSkin(Assets.instance.getSkin());
		this.setFillParent(false);
		this.left().bottom();
		if (Constants.DEBUG)
			this.debugTable();

		int indexBlanc = 0;
		int indexList = 0;
		Image img;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				this.getVal()[i * 8 + j] = WorldController.levels.get(n)[i].substring(j, j + 1);
				if (this.getVal()[i * 8 + j].equals(new String("*"))) {
					img = new Image(Assets.instance.cubes.noirAtlas);
					img.getColor().a = 0;
					img.setName(WorldController.levels.get(n)[i].substring(j, j + 1));
					this.add(img).minSize(Constants.SIZE_CUBE);
				}
				else {
					if (indexList < liste.size() && liste.get(indexList) == indexBlanc) {
						indexList++;
						img = new Image(Assets.instance.getSkin(), Constants.CouleurDuCube.get(WorldController.levels.get(n)[i].substring(j, j + 1))[0]);
						img.setName(WorldController.levels.get(n)[i].substring(j, j + 1).toUpperCase());
					}
					else {
						img = new Image(Assets.instance.cubes.blancAtlas);
						img.setName(WorldController.levels.get(n)[i].substring(j, j + 1));
					}
					indexBlanc++;
					this.add(img).minSize(Constants.SIZE_CUBE);
				}
			}
			this.row();
		}

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

	public void ShowSolution(boolean b) {

		for (Actor cell : getChildren()) {
			String name = new String(cell.getName());
			if (!name.equals("*"))
				if (b)
					((Image) cell).setDrawable(Assets.instance.getSkin(), Constants.CouleurDuCube.get(name.toLowerCase())[0]);
				else if (Character.isUpperCase(name.charAt(0)))
					((Image) cell).setDrawable(Assets.instance.getSkin(), Constants.CouleurDuCube.get(name.toLowerCase())[0]);
				else
					((Image) cell).setDrawable(Assets.instance.getSkin(), "cubeblanc");
			else
				((Image) cell).setDrawable(Assets.instance.getSkin(), "cubenoir");

		}

	}

	private ArrayList<Integer> RestoreClueList() {
		Preferences prefs = Gdx.app.getPreferences("GRILLE" + n + "NBCLUE" + nbClue);
		ArrayList<Integer> liste = new ArrayList<Integer>();
		boolean b = prefs.getBoolean("AlreadyComputed", false);
		if (b) {
			int nbClue = prefs.getInteger("nbClue", 0);
			for (int i = 0; i < nbClue; i++)
				liste.add(i, prefs.getInteger("clue" + i));

		} else
		{
			liste = CreateClueList(nbClue);
			SaveClueList(liste);
		}
		return liste;

	}

	private void SaveClueList(ArrayList<Integer> liste) {
		Preferences prefs = Gdx.app.getPreferences("GRILLE" + n + "NBCLUE" + nbClue);
		prefs.putInteger("nbClue", nbClue);
		for (int i = 0; i < nbClue; i++)
			prefs.putInteger("clue" + i, liste.get(i));
		prefs.putBoolean("AlreadyComputed", true);
		prefs.flush();
		Gdx.app.debug(TAG, "SaveClueList !");

	}

	private ArrayList<Integer> CreateClueList(int nb) {
		ArrayList<Integer> liste = new ArrayList<Integer>();

		if (nb != 0) {
			if (nb > nbCubesTotal)
				nb = nbCubesTotal;
			for (int i = 0; i < nbCubesTotal; i++)
				liste.add(i);
			for (int i = 0; i < nbCubesTotal - nb; i++)
				liste.remove(rand.nextInt(nbCubesTotal - i));
		}
		return liste;
	}
}
