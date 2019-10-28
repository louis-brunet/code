import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
	
	private JLabel whiteLabel;
	private JLabel blackLabel;
	private JCheckBox showCheckBox;
	private int whiteScore;
	private int blackScore;
	
	
	public ScorePanel() {
		whiteLabel = new JLabel("White : 0");
		whiteLabel.setVisible(false);
		
		blackLabel = new JLabel("Black : 0");
		blackLabel.setVisible(false);
		
		whiteScore = 0;
		blackScore = 0;
		
		showCheckBox = new JCheckBox("Show scores");
		showCheckBox.setOpaque(false);
		showCheckBox.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        JCheckBox cb = (JCheckBox) event.getSource();
		        if (cb.isSelected()) {
		            whiteLabel.setVisible(true);
		            blackLabel.setVisible(true);
		        } else {
		        	whiteLabel.setVisible(false);
		            blackLabel.setVisible(false);
		        }
		    }
		});
		
		setLayout( new BorderLayout() );
		setOpaque(false);
		
		add(blackLabel, BorderLayout.PAGE_START);
		
		JPanel lowerPanel = new JPanel( new BorderLayout());
		lowerPanel.setOpaque(false);
		lowerPanel.add(showCheckBox, BorderLayout.PAGE_END);
		lowerPanel.add(whiteLabel, BorderLayout.PAGE_START);
		add(lowerPanel);
	}

	public void incrementScore(String team) {
		if(team == Piece.white) {
			whiteScore++;
			whiteLabel.setText("White : " + whiteScore);
		}else if(team == Piece.black) {
			blackScore++;
			blackLabel.setText("Black : " + blackScore);
		}
	}
	
}
