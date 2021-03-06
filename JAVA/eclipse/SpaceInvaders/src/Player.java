import java.awt.Color;

public class Player extends LevelItem {
	
	static final int[][] RELATIVE_POINTS = {{0,4},{0,2},{5,1},{6,0},{7,1},{12,2},{12,4},{9,5},{4,5},{0,4}};
	static final int RELATIVE_WIDTH = 12;
	static final int RELATIVE_HEIGHT = 5;
	static final Color DEFAULT_P_COLOR = Color.GREEN;
	static final float RELATIVE_Y_POS = 8f/9f;
	static final int MAX_SPEED = 20;
	
	float speed = 0f;
	float speedGoal = 0f;
	
	int lives;
	boolean isAlive;
	boolean isShooting;

	public Player() {
		itemType = ItemType.PLAYER;
		sizeModifier = DEFAULT_SIZE_MODIF;
		color = DEFAULT_P_COLOR;
		lives = 3;
		isAlive = true;
		isShooting = false;
		
		// Init position
		width = (int) (( (float) RELATIVE_WIDTH) * sizeModifier);
		height = (int) (( (float) RELATIVE_HEIGHT) * sizeModifier);
		
		xLeft = DrawCanvas.CANVAS_WIDTH/2 - width/2;
		xRight = xLeft + width;
			
			// Set y to 8/9th of canvas
		yTop = (int) (RELATIVE_Y_POS * (float) DrawCanvas.CANVAS_HEIGHT - height/2);
		yBottom = yTop+ height;
		
		xCenter = xLeft+ width/2;
		yCenter = yTop + height/2;
		
		initPositionArrays(RELATIVE_POINTS);
	}
	
	public void initPositionArrays() {
		npoints = RELATIVE_POINTS.length;
		xpoints= new int[npoints];
		ypoints= new int[npoints];
		for(int i=npoints - 1, index = 0; i>=0; i--, index++) {
			xpoints[index] = (int) ( xLeft+sizeModifier * (float) RELATIVE_POINTS[i][0] );
			ypoints[index] = (int) ( yTop+sizeModifier * (float) RELATIVE_POINTS[i][1]);
		}
	}
	
	/**
	 * Return new projectile based on current position
	 */
	public Projectile shootProjectile() {
		Projectile proj = new Projectile(this);
		proj.color = color;
		return proj;
	}
	
	/**
	 * Decrement player's lives by 1
	 */
	public void loseLife() {
		if(lives == 1) {
			isAlive = false;
			//color = Color.BLACK;
		}else if(lives == 2) {
			color = Color.RED;
		}else if(lives == 3) {
			color = Color.ORANGE;
		}
		lives--;
		
		System.out.println("Lost one life");
	}
}
