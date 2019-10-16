import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PromotionCell extends JButton {
	String type;
	String team;
	
	public PromotionCell(String type, Board b){
		this.type = type;
		this.team = b.currentPlayer;
		String iconPath = "src/resources/"+this.team+this.type+".png";
		
		setIcon( new ImageIcon(iconPath) );
		setBorder(null);
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
		
		b.a.infoPanel.promotionPanel.setVisible(false);
	}
	
	public void updateTeam(String team) {
		this.team = team;
		String iconPath = "src/resources/"+this.team+this.type+".png";
		
		setIcon( new ImageIcon(iconPath) );
	}
}
