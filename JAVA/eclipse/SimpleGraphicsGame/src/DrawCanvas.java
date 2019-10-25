import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class DrawCanvas extends JPanel {
	static final int CANVAS_WIDTH = 900;
	static final int CANVAS_HEIGHT= 950;
	
	Player player; 
	ArrayList<Enemy> enemies;
	PowerUp pwrUp;
	/**
	 * Create the panel.
	 */
	public DrawCanvas(Player p, ArrayList<Enemy> enemies, PowerUp pwrUp) {
		this.player = p;
		this.enemies = enemies;
		this.pwrUp = pwrUp;
		setPreferredSize( new Dimension( CANVAS_WIDTH, CANVAS_HEIGHT ) );
	}
	
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g);     // saint parent's background
       setBackground(Color.BLACK);  // set background color for this JPanel

       // Drawing player
       g.setColor(player.color);    // set the drawing color
       g.fillOval((int) player.xMin,(int)  player.yMin,(int)  player.width,(int)  player.height);
       
       // Drawing enemies and displaying their position
       for(Enemy e: enemies) {
           g.setColor(e.color);
    	   g.fillRect( (int) e.xMin, (int) e.yMin, (int) Enemy.width, (int) Enemy.height);
       }
       
       //TODO: draw powerup
       g.setColor(pwrUp.color);
       g.fillRoundRect((int) pwrUp.xMin, (int) pwrUp.yMin,
    		   (int) pwrUp.width, (int) pwrUp.height, 
    		   (int) pwrUp.width/2, (int) pwrUp.height/2);
       
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
       
       g.fillPolygon(xpoints, ypoints, npoints);
       
       // Displaying player position
       g.drawString("Player at "+ player.xMin +", "+ player.yMin +" increment = "+ Player.speed+" lives = "+player.lives, 10, 20);
       
       if(!player.isAlive) {
    	   g.setColor(Color.RED);
    	   g.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 32));
    	   g.drawString("Game Over :(", (CANVAS_WIDTH/2)-100 , (CANVAS_HEIGHT/2));
       }else if(enemies.size()==0) {
    	   g.setColor(Color.RED);
    	   g.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 32));
    	   g.drawString("Round Won !", (CANVAS_WIDTH/2)-100 , (CANVAS_HEIGHT/2));
       }
    }
	
	public static float getRandomHeightInBounds() {
		Random rand = new Random();
		return rand.nextFloat()*CANVAS_HEIGHT;
	}
	
	public static float getRandomWidthInBounds() {
		Random rand = new Random();
		return rand.nextFloat()*CANVAS_WIDTH;
	}
	
	public void reset( Player p, ArrayList<Enemy> enemies, PowerUp pwrUp) {
		this.player = p;
		this.enemies = enemies;
		this.pwrUp = pwrUp;
		repaint();
	}
}
