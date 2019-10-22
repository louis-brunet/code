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

	private static final int REFRESH_DELAY = 40;
	private static final int enemiesPerRow = 8;
	private static final int rows = 5;
	private JPanel contentPane;
	private DrawCanvas canvas;
	private Player player;
	private Enemy[][] enemies;
	private ArrayList<Projectile> projectiles;

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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		player = new Player();
		
		projectiles = new ArrayList<Projectile>();
		
		enemies = new Enemy[rows][enemiesPerRow];
		
		int width = (int) ((float) Enemy.BODY_RELATIVE_WIDTH * LevelItem.DEFAULT_SIZE_MODIF);
		int height = (int) ((float) Enemy.BODY_RELATIVE_HEIGHT * LevelItem.DEFAULT_SIZE_MODIF);
		for(int i=0; i<rows; i++) {
			for(int j=0; j<enemiesPerRow; j++) {
				enemies[i][j] = new Enemy(30+j*(10+width), 100+i*(height+5));
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
	                	projectiles.add(player.shootProjectile());
	                	break;
	        	  }
	             
	          }
	       });	
		
		Thread updateThread = new Thread() {
			@Override
			public void run() {
				while(true) {
					for(int i=0; i<rows; i++) {
						for(int j=0; j<enemiesPerRow; j++) {
							enemies[i][j].doDefaultMovement();
						}
					}
					for(Projectile proj: projectiles) {
						proj.doDefaultMovement();
					}
					
					canvas.repaint();
					try {
	    				  Thread.sleep(REFRESH_DELAY);
	    			} catch (InterruptedException ignore) {}
				}
			}
		};
		updateThread.start();
	}

}
