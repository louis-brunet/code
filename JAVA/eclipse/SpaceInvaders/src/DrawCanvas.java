import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;


public class DrawCanvas extends JPanel {
	static final int CANVAS_WIDTH = 880;
	static final int CANVAS_HEIGHT = 920;
	private static final boolean drawHitboxes = false;
	
	Player player; 
	Enemy[][] enemies;
	ArrayList<Projectile> projectiles;
	Bunker[] bunkers;
	private String score; 
	private String lives;
	private int enemyCount;

	protected int xMouse;
	protected int yMouse;
	protected float xRelative; // 0.0f to 1.0f
	protected float yRelative; 
	
	public DrawCanvas(Player p, Enemy[][] enemies, ArrayList<Projectile> projectiles, Bunker[] bunkers) {
		xMouse = -1;
		yMouse = -1;
		
		setItems(p, enemies, projectiles, bunkers);
		setPreferredSize( new Dimension( CANVAS_WIDTH, CANVAS_HEIGHT ) );
	}
	
	public void setItems(Player p, Enemy[][] enemies, ArrayList<Projectile> projectiles, Bunker[] bunkers) {
		this.player = p;
		this.enemies = enemies;
		this.projectiles = projectiles;
		this.score = "" + 0;
		this.enemyCount = -1;// initialised on first frame refresh
		this.bunkers = bunkers;
	}
	
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g);     // saint parent's background
       setBackground(Color.BLACK);  // set background color for this JPanel

       // Drawing player
       drawItem(g, player);
       //g.drawLine(0, player.yCenter, CANVAS_WIDTH, player.yCenter);
       
       // Drawing enemies
       for(int i=0; i<enemies.length; i++) {
	       for(Enemy e: enemies[i]) {
	           if(e!=null) {
	        	   drawEnemy(g, e);
	           }
	       }
       }

       // Drawing projectiles
       for(Projectile proj: projectiles) {
    	   drawItem(g, proj);
       }
       // Drawing bunkers
       for(Bunker bunker: bunkers) {
    	   if(bunker != null) {
    		   drawItem(g, bunker);
    	   }
       }
       
       if(!player.isAlive) {
    	   g.setFont( new Font(Font.MONOSPACED, Font.PLAIN, 22));
    	   g.setColor(Color.WHITE); 
    	   g.drawString("Play again ? [ENTER]", (CANVAS_WIDTH/2)-124 , 117);
    	   
    	   g.setFont( new Font(Font.MONOSPACED, Font.BOLD, 32));
    	   g.setColor(Color.RED);
    	   g.drawString("GAME OVER", (CANVAS_WIDTH/2)-80 , 42);
    	   g.setFont( new Font(Font.MONOSPACED, Font.BOLD, 24));
    	   g.drawString("Score : "+score, (CANVAS_WIDTH/2)-80 , 77);

       }else if(enemyCount==0) {
    	   g.setColor(Color.RED);
    	   g.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 32));
    	   g.drawString("Wave cleared !", (CANVAS_WIDTH/2)-100 , (CANVAS_HEIGHT/2));
       }
       
       // Draw score
       g.setColor(Color.WHITE);
	   g.setFont( new Font(Font.DIALOG, Font.BOLD, 24));
	   g.drawString("Score : "+score, 10, CANVAS_HEIGHT - 10);

	   // Draw cursor position
	   if(xMouse != -1) {
		   g.setFont( new Font(Font.MONOSPACED, Font.PLAIN, 16));
		   g.drawString("( "+xMouse+", "+yMouse+" )", 5, 21);
		   g.drawString("( "+xRelative+", "+yRelative+" )", 5, 40);
	   }
       
	   
       
       
    }
	
	private void drawEnemy(Graphics g, Enemy e) {
		// Draw body
		drawItem(g, e);
		
        // Draw eyes
        g.setColor(e.colorEyes);
        g.fillRect(e.eyePositions[0][0], e.eyePositions[0][1], (int) e.sizeModifier, (int) e.sizeModifier);
        g.fillRect(e.eyePositions[1][0], e.eyePositions[1][1], (int) e.sizeModifier, (int) e.sizeModifier);
	}
	
	private void drawItem(Graphics g, LevelItem item) {
		g.setColor(item.color);
        
		if(item.itemType == LevelItem.ItemType.BUNKER) {
			Bunker b = (Bunker) item;
			if(b.hasLeft) {
				g.fillPolygon(b.xpointsLeft, b.ypointsLeft, b.npointsSides);
			}
			if(b.hasRight) {
				g.fillPolygon(b.xpointsRight, b.ypointsRight, b.npointsSides);
			}
			if(b.hasTop) {
				g.fillPolygon(b.xpointsTop, b.ypointsTop, b.npointsTop);
			}
		}else {
			g.fillPolygon(item.xpoints, item.ypoints, item.npoints);
		}
		
		if(drawHitboxes) {
			g.drawLine(item.xLeft, item.yTop, item.xLeft, item.yBottom);
	        g.drawLine(item.xRight, item.yTop, item.xRight, item.yBottom);
	        g.drawLine(item.xLeft, item.yTop, item.xRight, item.yTop);
	        g.drawLine(item.xLeft, item.yBottom, item.xRight, item.yBottom);
		}
	}
	
	public void setScore(int score) {
		this.score = "" + score;
	}

	public void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}
}
