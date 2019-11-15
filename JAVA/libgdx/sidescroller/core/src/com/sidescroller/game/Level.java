package com.sidescroller.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.sidescroller.game.objects.AbstractGameObject;
import com.sidescroller.game.objects.Clouds;
import com.sidescroller.game.objects.Mountains;
import com.sidescroller.game.objects.Rock;
import com.sidescroller.game.objects.WaterOverlay;
import com.sidescroller.game.objects.BunnyHead;
import com.sidescroller.game.objects.GoldCoin;
import com.sidescroller.game.objects.Feather;

public class Level {
	public static final String TAG = Level.class.getName();

	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0),
		ROCK(0, 255, 0),
		PLAYER_SPAWNPOINT(255, 255, 255),
		ITEM_FEATHER(0, 0, 255),
		ITEM_GOLD_COIN(255, 255, 0);
	
		private int color;
		
		private BLOCK_TYPE (int r, int g, int b) {
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}
		
		public boolean sameColor (int color) {
			return this.color == color;
		}
		
		public int getColor () {
			return color;
		}
	}
	
	// objects
	public Array<Rock> rocks;
	public BunnyHead bunnyHead;
	public Array<GoldCoin> goldCoins;
	public Array<Feather> feathers;
	
	
	// decorations
	public Clouds clouds;
	public Mountains mountains;
	public WaterOverlay waterOverlay;
	
	
	public Level(String filename) {
		init(filename);
	}


	private void init(String filename) {
		// player
		bunnyHead = null;
		// Objects
		rocks = new Array<Rock>();
		goldCoins = new Array<GoldCoin>();
		feathers = new Array<Feather>();
		
		// Load image file that represents the level data
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		
		// Scan pixels from top-left to bottom-right
		int lastPixelRGBA = -1;
		
		for(int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
			for(int pixelX = 0; pixelX < pixmap.getHeight(); pixelX++) {
				AbstractGameObject obj = null;
				float offsetHeight = 0;
				
				// height grows from bottom to top, renders objects in correct vertical orientation
				float baseHeight = pixmap.getHeight() - pixelY;
				
				// find current pixel color as 32-bit RGBA (int) value
				int currentPixelRGBA = pixmap.getPixel(pixelX, pixelY);
				
				// find matching color
				// empty pixel
				if(BLOCK_TYPE.EMPTY.sameColor(currentPixelRGBA)) {
					
				}
				// rock
				else if(BLOCK_TYPE.ROCK.sameColor(currentPixelRGBA)) {
					if (lastPixelRGBA != currentPixelRGBA) {
						obj = new Rock();
						float heightIncreaseFactor = 0.25f;
						offsetHeight = -2.5f;
						obj.position.set(pixelX, baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
						rocks.add((Rock) obj);
					}else {
						rocks.get(rocks.size - 1).increaseLength(1);
					}
				}
				// player spawn point
				else if(BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixelRGBA)) {
					obj = new BunnyHead();
					offsetHeight = -3.0f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					bunnyHead = (BunnyHead) obj;
				}
				// feather
				else if(BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixelRGBA)) {
					obj = new Feather();
					offsetHeight = -3.0f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					feathers.add((Feather) obj);
				}
				// gold coin
				else if(BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixelRGBA)) {
					obj = new GoldCoin();
					offsetHeight = -3.0f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					goldCoins.add((GoldCoin) obj);
				}
				// unknown color / object
				else {
					int r = 0xff & (currentPixelRGBA >>> 24);
					int g = 0xff & (currentPixelRGBA >>> 16);
					int b = 0xff & (currentPixelRGBA >>> 8);
					int a = 0xff & currentPixelRGBA;
					
					Gdx.app.error(TAG, "Unknown object at x<"+pixelX+"> y<"+pixelY+">: r<"
							+ r + "> g<"+g+"> b<"+b+"> a<"+a+">");
				}
				lastPixelRGBA = currentPixelRGBA;
			}
		}
		
		// decoration
		clouds = new Clouds(pixmap.getWidth());
		clouds.position.set(0, 2);
		
		mountains = new Mountains(pixmap.getWidth());
		mountains.position.set(-1, -1);
		
		waterOverlay = new WaterOverlay(pixmap.getWidth());
		waterOverlay.position.set(0, -3.75f);
		
		
		// free memory
		pixmap.dispose();
		Gdx.app.debug(TAG, "level '"+filename+"' loaded");
	}
	
	public void update (float deltaTime) {
		bunnyHead.update(deltaTime);
		
		for(Rock rock: rocks)
			rock.update(deltaTime);
		
		for(GoldCoin goldCoin: goldCoins)
			goldCoin.update(deltaTime);
		
		for(Feather feather: feathers)
			feather.update(deltaTime);
		
		clouds.update(deltaTime);
	}

	public void render (SpriteBatch batch) {
		// Draw mountains
		mountains.render(batch);
		
		// Draw rocks
		for(Rock r: rocks) 
			r.render(batch);

		// Draw gold coins
		for(GoldCoin gc: goldCoins)
			gc.render(batch);

		// Draw feathers
		for(Feather f: feathers)
			f.render(batch);

		// Draw player
		bunnyHead.render(batch);
		
		// Draw water overlay
		waterOverlay.render(batch);
		
		// Draw clouds
		clouds.render(batch);
	}


}
