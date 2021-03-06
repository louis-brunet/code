import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
	static final float width = 10f;
	static final float height = 10f;
	static final float defaultSpeed = 2f;
	static final float framesToTarget = 3f;
	static final float targetSpeed = 5f;
	float baseMovement = 4f;		
	Color color = Color.CYAN;
	
	Player target;
	int ref;
	float xCenter;
	float yCenter;
	float xMin;
	float xMax;
	float yMin;
	float yMax;
	float xSpeed;
	float ySpeed;
	boolean isAggressive;
	
	/**
	 * Create an Enemy by its center (x, y) with default speed
	 * @param x
	 * @param y
	 */
	public Enemy(Player p, float x, float y, int ref) {
		this.ref = ref;
		target = p; 
		isAggressive = false;
		initPosition(x, y);
		setSpeedsChasingTarget();
	}
	
	/**
	 * Create an Enemy by its center (x, y) with initial vertical and horizontal speed speed;
	 * @param x
	 * @param y
	 * @param speed
	 */
	public Enemy(Player p, float x, float y, int ref, float speed) {
		this.ref = ref;
		target = p;
		//xSpeed = speed;
		//ySpeed = speed;
		isAggressive = true;
		initPosition(x, y);
		setSpeedsChasingTarget();
	}
	
	private void initPosition(float xCenter, float yCenter) {
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		xMin = xCenter - width/2f;
		xMax = xMin + width;
		yMin = yCenter - width/2f;
		yMax = yMin + height;
	}

	private void setXMin( float xMin) {
		this.xMin = xMin;
		this.xMax = this.xMin + width;
		this.xCenter = this.xMin + width/2f;
	}
	
	private void setYMin( float yMin) {
		this.yMin = yMin;
		this.yMax = this.yMin + height;
		this.yCenter = this.yMin + height/2f;
	}
	
	public void updatePositionChasingTarget() {
		// Move e towards player by e.xSpeed and e.ySpeed
		//Random rand = new Random();
		//if(rand.nextInt(100)%6 != -1) {
		setSpeedsChasingTarget();
		//}
		setXMin(xMin + xSpeed);
		setYMin(yMin + ySpeed);
		
		keepInBounds();
	}
	
	public void updatePositionFleeingPlayer() {
		setSpeedsChasingTarget();
		xSpeed *= -1;
		ySpeed *= -1;
		
		setXMin(xMin + xSpeed);
		setYMin(yMin + ySpeed);
		
		keepInBounds();
	}
	
	// Keep enemy in bounds	
	private void keepInBounds() {
		if( xMin < 0 ) {
			setXMin(0);
		}
		if( xMax > DrawCanvas.CANVAS_WIDTH ) {
			setXMin(DrawCanvas.CANVAS_WIDTH - width);
		}
		if( yMin < 0 ) {
			setYMin(0);
		}
		if( yMax > DrawCanvas.CANVAS_HEIGHT ) {
			setYMin(DrawCanvas.CANVAS_HEIGHT - height);
		}
	}
	
	public float getYStepPerXTo( float x, float y ) {
		float res = (y - yCenter)/(x - xCenter);
		return res;
	}
	
	//set x and y speeds towards player 
	private void setSpeedsChasingTarget() {
		float distToPlayer = (float) Math.sqrt( (xCenter - target.xCenter)*(xCenter - target.xCenter)
				+ (yCenter - target.yCenter)*(yCenter - target.yCenter) );
		float totalSpeed = distToPlayer / (Main.REFRESH_DELAY*framesToTarget);		
		
		xSpeed = ( ( totalSpeed*totalSpeed ) / ( 1 + ( (yCenter-target.yCenter)*(yCenter-target.yCenter) / ((xCenter-target.xCenter)*(xCenter-target.xCenter) )) ) );
		xSpeed += baseMovement;
		if( Math.abs(xCenter + xSpeed - target.xCenter) > Math.abs(xCenter - target.xCenter) && isAggressive) {
			xSpeed *= -1;
		}
		
		//ySpeed = getYStepPerXTo(target.xCenter, target.yCenter) * xSpeed;
		ySpeed = ( (totalSpeed*totalSpeed) / ( 1 + ( (xCenter-target.xCenter)*(xCenter-target.xCenter) / ((yCenter-target.yCenter)*(yCenter-target.yCenter)) ) ) );
		ySpeed += baseMovement;
		if( Math.abs(yCenter + ySpeed - target.yCenter) > Math.abs(yCenter - target.yCenter) && isAggressive) {
			ySpeed *= -1;
		}
		
		System.out.println("Set speed to "+xSpeed+", "+ySpeed);
		
	}
	
	public boolean isCollidingWithAny(ArrayList<Enemy> enemies) {
		for(Enemy e: enemies) {
			if( this!=e && isCollidingWith( e.xMin, e.yMin, e.xMax, e.yMax )) {
				return true;
			}
		}
		
		return false;
	}
	
	// Check for collision by other piece's xMin, yMin, xMax, yMax
	public boolean isCollidingWith(float xMin, float yMin, float xMax, float yMax ) {
		return (this.yMin < yMax
	 			   && this.yMax > yMin
	 			   && this.xMin < xMax 
	 			   && this.xMax > xMin);
	}
	
	/**
	 * Returns enemy colliding with this enemy, returns null if none found.
	 * @param enemies
	 * @return
	 */
	public Enemy getCollisionTarget(ArrayList<Enemy> enemies) {
		for(Enemy e: enemies) {
			if(isCollidingWith( e.xMin, e.yMin, e.xMax, e.yMax )) {
				return e;
			}
		}
		return null;
	}
	
	public boolean isOutOfBounds() {
		return (xMin<0 || xMax>DrawCanvas.CANVAS_WIDTH
				|| yMin<0 || xMax>DrawCanvas.CANVAS_HEIGHT);
	}
}
