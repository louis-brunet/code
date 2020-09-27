package com.mygdx.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.actor.Ground;
import com.mygdx.game.actor.Player;
import com.mygdx.game.enums.ViewDirection;
import com.mygdx.game.utils.CollisionListener;
import com.mygdx.game.utils.Constants;
import com.mygdx.game.utils.BodyFactory;

public class GameStage extends Stage {
    private static final float TIME_STEP = 1f / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    private World world;
    private Player player;
    private float physicsDelta;

    private MyGdxGame game;
    private Box2DDebugRenderer debugRenderer;

    public GameStage (MyGdxGame game) {
        super (new FitViewport(
                Constants.WORLD_WIDTH,
                Constants.WORLD_HEIGHT,
                new OrthographicCamera(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT))
        );

        // set up camera
        this.initCamera();
        // set up physics world and actors
        this.initWorld();
        // set up menu, add UI actors
        this.initUI();

        this.game = game;
        this.debugRenderer = new Box2DDebugRenderer();

        Gdx.input.setInputProcessor(this); // TODO pause on ESC press
    }

    private void initCamera() {
        // TODO
    }

    private void initWorld() {
        this.world = BodyFactory.createWorld();

        // load actors (player, platforms, etc.)
        this.initActors();

        this.world.setContactListener(new CollisionListener(this.player));
    }

    private void initUI() {
        // TODO
    }

    private void initActors() {
        // TODO
        this.initPlayer();
        // this.initEnemies();
        this.initPlatforms();
    }

    private void initPlayer() {
        this.player = new Player(BodyFactory.createPlayer(this.world));
        this.addActor(this.player);
    }

    private void initPlatforms() {
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X - 8, Constants.PLAYER_START_Y - 4))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X - 8, Constants.PLAYER_START_Y - 5))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X - 8, Constants.PLAYER_START_Y - 6))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X - 8, Constants.PLAYER_START_Y - 7))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X - 7, Constants.PLAYER_START_Y - 8))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X - 6, Constants.PLAYER_START_Y - 8))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X - 5, Constants.PLAYER_START_Y - 8))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X - 4, Constants.PLAYER_START_Y - 8))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X - 3, Constants.PLAYER_START_Y - 8))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X - 2, Constants.PLAYER_START_Y - 8))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X - 1, Constants.PLAYER_START_Y - 8))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X, Constants.PLAYER_START_Y - 8))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X + 1, Constants.PLAYER_START_Y - 8))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X + 2, Constants.PLAYER_START_Y - 8))));
        this.addActor(new Ground(BodyFactory.createGround(this.world, new Vector2(Constants.PLAYER_START_X + 2, Constants.PLAYER_START_Y - 7))));
    }

    private void stepWorld(float delta) {
        this.physicsDelta += Math.min(delta, 0.25f);

        if (this.physicsDelta >= GameStage.TIME_STEP) {
            this.physicsDelta -= GameStage.TIME_STEP;
            this.world.step(
                    GameStage.TIME_STEP,
                    GameStage.VELOCITY_ITERATIONS,
                    GameStage.POSITION_ITERATIONS);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // TODO if game is paused, return
        this.handleMovementInput(delta);
        this.stepWorld(delta);
    }

    @Override
    public void draw() {
        super.draw();

        this.game.batch.begin();
        this.debugRenderer.render(this.world, this.getCamera().combined);
        this.game.batch.end();
    }

    private void handleMovementInput(float delta) {
        // TODO
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.player.jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            this.player.move(ViewDirection.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.player.move(ViewDirection.RIGHT);
        }
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            this.player.crouch();
//        }
    }
}
