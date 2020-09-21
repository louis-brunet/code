package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.stage.MainMenuStage;
import com.mygdx.game.utils.Constants;

public class MainMenuScreen implements Screen {
    private MyGdxGame game;
    private MainMenuStage stage;

    public MainMenuScreen(final MyGdxGame game) {
        super ();

        this.game = game;
        this.stage = new MainMenuStage();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.draw();
        this.stage.act(delta);

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(this.game));
            this.dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
//        this.stage.getViewport().update(width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void show() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
