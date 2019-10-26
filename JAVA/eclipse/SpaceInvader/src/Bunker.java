import java.awt.Color;

public class Bunker extends LevelItem {
	private static final int[][] RELATIVE_POINTS = {{0,3},{1,2},{4,0},{8,0},{11,2},{12,3},
			{11,7},{8,6},{8,4},{7,3},{5,3},{4,4},{4,6},{1,7},{0,3}};
	protected static final int RELATIVE_WIDTH = 12;
	protected static final int RELATIVE_HEIGHT = 7;
	private static final Color DEFAULT_B_COLOR = Player.DEFAULT_P_COLOR;
	
	int lives;
	boolean isAlive;
	
	public Bunker(int xLeft, int yTop) {
		// TODO Auto-generated constructor stub
		itemType = ItemType.BUNKER;
		sizeModifier = DEFAULT_SIZE_MODIF;
		color = DEFAULT_B_COLOR;
		lives = 3;
		isAlive = true;
		
		width = (int) (( (float) RELATIVE_WIDTH) * sizeModifier);
		height = (int) (( (float) RELATIVE_HEIGHT) * sizeModifier);
		
		this.xLeft = xLeft;
		this.yTop = yTop;
		
		initPositionArrays(RELATIVE_POINTS);

		setXLeft(xLeft);
		setYTop(yTop);
	}
	
	/**
	 * Update relative points after enemy projectile collision at given relative point 
	 * @param xCollisionPoint
	 * @param yCollisionPoint
	 */
	protected void doEnemyProjectileDestruction( int xCollisionPoint, int yCollisionPoint) {
		
	}

	/**
	 * Decrement bunker's lives by 1
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
	}

}
