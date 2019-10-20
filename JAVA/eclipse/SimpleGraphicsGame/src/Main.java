import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

	public static final int REFRESH_DELAY = 50;
	private DrawCanvas canvas;
	private Level currentLevel;

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
		currentLevel = new Level( 3 );
		// Set the Level JPanel as the JFrame's content-pane
		Container cp = getContentPane();
		cp.add(currentLevel);
		// or "setContentPane(canvas);"
	 
		setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
		pack();              // Either pack() the components; or setSize()
		setTitle("......");  // "super" JFrame sets the title
		setVisible(true);    // "super" JFrame show
		
		
		addKeyListener(new KeyAdapter() {
	          @Override
	          public void keyPressed(KeyEvent evt) {
	        	  System.out.println("key pressed");
	        	  switch(evt.getKeyCode()) {
	        	  	case KeyEvent.VK_LEFT:  
	        	  		currentLevel.player.moveLeft();  
	        	  		break;
	                case KeyEvent.VK_RIGHT:	
	                	currentLevel.player.moveRight(); 
	                	break;
	                case KeyEvent.VK_UP:	
	                	currentLevel.player.moveUp(); 	
	                	break;
	                case KeyEvent.VK_DOWN:	
	                	currentLevel.player.moveDown(); 	
	                	break;
	                //case KeyEvent.VK_ESCAPE: canvas.rectMvmtActive = ! canvas.rectMvmtActive ; break;
	                case KeyEvent.VK_SPACE:
	                	currentLevel.isPaused = !currentLevel.isPaused; 
	                	break;
	        	  }
	             
	          }
	       });	

	      Thread refreshThread = new Thread() {
	    	  @Override
	    	  public void run() {
	    		  while ( currentLevel.player.isAlive ) {
	    			while(!currentLevel.isPaused) {
		    			  currentLevel.updateEnemyPositions();
		    			  if(currentLevel.roundOver()) {
		    				  currentLevel.pause();
		    			  }
		    			  currentLevel.canvas.repaint();
		    			  
		    			  try {
		    				  // Delay and give other thread a chance to run
		    				  Thread.sleep(REFRESH_DELAY);  // milliseconds
		    			  } catch (InterruptedException ignore) {}
	    		  	}
	    			if( currentLevel.oneEnemyLeft()&&currentLevel.player.isAlive ) {
	    				try {
		    				  Thread.sleep(2000);
		    			} catch (InterruptedException ignore) {}  
	    				nextLevel();
	    			}
	    			try {
	    				  Thread.sleep(100);
	    			} catch (InterruptedException ignore) {}
	    		  }
	    	  }
	      };
	      refreshThread.start(); // called back run()
	}
	
	private void nextLevel() {
		remove(currentLevel);
		currentLevel = new Level ( currentLevel.initEnemyCount + 1 ) ;
		add(currentLevel);
		revalidate();
	}

}
