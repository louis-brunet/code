import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SidePanel extends JPanel {

	final static Color backgroundColor = Color.getHSBColor(37f/360f, 0.05f, 1f);
	TeamCapturedPiecesPanel blackInfoPanel;
	TeamCapturedPiecesPanel whiteInfoPanel;
	JPanel contentPanel;
	JPanel btnPanel;
	JButton resetBtn;
	JButton undoBtn;
	Board linkedBoard;
	ScorePanel scorePanel;
	
	public SidePanel(Board b) {
		blackInfoPanel = new TeamCapturedPiecesPanel("Black");
		whiteInfoPanel = new TeamCapturedPiecesPanel("White");
		contentPanel = new JPanel();
		btnPanel = new JPanel();
		resetBtn = new JButton( new ImageIcon("src/resources/reset.png") );
		undoBtn = new JButton( new ImageIcon("src/resources/undo.png") );
		linkedBoard = b;
		
		scorePanel = new ScorePanel();
		scorePanel.setBorder( BorderFactory.createEmptyBorder(15,15,15,15) );
		
		contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		resetBtn.setActionCommand("reset");
		//resetBtn.setOpaque(false);
		//resetBtn.setContentAreaFilled(false);
		resetBtn.setBorderPainted(false);
		resetBtn.setBackground(Cell.lightCell);
		resetBtn.setPreferredSize( new Dimension(40, 40) );
		resetBtn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				linkedBoard.a.resetBoard();
				
			}
		});
		
		//undoBtn.setOpaque(false);
		//undoBtn.setContentAreaFilled(false);
		undoBtn.setBorderPainted(false);
		undoBtn.setBackground(Cell.lightCell);
		undoBtn.setPreferredSize( new Dimension(40, 40) );
		undoBtn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				linkedBoard.a.undoMove();
				
			}
		});
		
		setLayout( new BorderLayout() );
		setBackground(backgroundColor);

		contentPanel.setLayout( new BorderLayout() );
		contentPanel.setBackground(backgroundColor);
		btnPanel.setLayout( new BorderLayout() );
		
		btnPanel.setBackground(backgroundColor);

		this.add(blackInfoPanel, BorderLayout.PAGE_START);
		this.add(whiteInfoPanel, BorderLayout.PAGE_END);
		this.add(contentPanel, BorderLayout.LINE_START);
		this.add(scorePanel, BorderLayout.LINE_END);
		
		contentPanel.add(btnPanel, BorderLayout.LINE_START);
		//contentPanel.add(scorePanel, BorderLayout.LINE_END);
		
		btnPanel.add(undoBtn, BorderLayout.PAGE_END);
		btnPanel.add(resetBtn, BorderLayout.PAGE_START);
				
	}
	
	public TeamCapturedPiecesPanel getInfoPanel(String team) {
		if(team==Piece.white) {
			return whiteInfoPanel;
		}else if(team==Piece.black) {
			return blackInfoPanel;
		}
		else return null;
	}
	

	
}
