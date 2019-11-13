package com.sidescroller.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.sidescroller.game.objects.AbstractGameObject;

public class CameraHelper {
	public static final String TAG = CameraHelper.class.getName();
	
	private static final float MAX_ZOOM_IN = 0.25f;
	private static final float MAX_ZOOM_OUT = 10.0f;
	
	private Vector2 position;
	private float zoom;
	private AbstractGameObject target;
	
	public CameraHelper() {
		position = new Vector2();
		zoom = 1.0f;
	}
	
	public void update (float deltaTime) {
		if(!hasTarget()) return;

		position.x = target.position.x + target.origin.x;
		position.y = target.position.y + target.origin.y;
	}

	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(float x, float y) {
		position.set(x, y);
	}
	
	public void addZoom (float amount) {
		setZoom(zoom + amount);
	}
	
	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}

	public AbstractGameObject getTarget() {
		return target;
	}

	public void setTarget(AbstractGameObject target) {
		this.target = target;
	}
	
	public boolean hasTarget() {
		return target != null;
	}
	
	public boolean hasTarget(AbstractGameObject target) {
		return this.target != null && this.target.equals(target);
	}
	
	public void applyTo (OrthographicCamera camera) {
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}

}
