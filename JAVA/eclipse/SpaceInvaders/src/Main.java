import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

	protected static final int REFRESH_DELAY = 30;
	private static final int WAVE_CLEAR_DELAY = 2000; // milliseconds
	private static final int PROJECTILE_COOLDOWN_FRAMES = 12;
	private static final int MAX_ROWS = 8;
	private static final int FIRST_WAVE_ROWS = 2;
	protected static final int enemiesPerRow = 11;
	protected static int rows = 2;
	private JPanel contentPane;
	private DrawCanvas canvas;
	private Player player;
	private Enemy[][] enemies;
	protected int enemyCount;
	private ArrayList<Projectile> projectiles;
	private Bunker[] bunkers;
	private int framesSinceProjectile;
	private int score;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		player = new Player();
		projectiles = new ArrayList<Projectile>();
		
		rows = FIRST_WAVE_ROWS;
		initEnemies();
		initBunkers();
		score = 0;
		canvas = new DrawCanvas(player, enemies, projectiles, bunkers);
		
		contentPane.add(canvas);
		pack();
		setLocationRelativeTo(null);
		
		/**
		 * display cursor position on click
		 */
		canvas.addMouseListener( new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				canvas.xMouse = evt.getX();
				canvas.yMouse = evt.getY();
				canvas.xRelative = (float) canvas.xMouse / (float) DrawCanvas.CANVAS_WIDTH;
				canvas.yRelative = (float) canvas.yMouse / (float) DrawCanvas.CANVAS_HEIGHT;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/**
		 * Init player movement and projectile shooting
		 */
		addKeyListener(new KeyAdapter() {
	          @Override
	          public void keyPressed(KeyEvent evt) {
	        	  System.out.println("key pressed");
	        	  if(player.isAlive) {
	        		  switch(evt.getKeyCode()) {
		        	  	case KeyEvent.VK_LEFT:  
		        	  		//player.setXLeft(player.xLeft - player.speed);  
		        	  		player.speedGoal = -1 * Player.MAX_SPEED;
		        	  		break;
		                case KeyEvent.VK_RIGHT:	
		                	//player.setXLeft(player.xLeft + player.speed);
		                	player.speedGoal = Player.MAX_SPEED;
		                	break;
		                case KeyEvent.VK_SPACE:
		                	player.isShooting = true;
		                	break;
		        	  }
	        	  }else if(evt.getKeyCode()== KeyEvent.VK_ENTER) {
	        		  player = new Player();
	        		  projectiles = new ArrayList<Projectile>();
	        		  rows = FIRST_WAVE_ROWS;
	        		  initEnemies();
	        		  initBunkers();
	        		  score = 0;
	        		  canvas.setItems(player, enemies, projectiles, bunkers);
	        	  }
	          }
	          
	          @Override
	          public void keyReleased(KeyEvent evt) {
	        	  System.out.println("key released");
	        	  if(player.isAlive  ) {
	        		  if(evt.getKeyCode() == KeyEvent.VK_LEFT && player.speedGoal == -1.0f*Player.MAX_SPEED) {
	        			  player.speedGoal = 0;
	        		  }else if(evt.getKeyCode() == KeyEvent.VK_RIGHT && player.speedGoal == Player.MAX_SPEED) {
	        			  player.speedGoal = 0;
	        		  }
	        	  }
	        	  if(evt.getKeyCode() == KeyEvent.VK_SPACE) {
	        		  player.isShooting= false;
	        	  }
	          }
	       });	
		
		/**
		 * Update enemy & projectile positions
		 */
		Thread updateThread = new Thread() {
			@Override
			public void run() {
				while(/*player.isAlive*/true) {
					while(enemyCount != 0 && player.isAlive) {
						doPlayerMovement();
						
						// Update enemy positions, shoot enemy projectiles
						doDefaultEnemiesMovement();
						
						// Update projectiles' position, do collisions
						doDefaultProjectilesMovement();
						canvas.setScore(score);
						canvas.setEnemyCount(enemyCount);
						
						canvas.repaint();
						  
						try {
		    				  Thread.sleep(REFRESH_DELAY);
		    			} catch (InterruptedException ignore) {}
						
					}	
					//All enemies are dead
					if(player.isAlive) {
						int framesSinceWaveCleared = 0;
						while (framesSinceWaveCleared < (WAVE_CLEAR_DELAY/REFRESH_DELAY)) {
							framesSinceWaveCleared++;
							doDefaultProjectilesMovement();
							doPlayerMovement();
							canvas.setScore(score);
							canvas.repaint();
							try {
			    				  Thread.sleep(REFRESH_DELAY);
			    			} catch (InterruptedException ignore) {}
						}
						if(rows < MAX_ROWS) {
							rows++;
						}
						initEnemies();
						canvas.enemies = enemies;
						canvas.repaint(); 
					}
					
					try {
	    				  Thread.sleep(REFRESH_DELAY);
	    			} catch (InterruptedException ignore) {}
					canvas.repaint();
				}
			}
			
			
		};
		updateThread.start();
	}
	
	private void initEnemies() {
		enemies = new Enemy[rows][enemiesPerRow];
		enemyCount = rows*enemiesPerRow;
		
		int width = (int) ((float) Enemy.BODY_RELATIVE_WIDTH * LevelItem.DEFAULT_SIZE_MODIF);
		int height = (int) ((float) Enemy.BODY_RELATIVE_HEIGHT * LevelItem.DEFAULT_SIZE_MODIF);
		for(int i=0; i<rows; i++) {
			for(int j=0; j<enemiesPerRow; j++) {
				enemies[i][j] = new Enemy(40+j*(8+width), 80+i*(height+4));
				enemies[i][j].setRow(i);
				enemies[i][j].setCol(j);
				if(i%2 == j%2) {
					enemies[i][j].setColor(Color.PINK);
					enemies[i][j].setEyeColor(Color.MAGENTA);
				}
			}
		}
	}
	
	private void initBunkers() {
		float width = ((float) Bunker.RELATIVE_WIDTH * LevelItem.DEFAULT_SIZE_MODIF * Bunker.BUNKER_SIZE_MODIFIER);
		int height = (int) ((float) Bunker.RELATIVE_HEIGHT * LevelItem.DEFAULT_SIZE_MODIF * Bunker.BUNKER_SIZE_MODIFIER);
		int bunkerHeight = player.yTop - height - 80;
		int numBunkers = 4;
		int drawingWidth = (int) (DrawCanvas.CANVAS_WIDTH - 1.6f*width) / ( numBunkers - 1 );//(int) (width*2f) ;// includes empty space between bunkers
		
		//int numBunkers = ( DrawCanvas.CANVAS_WIDTH / drawingWidth ) ;
		bunkers = new Bunker[ numBunkers ];
		
		for(int i = 0; i < numBunkers; i++) {
			int newXLeft = (int) (width/3) + i*drawingWidth;
			bunkers[i] = new Bunker( newXLeft , bunkerHeight);
		}
		
	}
	
	private void removeEnemy(LevelItem e) {
		if(e.itemType != LevelItem.ItemType.ENEMY) {
			return;
		}
		for(int row=0; row<rows; row++) {
			for(int col=0; col<enemiesPerRow; col++) {
				if(enemies[row][col] != null && enemies[row][col].equals(e)) {
					enemies[row][col] = null;
					enemyCount--;
					return;
				}
			}
		}
	}

	
	private void removeBunker(LevelItem b) {
		if(b.itemType != LevelItem.ItemType.BUNKER) {
			return;
		}
		for(int i=0; i<bunkers.length; i++) {
			if(bunkers[i]!=null && bunkers[i].equals(b)) {
				bunkers[i] = null;
				return;
			}
		}
	}

	
	private void doDefaultEnemiesMovement() {
		Random rand = new Random();
		for(int i=0; i<rows; i++) {
			for(int j=0; j<enemiesPerRow; j++) {
				Enemy currEnemy = enemies[i][j];
				if(currEnemy != null) {
					currEnemy.doDefaultMovement();
					
					//check if enemy is colliding with any bunker area
					for(Bunker b: bunkers) {
						if(b != null && b.doCollision(currEnemy)) {
							removeEnemy(currEnemy);
						}
					}
					
					if(currEnemy.isCollidingWith(player)) {
						player.loseLife();
						removeEnemy(currEnemy);
					}
					if(currEnemy.isCompletelyOutOfBounds()) {
						removeEnemy(currEnemy);
						player.loseLife();
					}else if( currEnemy.canShoot(enemies) && rand.nextInt(400) == 0 ){
						projectiles.add(currEnemy.shootProjectile(player));
					}
					
				}
			}
		}
	}
	
	private void doDefaultProjectilesMovement() {
		for(int i=0; i < projectiles.size(); i++) {
			Projectile proj = projectiles.get(i);
			proj.doDefaultMovement();
			if(proj.isCompletelyOutOfBounds()) {
				projectiles.remove(proj);
				System.out.println("Projectile destroyed");
			}else {
				LevelItem collidingItem = proj.doCollisionCheck(player, enemies, projectiles, bunkers);
				if(collidingItem != null) {
					System.out.println("Colliding with "+collidingItem.toString());
					switch(collidingItem.itemType) {
						case ENEMY:
							removeEnemy(collidingItem);
							score += 100;
							projectiles.remove(proj);
							break;
						case PLAYER:
							player.loseLife();
							projectiles.remove(proj);
							break;
						case PROJECTILE:
							// TODO: don't increment if two enemy projectiles collide
							score += 50;
							projectiles.remove(collidingItem);
							projectiles.remove(proj);
							break;
						case BUNKER:
							Bunker b = (Bunker) collidingItem;
							if(doBunkerCollision(b, proj)) {
								if(proj.projType == Projectile.ProjectileType.ENEMY) {	
									if(! (b.hasLeft || b.hasTop || b.hasRight) ) {
										removeBunker(b);
									}
								}
								projectiles.remove(proj);
							}	
							
					}
					

				}
			}
		}
		framesSinceProjectile++;
	}
	
	/**
	 * Do player movement and shooting each frame
	 */
	private void doPlayerMovement() {
		// Update player position
		float speedStep = (float) REFRESH_DELAY / 20f;
		player.speed =  approach(player.speedGoal, player.speed, speedStep);
		int newXLeft = (int) (player.xLeft + player.speed);
		player.setXLeft( newXLeft );
		
		// Do player shooting
		if(player.isShooting && framesSinceProjectile > PROJECTILE_COOLDOWN_FRAMES) {
    		projectiles.add(player.shootProjectile());
    		framesSinceProjectile = 0;
    	}
	}
	
	/**
	 * Returns true if proj destroyed one of bunker's areas
	 * @param bunker
	 * @param proj
	 * @return
	 */
	private boolean doBunkerCollision(LevelItem bunker, Projectile proj) {
		for(Bunker b: bunkers) {
			if(b != null && b.equals(bunker)) {
				return b.doCollision(proj);
			}
		}
		return false;
	}
	
	private float approach(float goal, float current, float dt) {
		float difference = goal - current;
		
		if(difference > dt) {
			return current + dt;
		}
		if(difference < -1*dt) {
			return current - dt;
		}
		
		return goal;
	}
}
