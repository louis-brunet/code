import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

	private static final int REFRESH_DELAY = 30;
	private static final int PROJECTILE_COOLDOWN_FRAMES = 15;
	protected static final int enemiesPerRow = 12;
	protected static final int rows = 4;
	private JPanel contentPane;
	private DrawCanvas canvas;
	private Player player;
	private Enemy[][] enemies;
	protected int enemyCount;
	private ArrayList<Projectile> projectiles;
	private int framesSinceProjectile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
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
		//setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(2, 2, 2, 2));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		player = new Player();
		
		projectiles = new ArrayList<Projectile>();
		
		enemies = new Enemy[rows][enemiesPerRow];
		enemyCount = rows*enemiesPerRow;
		
		int width = (int) ((float) Enemy.BODY_RELATIVE_WIDTH * LevelItem.DEFAULT_SIZE_MODIF);
		int height = (int) ((float) Enemy.BODY_RELATIVE_HEIGHT * LevelItem.DEFAULT_SIZE_MODIF);
		for(int i=0; i<rows; i++) {
			for(int j=0; j<enemiesPerRow; j++) {
				enemies[i][j] = new Enemy(40+j*(10+width), 100+i*(height+5));
				if(i%2 == j%2) {
					enemies[i][j].setColor(Color.magenta);
				}
			}
		}
		
		DrawCanvas canvas = new DrawCanvas(player, enemies, projectiles);
		
		contentPane.add(canvas);
		pack();
		
		addKeyListener(new KeyAdapter() {
	          @Override
	          public void keyPressed(KeyEvent evt) {
	        	  System.out.println("key pressed");
	        	  switch(evt.getKeyCode()) {
	        	  	case KeyEvent.VK_LEFT:  
	        	  		player.setXLeft(player.xLeft - player.speed);  
	        	  		break;
	                case KeyEvent.VK_RIGHT:	
	                	player.setXLeft(player.xLeft + player.speed);
	                	break;
	                case KeyEvent.VK_SPACE:
	                	if(framesSinceProjectile > PROJECTILE_COOLDOWN_FRAMES) {
	                		projectiles.add(player.shootProjectile());
	                		framesSinceProjectile = 0;
	                	}
	                	break;
	        	  }
	             
	          }
	       });	
		
		Thread updateThread = new Thread() {
			@Override
			public void run() {
				while(true) {
					// Update enemy positions
					for(int i=0; i<rows; i++) {
						for(int j=0; j<enemiesPerRow; j++) {
							if(enemies[i][j] != null) {
								enemies[i][j].doDefaultMovement();
							}
						}
					}
					
					// Update projectile positions, do collisions
					for(int i=0; i < projectiles.size(); i++) {
						Projectile proj = projectiles.get(i);
						proj.doDefaultMovement();
						LevelItem collidingItem = proj.doCollisionCheck(player, enemies, projectiles);
						if(collidingItem != null) {
							projectiles.remove(proj);
							System.out.println("Colliding with "+collidingItem.toString());
							switch(collidingItem.itemType) {
								case ENEMY:
									removeEnemy(collidingItem);
									break;
								case PLAYER:
									player.loseLife();
									break;
								case PROJECTILE:
									projectiles.remove(collidingItem);
									break;
							}
						}
					}
					framesSinceProjectile++;
					  
					try {
	    				  Thread.sleep(REFRESH_DELAY);
	    			} catch (InterruptedException ignore) {}
					canvas.repaint();
				}
			}
		};
		updateThread.start();
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

}
