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
	/*private void initTestObjects () {
		// New array for 5 sprites
		testSprites = new Sprite[5];
		
		// Create list of texture regions
		Array<TextureRegion> regions = new Array<TextureRegion>();
		regions.add(Assets.instance.bunny.head);
		regions.add(Assets.instance.feather.feather);
		regions.add(Assets.instance.goldCoin.goldCoin);
		//regions.add(Assets.instance.rock.middle);
		//regions.add(Assets.instance.rock.edge);
		//regions.add(Assets.instance.levelDecoration.cloud01);
		/*
		// New pixmap
		int width = 32;
		int height = 32;
		Pixmap pixmap = createProceduralPixmap(width, height);
		
		// New texture from pixmap data
		Texture texture = new Texture(pixmap);
		
		// Fill sprites array with newly created texture
		for(int i=0; i<testSprites.length; i++) {
			Sprite spr = new Sprite(texture);
			Sprite spr = new Sprite(regions.random());
			
			// Define sprite's width and height to 1 x 1 game world units
			spr.setSize(1, 1);
			// Set sprite's origin to its center
			spr.setOrigin(spr.getWidth()/2, spr.getHeight()/2);
			
			// Get random position for sprite
			float randX = MathUtils.random(-2f, 2f);
			float randY = MathUtils.random(-2f, 2f);
			spr.setPosition(randX, randY);
			
			// Put new sprite in array
			testSprites[i] = spr;
		}
		
		// Set first created sprite as first selected one
		selectedSprite = 0;
	}*/
	
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
		//updateTestObjects(deltaTime);
		cameraHelper.update(deltaTime);
	}
	
	private void handleDebugInput(float deltaTime) {
		if(Gdx.app.getType() != ApplicationType.Desktop) return;
		
		
		/*
		// Move selected sprite
		float sprMoveSpeed = 5 * deltaTime;
		if(Gdx.input.isKeyPressed(Keys.Q)) moveSelectedSprite(- sprMoveSpeed, 0f );
		if(Gdx.input.isKeyPressed(Keys.D)) moveSelectedSprite( sprMoveSpeed, 0f );
		if(Gdx.input.isKeyPressed(Keys.S)) moveSelectedSprite( 0f, - sprMoveSpeed );
		if(Gdx.input.isKeyPressed(Keys.Z)) moveSelectedSprite( 0f, sprMoveSpeed) ;
		*/
		
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
/*
	private void moveSelectedSprite(float xMovement, float yMovement) {
		testSprites[selectedSprite].translate(xMovement, yMovement);
	}
*/
	/*
	// Spin the selected sprite around its origin
	private void updateTestObjects (float deltaTime) {
		// Get selected sprite's current rotation
		float rot = testSprites[selectedSprite].getRotation();
		
		// Rotate sprite by 90 degrees per sec
		rot += 45 * deltaTime;
		rot %= 360;
		
		testSprites[selectedSprite].setRotation(rot);
	}*/
	
	@Override 
	public boolean keyUp (int keycode) {
		// Reset world
		if(keycode == Keys.R) {
			init();
		}
		/*
		// Select next sprite, update camera's target
		else if(keycode == Keys.SPACE) {
			selectedSprite = (selectedSprite + 1) % testSprites.length;
			if(cameraHelper.hasTarget()) {
				cameraHelper.setTarget(testSprites[selectedSprite]);
			}
		}
		
		// Toggle camera follow
		else if(keycode == Keys.ENTER) {
			cameraHelper.setTarget( cameraHelper.hasTarget() ? null : testSprites[selectedSprite]);
		}*/
		return false;
	}
}
