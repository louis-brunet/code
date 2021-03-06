package com.sidescroller.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.sidescroller.util.CameraHelper;
import com.sidescroller.util.Constants;

public class WorldController extends InputAdapter {
	public static final String TAG = WorldController.class.getName();
	
	//public Sprite[] testSprites;
	//public int selectedSprite;
	
	public CameraHelper cameraHelper;
	public Level level;
	public int lives;
	public int score;
	
	public WorldController() {
		init();
	}
	
	private void init () {
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		
		lives = Constants.LIVES_START;
		initLevel();
		
		//initTestObjects();
	}
	
	private void initLevel () {
		score = 0;
		level = new Level(Constants.LEVEL_01);
	}
	
	
	private Pixmap createProceduralPixmap(int width, int height) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		// red half-transparent background
		pixmap.setColor(1, 0, 0, 0.5f);
		pixmap.fill();
		
		// yellow diagonals
		pixmap.setColor(Color.BLACK);
		pixmap.drawLine(0, 0, width, height);
		pixmap.drawLine(width, 0, 0, height);
		
		// gold outline
		pixmap.setColor(Color.GOLD);
		pixmap.drawRectangle(0, 0, width, height);
		
		return pixmap;
	}
	
	public void update (float deltaTime) {
		handleDebugInput(deltaTime);
		level.update(deltaTime);
		cameraHelper.update(deltaTime);
	}
	
	private void handleDebugInput(float deltaTime) {
		if(Gdx.app.getType() != ApplicationType.Desktop) return;
		
		
		
		// Control camera mvmnt
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if(Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
		if(Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0, -camMoveSpeed);
		if(Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
		if(Gdx.input.isKeyPressed(Keys.BACKSPACE)) cameraHelper.setPosition(0, 0);
		
		//Control camera zoom
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if(Gdx.input.isKeyPressed(Keys.COMMA)) cameraHelper.addZoom(camZoomSpeed);
		if(Gdx.input.isKeyPressed(Keys.N)) cameraHelper.addZoom(-camZoomSpeed);
		
	}

	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		
		cameraHelper.setPosition(x,  y);
	}
	
	@Override 
	public boolean keyUp (int keycode) {
		// Reset world
		if(keycode == Keys.R) {
			init();
		}
		return false;
	}
}
