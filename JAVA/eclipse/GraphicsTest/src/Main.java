import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import javax.swing.*;    // Using Swing's components and containers

import org.omg.PortableServer.POAManagerPackage.State;
 
/** Custom Drawing Code Template */
// A Swing application extends javax.swing.JFrame
public class Main extends JFrame {
   // Define constants
	public static final int CANVAS_WIDTH  = 640;
	public static final int CANVAS_HEIGHT = 480;
	public static final int REFRESH_DELAY = 50;
	
   // Declare an instance of the drawing canvas,
   // which is a package class called DrawPanel extending javax.swing.JPanel.
   private DrawPanel canvas;
   
   // Declare a timed task to update shape positions
   private Timer updateUITimer;
   // Constructor to set up the GUI components and event handlers
   public Main() {
	   
      canvas = new DrawPanel();    // Construct the drawing canvas
      canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
 
      // Set the Drawing JPanel as the JFrame's content-pane
      Container cp = getContentPane();
      cp.add(canvas);
      // or "setContentPane(canvas);"
 
      setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
      pack();              // Either pack() the components; or setSize()
      setTitle("......");  // "super" JFrame sets the title
      setVisible(true);    // "super" JFrame show
      
      /*ActionListener updateTask = new ActionListener() {
     	  @Override
     	  public void actionPerformed(ActionEvent e){
     		  if(!canvas.isColliding()) {
     			  updateUITimer.setDelay(REFRESH_DELAY);
     			  canvas.updateRectPosition();
     			  canvas.repaint();

     		  }else {
     			  updateUITimer.setDelay(100);
     		  }
     	  }
       };
       updateUITimer = new Timer(REFRESH_DELAY, updateTask);
       updateUITimer.start();*/
      
   // Create a new thread to run update at regular interval
      Thread updateThread = new Thread() {
         @Override
         public void run() {
        	 while (true) {
            	if(!canvas.isColliding()) {
            		if(canvas.rectMvmtActive) {
            			canvas.updateRectPosition();
                		canvas.repaint();
            		}
            		
            	}
            	try {
            		// Delay and give other thread a chance to run
            		Thread.sleep(REFRESH_DELAY);  // milliseconds
            	} catch (InterruptedException ignore) {}
        	 }
         }
      };
      updateThread.start(); // called back run()
      
      addKeyListener(new KeyAdapter() {
          @Override
          public void keyPressed(KeyEvent evt) {
             switch(evt.getKeyCode()) {
                case KeyEvent.VK_LEFT:  canvas.moveOval(-1*canvas.playerSpeed, 0);  break;
                case KeyEvent.VK_RIGHT: canvas.moveOval(canvas.playerSpeed, 0); break;
                case KeyEvent.VK_UP: canvas.moveOval(0, -1*canvas.playerSpeed); break;
                case KeyEvent.VK_DOWN: canvas.moveOval(0, canvas.playerSpeed); break;
                case KeyEvent.VK_ESCAPE: canvas.rectMvmtActive = ! canvas.rectMvmtActive ; break;
             }
          }
       });
   }
   
  
   // The entry main method
   public static void main(String[] args) {
      // Run the GUI codes on the Event-Dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new Main(); // Let the constructor do the job
         }
      });
   }
}