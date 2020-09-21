package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screen.MainMenuScreen;
import com.mygdx.game.utils.Assets;

public class MyGdxGame extends Game {
	// à enlever ?
	public SpriteBatch batch;
	public BitmapFont font;
//	 Texture img;
	
	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.font = new BitmapFont();
//		 img = new Texture("elf_m_idle_anim_f0.png");
		Assets.loadAssets();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		Assets.dispose();
//		batch.dispose();
		// img.dispose();
	}
}
