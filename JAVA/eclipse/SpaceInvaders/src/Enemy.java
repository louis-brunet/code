import java.awt.Color;

public class Enemy extends LevelItem{
	//basic body proportions - 11 wide and 8 tall
	private static final  int RELATIVE_BODY_POINTS[][] = {{0,1}, {0,4}, {1,4}, {1,5}, {2,5}, {2,6}, {3,6}, {3,7},
 		   {2,7}, {2,8}, {3,8}, {3,7}, {4,7}, {4,6}, {7,6}, {7,7}, {8,7}, 
 		   {8,8}, {9,8}, {9,7}, {8,7}, {8,6}, {9,6}, {9,5}, {10,5}, {10,4}, 
 		   {11,4}, {11,1}, {10,1}, {10,3}, {9,3}, {9,1}, {8,1}, {8,0}, {6,0}, 
 		   {6,1}, {8,1}, {8,2}, {3,2}, {3,1}, {5,1}, {5,0}, {3,0}, {3,1}, {2,1},
 		   {2,3}, {1,3}, {1,1}, {0,1}};
	static final int BODY_RELATIVE_WIDTH = 11;
	static final int BODY_RELATIVE_HEIGHT = 8;
	
	//eye positions by top left
	private static final int RELATIVE_EYE_POSITIONS[][] = {{3,5}, {7,5}}; 
	private static final Color DEFAULT_COLOR_BODY = Color.RED;
	private static final Color DEFAULT_COLOR_EYES = Color.ORANGE;
	
	static int speed = 2;
	int[][] eyePositions;
	int movementSeed;
	Color colorEyes;
	private int row, col;
	
	
	public Enemy(int xLeft, int yTop) {
		sizeModifier = DEFAULT_SIZE_MODIF;
		initAttributes(xLeft, yTop);

		initPositionArrays();
		
		System.out.println("initializing enemy xLeft="+this.xLeft+", xRight="+xRight);

		setXLeft(xLeft);
		setYTop(yTop);
		System.out.println("initialized enemy xLeft="+this.xLeft+", xRight="+xRight);
	}
	
	public Enemy(int xLeft, int yTop, int sizeModifier) {
		this.sizeModifier = sizeModifier;
		initAttributes(xLeft, yTop);

		initPositionArrays();

		setXLeft(xLeft);
		setYTop(yTop);
		
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public void setEyeColor(Color color) {
		this.colorEyes = color;
	}
	
	private void initAttributes(int xLeft, int yTop) {
		itemType = ItemType.ENEMY;
		color = DEFAULT_COLOR_BODY;
		colorEyes = DEFAULT_COLOR_EYES;
		width = (int) (sizeModifier * (float) BODY_RELATIVE_WIDTH);
		height = (int) (sizeModifier * (float) BODY_RELATIVE_HEIGHT);
		this.xLeft = xLeft;
		this.yTop = yTop;
		movementSeed = 0;
	}
	
	private void initPositionArrays() {
		npoints = RELATIVE_BODY_POINTS.length;
		xpoints= new int[npoints];
		ypoints= new int[npoints];
		for(int i=npoints - 1, index = 0; i>=0; i--, index++) {
			xpoints[index] = (int) ( xLeft+sizeModifier* (float) RELATIVE_BODY_POINTS[i][0] );
			ypoints[index] = (int) ( yTop+sizeModifier* (float) (BODY_RELATIVE_HEIGHT-RELATIVE_BODY_POINTS[i][1]));
		}
		
		eyePositions = new int[2][2];
		//left then right eye, xLeft then yTop
	    for(int i=0; i<2; i++) {
	    	eyePositions[i][0] = (int) ( xLeft + sizeModifier * (float) (RELATIVE_EYE_POSITIONS[i][0]) ); 
	    	eyePositions[i][1] = (int) ( yTop + sizeModifier * (float) (BODY_RELATIVE_HEIGHT-RELATIVE_EYE_POSITIONS[i][1]) );
	    }
	}
	
	// Do basic enemy up and down motion: seed in [0;10]->move up, seed in [10;22]->move down
	public void doDefaultMovement() {
		int max = 60;
		int limit = 26; // limit is included in up motion
		
		if(movementSeed < limit) {
			 setYTop( yTop-speed );
		}else {
			 setYTop( yTop+speed );
		}
		
		movementSeed = (movementSeed+1)%(max+1);
	}
	
	public void setYTop( int yTop ) {
		int offset = this.yTop - yTop;
		// move eyes
		for(int i=0; i<eyePositions.length && offset!=0; i++) {
			eyePositions[i][1] -= offset;
		}
		super.setYTop(yTop);
	}
	
	public void setXLeft( int xLeft ) {
		int offset = this.xLeft - xLeft;
		//move eyes
		for(int i=0; i<eyePositions.length && offset!=0; i++) {
			eyePositions[i][0] -= offset;
		}
		super.setXLeft(xLeft);
	}
	
	// returns true if this enemy can shoot (i.e. is bottom-most enemy in col in enemies)
	public boolean canShoot(Enemy[][] enemies) {
		for(int row = this.row+1; row < enemies.length; row++) {
			if(enemies[row][this.col] != null) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Return new projectile based on current enemy position, 
	 * set xSpeed and ySpeed towards current player position.
	 */
	public Projectile shootProjectile(Player player) {
		Projectile proj = new Projectile(this, player);
		return proj;
	}
	
	
}
