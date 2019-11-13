package com.sidescroller.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

public class SideScrollerMain implements ApplicationListener {
	public static final String TAG = SideScrollerMain.class.getName();
	
	private WorldRenderer worldRenderer;
	private WorldController worldController;
	
	boolean paused;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		// Load assets
		Assets.instance.init(new AssetManager());
		
		
		// Init controller and renderer
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		
		paused = false;
	}

	@Override
	public void render () {
		// Update game world
		if(!paused) 
			worldController.update(Gdx.graphics.getDeltaTime());
		
		// Clear screen light blue
		Gdx.gl.glClearColor(190f/255f, 250f/255f, 238f/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Render game world
		worldRenderer.render();
	}
	
	@Override
	public void dispose () {
		worldRenderer.dispose();
		Assets.instance.dispose();
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		Assets.instance.init(new AssetManager());
		paused = false;
	}
}
