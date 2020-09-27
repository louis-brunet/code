package com.mygdx.game.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class MovingBody extends Body {
    private Vector2 oldPosition;

    private Vector2 oldSpeed;
    private Vector2 speed;

    public MovingBody(Vector2 position, Vector2 scale,
                      AxisAlignedBoundingBox bb,
                      Vector2 bbOffset) {
        this(position, scale, new Vector2(0,0), bb, bbOffset);
    }

    public MovingBody(Vector2 position, Vector2 scale, Vector2 speed,
                      AxisAlignedBoundingBox bb,
                      Vector2 bbOffset) {
        super(position, scale, bb, bbOffset);
        this.speed = speed;
    }

    public void updatePhysics(float delta) {
        this.oldPosition = this.position;
        this.oldSpeed = this.speed;

        this.position = this.position.add(
                this.speed.x * delta,
                this.speed.y * delta);

        this.updateBoundingBoxCenter();
    }

    protected void updateBoundingBoxCenter() {
        this.boundingBox.setCenter(
                this.position.add(this.boundingBoxOffset));
    }
}
