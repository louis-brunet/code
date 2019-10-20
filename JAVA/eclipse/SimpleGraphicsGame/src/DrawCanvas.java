import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawCanvas extends JPanel {
	static final int CANVAS_WIDTH = 900;
	static final int CANVAS_HEIGHT= 950;
	
	Player player; 
	ArrayList<Enemy> enemies;
	/**
	 * Create the panel.
	 */
	public DrawCanvas(Player p, ArrayList<Enemy> enemies) {
		this.player = p;
		this.enemies = enemies;
		setPreferredSize( new Dimension( CANVAS_WIDTH, CANVAS_HEIGHT ) );
	}
	
	@Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g);     // paint parent's background
       setBackground(Color.BLACK);  // set background color for this JPanel

       // Your custom painting codes. For example,
       // Drawing primitive shapes
       g.setColor(player.color);    // set the drawing color
       g.fillOval((int) player.xMin,(int)  player.yMin,(int)  player.width,(int)  player.height);
       
       // Drawing enemies and displaying their position
       for(Enemy e: enemies) {
           g.setColor(e.color);
    	   g.fillRect( (int) e.xMin, (int) e.yMin, (int) Enemy.width, (int) Enemy.height);
       }
       
       // Displaying player position
       g.drawString("Player at "+ player.xMin +", "+ player.yMin +" Speed = "+ Player.speed, 10, 20);
       
       if(!player.isAlive) {
    	   g.setColor(Color.RED);
    	   g.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 32));
    	   g.drawString("Game Over :(", (CANVAS_WIDTH/2)-100 , (CANVAS_HEIGHT/2));
       }else if(enemies.size()<2) {
    	   g.setColor(Color.RED);
    	   g.setFont( new Font(Font.SANS_SERIF, Font.BOLD, 32));
    	   g.drawString("Round Won !", (CANVAS_WIDTH/2)-100 , (CANVAS_HEIGHT/2));
       }
    }
	

}
