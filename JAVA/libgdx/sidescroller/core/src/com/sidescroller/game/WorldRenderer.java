package com.sidescroller.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.sidescroller.util.Constants;

public class WorldRenderer implements Disposable {
	public static final String TAG = WorldRenderer.class.getName();
	
	private WorldController worldController;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	
	public WorldRenderer(WorldController controller) {
		this.worldController = controller;
		init();
	}
	
	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
	}
	
	
	public void render() {
		//renderTestObjects();
		renderWorld(batch);
	}
	/*
	private void renderTestObjects() {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		for(Sprite spr: worldController.testSprites) {
			spr.draw(batch);
		}
		batch.end();
	}
	*/
	private void renderWorld (SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		worldController.level.render(batch);
		batch.end();
	}
	public void resize (int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}

}
