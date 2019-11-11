package com.simplephysics.game;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;


public class SimplePhysicsGame extends ApplicationAdapter implements InputProcessor{
	static float MOUSE_SENS = 0.3f;
	static float MAX_VELOCITY = 0.5f;
	static float LERP_ALPHA = 0.05f;
	static int numCubes = 10;
	
	ModelBatch batch;
	ModelBuilder builder;
	Model floor;
	ModelInstance floorInstance;
	Model cube;
	ModelInstance[] cubes;
	Model cone;
	ModelInstance cone1;
	Environment environment;
	PerspectiveCamera camera;
	
	Vector3 playerCenterPos;
	//Vector3 cameraFocusPoint;
	Vector3 playerVelocity;
	Vector3 playerVelocityGoal;
	float forwardVelocityGoal;
	float rightVelocityGoal;
	float verticalVelocityGoal;
		
	Vector3 cameraPos;
	
	@Override
	public void create () {
		
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCursorCatched(true);
		//Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

		playerCenterPos = new Vector3(3, 5, -3);
		playerVelocity = new Vector3(0f, 0f, 0f);
		playerVelocityGoal = new Vector3(0f, 0f, 0f);
		verticalVelocityGoal = 0f;
		
		cameraPos = new Vector3(playerCenterPos.x, playerCenterPos.y + 5, playerCenterPos.z+3);
		//cameraFocusPoint = new Vector3(playerCenterPos.x + 4, playerCenterPos.y + 1, playerCenterPos.z);
		
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(cameraPos);
		camera.lookAt(playerCenterPos);
		//camera.lookAt(cameraFocusPoint);
		camera.near = 0.1f;
		camera.far = 300f;
				
		batch = new ModelBatch();
		builder = new ModelBuilder();
		cube = builder.createBox(2f, 2f, 2f,
				new Material(ColorAttribute.createDiffuse(0.7f, 0.5f, 0.05f, 1)),
				VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);
		
		cubes = new ModelInstance[numCubes];
		Random rand = new Random();
		float xCube, zCube;
		for(int i=0; i < numCubes; i++) {
			xCube = rand.nextFloat()*100;
			zCube = rand.nextFloat()*100;
			cubes[i] = new ModelInstance(cube, xCube, 1, zCube);
		}
		
		
		floor = builder.createBox(100, 0, 100, 
				new Material(ColorAttribute.createDiffuse(0, 0.6f, 0.6f, 1f)), 
				VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);
		floorInstance = new ModelInstance(floor, 50, 0, 50);
		
		cone = builder.createCone(3, 6, 3, 200,
				new Material(ColorAttribute.createDiffuse(0.6f, 0.6f, 0, 1f)), 
				VertexAttributes.Usage.Normal|VertexAttributes.Usage.Position);
		cone1 = new ModelInstance(cone, playerCenterPos);
		
		
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		DirectionalLight light = new DirectionalLight();
		light.set(Color.SKY, -1f,-0.5f,-1.5f);
		environment.add(light);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(.9f, .9f, .9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		
		
		playerVelocity.lerp(playerVelocityGoal, LERP_ALPHA);
		
		float yCamCorrection = 0f;
		if(playerCenterPos.y < 3f) {
			yCamCorrection = 3f-playerCenterPos.y;
			playerCenterPos.y = 3f;
			playerVelocityGoal.y = 0f;
			playerVelocity.y = 0;
			verticalVelocityGoal = 0;
		}
		
		playerCenterPos.add(playerVelocity);
		
		cone1.transform.setToTranslation(playerCenterPos);
		cone1.calculateTransforms();
		
		// Player-cube collision detection
		/*BoundingBox playerHitbox = new BoundingBox();
		cone1.calculateBoundingBox(playerHitbox);
		for(int i=0; i<cubes.length; i++) {
			BoundingBox cubeHitbox = new BoundingBox();
			if(cubes[i] != null && cubes[i].calculateBoundingBox(cubeHitbox).intersects(playerHitbox)) {
				cubes[i] = null;
				System.out.println("Cube deleted");
			}
		}*/
		
		camera.translate(playerVelocity.add(0f, yCamCorrection, 0f));
		camera.lookAt(playerCenterPos);
		
		camera.update();
		
		batch.begin(camera);
		
		for(ModelInstance c: cubes) {
			if(c != null) {
				batch.render(c, environment);
			}
		}
		batch.render(floorInstance, environment);
		batch.render(cone1, environment);
		
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		cube.dispose();
		floor.dispose();
		cone.dispose();
	}

	
	/**
	 * Input processing
	 */
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
			case Input.Keys.LEFT:
				camera.rotateAround(playerCenterPos, new Vector3(0f,1f,0f), -10f);
				//camera.rotateAround(cameraFocusPoint, new Vector3(0f,1f,0f), -10f);
				break;
			case Input.Keys.RIGHT:
				camera.rotateAround(playerCenterPos, new Vector3(0f,1f,0f), 10f);
				//camera.rotateAround(cameraFocusPoint, new Vector3(0f,1f,0f), -10f);
				break;
			case Input.Keys.UP:
				camera.translate(camera.direction);
				break;
			case Input.Keys.DOWN:
				camera.translate(-1 * camera.direction.x, -1 * camera.direction.y, -1 * camera.direction.z);
				break;
			case Input.Keys.Z:
				if(forwardVelocityGoal != MAX_VELOCITY) {
					forwardVelocityGoal = MAX_VELOCITY;
					setVelocityGoalRelativeToCamera(MAX_VELOCITY, verticalVelocityGoal, rightVelocityGoal);
				}
				break;
			case Input.Keys.S:
				if(forwardVelocityGoal != -1f* MAX_VELOCITY) {
					forwardVelocityGoal = -1f * MAX_VELOCITY;
					setVelocityGoalRelativeToCamera(forwardVelocityGoal, verticalVelocityGoal, rightVelocityGoal);
				}
				break;
			case Input.Keys.Q:
				if(rightVelocityGoal != MAX_VELOCITY) {
					rightVelocityGoal = MAX_VELOCITY;
					setVelocityGoalRelativeToCamera(forwardVelocityGoal, verticalVelocityGoal, rightVelocityGoal);
				}
				break;
			case Input.Keys.D:
				if(rightVelocityGoal != -1f * MAX_VELOCITY) {
					rightVelocityGoal = -1f * MAX_VELOCITY;
					setVelocityGoalRelativeToCamera(forwardVelocityGoal, verticalVelocityGoal, rightVelocityGoal);
				}
				break;
			case Input.Keys.SPACE:
				if(verticalVelocityGoal != MAX_VELOCITY) {
					verticalVelocityGoal = MAX_VELOCITY;
					setVelocityGoalRelativeToCamera(forwardVelocityGoal, verticalVelocityGoal, rightVelocityGoal);
				}
				break;
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Input.Keys.Z:
			if(forwardVelocityGoal == MAX_VELOCITY) {
				forwardVelocityGoal = 0f;
				setVelocityGoalRelativeToCamera(0f, verticalVelocityGoal, rightVelocityGoal);
			}
			break;
		case Input.Keys.S:
			if(forwardVelocityGoal == -1f*MAX_VELOCITY) {
				forwardVelocityGoal = 0f;
				setVelocityGoalRelativeToCamera(0f, verticalVelocityGoal, rightVelocityGoal);
			}
			break;
		case Input.Keys.Q:
			if(rightVelocityGoal == MAX_VELOCITY) {
				rightVelocityGoal = 0f;
				setVelocityGoalRelativeToCamera(forwardVelocityGoal, verticalVelocityGoal, rightVelocityGoal);
			}
			break;
		case Input.Keys.D:
			if(rightVelocityGoal == -1f*MAX_VELOCITY) {
				rightVelocityGoal = 0f;
				setVelocityGoalRelativeToCamera(forwardVelocityGoal, verticalVelocityGoal, rightVelocityGoal);
			}
			break;
		case Input.Keys.SPACE:
			if(verticalVelocityGoal == MAX_VELOCITY) {
				verticalVelocityGoal = -MAX_VELOCITY;
				setVelocityGoalRelativeToCamera(forwardVelocityGoal, verticalVelocityGoal, rightVelocityGoal);
			}
			break;
	}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		int centerX = Gdx.graphics.getWidth()/2;
		int centerY = Gdx.graphics.getHeight()/2;

		int xoffset = centerX - screenX;
		int yoffset = screenY - centerY;

		// left-right mouse movement
		camera.rotateAround(playerCenterPos, new Vector3(0,1,0) , xoffset*MOUSE_SENS);
		
		// up-down mouse movement
		Vector3 horizontalRotationAxis = new Vector3(camera.direction.x, 0, camera.direction.z).crs(camera.direction);
		
		camera.rotateAround(playerCenterPos, horizontalRotationAxis, yoffset*MOUSE_SENS);
		
		Vector3 camToPlayer = new Vector3(camera.direction)
				.scl( Vector3.dst(camera.position.x, camera.position.y, camera.position.z,
						playerCenterPos.x, playerCenterPos.y, playerCenterPos.z));
		Vector3 camToPlayerY0 = new Vector3(camToPlayer);
		camToPlayer.y = 0;
		
		float camAngle = (float) Math.acos( new Vector3(camToPlayer).dot(camToPlayerY0) / (camToPlayer.len() * camToPlayerY0.len()));
		if(camera.position.y <= playerCenterPos.y || camAngle < Math.PI/6f || camAngle > Math.PI/3f) {
			camera.rotateAround(playerCenterPos, horizontalRotationAxis, -yoffset*MOUSE_SENS);
			System.out.println("horizontal rotation undone, angle : "+camAngle);
		}
		

		Gdx.input.setCursorPosition(centerX, centerY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void setVelocityGoalRelativeToCamera(float xRelative, float yRelative, float zRelative) {
		System.out.println("trying to set forward "+xRelative+", right "+zRelative);

		Vector3 forward = new Vector3(camera.direction);
		forward.y = 0;
		forward.nor();
		
		Vector3 right = new Vector3(0,1,0);
		right.crs(forward);
		right.nor();
		
		
		forward.scl(xRelative);
		right.scl(zRelative);
		
		playerVelocityGoal.set( forward.add(right).mulAdd(new Vector3(0, 1, 0), yRelative) );
		System.out.println("Set velocity goal to "+forward.toString());
	}
	/*
	private void setCameraPos() {
		cameraPos.set(playerCenter)
	}
	*/
}
	
