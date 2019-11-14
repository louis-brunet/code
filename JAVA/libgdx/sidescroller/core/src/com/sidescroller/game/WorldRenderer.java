package com.sidescroller.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.sidescroller.util.Constants;

public class WorldRenderer implements Disposable {
	public static final String TAG = WorldRenderer.class.getName();
	
	private WorldController worldController;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private OrthographicCamera cameraGUI;
	
	
	public WorldRenderer(WorldController controller) {
		this.worldController = controller;
		init();
	}
	
	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true); // flip y-axis
		camera.update();
	}
	
	public void resize (int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();

		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float) height) * (float) width;
		
		cameraGUI.position.set(Constants.VIEWPORT_GUI_WIDTH / 2, Constants.VIEWPORT_GUI_HEIGHT /2, 0);
		cameraGUI.update();
		
	}
	
	public void render() {
		renderWorld(batch);
		renderGui(batch);
	}
	
	private void renderWorld (SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		worldController.level.render(batch);
		batch.end();
	}
	
	private void renderGui (SpriteBatch batch) {
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();
		
		// draw collected coins icon + text (top left)
		renderGuiScore(batch);
		// draw extra lives icon + text (top right)
		renderGuiExtraLives(batch);
		// draw FPS text (bottom right)
		renderGuiFpsCounter(batch);
		
		batch.end();
	}
	
	private void renderGuiScore (SpriteBatch batch) {
		float x = -15.0f;
		float y = -15.0f;
		
		batch.draw(Assets.instance.goldCoin.goldCoin,
				x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
		Assets.instance.fonts.defaultBig.draw(batch, "" + worldController.score, x + 75, y + 37);
	}
	
	private void renderGuiExtraLives (SpriteBatch batch) {
		float x = cameraGUI.viewportWidth - 50 - Constants.LIVES_START * 50;
		float y = -15;
		
		for(int i = 0; i < Constants.LIVES_START; i++) {
			if(worldController.lives < i)
				batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			batch.draw(Assets.instance.bunny.head,
					x + i*50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
		}
	}
	
	private void renderGuiFpsCounter (SpriteBatch batch) {
		float x = cameraGUI.viewportWidth - 55;
		float y = cameraGUI.viewportHeight - 15;
		
		int fps = Gdx.graphics.getFramesPerSecond();
		BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
		
		if (fps >= 45) {
			fpsFont.setColor(0, 1, 0, 1); // green if 45+ fps
		}else if (fps >= 30) {
			fpsFont.setColor(1, 1, 0, 1); // yellow if 30+ fps
		}else {
			fpsFont.setColor(1, 0, 0, 1); // red if under 30 fps
		}
		
		fpsFont.draw(batch, "FPS : " + fps, x, y);
		fpsFont.setColor(1, 1, 1, 1);
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}

}
