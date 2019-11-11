package com.simple2d.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.simple2d.game.Screens.PlayScreen;

public class Simple2DGame extends Game {
	public static final int V_WIDTH = 16 * 20;
	public static final int V_HEIGHT = 16 * 15;
	
	public SpriteBatch batch; // batch for all screens to use
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen( new PlayScreen(this) );
	}

	@Override
	public void render () {
		super.render();
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
