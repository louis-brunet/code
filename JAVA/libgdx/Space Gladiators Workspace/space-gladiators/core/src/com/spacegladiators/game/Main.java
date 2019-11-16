package com.spacegladiators.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class Main extends ApplicationAdapter {
	
	public PerspectiveCamera cam;
	public Model model;
	public ModelInstance instance;
	public ModelBatch modelBatch;
	public Environment environment;
	
	Vector3 position;
	
	@Override
	public void create () {
		cam = new PerspectiveCamera( 67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
		
		ModelBuilder modelBuilder = new ModelBuilder();
		Material mat = new Material(ColorAttribute.createDiffuse(Color.BLUE),
				FloatAttribute.createShininess(8f));
		
		model = modelBuilder.createBox(5, 5, 5, mat,
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		instance = new ModelInstance(model);
		
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(
				ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f,
				1f, -0.8f, -0.2f));
		
		position = new Vector3();
	}

	@Override
	public void render () {
		movement();
		rotate();
		
		Gdx.gl.glViewport(0, 0,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		modelBatch.begin(cam);
		modelBatch.render(instance, environment);
		modelBatch.end();
	}
	
	@Override
	public void dispose () {
		model.dispose();
	}
	
	private void movement () {
		instance.transform.getTranslation(position);

		if(Gdx.input.isKeyPressed(Keys.Z))
			position.x += Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.D))
			position.z += Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.Q))
			position.z -= Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.S))
			position.x -= Gdx.graphics.getDeltaTime();
		
		instance.transform.setTranslation(position);
	}
	
	private void rotate () {
		
		if(Gdx.input.isKeyPressed(Keys.NUM_1))
			instance.transform.rotate(Vector3.X, Gdx.graphics.getDeltaTime() * 100);
		if(Gdx.input.isKeyPressed(Keys.NUM_2))
			instance.transform.rotate(Vector3.Y, Gdx.graphics.getDeltaTime() * 100);
		if(Gdx.input.isKeyPressed(Keys.NUM_3))
			instance.transform.rotate(Vector3.Z, Gdx.graphics.getDeltaTime() * 100);
		
	}
}
