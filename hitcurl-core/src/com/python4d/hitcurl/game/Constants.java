package com.python4d.hitcurl.game;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

public class Constants {
	public static final boolean DEBUG = false;
	public static final int TEMPO_TOUCHE = 10;
	// Visible game world
	public static final float VIEWPORT_WIDTH = 600.0f;
	// Visible game world
	public static final float VIEWPORT_HEIGHT = 800.0f;

	// GUI Width
	public static final float VIEWPORT_HUD_WIDTH = VIEWPORT_WIDTH;
	// GUI Height
	public static final float VIEWPORT_HUD_HEIGHT = VIEWPORT_HEIGHT;

	// Location of description file for texture atlas
	public static final String TEXTURE_ATLAS_OBJECTS = "images/hitcurl.atlas";

	// Location of description file JSON for skin
	public static final String SKIN_OBJECTS = "images/hitcurl.json";
	public static final String LEVEL_01 = "";
	public static final float SIZE_CUBE = 24.0f;
	public static final int NB_NIVEAU = 3;
	protected static final float AD_BANNER_HEIGHT = 50.0f;

	public static enum ColorCube {
		CubeJaune
	}

	public static HashMap<Integer, String[]> levels = new HashMap<Integer, String[]>();
	static
	{
		levels.put(0, new String[] {
				"*h*h*i**",
				"*hhh*i**",
				"*h*h*i**",
				"*tttcc**",
				"*utuc*rr",
				"lutuccr*",
				"luuu**r*",
				"lll*****" });
		levels.put(2, new String[] {
				"**lll***",
				"*ilt*hhh",
				"*ilttth*",
				"*ictchhh",
				"**cccuuu",
				"****rr*u",
				"****ruuu",
				"****r***" });

		levels.put(1, new String[] {
				"*h*huuu*",
				"*hhhu*u*",
				"*hthuru*",
				"l*tccrrr",
				"ltttc***",
				"lllcc***",
				"*iii****",
				"********" });
	}

	public static HashMap<String, Object[][]> lettres = new HashMap<String, Object[][]>();
	static
	{
		lettres.put("h", new Object[][] {
				{ new Vector2(0, 0), Assets.instance.cubes.jauneAtlas },
				{ new Vector2(2, 0), Assets.instance.cubes.jauneAtlas },
				{ new Vector2(0, 1), Assets.instance.cubes.jauneAtlas },
				{ new Vector2(1, 1), Assets.instance.cubes.jauneAtlas },
				{ new Vector2(2, 1), Assets.instance.cubes.jauneAtlas },
				{ new Vector2(0, 2), Assets.instance.cubes.jauneAtlas },
				{ new Vector2(2, 2), Assets.instance.cubes.jauneAtlas } });
		lettres.put("i", new Object[][] {
				{ new Vector2(0, 0), Assets.instance.cubes.vertAtlas },
				{ new Vector2(0, 1), Assets.instance.cubes.vertAtlas },
				{ new Vector2(0, 2), Assets.instance.cubes.vertAtlas } });
		lettres.put("t", new Object[][] {
				{ new Vector2(1, 0), Assets.instance.cubes.bleuAtlas },
				{ new Vector2(1, 1), Assets.instance.cubes.bleuAtlas },
				{ new Vector2(0, 2), Assets.instance.cubes.bleuAtlas },
				{ new Vector2(1, 2), Assets.instance.cubes.bleuAtlas },
				{ new Vector2(2, 2), Assets.instance.cubes.bleuAtlas } });
		lettres.put("c", new Object[][] {
				{ new Vector2(0, 0), Assets.instance.cubes.roseAtlas },
				{ new Vector2(1, 0), Assets.instance.cubes.roseAtlas },
				{ new Vector2(0, 1), Assets.instance.cubes.roseAtlas },
				{ new Vector2(0, 2), Assets.instance.cubes.roseAtlas },
				{ new Vector2(1, 2), Assets.instance.cubes.roseAtlas } });
		lettres.put("u", new Object[][] {
				{ new Vector2(0, 0), Assets.instance.cubes.rougeAtlas },
				{ new Vector2(1, 0), Assets.instance.cubes.rougeAtlas },
				{ new Vector2(2, 0), Assets.instance.cubes.rougeAtlas },
				{ new Vector2(0, 1), Assets.instance.cubes.rougeAtlas },
				{ new Vector2(2, 1), Assets.instance.cubes.rougeAtlas },
				{ new Vector2(0, 2), Assets.instance.cubes.rougeAtlas },
				{ new Vector2(2, 2), Assets.instance.cubes.rougeAtlas }, });
		lettres.put("r", new Object[][] {
				{ new Vector2(0, 0), Assets.instance.cubes.grisAtlas },
				{ new Vector2(0, 1), Assets.instance.cubes.grisAtlas },
				{ new Vector2(0, 2), Assets.instance.cubes.grisAtlas },
				{ new Vector2(1, 2), Assets.instance.cubes.grisAtlas } });
		lettres.put("l", new Object[][] {
				{ new Vector2(0, 0), Assets.instance.cubes.cielAtlas },
				{ new Vector2(0, 1), Assets.instance.cubes.cielAtlas },
				{ new Vector2(0, 2), Assets.instance.cubes.cielAtlas },
				{ new Vector2(1, 0), Assets.instance.cubes.cielAtlas },
				{ new Vector2(2, 0), Assets.instance.cubes.cielAtlas } });
	}

}
