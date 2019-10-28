import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class TeamCapturedPiecesPanel extends JPanel {

	String team;
	CapturedPiecePanel[] pieces;
	JPanel contentPanel;
	private CapturedPiecePanel pieceAddedLastTurn;
	private boolean wasPieceAdded;
	
	public TeamCapturedPiecesPanel(String team) {
		this.team = team=="White"? Piece.white : Piece.black;
		contentPanel = new JPanel();
		pieces = new CapturedPiecePanel[5];
		pieceAddedLastTurn = null;
		wasPieceAdded = false;
		initPieces();
		
		//setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS ) );
		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), team+" captured pieces");
		titledBorder.setTitleColor(Color.WHITE);
		setBorder(titledBorder);
		
		setBackground(Cell.darkCell);
		setPreferredSize( new Dimension( 250, 160 ) );
		
		contentPanel.setBackground(Cell.lightCell);
		//contentPanel.setBorder(BorderFactory.createEmptyBorder());
		contentPanel.setPreferredSize( new Dimension(230, 125) );
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
				wasPieceAdded = true;
				pieceAddedLastTurn = pieces[i];
			}
		}
	}

	public void reset() {
		for(int i = 0; i < pieces.length; i++) {
			contentPanel.remove(pieces[i]);
			pieces[i].setCount(0);
		}
	}
	
	public void removePieceAddedLastTurn() {
		if(wasPieceAdded && pieceAddedLastTurn != null) {
			System.out.println("Testing removePieceAddedLastTurn() 1:true for "+team);
			pieceAddedLastTurn.setCount(pieceAddedLastTurn.count-1);
			if(pieceAddedLastTurn.count == 0) {
				contentPanel.remove(pieceAddedLastTurn);	
				System.out.println("Testing removePieceAddedLastTurn() 2:true");
			}
		}else {

			System.out.println("Testing removePieceAddedLastTurn() 1:false");
		}
	}
	
	public void noPieceAdded() {
		wasPieceAdded = false;
	}
}
