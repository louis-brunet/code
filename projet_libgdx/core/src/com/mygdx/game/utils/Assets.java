package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;

public class Assets {
    private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
    private static BitmapFont largeFont;
    private static BitmapFont smallFont;

    public static void loadAssets() {

        Assets.textures.put(Constants.PLAYER_JUMPING_ASSET_ID, new Texture(
                Gdx.files.internal(Constants.PLAYER_JUMPING_ASSET_PATH)));
        Assets.textures.put(Constants.PLAYER_IDLE_ASSET_ID, new Texture(
                Gdx.files.internal(Constants.PLAYER_IDLE_ASSET_PATH)));
        Assets.textures.put(Constants.GROUND_ASSET_ID, new Texture(
                Gdx.files.internal(Constants.GROUND_ASSET_PATH)));

        // TODO create fonts..
        Assets.largeFont = new BitmapFont();
        Assets.smallFont = new BitmapFont();
    }

    public static Texture getTexture(String name) {
        return Assets.textures.get(name);
    }

    public static BitmapFont getLargeFont() {
        return largeFont;
    }

    public static BitmapFont getSmallFont() {
        return smallFont;
    }

    public static void dispose() {
        Assets.textures.clear();
        Assets.smallFont.dispose();
        Assets.largeFont.dispose();
    }
}
