import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

	TeamCapturedPiecesPanel blackInfoPanel;
	TeamCapturedPiecesPanel whiteInfoPanel;
	
	public InfoPanel() {
		blackInfoPanel = new TeamCapturedPiecesPanel("Black");
		whiteInfoPanel = new TeamCapturedPiecesPanel("White");
		
		//setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS) );
		
		
		add(blackInfoPanel);
		add(whiteInfoPanel);
		
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
