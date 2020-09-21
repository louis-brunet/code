package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.stage.GameStage;

public class GameScreen implements Screen {
    private MyGdxGame game;
    private GameStage stage;


    public GameScreen(MyGdxGame game) {
        this.game = game;

        this.stage = new GameStage(game);
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        // clear screen with black
        Gdx.gl.glClearColor(0,0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.draw();

        this.stage.act(delta);
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
