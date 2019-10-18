import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Application extends JFrame {

	private JPanel contentPane;
	private Board board; //board panel
	public SidePanel sidePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application frame = new Application();
					
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
	public Application() {
		initUI();
	}
	
	private void initUI() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
		contentPane.setLayout(new BorderLayout());
		
		board = new Board(this);
		contentPane.add(board, BorderLayout.LINE_START);
		
		sidePanel = new SidePanel(board);
		contentPane.add(sidePanel, BorderLayout.LINE_END);
		
		setContentPane(contentPane);
		
		pack();
		setLocationRelativeTo(null);
		setTitle("Chess proto 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	public void addOneToCaptured(String team, Piece piece) {
		sidePanel.getInfoPanel(team).addOne(piece);
	}
	
	public void resetBoard(){
		contentPane.remove(board);
		board = new Board(this);
		contentPane.add(board, BorderLayout.LINE_START);
		contentPane.revalidate();
	}
	
	public void undoMove() {
		if(board.lastBoard != null) {
			contentPane.remove(board);
			board = board.lastBoard;
			contentPane.add(board, BorderLayout.LINE_START);
			contentPane.revalidate();
		}
	}
	
}
