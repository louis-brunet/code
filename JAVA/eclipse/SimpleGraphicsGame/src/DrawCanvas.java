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
