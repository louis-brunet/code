package com.mygdx.game.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.actor.Player;
import com.mygdx.game.enums.UserDataType;

public class CollisionListener implements ContactListener {
    private Player player;

    public CollisionListener (Player p) {
        this.player = p;
    }

    @Override
    public void beginContact(Contact contact) {
//        System.out.println("Contact between " +
//                ((UserData) contact.getFixtureA().getBody().getUserData()).getUserDataType().name() +
//                " and " + ((UserData) contact.getFixtureB().getBody().getUserData()).getUserDataType().name()
//        );
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (UserDataType.bodyIsPlayer(a) && UserDataType.bodyIsGround(b)) {
            this.player.groundHit(b);
        } else if (UserDataType.bodyIsPlayer(b) && UserDataType.bodyIsGround(a)) {
            this.player.groundHit(a);
        }
        /*if bodies are player && lava
         *    take damage
         *...
         */
    }

    @Override
    public void endContact(Contact contact) {
    }

    // ??
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    // ??
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
