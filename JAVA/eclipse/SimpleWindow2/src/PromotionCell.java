import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PromotionCell extends JButton {
	String type;
	String team;
	PromotionDialog dialog;
	
	public PromotionCell(String type, Board b, PromotionDialog dialog){
		this.type = type;
		this.team = b.currentPlayer;
		this.dialog = dialog;
		String iconPath = "src/resources/"+this.team+this.type+".png";
		
		setIcon( new ImageIcon(iconPath) );
		setBorder(null);
		setBackground(null);
		setPreferredSize( new Dimension(70, 70) );
		
	}
	
	public void doPromotion(Board b) {
		b.targetCell.piece = new Piece(b.selectedCell.piece);
		b.targetCell.piece.setType(type);
		b.targetCell.displayImage();
		
		b.selectedCell.piece.empty();
		b.selectedCell.displayImage();
		
		b.updateGameOver();
		b.nextPlayer();
		dialog.setVisible(false);
	}
	
	public void updateTeam(String team) {
		this.team = team;
		String iconPath = "src/resources/"+this.team+this.type+".png";
		
		setIcon( new ImageIcon(iconPath) );
	}
}
