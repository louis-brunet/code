import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SidePanel extends JPanel {

	TeamCapturedPiecesPanel blackInfoPanel;
	TeamCapturedPiecesPanel whiteInfoPanel;
	JPanel contentPanel;
	JPanel btnPanel;
	JButton resetBtn;
	JButton undoBtn;
	
	public SidePanel(Board b) {
		blackInfoPanel = new TeamCapturedPiecesPanel("Black");
		whiteInfoPanel = new TeamCapturedPiecesPanel("White");
		contentPanel = new JPanel();
		btnPanel = new JPanel();
		resetBtn = new JButton( new ImageIcon("src/resources/reset.png") );
		undoBtn = new JButton( new ImageIcon("src/resources/undo.png") );
		
		contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
		
		resetBtn.setActionCommand("reset");
		/*resetBtn.setOpaque(false);
		resetBtn.setContentAreaFilled(false);
		resetBtn.setBorderPainted(false);
		*/resetBtn.setPreferredSize( new Dimension(40, 40) );
		resetBtn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				b.a.resetBoard();
			}
		});
		
		undoBtn.setPreferredSize( new Dimension(40, 40) );
		undoBtn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				b.a.undoMove();
			}
		});
		
		setLayout( new BorderLayout() );
		contentPanel.setLayout( new BorderLayout() );
		//btnPanel.setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS) );
		
		add(blackInfoPanel, BorderLayout.PAGE_START);
		add(whiteInfoPanel, BorderLayout.PAGE_END);
		add(contentPanel, BorderLayout.LINE_START);
		
		contentPanel.add( btnPanel );
		
		btnPanel.add(resetBtn);
		btnPanel.add(undoBtn);
				
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
