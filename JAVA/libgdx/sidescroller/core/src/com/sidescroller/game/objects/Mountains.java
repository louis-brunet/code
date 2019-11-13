/**
 * 
 */
package com.sidescroller.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.sidescroller.game.Assets;

public class Mountains extends AbstractGameObject{
	private TextureRegion regMountainLeft;
	private TextureRegion regMountainRight;
	
	private int length;
	
	public Mountains(int length) {
		this.length = length;
		init();
	}

	private void init() {
		dimension.set(10, 2);

		regMountainLeft = Assets.instance.levelDecoration.mountainLeft;
		regMountainRight = Assets.instance.levelDecoration.mountainRight;
		
		// Shift mountain, extend length
		origin.x = -dimension.x * 2;
		length += dimension.x * 2;
	}
	
	private void drawMountain(SpriteBatch batch, float offsetX, float offsetY, float tintColor) {
		TextureRegion reg = null;
		
		batch.setColor(tintColor, tintColor, tintColor, 1);

		float xRel = dimension.x * offsetX;
		float yRel = dimension.y * offsetY;
		
		// Mountains span the whole level
		int mountainLength = 0;
		mountainLength += MathUtils.ceil( length / (2 * dimension.x) );
		mountainLength += MathUtils.ceil( 0.5f + offsetX );
		
		for(int i=0; i < mountainLength; i++) {
			
			// Mountain left
			reg = regMountainLeft;
			batch.draw(reg.getTexture(), origin.x + xRel,
					position.y + origin.y + yRel, origin.x, origin.y,
					dimension.x, dimension.y, scale.x, scale.y, rotation,
					reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
					reg.getRegionHeight(), false, false);
			xRel += dimension.x;
			
			// Mountain right
			reg = regMountainRight;
			batch.draw(reg.getTexture(), origin.x + xRel,
					position.y + origin.y + yRel, origin.x, origin.y,
					dimension.x, dimension.y, scale.x, scale.y, rotation,
					reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
					reg.getRegionHeight(), false, false);
			xRel += dimension.x;
		}
		
		// Reset batch color to white (removes tint)
		batch.setColor(1, 1, 1, 1);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		// dark gray mountains
		drawMountain(batch, 0.5f, 0.5f, 0.5f);
		// dark gray mountains
		drawMountain(batch, 0.25f, 0.25f, 0.7f);
		// dark gray mountains
		drawMountain(batch, 0.0f, 0.0f, 0.9f);
	}

}
