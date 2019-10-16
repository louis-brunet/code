import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TeamCapturedPiecesPanel extends JPanel {

	String team;
	HashMap<String, Integer> capturedPieces;
	HashMap<JLabel, JLabel> pieceLabels;
	HashMap<String, JLabel> linkPiecesToLabels;
	JPanel imagesPanel;
	JPanel countPanel;
	
	public TeamCapturedPiecesPanel(String team) {
		
		this.team = team;
		capturedPieces = new HashMap<>(); //<iconPath, number of occurrences>
		pieceLabels = new HashMap<>(); //<iconPath, iconLabel>
		linkPiecesToLabels = new HashMap<>(); //<iconLabel, countLabel>

		setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS ) );
		setBorder(BorderFactory.createTitledBorder(team));
		setPreferredSize( new Dimension( 300, 120 ) );

		imagesPanel = new JPanel();
		imagesPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 4) );
		
		countPanel = new JPanel();
		imagesPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 4) );
		
		add(imagesPanel);
		add(countPanel);
		
	}
	
	public void addOne(Piece p) {
		if( capturedPieces.containsKey( p.iconPath ) ) {
			capturedPieces.replace( p.iconPath, capturedPieces.get(p.iconPath)+1 );
			JLabel imageLabel = linkPiecesToLabels.get(p.iconPath);
			JLabel countLabel = pieceLabels.get(imageLabel);
			
			countLabel.setText(capturedPieces.get(p.iconPath).toString());
		}else {
			capturedPieces.put(p.iconPath, 1);
			JLabel imageLabel = getInitImage(p);
			linkPiecesToLabels.put(p.iconPath, imageLabel);
			JLabel countLabel =  new JLabel( "1", JLabel.CENTER );
			pieceLabels.put( imageLabel, countLabel );
			
			imagesPanel.add(imageLabel);
			countPanel.add(countLabel);
		}	
	}
	
	public JLabel getInitImage(Piece p){
		ImageIcon ii = new ImageIcon(p.iconPath);
		return new JLabel(ii, JLabel.CENTER);
		
	}

}
