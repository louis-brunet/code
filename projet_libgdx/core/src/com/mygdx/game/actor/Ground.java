package com.mygdx.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.userdata.GroundUserData;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.Constants;

public class Ground extends GameActor {
    private Texture texture;

    public Ground(Body body) {
        super(body);

        this.texture = Assets.getTexture(Constants.GROUND_ASSET_ID);
    }

    @Override
    public GroundUserData getUserData() {
        return (GroundUserData) this.userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(
                this.texture,
                this.bounds.x, this.bounds.y,
                this.bounds.width, this.bounds.height);
    }
}
