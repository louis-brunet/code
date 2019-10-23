import java.awt.Color;
import java.util.ArrayList;

public class Projectile extends LevelItem {
	private static final int DEFAULT_SPEED = -12;
	private static final Color DEFAULT_P_PROJECTILE_COLOR = Player.DEFAULT_P_COLOR;
	private static final Color DEFAULT_E_PROJECTILE_COLOR = Color.ORANGE;
	private static final int[][] DEFAULT_PROJECTILE_POINTS = {{0,6},{1,0},{2,6},{1,12},{0,6}};
	private static final int RELATIVE_WIDTH = 2;
	private static final int RELATIVE_HEIGHT = 12;
	
	protected enum ProjectileType{PLAYER,ENEMY};
	
	private int xspeed;
	private int yspeed;
	private ProjectileType projType;
	
	public Projectile(LevelItem shooter) {
		initAttributesByParent(shooter);
		initPositionArrays(DEFAULT_PROJECTILE_POINTS);
	}
	
	private void initAttributesByParent(LevelItem shooter) {
		itemType = ItemType.PROJECTILE;
		
		switch(shooter.itemType) {
			case PLAYER:
				projType = ProjectileType.PLAYER;
				break;
			case ENEMY:
				projType = ProjectileType.ENEMY;
				break;
			case PROJECTILE:
				return;
		}
		
		xspeed = 0;
		sizeModifier = (float) DEFAULT_SIZE_MODIF / 2f;
		width = (int) (( (float) RELATIVE_WIDTH) * sizeModifier);
		height = (int) (( (float) RELATIVE_HEIGHT) * sizeModifier);

		int xL = shooter.xCenter - width/2;
		setXLeft(xL);
		
		if(shooter.itemType == ItemType.PLAYER) {
			yspeed = DEFAULT_SPEED;
			color = DEFAULT_P_PROJECTILE_COLOR;
			
			setYTop(shooter.yTop - height); // could cause error? 
		}else if(shooter.itemType == ItemType.ENEMY) {
			yspeed = -1 * DEFAULT_SPEED;
			color = DEFAULT_E_PROJECTILE_COLOR;
			setYTop( shooter.yBottom );
		}
	}
	
	public void doDefaultMovement() {
		setYTop(yTop + yspeed);
	}
	
	// Returns null if not colliding, else returns collision target
	public LevelItem doCollisionCheck(Player p, Enemy[][] enemies, ArrayList<Projectile> projectiles) {
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
		}
		
		return null;
	}
}
