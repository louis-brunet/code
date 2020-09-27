package com.mygdx.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.enums.ViewDirection;
import com.mygdx.game.userdata.UserData;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.Constants;
import com.mygdx.game.userdata.PlayerUserData;

public class Player extends GameActor {

    private boolean jumping;
    private boolean grounded;
    private boolean xMovementBlocked;
    private ViewDirection viewDirection; // TODO use to flip texture if needed

    private Texture jumpingTexture;
    private Texture idleTexture;

    public Player(Body body) {
        super(body);

        this.jumping = false;
        this.grounded = true;
        this.xMovementBlocked = false;
        this.viewDirection = ViewDirection.RIGHT;
        this.jumpingTexture = Assets.getTexture(Constants.PLAYER_JUMPING_ASSET_ID);
        this.idleTexture = Assets.getTexture(Constants.PLAYER_IDLE_ASSET_ID);
    }

    public void jump () {
        if ( ! this.jumping && this.grounded ) {
            this.body.applyLinearImpulse(
                    Constants.PLAYER_JUMP_IMPULSE,
                    this.body.getPosition(), true);
            this.jumping = true;
        }
    }

    public void landed() {

        this.jumping = false;

    }

    public void move(ViewDirection direction) {
        if (this.xMovementBlocked && direction.equals(this.viewDirection)) {
            // TODO bug: trying to jump over the obstacle, movement still blocked
            this.body.setLinearVelocity(0, this.body.getLinearVelocity().y);
        } else {
            this.xMovementBlocked = false;
            if (Math.abs(this.body.getLinearVelocity().x) < Constants.PLAYER_MAX_VELOCITY_X) {
                if(direction == ViewDirection.LEFT) {
                    this.body.applyLinearImpulse(
                            - Constants.PLAYER_MOVE_IMPULSE.x,
                            Constants.PLAYER_MOVE_IMPULSE.y,
                            this.body.getPosition().x,
                            this.body.getPosition().y,
                            true);
                } else {
                    this.body.applyLinearImpulse(
                            Constants.PLAYER_MOVE_IMPULSE,
                            this.body.getPosition(),true);
                }
            } else if (direction == ViewDirection.RIGHT){
                this.body.setLinearVelocity(Constants.PLAYER_MAX_VELOCITY_X,
                        this.body.getLinearVelocity().y);
            } else {
                this.body.setLinearVelocity(-Constants.PLAYER_MAX_VELOCITY_X,
                        this.body.getLinearVelocity().y);
            }
        }
        this.viewDirection = direction;
    }

    public void groundHit (Body b) {
        if (this.isOnTopOf(b)) {
            this.landed();
        } else if (this.isRunningInto(b)) {
            this.xMovementBlocked = true;
            System.out.println("X movement blocked towards "+ this.viewDirection.name());
        }
    }

    private boolean isOnTopOf(Body b) {
        UserData bodyData = (UserData) b.getUserData();
        float width = bodyData.getWidth();
        float height = bodyData.getHeight();
        float xMin = b.getPosition().x - (width / 2f);
        float xMax = b.getPosition().x + (width / 2f);
        float yMax = b.getPosition().y + (height / 2f);

        return Math.abs(yMax - this.bounds.y) < 0.2f
                && this.bounds.x <= xMax && ((this.bounds.x + this.bounds.width) >= xMin);
//            System.out.println("Player is on top of " + bodyData.getUserDataType().name());
    }


    private boolean isRunningInto (Body b) {
        UserData bodyData = (UserData) b.getUserData();
        float width = bodyData.getWidth();
        float height = bodyData.getHeight();
        float xMin = b.getPosition().x - (width / 2f);
        float xMax = b.getPosition().x + (width / 2f);
        float yMax = b.getPosition().y + (height / 2f);
        float yMin = b.getPosition().y - (height / 2f);

        boolean xTouching = false;

        switch (this.viewDirection) {
            case LEFT:
                xTouching = Math.abs(xMax - this.bounds.x) < 0.2f;
                break;
            case RIGHT:
                xTouching = Math.abs(xMin - this.bounds.x - this.bounds.width) < 0.2f;
                break;
        }

        return xTouching && (yMax >= this.bounds.y)
                && (yMin <= this.bounds.y + this.bounds.height);
    }


    @Override
    public PlayerUserData getUserData() {
        return (PlayerUserData) this.userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (this.jumping) {
            batch.draw(
                    this.jumpingTexture,
                    this.bounds.x, this.bounds.y,
                    this.bounds.width, this.bounds.height);
        } else {
            batch.draw(
                    this.idleTexture,
                    this.bounds.x, this.bounds.y,
                    this.bounds.width, this.bounds.height);
        }
    }


}
