import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * JPanel used for drawing
 */
public class DrawPanel extends JPanel {
	
	private int rectXSpeed = 7;
	private int rectYSpeed = 7;
	public int playerSpeed = 15;
	public boolean rectMvmtActive;
	int xOval;
	int yOval;
	final int wOval = 20;
	final int hOval = 30;
	
	int xRect;
	int yRect;
	final int wRect = 10;
	final int hRect = 10;
    
	public DrawPanel() {
		xOval = 300;
		yOval = 210;
		xRect = 400;
		yRect = 350;
		rectMvmtActive = true;
	}
	
	// Override paintComponent to perform your own painting
    @Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g);     // paint parent's background
       setBackground(Color.BLACK);  // set background color for this JPanel

       // Your custom painting codes. For example,
       // Drawing primitive shapes
       g.setColor(Color.YELLOW);    // set the drawing color
       g.drawLine(30, 40, 100, 200);
       g.drawOval(150, 180, 10, 10);
       g.drawRect(200, 210, 20, 30);
       g.setColor(Color.RED);       // change the drawing color
       g.fillOval(xOval, yOval, wOval, hOval);
       g.fillRect(xRect, yRect, wRect, hRect);
       // Printing texts
       g.setColor(Color.WHITE);
       g.setFont(new Font("Monospaced", Font.PLAIN, 17));
       g.drawString("Ennemy rect at"+xRect+", "+yRect+" Speed = "+rectXSpeed + ", "+rectYSpeed, 10, 20);
       g.drawString("Player at"+xOval+", "+yOval+" Speed = "+playerSpeed, 10, 40);

       
    }
    
    
    public void moveOval(int xOffset, int yOffset) {
    	xOval += xOffset;
    	yOval += yOffset;
    	repaint();
    }
    
    public void updateRectPosition() {
    	/*if(yRect+rectSpeed < 0 || yRect+rectSpeed > Main.CANVAS_HEIGHT-50) {
    		rectSpeed *= -1;
    	}
    	yRect += rectSpeed;*/
    	
    	//if( Math.abs(xRect - xOval) > Math.abs(yRect - yOval) ) {
    		if( Math.abs(xRect + rectXSpeed - xOval) > Math.abs(xRect - xOval) ) {
    			rectXSpeed *= -1;
    		}
    		
    		if(Math.abs(xRect + rectXSpeed - xOval) < Math.abs(xRect - xOval) ) {
    			xRect += rectXSpeed;
    		}
    	//}else {
    		if( Math.abs(yRect + rectYSpeed - yOval) > Math.abs(yRect - yOval) ) {
    			rectYSpeed *= -1;
    		}
    		if(Math.abs(yRect + rectYSpeed - yOval) < Math.abs(yRect - yOval) ) {
    			yRect += rectYSpeed;
    		}
    	//}
    	
    }
    
    public boolean isColliding() {
 	   return (yOval < yRect+hRect 
 			   && yOval+hOval > yRect 
 			   && xOval < xRect+wRect 
 			   && xOval+wOval > xRect);
    }
    
 }

















