package com.simple2d.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.simple2d.game.Simple2DGame;
import com.simple2d.game.Scenes.Hud;

public class PlayScreen implements Screen {
	private Simple2DGame game;
	private OrthographicCamera gameCam;
	private Viewport gameViewport;
	private Hud hud;
	
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	public PlayScreen(Simple2DGame game) {
		this.game = game;
		gameCam = new OrthographicCamera();
		gameViewport = new ScreenViewport(gameCam);

		gameCam.position.set(Simple2DGame.V_WIDTH/2, Simple2DGame.V_HEIGHT/2, 0);
		//gameViewport = new StretchViewport(Simple2DGame.V_WIDTH, Simple2DGame.V_HEIGHT, gameCam);
		//gameViewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hud = new Hud(game.batch);
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("level1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		
		gameCam.position.set(gameViewport.getWorldWidth()/2, gameViewport.getWorldWidth()/2, 0);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	
	public void handleInput(float dt) {
		if(Gdx.input.isTouched()) {
			gameCam.position.x += 100 * dt;
		}
	}
	
	public void update(float dt) {
		handleInput(dt);
		
		gameCam.update();
		renderer.setView(gameCam);
	}

	@Override
	public void render(float delta) {
		update(delta);
		
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
		
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		gameViewport.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
