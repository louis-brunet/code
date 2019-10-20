import java.awt.Color;

public class Player {
	float xMin;
	float xMax;
	float yMin;
	float yMax;
	float xCenter;
	float yCenter;
	Color color;
	int lives;
	boolean isAlive;

	static final float speed = 25f;
	float width = 50f;
	float height = 50f;
	
	/**
	 * Create a player at the center of the drawing canvas
	 */
	public Player() {
		xMin = DrawCanvas.CANVAS_WIDTH/2f - width/2f;
		xMax = xMin + width;
		yMin = DrawCanvas.CANVAS_HEIGHT/2f - height/2f;
		yMax = yMin + height;
		xCenter = xMin + width/2f;
		yCenter = yMin + height/2f;
		
		color = Color.green;
		lives = 3;
		isAlive = true;
	}
	
	public boolean isCollidingWith(Enemy e) {
		return (yMin < e.yMax
	 			   && yMax > e.yMin
	 			   && xMin < e.xMax 
	 			   && xMax > e.xMin);
	}
	
	/**
	 * Decrement player's lives by 1
	 */
	public void loseLife() {
		if(lives == 1) {
			isAlive = false;
			color = Color.BLACK;
		}else if(lives == 2) {
			color = Color.RED;
		}else if(lives == 3) {
			color = Color.ORANGE;
		}
		lives--;
	}
	
	public void moveLeft() {
		setXMin(xMin - speed);
	}
	
	public void moveRight() {
		setXMin(xMin + speed);
	}

	public void moveDown() {
		setYMin(yMin + speed);
	}

	public void moveUp() {
		setYMin(yMin - speed);
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
}
