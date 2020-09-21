package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {
    public static final int APP_WIDTH = 960;
    public static final int APP_HEIGHT = 720;

    public static final float WORLD_TO_SCREEN = 20f;
    public static final float WORLD_WIDTH = Constants.APP_WIDTH / Constants.WORLD_TO_SCREEN;
    public static final float WORLD_HEIGHT = Constants.APP_HEIGHT / Constants.WORLD_TO_SCREEN;
    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    public static final String PLAYER_JUMPING_ASSET_ID = "player_jumping";
    public static final String PLAYER_IDLE_ASSET_ID = "player_idle";
    public static final String GROUND_ASSET_ID = "ground";

    // image paths
    public static final String PLAYER_JUMPING_ASSET_PATH = "player_jumping.png";
    public static final String PLAYER_IDLE_ASSET_PATH = "player_idle.png";
    public static final String GROUND_ASSET_PATH = "ground.png";

    public static final float PLAYER_START_X = 24f;
    public static final float PLAYER_START_Y = 18f;
    public static final float PLAYER_WIDTH = 2f;
    public static final float PLAYER_HEIGHT = 3.5f;
    public static final float PLAYER_GRAVITY_SCALE = 1f;
    public static final float PLAYER_DENSITY = 1f;
    public static final Vector2 PLAYER_JUMP_IMPULSE = new Vector2(0, 60);
    public static final Vector2 PLAYER_MOVE_IMPULSE = new Vector2(10, 0);
    public static final float PLAYER_MAX_VELOCITY_X = 5f;

    public static final float GROUND_WIDTH = 1f;
    public static final float GROUND_HEIGHT = 1f;
    public static final float GROUND_DENSITY = 0f;

}
