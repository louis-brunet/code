import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Cell extends JButton{
	int row, col;
	Piece piece;
	ImageIcon image;
	int[][] possibleMoves;
	boolean canMove;
	
	/**
	 * Create initial cell for row, col
	 */
	public Cell(int row, int col) {
		initAttributes(row, col);
				
		displayImage();
		initBackground();
		
		setBorder(null);
		setPreferredSize( new Dimension(70, 70) );
	}
	
	//creates a copy of cell c
	public Cell(Cell c) {
		row = c.row;
		col = c.col;
		piece = new Piece(c.piece);
	}
	
	/**
	 * Set button icon to piece png
	 */
	public void displayImage() {
		image = new ImageIcon(piece.iconPath);
		setIcon(image);	
	}
	
	/**
	 * Initialise attributes for cell at row, col
	 */
	private void initAttributes(int row, int col) {
		this.row = row;
		this.col = col;
		piece = new Piece(row, col);
	}
	
	/**
	 * Initialise cell background
	 */
	public void initBackground() {
		if(row%2 == 0) {
			if(col%2 == 0) {
				setBackground(Color.WHITE);
			}else {
				setBackground(Color.GRAY);
			}
		}else if (col%2 == 0) {
			setBackground(Color.GRAY);
		}else {
			setBackground(Color.WHITE);
		}
		
	}
	
	
	/**
	 * Set button click listener to move one of current player's pieces
	 */
	public void addPieceSelectedListener(Board b) {
		addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				if(! b.gameOver ) {
					Cell clickedCell = (Cell) e.getSource();
					//case where player selects starting cell
					if( piece.team == b.currentPlayer ) {
						//re-initialise old selected piece background if another starting piece is chosen
						if( b.selectedCell != null ) {
							b.selectedCell.initBackground();
						}
						setBackground(Color.YELLOW);
						b.selectedCell = clickedCell;
						
					}
					//case where player selects target cell
					else if( b.selectedCell != null && !(clickedCell.piece.isSameTeam(b.selectedCell.piece))) {
						b.setPossibleMoves(b.selectedCell);
						if ( b.selectedCell.canMoveTo( clickedCell, b )) {
							Cell friendlyKing = b.getKingLocation(b.currentPlayer);
							if( b.isThreatened(friendlyKing, b.currentPlayer) ) {
								Board b2 = new Board(b, b.selectedCell, clickedCell);
								if( b.selectedCell.piece.type != Piece.king && !b2.isThreatened(b2.board[friendlyKing.row][friendlyKing.col], b.currentPlayer) ){
									b.targetCell = clickedCell;
									b.moveSelected();
								}else if( b.selectedCell.piece.type == Piece.king && !b2.isThreatened(b2.board[clickedCell.row][clickedCell.col], b.currentPlayer) ) {
									b.targetCell = clickedCell;
									b.moveSelected();
								}
							}else {
								b.targetCell = clickedCell;
								b.moveSelected();
							}
						}
					}
				}
			}
		});
	}
	
	/**
	 * return true if move is available, false otherwise. 
	 */
	public boolean canMoveTo( Cell target, Board b ) {		
		for(int i = 0; i < possibleMoves.length; i++) {
			if( (possibleMoves[i][0] == target.row) && ( possibleMoves[i][1] == target.col ) ) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Update UI with player move
	 */
	public void movePieceTo(Cell c, Board b) {		
		if ( piece.type == "" ) {
			return;
		}
		
		boolean isPawnPromotion = (c.row == 0 && b.currentPlayer == Piece.white && b.selectedCell.piece.type == Piece.pawn) || 
				(c.row == 7 && b.currentPlayer == Piece.black && b.selectedCell.piece.type == Piece.pawn); 
		
		if( isPawnPromotion ) {
			b.gameOver = true;
			b.a.infoPanel.promotionPanel.setTeam(b.currentPlayer);
			b.a.infoPanel.promotionPanel.setVisible(true);
		}else {
			c.piece = new Piece(piece);
			c.displayImage();
			piece.empty();
			displayImage();
			b.updateGameOver();
			b.nextPlayer();
		}
		
	}
	
	public boolean isThreatenedByKnightAt(int knightRow, int knightCol, String team, Board b) {
		if(knightRow>=0 && knightRow<8 && knightCol>=0 && knightCol<8) {
			if(b.board[knightRow][knightCol].piece.type == Piece.knight && b.board[knightRow][knightCol].piece.isOpposingTeam(team) ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isThreatenedByPawn(String team, Board b) {
		int pawnRow;
		if(team == Piece.white) {
			pawnRow = row-1;
		}else {
			pawnRow = row+1;
		}
		if(pawnRow>=0 && pawnRow<8) {
			if( col-1>=0 && b.board[pawnRow][col-1].piece.type == Piece.pawn && b.board[pawnRow][col-1].piece.isOpposingTeam( team ) ) {
				return true;
			}
			if( col+1<8 && b.board[pawnRow][col+1].piece.type == Piece.pawn && b.board[pawnRow][col+1].piece.isOpposingTeam( team ) ) {
				return true;
			}	
		}
		return false;
	}
	
	public Cell getPawnThreat(String team, Board b) {
		int pawnRow;
		if(team == Piece.white) {
			pawnRow = row-1;
		}else {
			pawnRow = row+1;
		}
		if(pawnRow>=0 && pawnRow<8) {
			if( col-1>=0 && b.board[pawnRow][col-1].piece.type == Piece.pawn && b.board[pawnRow][col-1].piece.isOpposingTeam( team ) ) {
				return b.board[pawnRow][col-1];
			}
			if( col+1<8 && b.board[pawnRow][col+1].piece.type == Piece.pawn && b.board[pawnRow][col+1].piece.isOpposingTeam( team ) ) {
				return b.board[pawnRow][col+1];
			}	
		}
		return this;
	}
	
}
