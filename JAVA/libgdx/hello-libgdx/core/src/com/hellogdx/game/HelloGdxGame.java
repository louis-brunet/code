package com.hellogdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class HelloGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont font;
	//Texture img;
	ShapeRenderer shapeRenderer;
	int width;
	int height;
	ArrayList<Rectangle> visitedRectangles;
	static int rectW = 1;
	static int rectH = 1;
	Vector2 currPos;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();
		visitedRectangles = new ArrayList<Rectangle>();
		currPos = new Vector2(0, 0);
		
		Gdx.graphics.setTitle("Hello libGDX");
	}
	
	@Override
	public void resize (int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void render () {
		visit(currPos);
		
		while(visitedRectangles.contains(new Rectangle(currPos.x, currPos.y, rectW, rectH))) {
			Random rand = new Random();
			int xoffset = rand.nextInt(3) - 1;
			int yoffset = rand.nextInt(3) - 1;
			
			currPos.add(xoffset, yoffset);
			System.out.println("Added "+xoffset+", "+yoffset+" to currPos");
			if(currPos.x < 0)
				currPos.x = width;
			if(currPos.x > width)
				currPos.x = 0;
			if(currPos.y < 0)
				currPos.y = height;
			if(currPos.y > height)
				currPos.y = 0;
		}
		
		
		Gdx.gl.glClearColor(0, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		font.draw(batch, "Hello :)", -40 + width/2, height-20);
		batch.end();

		shapeRenderer.begin(ShapeType.Filled);
		
		shapeRenderer.setColor(0, 0, 0, 1);
		shapeRenderer.x(width/2, height/2, 150);
		
		//shapeRenderer.setColor(0.6f, 0.3f, 0.1f, 1);
		shapeRenderer.setColor(1f, 1f, 1f, 1f);
		for(Rectangle rect: visitedRectangles) {
			shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		shapeRenderer.end();
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		//img.dispose();
	}
	
	private void visit(Vector2 pos) {
		visitedRectangles.add(new Rectangle(pos.x, pos.y, rectW, rectH));
	}
}
