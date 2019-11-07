import java.awt.Color;

public class Bunker extends LevelItem {
	//private static final int[][] RELATIVE_POINTS = { {0,3},{1,1},{5,0},{9,1},{10,3},{9,7},{7,8},{7,4},{5,3},{3,4},{3,8},{1,7},{0,3} };
			/*{{0,3},{1,2},{4,0},{8,0},{11,2},{12,3},
			{11,7},{8,6},{8,4},{7,3},{5,3},{4,4},{4,6},{1,7},{0,3}};*/
	protected static final int RELATIVE_WIDTH = 10;
	protected static final int RELATIVE_HEIGHT = 8;
	private static final Color DEFAULT_B_COLOR = Player.DEFAULT_P_COLOR;

	private static final int[][] LEFT_RELATIVE_POINTS = {{0,3},{1,1},{3,4},{3,8},{1,7},{0,3}};
	private static final int[][] TOP_RELATIVE_POINTS = {{1,1},{5,0},{9,1},{7,4},{3,4},{1,1}};
	private static final int[][] RIGHT_RELATIVE_POINTS = {{7,4},{9,1},{10,3},{9,7},{7,8},{7,4}};
	
	static final float BUNKER_SIZE_MODIFIER = 1f;
	
	//int lives;
	//boolean isAlive;
	protected int[] xpointsLeft, ypointsLeft, xpointsRight, ypointsRight;
	protected int npointsSides;
	protected int[] xpointsTop, ypointsTop;
	protected int npointsTop;
	boolean hasLeft, hasRight, hasTop;
	
	public Bunker(int xLeft, int yTop) {
		itemType = ItemType.BUNKER;
		sizeModifier = DEFAULT_SIZE_MODIF * BUNKER_SIZE_MODIFIER;
		color = DEFAULT_B_COLOR;
		hasLeft = true;
		hasRight = true;
		hasTop = true;
		
		width = (int) (( (float) RELATIVE_WIDTH) * sizeModifier);
		height = (int) (( (float) RELATIVE_HEIGHT) * sizeModifier);
		
		this.xLeft = xLeft;
		this.yTop = yTop;
		
		int[][][] relativePositionArrays = {LEFT_RELATIVE_POINTS, TOP_RELATIVE_POINTS, RIGHT_RELATIVE_POINTS};
		initPositionArrays( relativePositionArrays );

		setXLeft(xLeft);
		setYTop(yTop);
	}

	protected void initPositionArrays(int[][][] areasRelativePoints) {
		npointsSides = areasRelativePoints[0].length;
		xpointsLeft = new int[npointsSides];
		ypointsLeft = new int[npointsSides];
		for(int i=npointsSides - 1, index = 0; i>=0; i--, index++) {
			xpointsLeft[index] = (int) ( xLeft+sizeModifier * (float) areasRelativePoints[0][i][0] );
			ypointsLeft[index] = (int) ( yTop+sizeModifier * (float) areasRelativePoints[0][i][1]);
		}
		
		xpointsRight = new int[npointsSides];
		ypointsRight = new int[npointsSides];
		for(int i=npointsSides - 1, index = 0; i>=0; i--, index++) {
			xpointsRight[index] = (int) ( xLeft+sizeModifier * (float) areasRelativePoints[2][i][0] );
			ypointsRight[index] = (int) ( yTop+sizeModifier * (float) areasRelativePoints[2][i][1]);
		}
		
		npointsTop= areasRelativePoints[1].length;
		xpointsTop = new int[npointsTop];
		ypointsTop = new int[npointsTop];
		for(int i=npointsTop - 1, index = 0; i>=0; i--, index++) {
			xpointsTop[index] = (int) ( xLeft+sizeModifier * (float) areasRelativePoints[1][i][0] );
			ypointsTop[index] = (int) ( yTop+sizeModifier * (float) areasRelativePoints[1][i][1]);
		}
	}
	
	
	/**
	 * Decrement bunker's lives by 1
	 *//*
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
	}*/
	
	/**
	 * Destroy the third of the bunker hit by enemy projectile at given collision point
	 * @param xCol
	 * @param yCol
	 * @return true if an area has been destroyed
	 */
	public boolean doCollision(Projectile proj) {
		int xProjRelative = (int) ((float)(proj.xLeft - xLeft) / sizeModifier);
		int yProjRelative = (int) ((float)(proj.yTop - yTop) / sizeModifier);
		boolean isPlayerProj = (proj.projType != Projectile.ProjectileType.PLAYER);
		System.out.println("collision with bunker: isplayerproj="+isPlayerProj);
		
		switch(getCollisionArea(xProjRelative, yProjRelative)) {
			case 0:
				hasLeft = !isPlayerProj;
				break;
			case 1:
				hasTop = !isPlayerProj;
				break;
			case 2:
				hasRight = !isPlayerProj;
				break;
			case -1:
				return false;
		}
		return true;
		
	}
	
	/**
	 * Destroy the third of the bunker hit by enemy projectile at given collision point
	 * @param xCol
	 * @param yCol
	 * @return true if an area has been destroyed
	 */
	public boolean doCollision(Enemy e) {
		if(isCollidingWith(e)) {
			if(hasTop && e.isCollidingWith(xpointsTop, ypointsTop)) {
				hasTop = false;
				return true;
			}
			
			if(hasLeft && e.isCollidingWith(xpointsLeft, ypointsLeft)) {
				hasLeft = false;
				return true;
			}
			
			if(hasRight && e.isCollidingWith(xpointsRight, ypointsRight)) {
				hasRight= false;
				return true;
			} 
		}
		return false;
	}

	/**
	 * 
	 * @param xProj in points relative to bunker's {0,0}
	 * @param yProj in points
	 * @return areaNum  0, 1, 2 or -1 if not colliding with any remaining areas
	 */
	private int getCollisionArea(int xRelative, int yRelative) {
		int areaNum = -1;
		
		if(yRelative < 1) {
			if(hasTop) {
				areaNum = 1;
			}
		}
		if( (xRelative < 1) || (!hasTop && xRelative < 3) ) {
			if(hasLeft) {
				areaNum = 0;
			}
		}
		if( (xRelative > 9) || (!hasTop && xRelative > 5)  ) {
			if(hasRight) {
				areaNum = 2;
			}
		}
		
		System.out.println("collision with "+this.toString()+"'s area "+areaNum);
		return areaNum;
	}
}
