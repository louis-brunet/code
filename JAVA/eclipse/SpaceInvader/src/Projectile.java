import java.awt.Color;
import java.util.ArrayList;

public class Projectile extends LevelItem {
	private static final int DEFAULT_YSPEED = -12;
	private static final Color DEFAULT_P_PROJECTILE_COLOR = Player.DEFAULT_P_COLOR;
	private static final Color DEFAULT_E_PROJECTILE_COLOR = Color.ORANGE;
	private static final int[][] DEFAULT_P_PROJECTILE_POINTS = {{0,6},{1,0},{2,6},{1,12},{0,6}};
	private static final int[][] DEFAULT_E_PROJECTILE_POINTS = 
		{{0,0},{1,0},{2,1},{3,0},{4,0},
				{4,1},{3,2},{4,3},{4,4},{3,4},{2,3},
				{1,4},{0,4},{0,3},{1,2},{0,1},{0,0}};
	private static final int RELATIVE_P_WIDTH = 2;
	private static final int RELATIVE_P_HEIGHT = 12;
	private static final int RELATIVE_E_HEIGHT = 4;
	private static final int RELATIVE_E_WIDTH= 4;
	
	protected enum ProjectileType{PLAYER,ENEMY};
	
	private int xspeed;
	private int yspeed;
	protected ProjectileType projType;
	
	/**
	 * Create new projectile with no xspeed, default yspeed
	 * @param shooter
	 */
	public Projectile(LevelItem shooter) {
		initAttributesByParent(shooter);
		initPositionArrays(DEFAULT_P_PROJECTILE_POINTS);
	}
	
	/**
	 * Create new projectile with speeds towards current target position
	 * @param shooter
	 */
	public Projectile(LevelItem shooter, LevelItem target) {
		initAttributesByParent(shooter);
		initPositionArrays(DEFAULT_E_PROJECTILE_POINTS);
		setSpeedsTowards(target);
	}
	
	private void initAttributesByParent(LevelItem shooter) {
		itemType = ItemType.PROJECTILE;
		sizeModifier = (float) DEFAULT_SIZE_MODIF / 2f;
		xspeed = 0;
		
		switch(shooter.itemType) {
			case PLAYER:
				projType = ProjectileType.PLAYER;
				yspeed = DEFAULT_YSPEED;
				color = DEFAULT_P_PROJECTILE_COLOR;
				width = (int) (( (float) RELATIVE_P_WIDTH) * sizeModifier);
				height = (int) (( (float) RELATIVE_P_HEIGHT) * sizeModifier);
				
				setYTop(shooter.yTop - height);
				break;
			case ENEMY:
				projType = ProjectileType.ENEMY;
				color = DEFAULT_E_PROJECTILE_COLOR;
				width = (int) (( (float) RELATIVE_E_WIDTH) * sizeModifier);
				height = (int) (( (float) RELATIVE_E_HEIGHT) * sizeModifier);
				
				setYTop( shooter.yBottom );
				break;
			case PROJECTILE:
				return;
		}
		
		int xL = shooter.xCenter - width/2;
		setXLeft(xL);
	}
	
	public void doDefaultMovement() {
		setYTop(yTop + yspeed);
		if(xspeed != 0) {
			setXLeft(xLeft + xspeed);
		}
	}
	
	// Returns null if not colliding, else returns collision target
	public LevelItem doCollisionCheck(Player p, Enemy[][] enemies, ArrayList<Projectile> projectiles, Bunker[] bunkers) {
		for(Projectile proj: projectiles) {
			if(this!=proj && isCollidingWith(proj) ) {
				return proj;
			}
		}
		
		if(projType == ProjectileType.PLAYER) {
			for(int i = 0; i < Main.rows; i++) {
				for(int j = 0; j < Main.enemiesPerRow; j++) {
					//System.out.println("Testing collision with "+ enemies[i][j].toString());
					if(enemies[i][j] != null && isCollidingWith(enemies[i][j]) ) {
						return enemies[i][j];
					}
				}
			}
		} else if(projType == ProjectileType.ENEMY) {
			if(isCollidingWith(p)) {
				return p;
			}
			for(Bunker b: bunkers) {
				if(b != null && isCollidingWith(b)) {
					return b;
				}
			}
		}
		
		return null;
	}

	//set x and y speeds towards player 
	private void setSpeedsTowards(LevelItem target) {
		float framesToTarget = 10.0f;
		float distToPlayer = (float) Math.sqrt( (xCenter - target.xCenter)*(xCenter - target.xCenter)
				+ (yCenter - target.yCenter)*(yCenter - target.yCenter) );
		float totalSpeed = distToPlayer / (Main.REFRESH_DELAY*framesToTarget);		
		
		xspeed = (int) ( ( totalSpeed*totalSpeed ) / ( 1 + ( (yCenter-target.yCenter)*(yCenter-target.yCenter) / ((xCenter-target.xCenter)*(xCenter-target.xCenter) + 1)) ) );
		xspeed += 1;
		if( Math.abs(xCenter + xspeed - target.xCenter) > Math.abs(xCenter - target.xCenter) ) {
			xspeed *= -1;
		}
		
		yspeed = (int) ( (totalSpeed*totalSpeed) / ( 1 + ( (xCenter-target.xCenter)*(xCenter-target.xCenter) / ((yCenter-target.yCenter)*(yCenter-target.yCenter) + 1) ) ) );
		yspeed += 2;
		if( Math.abs(yCenter + yspeed - target.yCenter) > Math.abs(yCenter - target.yCenter) ) {
			yspeed *= -1;
		}
		
		System.out.println("Set speed to "+xspeed+", "+yspeed);
		
	}
}
