import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class Level extends JPanel{
	
	public int initEnemyCount;
	public Player player; 
	public ArrayList<Enemy> enemies;
	public DrawCanvas canvas;
	public boolean isPaused;
	public PowerUp pwrUp;
	
	/**
	 * Create new level with count enemies
	 * @param count
	 */
	public Level(int count) {
		player = new Player();
		isPaused = false;
		initEnemyCount = count;
		pwrUp = new PowerUp(PowerUp.pwrUpType.SMALL, player);
		
		// Setting enemies for this level
		enemies = new ArrayList<Enemy>();
		Random rand = new Random();
		for(int i = 0; i < count; i++) {
			float x = rand.nextFloat() * DrawCanvas.CANVAS_WIDTH;
			float y = rand.nextFloat() * DrawCanvas.CANVAS_HEIGHT;
			
			Enemy createdEnemy = new Enemy(player, x, y, i, 5.0f);
			
			if(player.isCollidingWith(createdEnemy) || createdEnemy.isCollidingWithAny(enemies)) {
				i--;
			}else{
				enemies.add( createdEnemy );
			}
		}
		/*
		// Creating canvas with created player, enemies, and powerup
		canvas = new DrawCanvas(player, enemies, pwrUp);
		add(canvas);
		  */    
	}
	
	public void updateEnemyPositions() {
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if(! player.isCollidingWith(e)) {
				if(e.isCollidingWithAny(enemies) || e.isOutOfBounds()) {
					destroyEnemy(e);
					Enemy collisionTarget = e.getCollisionTarget(enemies);
					if(collisionTarget != null) {
						destroyEnemy(collisionTarget);
					}
				}
				if(oneEnemyLeft()) {
					e.updatePositionFleeingPlayer();
				}else {
					e.updatePositionChasingTarget();	
				}
			}else {
				player.loseLife();
				destroyEnemy(e);
				if(!player.isAlive) {
					pause();
				}
			}
		}
	}
	
	public void destroyEnemy( Enemy e ) {
		enemies.remove( e.ref );
		updateReferences( e.ref );
	}
	
	//After removing enemy, update indexes in (e.ref) starting at index
	private void updateReferences( int startIndex ) {
		for(int i = startIndex; i < enemies.size(); i++) {
			enemies.get( i ).ref = i;
		}
	}
	
	public boolean oneEnemyLeft() {
		return ( enemies.size() < 2 );
	}
	
	public boolean roundOver() {
		return enemies.size() == 0;
	}
	
	public void pause() {
		isPaused = true;
	}
	
	public void setCanvas(DrawCanvas canvas) {
		this.canvas = canvas;
		this.canvas.reset(player, enemies, pwrUp);
		add(canvas);
	}
}
