import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class DrawCanvas extends JPanel {
	static final int CANVAS_WIDTH = 840;//900
	static final int CANVAS_HEIGHT= 950;
	
	Player player; 
	Enemy[][] enemies;
	ArrayList<Projectile> projectiles;
	/**
	 * Create the panel.
	 */
	public DrawCanvas(Player p, Enemy[][] enemies, ArrayList<Projectile> projectiles) {
		this.player = p;
		this.enemies = enemies;
		this.projectiles = projectiles;
		setPreferredSize( new Dimension( CANVAS_WIDTH, CANVAS_HEIGHT ) );
	}
	
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g);     // saint parent's background
       setBackground(Color.BLACK);  // set background color for this JPanel

       // Drawing player
       drawPlayer(g, player);
       g.drawLine(0, player.yCenter, CANVAS_WIDTH, player.yCenter);
       
       // Drawing enemies and displaying their position
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
       
      
       
       /*   
       int pointsBody[][] = {{0,1}, {0,4}, {1,4}, {1,5}, {2,5}, {2,6}, {3,6}, {3,7},
    		   {2,7}, {2,8}, {3,8}, {3,7}, {4,7}, {4,6}, {7,6}, {7,7}, {8,7}, 
    		   {8,8}, {9,8}, {9,7}, {8,7}, {8,6}, {9,6}, {9,5}, {10,5}, {10,4}, 
    		   {11,4}, {11,1}, {10,1}, {10,3}, {9,3}, {9,1}, {8,1}, {8,0}, {6,0}, 
    		   {6,1}, {8,1}, {8,2}, {3,2}, {3,1}, {5,1}, {5,0}, {3,0}, {3,1}, {2,1},
    		   {2,3}, {1,3}, {1,1}, {0,1}};
       int npoints = pointsBody.length;
       int xpoints[] = new int[npoints]; 
       int ypoints[] = new int[npoints]; 
       int sizeModifier = 10;
       
       for(int i=npoints - 1, index = 0; i>=0; i--, index++) {
    	   xpoints[index] = 20+sizeModifier*pointsBody[i][0];
    	   ypoints[index] = 20+sizeModifier*(8-pointsBody[i][1]);
       }
       
       g.fillPolygon(xpoints, ypoints, npoints);*/
       
       // Displaying player position
       //g.drawString("Player at "+ player.xMin +", "+ player.yMin +" increment = "+ Player.speed+" lives = "+player.lives, 10, 20);
       /*
       if(!player.isAlive) {
    	   g.setColor(Color.RED);
    	   g.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 32));
    	   g.drawString("Game Over :(", (CANVAS_WIDTH/2)-100 , (CANVAS_HEIGHT/2));
       }else if(enemies.==0) {
    	   g.setColor(Color.RED);
    	   g.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 32));
    	   g.drawString("Round Won !", (CANVAS_WIDTH/2)-100 , (CANVAS_HEIGHT/2));
       }*/
    }
	/*
	public static float getRandomHeightInBounds() {
		Random rand = new Random();
		return rand.nextFloat()*CANVAS_HEIGHT;
	}
	
	public static float getRandomWidthInBounds() {
		Random rand = new Random();
		return rand.nextFloat()*CANVAS_WIDTH;
	}
	*/
	
	private void drawEnemy(Graphics g, Enemy e) {
		// Draw body
		g.setColor(e.color);
        g.fillPolygon(e.xpoints, e.ypoints, e.npoints);
        // Draw eyes
        g.setColor(e.colorEyes);
        g.fillRect(e.eyePositions[0][0], e.eyePositions[0][1], (int) e.sizeModifier, (int) e.sizeModifier);
        g.fillRect(e.eyePositions[1][0], e.eyePositions[1][1], (int) e.sizeModifier, (int) e.sizeModifier);
	}
	
	private void drawPlayer(Graphics g, Player p) {
		g.setColor(player.color);
		//g.fillRect((int) player.xLeft,(int)  player.yTop,(int)  player.width,(int)  player.height);
		g.fillPolygon(p.xpoints, p.ypoints, p.npoints);
	}
	
	private void drawItem(Graphics g, LevelItem item) {
		g.setColor(item.color);
        g.fillPolygon(item.xpoints, item.ypoints, item.npoints);
	}
}
