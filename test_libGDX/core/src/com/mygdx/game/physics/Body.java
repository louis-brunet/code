package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;

public class Body {
    protected Vector2 position;
    protected Vector2 scale;

    protected AxisAlignedBoundingBox boundingBox;
    protected Vector2 boundingBoxOffset;

    public Body(Vector2 pos, Vector2 scale,
                AxisAlignedBoundingBox boundingBox,
                Vector2 boundingBoxOffset ) {
        this.position = pos;
        this.scale = scale;
        this.boundingBox = boundingBox;
        this.boundingBoxOffset = boundingBoxOffset;
    }
}
