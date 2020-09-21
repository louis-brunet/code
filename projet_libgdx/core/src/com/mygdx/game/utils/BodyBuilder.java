package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.userdata.GroundUserData;
import com.mygdx.game.userdata.PlayerUserData;

public class BodyBuilder {

    public static World createWorld() {
        return new World(Constants.WORLD_GRAVITY, true);
    }

    public static Body createPlayer(World world) {
        // create BodyDef, Body, Fixture, UserData
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(
                Constants.PLAYER_START_X, Constants.PLAYER_START_Y));
        bodyDef.fixedRotation = true;
        bodyDef.gravityScale = Constants.PLAYER_GRAVITY_SCALE;
        bodyDef.linearDamping = 0f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.PLAYER_WIDTH / 2,
                Constants.PLAYER_HEIGHT / 2);

        Body body = world.createBody(bodyDef);
        body.createFixture(shape, Constants.PLAYER_DENSITY);
        body.resetMassData();
        body.setUserData(new PlayerUserData(
                Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT));


        shape.dispose();
        return body;
    }

    public static Body createGround(World world, Vector2 position) {
        // create BodyDef, Body, Fixture, UserData
        BodyDef bodyDef = new BodyDef();
        // TODO change constants
        bodyDef.position.set( position );
        bodyDef.type = BodyDef.BodyType.StaticBody;
//        bodyDef.linearDamping = 0;

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.GROUND_WIDTH / 2,Constants.GROUND_HEIGHT / 2);
        body.createFixture(shape, Constants.GROUND_DENSITY);
        body.setUserData(new GroundUserData(Constants.GROUND_WIDTH, Constants.GROUND_HEIGHT));
        shape.dispose();
        return body;
    }
}
