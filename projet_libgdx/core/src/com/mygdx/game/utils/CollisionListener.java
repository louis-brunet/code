package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.actor.Player;
import com.mygdx.game.enums.UserDataType;
import com.mygdx.game.userdata.UserData;

public class CollisionListener implements ContactListener {
    private Player player;

    public CollisionListener (Player p) {
        this.player = p;
    }

    // TODO optimize
    @Override
    public void beginContact(Contact contact) {
        // TODO remove print
        System.out.println("Contact between " +
                ((UserData) contact.getFixtureA().getBody().getUserData()).getUserDataType().name() +
                " and " + ((UserData) contact.getFixtureB().getBody().getUserData()).getUserDataType().name()
        );

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (UserDataType.bodyIsPlayer(a) && UserDataType.bodyIsGround(b)
          || UserDataType.bodyIsPlayer(b) && UserDataType.bodyIsGround(a)) {
            // TODO check if player is actually standing on ground

            if (UserDataType.bodyIsPlayer(a)) {
                if (this.player.isOnTopOf(b)) {
                    this.player.landed();
                }
//                else if (this.player.isRunningInto(b)) {
//                    this.player.stopHorizontalMovement();
//                }
            } else if (this.player.isOnTopOf(a)) {
                this.player.landed();
            }
//            else if (this.player.isRunningInto(a)) {
//                this.player.stopHorizontalMovement();
//            }
        }

        /*if  bodies are player && lava
         *     take damage
         *
         * etc...
         */
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("Contact ended");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
//        System.out.println("PRE-SOLVE");
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
//        System.out.println("POST-SOLVE");
    }
}
