package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;

public class Character extends MovingBody {

    private CharacterState state;

    private boolean wasPushedRightWall;
    private boolean isPushedRightWall;
    private boolean wasPushedLeftWall;
    private boolean isPushedLeftWall;
    private boolean wasOnGround;
    private boolean isOnGround;
    private boolean wasAtCeiling;
    private boolean isAtCeiling;

    public Character(Vector2 position, Vector2 scale,
                     AxisAlignedBoundingBox bb,
                     Vector2 bbOffset) {
        super(position, scale, bb, bbOffset);
        this.state = CharacterState.STAND;
    }

    public void updateMovement() {

    }

    @Override
    public void updatePhysics(float delta) {
        super.updatePhysics(delta);

        this.wasOnGround = this.isOnGround;
        this.wasAtCeiling = this.isAtCeiling;
        this.wasPushedLeftWall = this.isPushedLeftWall;
        this.wasPushedRightWall = this.isPushedRightWall;

        // TODO replace with collision check
        if (this.position.y < 0f) {
            this.position.y = 0f;
            this.isOnGround = true;
            this.updateBoundingBoxCenter();
        } else this.isOnGround = false;
    }
}
