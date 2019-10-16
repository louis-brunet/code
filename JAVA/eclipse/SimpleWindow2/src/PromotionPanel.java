
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class PromotionPanel extends JPanel {
	
	PromotionCell[] promotionBtns;
	Board linkedBoard;

	public PromotionPanel(Board b) {
		
		linkedBoard = b;
		promotionBtns = new PromotionCell[4];
		
		setLayout( new GridLayout(1, 4) );
		
		promotionBtns[0] = new PromotionCell(Piece.queen, b);
		promotionBtns[1] = new PromotionCell(Piece.knight, b);
		promotionBtns[2] = new PromotionCell(Piece.bishop, b);
		promotionBtns[3] = new PromotionCell(Piece.rook, b);
		
		for(int i=0; i<promotionBtns.length; i++) {
			addPromotionListener(promotionBtns[i]);
			add(promotionBtns[i]);
		}
		
	}
	
	public void addPromotionListener(PromotionCell pc) {
		pc.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				pc.doPromotion(linkedBoard);
				linkedBoard.gameOver = false;
			}
		});
	}
	
	public void setTeam(String team) {
		for(int i=0; i<promotionBtns.length; i++) {
			 promotionBtns[i].updateTeam(team);
		}
	}
	
}
