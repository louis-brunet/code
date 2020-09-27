package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;

public class AxisAlignedBoundingBox {
    private Vector2 center;
    private Vector2 halfSize;

    public AxisAlignedBoundingBox(Vector2 center, Vector2 halfSize) {
        this.center = center;
        this.halfSize = halfSize;
    }

    public boolean overlaps(AxisAlignedBoundingBox box) {
        if (Math.abs(this.center.x - box.center.x)
                > this.halfSize.x + box.halfSize.x)
            return false;
        if (Math.abs(this.center.y - box.center.y)
                > this.halfSize.y + box.halfSize.y) return false;
        return true;
    }

    public void setCenter(Vector2 center) {
        this.center = center;
    }
}
