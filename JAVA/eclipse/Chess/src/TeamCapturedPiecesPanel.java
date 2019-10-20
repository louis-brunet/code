import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class TeamCapturedPiecesPanel extends JPanel {

	String team;
	CapturedPiecePanel[] pieces;
	JPanel contentPanel;
	
	public TeamCapturedPiecesPanel(String team) {
		this.team = team=="White"? Piece.white : Piece.black;
		contentPanel = new JPanel();
		pieces = new CapturedPiecePanel[5];
		initPieces();
		
		//setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS ) );
		setBorder(BorderFactory.createTitledBorder(team));
		setPreferredSize( new Dimension( 250, 160 ) );

		
		contentPanel.setBorder(BorderFactory.createEmptyBorder());
		add(contentPanel);
	}
	
	private void initPieces() {
		String ennemyTeam = team == Piece.white ? Piece.black : Piece.white;
		pieces[0] = new CapturedPiecePanel(ennemyTeam, Piece.pawn);
		pieces[1] = new CapturedPiecePanel(ennemyTeam, Piece.knight);
		pieces[2] = new CapturedPiecePanel(ennemyTeam, Piece.bishop);
		pieces[3] = new CapturedPiecePanel(ennemyTeam, Piece.rook);
		pieces[4] = new CapturedPiecePanel(ennemyTeam, Piece.queen);
		
		/*for(int i=0; i<pieces.length; i++) {
			add(pieces[i]);
		}*/
	}
	
	public void addOne(Piece p) {
		for(int i=0; i<pieces.length; i++) {
			if(pieces[i].type == p.type) {
				if(pieces[i].count == 0) {
					contentPanel.add(pieces[i]);
				}
				pieces[i].addOne();
			}
		}
	}

}
