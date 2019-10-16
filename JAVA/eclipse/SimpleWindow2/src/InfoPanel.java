import java.awt.BorderLayout;

import javax.swing.JPanel;

public class InfoPanel extends JPanel {

	TeamCapturedPiecesPanel blackInfoPanel;
	TeamCapturedPiecesPanel whiteInfoPanel;
	PromotionPanel promotionPanel;
	
	public InfoPanel(Board b) {
		blackInfoPanel = new TeamCapturedPiecesPanel("Black");
		whiteInfoPanel = new TeamCapturedPiecesPanel("White");
		promotionPanel = new PromotionPanel(b);
		
		promotionPanel.setVisible(false);
		
		setLayout( new BorderLayout() );
		
		add(blackInfoPanel, BorderLayout.PAGE_START);
		add(whiteInfoPanel, BorderLayout.PAGE_END);
		add(promotionPanel, BorderLayout.CENTER);
		
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
