package com.mygdx.game.actor;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.utils.Constants;
import com.mygdx.game.userdata.UserData;

public abstract class GameActor extends Actor {
    protected Body body;
    protected UserData userData;
    protected Rectangle bounds;

    public GameActor(Body body) {
        this.body = body;
        this.userData = (UserData) body.getUserData();
        this.bounds = new Rectangle();
    }

    public abstract UserData getUserData();

    private void updateBounds() {
        // use body position & userdata width/height to update bounds
        this.bounds.x = this.body.getPosition().x - this.userData.getWidth() / 2f;
        this.bounds.y = this.body.getPosition().y - this.userData.getHeight() / 2f;
        this.bounds.width = this.userData.getWidth();
        this.bounds.height =this.userData.getHeight();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // TODO if game is paused, return

        if (this.body.getUserData() != null) {
            this.updateBounds();
        } else {
            this.remove();
        }
    }
}
