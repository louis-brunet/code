package com.sidescroller.util;

public class Constants {
	// Visible game world is 5 meters wide    
	public static final float VIEWPORT_WIDTH = 5.0f;    
	// Visible game world is 5 meters tall    
	public static final float VIEWPORT_HEIGHT = 5.0f;
	// GUI Width
	public static final float VIEWPORT_GUI_WIDTH = 800f;
	// GUI Height
	public static final float VIEWPORT_GUI_HEIGHT = 480f;
	
	// Location of description file for texture atlas
	public static final String TEXTURE_ATLAS_OBJECTS = "images/textures.txt";
	
	// Location of first level img file
	public static final String LEVEL_01 = "levels/level01.png";
	
	// Amount of extra lives at start of level
	public static final int LIVES_START = 3;
	
	// Score value for gold coin
	public static final int GOLD_COIN_SCORE = 100;
	
	// Score value for feather
	public static final int FEATHER_SCORE = 250;
	
	// Duration of feather power-up in seconds
	public static final float ITEM_FEATHER_POWERUP_DURATION = 9.0f;
}
