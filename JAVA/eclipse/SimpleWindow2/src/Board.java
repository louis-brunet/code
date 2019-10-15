import javax.swing.JPanel;

import java.awt.GridLayout;
import java.util.Arrays;

public class Board extends JPanel {

	Cell[][] board;
	String currentPlayer;
	Cell selectedCell;
	boolean gameOver;
	InfoPanel infoPanel;
	
	public Board(InfoPanel infoPanel) {
		initBoard();
		
		this.infoPanel = infoPanel;
	}
	
	//creates a copy of board b, move piece at firstCell to targetCell
	public Board(Board b, Cell firstCell, Cell targetCell) {
		setLayout( new GridLayout(8, 8) );
		
		board = new Cell[8][8];
		
		currentPlayer = b.currentPlayer;
		selectedCell = null;
		
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				board[i][j] = new Cell(b.board[i][j]);
				Cell currCell = board[i][j];
				add(currCell);
				//currCell.addPieceSelectedListener(this);
			}
		}
		
		gameOver = b.gameOver;
		
		board[firstCell.row][firstCell.col].movePieceTo(board[targetCell.row][targetCell.col]);
	}
	
	/**
	 * Initialise the Board
	 */
	private void initBoard() {
		setLayout( new GridLayout(8, 8) );
		board = new Cell[8][8];
		
		currentPlayer = Piece.white;
		selectedCell = null;
		
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				board[i][j] = new Cell(i, j);
				Cell currCell = board[i][j];
				add(currCell);
				currCell.addPieceSelectedListener(this);
			}
		}
		
		gameOver = false;
	}
	
	/**
	 * set possibleMoves to array filled with possible positions 
	 */
	public void setPossibleMoves(Cell c) {
		int index = 0;
		int row = c.row;
		int col = c.col;
		String type = c.piece.type;
		String team = c.piece.team;
		boolean isBlocked = false;
		
		c.possibleMoves = new int[40][2];
		
		//rook moves or rook-like queen moves
		if(type == Piece.queen || type == Piece.rook) {
			
			//check above
			for(int i = row-1; (i >= 0) && !isBlocked; i--) {
				Piece targetPiece = board[i][col].piece;
				if( ! targetPiece.isSameTeam( c.piece ) ) {
					c.possibleMoves[index][0] = i;
					c.possibleMoves[index][1] = col;
					index++;
					
					if( targetPiece.isOpposingTeam( c.piece ) ) {
						isBlocked = true;
					}
				}else {
					isBlocked = true;
				}
			}
			isBlocked = false;
			
			//check below
			for(int i = row+1; (i < 8) && !isBlocked; i++) {
				Piece targetPiece = board[i][col].piece;
				if( ! targetPiece.isSameTeam( c.piece ) ) {
					c.possibleMoves[index][0] = i;
					c.possibleMoves[index][1] = col;
					index++;
					
					if( targetPiece.isOpposingTeam( c.piece ) ) {
						isBlocked = true;
					}
				}else {
					isBlocked = true;
				}
			}
			isBlocked = false;
			
			//check left
			for(int j = col-1; (j >= 0) && !isBlocked; j--) {
				Piece targetPiece = board[row][j].piece;
				if( ! targetPiece.isSameTeam( c.piece ) ) {
					c.possibleMoves[index][0] = row;
					c.possibleMoves[index][1] = j;
					index++;
					
					if( targetPiece.isOpposingTeam( c.piece ) ) {
						isBlocked = true;
					}
				}else {
					isBlocked = true;
				}
			}
			isBlocked = false;
			
			//check right
			for(int j = col+1; (j < 8) && !isBlocked; j++) {
				Piece targetPiece = board[row][j].piece;
				if( ! targetPiece.isSameTeam( c.piece ) ) {
					c.possibleMoves[index][0] = row;
					c.possibleMoves[index][1] = j;
					index++;
					
					if( targetPiece.isOpposingTeam( c.piece ) ) {
						isBlocked = true;
					}
				}else {
					isBlocked = true;
				}
			}
			isBlocked = false;
			
		}
		
		//bishop moves or bishop-like queen moves
		if(type == Piece.queen || type == Piece.bishop) {
			//top left
			for(int i = row - 1, j = col - 1; i >= 0 && j >=0 && !isBlocked; i--, j--) {
				Piece targetPiece = board[i][j].piece;
				if( ! targetPiece.isSameTeam( c.piece ) ) {
					c.possibleMoves[index][0] = i;
					c.possibleMoves[index][1] = j;
					index++;
					
					if( targetPiece.isOpposingTeam( c.piece ) ) {
						isBlocked = true;
					}
				}else {
					isBlocked = true;
				}
				
			}
			isBlocked = false;
			
			//top right
			for(int i = row - 1, j = col + 1; i >= 0 && j < 8 && !isBlocked; i--, j++) {
				Piece targetPiece = board[i][j].piece;
				if( ! targetPiece.isSameTeam( c.piece ) ) {
					c.possibleMoves[index][0] = i;
					c.possibleMoves[index][1] = j;
					index++;
					
					if( targetPiece.isOpposingTeam( c.piece ) ) {
						isBlocked = true;
					}
				}else {
					isBlocked = true;
				}
				
			}
			isBlocked = false;
			
			//bottom right
			for(int i = row + 1, j = col + 1; i < 8 && j < 8 && !isBlocked; i++, j++) {
				Piece targetPiece = board[i][j].piece;
				if( ! targetPiece.isSameTeam( c.piece ) ) {
					c.possibleMoves[index][0] = i;
					c.possibleMoves[index][1] = j;
					index++;
					
					if( targetPiece.isOpposingTeam( c.piece ) ) {
						isBlocked = true;
					}
				}else {
					isBlocked = true;
				}
				
			}
			isBlocked = false;
			
			//bottom left
			for(int i = row + 1, j = col - 1; i < 8 && j >= 0 && !isBlocked; i++, j--) {
				Piece targetPiece = board[i][j].piece;
				if( ! targetPiece.isSameTeam( c.piece ) ) {
					c.possibleMoves[index][0] = i;
					c.possibleMoves[index][1] = j;
					index++;
					
					if( targetPiece.isOpposingTeam( c.piece ) ) {
						isBlocked = true;
					}
				}else {
					isBlocked = true;
				}
				
			}
		}
		//knight moves
		else if(type == Piece.knight) {
			//-2 -1, -2 +1
			boolean topLeftExists = row-2>=0 && col-1>=0;
			boolean topRightExists = row-2>=0 && col+1<8;
			//+2 -1, +2 +1
			boolean bottomRightExists = row+2<8 && col+1<8;
			boolean bottomLeftExists = row+2<8 && col-1>=0;
			//-1 -2, +1 -2
			boolean leftTopExists = row-1>=0 && col-2>=0;
			boolean leftBottomExists = row+1<8 && col-2>=0;
			//-1 +2, +1 +2
			boolean rightTopExists = row-1>=0 && col+2<8;
			boolean rightBottomExists = row+1<8 && col+2<8;
			
			//check for horizontal moves
			if( topLeftExists && ! board[row-2][col-1].piece.isSameTeam( c.piece ) ) {
				c.possibleMoves[index][0] = row-2;
				c.possibleMoves[index][1] = col-1;
				index++;
			}
			if( topRightExists && ! board[row-2][col+1].piece.isSameTeam( c.piece ) ) {
				c.possibleMoves[index][0] = row-2;
				c.possibleMoves[index][1] = col+1;
				index++;
			}
			if( bottomRightExists && ! board[row+2][col+1].piece.isSameTeam( c.piece ) ) {
				c.possibleMoves[index][0] = row+2;
				c.possibleMoves[index][1] = col+1;
				index++;
			}
			if( bottomLeftExists && ! board[row+2][col-1].piece.isSameTeam( c.piece ) ) {
				c.possibleMoves[index][0] = row+2;
				c.possibleMoves[index][1] = col-1;
				index++;
			}
			
			//check for vertical moves
			if( leftTopExists && ! board[row-1][col-2].piece.isSameTeam( c.piece ) ) {
				c.possibleMoves[index][0] = row-1;
				c.possibleMoves[index][1] = col-2;
				index++;
			}
			if( leftBottomExists && ! board[row+1][col-2].piece.isSameTeam( c.piece ) ) {
				c.possibleMoves[index][0] = row+1;
				c.possibleMoves[index][1] = col-2;
				index++;  
			}
			if( rightTopExists && ! board[row-1][col+2].piece.isSameTeam( c.piece ) ) {
				c.possibleMoves[index][0] = row-1;
				c.possibleMoves[index][1] = col+2;
				index++;
			}
			if( rightBottomExists && ! board[row+1][col+2].piece.isSameTeam( c.piece ) ) {
				c.possibleMoves[index][0] = row+1;
				c.possibleMoves[index][1] = col+2;
				index++;
			}
			
		}
		//king moves
		else if(type == Piece.king) {
			for(int i = -1; i<2; i++) {
				for(int j = -1; j<2; j++) {
					if(isValidPosition(row+i, col+j) && (i!=0 || j!=0) ) {
						
						boolean isNotOccupied = ! board[row+i][col+j].piece.isSameTeam( c.piece );
						if(isNotOccupied && !isThreatened(board[row+i][col+j], team) ) {
							c.possibleMoves[index][0] = row+i;
							c.possibleMoves[index][1] = col+j;
							index++;
						}
						
					}
				}
			}
		}
		//pawn moves
		else if(type == Piece.pawn) {
			int targetRow = -1;
			int targetRowFirstMove = -2;
			boolean canMoveTwice = false;
			
			if(c.piece.team == Piece.white) {
				targetRow = row - 1;
				targetRowFirstMove = row - 2;
				canMoveTwice = (row == 6);
			}else if(c.piece.team == Piece.black) {
				targetRow = row + 1;
				targetRowFirstMove = row + 2;
				canMoveTwice = (row == 1);
			}
			
			if(targetRow >= 0 && targetRow < 8) {
				//check for captures
				for(int i = -1; i < 2; i+=2) {
					if(col+i >= 0 &&  col + i < 8) {
						Piece targetPiece = board[targetRow][col+i].piece;
						if( targetPiece.isOpposingTeam( c.piece ) ) {
							c.possibleMoves[index][0] = targetRow;
							c.possibleMoves[index][1] = col+i;
							index++;
						}
					}
					
				}
				//check normal move
				Piece targetPiece = board[targetRow][col].piece;
				if( targetPiece.isEmpty() ) {
					c.possibleMoves[index][0] = targetRow;
					c.possibleMoves[index][1] = col;
					index++;
				}
			}
			//check first move (two cells instead of one)
			if( isValidPosition(targetRowFirstMove, col) ) {
				Piece targetFirstMovePiece = board[targetRowFirstMove][col].piece;
				if( targetFirstMovePiece.isEmpty() && canMoveTwice ) {
					c.possibleMoves[index][0] = targetRowFirstMove;
					c.possibleMoves[index][1] = col;
					index++;
				}
			}
		}
		
		c.possibleMoves = Arrays.copyOfRange(c.possibleMoves, 0, index);
		c.canMove = !(index == 0);
	}
	
	//check if cell c is threatened for team
	public boolean isThreatened(Cell c, String team) {
		int row = c.row;
		int col = c.col;
		boolean isBlocked = false;

		//queen/rook
		//above
		for(int i = row-1; (i >= 0) && !isBlocked; i--) {
			Piece targetPiece = board[i][col].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.rook);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) || (targetPiece.isOpposingTeam( team ) && targetPiece.type!=Piece.queen && targetPiece.type!=Piece.rook) ) {
					isBlocked = true;
				}
			}else {
				return true;
			}
		}
		isBlocked = false;
		
		//below
		for(int i = row+1; (i < 8) && !isBlocked; i++) {
			Piece targetPiece = board[i][col].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.rook);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) || (targetPiece.isOpposingTeam( team ) && targetPiece.type!=Piece.queen && targetPiece.type!=Piece.rook) ) {
					isBlocked = true;
				}
			}else {
				return true;
			}
		}
		isBlocked = false;
		
		//right
		for(int i = col+1; (i < 8) && !isBlocked; i++) {
			Piece targetPiece = board[row][i].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.rook);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) || (targetPiece.isOpposingTeam( team ) && targetPiece.type!=Piece.queen && targetPiece.type!=Piece.rook) ) {
					isBlocked = true;
				}
			}else {
				return true;
			}
		}
		isBlocked = false;
		
		//left
		for(int i = col-1; (i >= 0) && !isBlocked; i--) {
			Piece targetPiece = board[row][i].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.rook);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) || (targetPiece.isOpposingTeam( team ) && targetPiece.type!=Piece.queen && targetPiece.type!=Piece.rook) ) {
					isBlocked = true;
				}
			}else {
				return true;
			}
		}
		isBlocked = false;
		
		//queen/bishop
		//top left
		for(int i = row - 1, j = col - 1; i >= 0 && j >=0 && !isBlocked; i--, j--) {
			Piece targetPiece = board[i][j].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.bishop);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) || (targetPiece.isOpposingTeam( team ) && targetPiece.type!=Piece.queen && targetPiece.type!=Piece.bishop) ) {
					isBlocked = true;
				}
			}else {
				return true;
			}
			
		}
		isBlocked = false;
		
		//top right
		for(int i = row - 1, j = col + 1; i >= 0 && j < 8 && !isBlocked; i--, j++) {
			Piece targetPiece = board[i][j].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.bishop);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) || (targetPiece.isOpposingTeam( team ) && targetPiece.type!=Piece.queen && targetPiece.type!=Piece.bishop) ) {
					isBlocked = true;
				}
			}else {
				return true;
			}
			
		}
		isBlocked = false;
		
		//bottom left
		for(int i = row + 1, j = col - 1; i < 8 && j >=0 && !isBlocked; i++, j--) {
			Piece targetPiece = board[i][j].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.bishop);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) || (targetPiece.isOpposingTeam( team ) && targetPiece.type!=Piece.queen && targetPiece.type!=Piece.bishop) ) {
					isBlocked = true;
				}
			}else {
				return true;
			}
			
		}
		isBlocked = false;
		
		//bottom right
		for(int i = row + 1, j = col + 1; i < 8 && j < 8 && !isBlocked; i++, j++) {
			Piece targetPiece = board[i][j].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.bishop);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) || (targetPiece.isOpposingTeam( team ) && targetPiece.type!=Piece.queen && targetPiece.type!=Piece.bishop) ) {
					isBlocked = true;
				}
			}else {
				return true;
			}
			
		}
		isBlocked = false;
		
		//king		
		for(int i = -1; i<2 && row+i>=0 && row+i<8; i++) {
			for(int j = -1; j<2 && col+j>=0 && col+j<8; j++) {
				if( (i!=0 || j!=0) && board[row+i][col+j].piece.type == Piece.king && board[row+i][col+j].piece.isOpposingTeam(team) ) {
					setPossibleMoves(board[row+i][col+j]);
					if(board[row+i][col+j].canMoveTo(board[row][col], this)) {
						return true;
					}
					
				}
			}
		}
		
		//knight
		//-2 -1, -2 +1
		//+2 -1, +2 +1
		//-1 -2, +1 -2
		//-1 +2, +1 +2
		
		//check for vertical moves
		for(int i = -2; i<3; i+=4) {
			for(int j = -1; j<2; j+=2) {
				if( c.isThreatenedByKnightAt(row+i, col+j, team, this) ) {
					return true;
				}
			}
		}
		
		//check for horizontal moves
		for(int i = -1; i<2; i+=2) {
			for(int j = -2; j<3; j+=4) {
				if( c.isThreatenedByKnightAt(row+i, col+j, team, this) ) {
					return true;
				}
			}
		}
		
		//pawn 
		if(c.isThreatenedByPawn(team, this)) {
			return true;
		}
		
		return false;
		
	}
	
	public void updateGameOver() {
		String ennemyTeam = currentPlayer == Piece.white ? Piece.black : Piece.white;
		Cell ennemyKing = getKingLocation( ennemyTeam );
		if( isThreatened(ennemyKing, ennemyKing.piece.team) ){
			setPossibleMoves(ennemyKing);
			if(!ennemyKing.canMove) {
				Cell threat = getThreat(ennemyKing, ennemyKing.piece.team);
				System.out.println("Found threat at "+threat.row+", "+threat.col);
				
				if(!isThreatened(threat, ennemyTeam)) {
					gameOver = true;
				}else {
					Cell threat2 = getThreat(threat, currentPlayer );
					System.out.println("Found threat to threat at "+threat2.row+", "+threat2.col);

				}
			}
		}
		
	}
	
	public Cell getKingLocation(String team) {
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				if( (board[i][j].piece.type == Piece.king) && (board[i][j].piece.team == team) ) {
					return board[i][j];
				}
			}
		}
		
		return new Cell(0, 4);
	}
	
	/**
	 * Switch turn
	 */
	public void nextPlayer() {
		if(currentPlayer == Piece.white) {
			currentPlayer = Piece.black;
		}else {
			currentPlayer = Piece.white;
		}
		selectedCell.initBackground();
		selectedCell = null;
	}
	
	private boolean isValidPosition(int row, int col) {
		return (row>=0 && row<8 && col>=0 && col<8);
	}
	
	private Cell getThreat(Cell c, String team) {
		int row = c.row;
		int col = c.col;
		boolean isBlocked = false;

		//queen/rook
		//above
		for(int i = row-1; (i >= 0) && !isBlocked; i--) {
			Piece targetPiece = board[i][col].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.rook);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) ) {
					isBlocked = true;
				}
			}else {
				return board[i][col];
			}
		}
		isBlocked = false;
		
		//below
		for(int i = row+1; (i < 8) && !isBlocked; i++) {
			Piece targetPiece = board[i][col].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.rook);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) ) {
					isBlocked = true;
				}
			}else {
				return board[i][col];
			}
		}
		isBlocked = false;
		
		//right
		for(int i = col+1; (i < 8) && !isBlocked; i++) {
			Piece targetPiece = board[row][i].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.rook);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) ) {
					isBlocked = true;
				}
			}else {
				return board[row][i];
			}
		}
		isBlocked = false;
		
		//left
		for(int i = col-1; (i >= 0) && !isBlocked; i--) {
			Piece targetPiece = board[row][i].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.rook);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) ) {
					isBlocked = true;
				}
			}else {
				return board[row][i];
			}
		}
		isBlocked = false;
		
		//queen/bishop
		//top left
		for(int i = row - 1, j = col - 1; i >= 0 && j >=0 && !isBlocked; i--, j--) {
			Piece targetPiece = board[i][j].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.bishop);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) ) {
					isBlocked = true;
				}
			}else {
				return board[i][j];
			}
			
		}
		isBlocked = false;
		
		//top right
		for(int i = row - 1, j = col + 1; i >= 0 && j < 8 && !isBlocked; i--, j++) {
			Piece targetPiece = board[i][j].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.bishop);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) ) {
					isBlocked = true;
				}
			}else {
				return board[i][j];
			}
			
		}
		isBlocked = false;
		
		//bottom left
		for(int i = row + 1, j = col - 1; i < 8 && j >=0 && !isBlocked; i++, j--) {
			Piece targetPiece = board[i][j].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.bishop);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) ) {
					isBlocked = true;
				}
			}else {
				return board[i][j];
			}
			
		}
		isBlocked = false;
		
		//bottom right
		for(int i = row + 1, j = col + 1; i < 8 && j < 8 && !isBlocked; i++, j++) {
			Piece targetPiece = board[i][j].piece;
			boolean canCapture = targetPiece.isOpposingTeam( team ) && (targetPiece.type==Piece.queen || targetPiece.type==Piece.bishop);
			if( ! canCapture ) {
				if( targetPiece.isSameTeam( team ) ) {
					isBlocked = true;
				}
			}else {
				return board[i][j];
			}
			
		}
		isBlocked = false;
		
		//king		
		for(int i = -1; i<2 && row+i>=0 && row+i<8; i++) {
			for(int j = -1; j<2 && col+j>=0 && col+j<8; j++) {
				if( (i!=0 || j!=0) && board[row+i][col+j].piece.type == Piece.king ) {
					if(board[row+i][col+j].canMoveTo(board[row][col], this)) {
						return board[i][j];
					}
					
				}
			}
		}
		
		//knight
		//-2 -1, -2 +1
		//+2 -1, +2 +1
		//-1 -2, +1 -2
		//-1 +2, +1 +2
		
		//check for vertical moves
		for(int i = -2; i<3; i+=4) {
			for(int j = -1; j<2; j+=2) {
				if( c.isThreatenedByKnightAt(row+i, col+j, team, this) ) {
					return board[row+i][col+j];
				}
			}
		}
		
		//check for horizontal moves
		for(int i = -1; i<2; i+=2) {
			for(int j = -2; j<3; j+=4) {
				if( c.isThreatenedByKnightAt(row+i, col+j, team, this) ) {
					return board[row+i][col+j];
				}
			}
		}
		
		//pawn 
		if(c.isThreatenedByPawn(team, this)) {
			return c.getPawnThreat(team, this);
		}
		
		return c;
	}

	public void moveSelectedTo(Cell clicked) {
		if(clicked.piece.isOpposingTeam(selectedCell.piece.team)) {
			infoPanel.getInfoPanel(currentPlayer).addOne(clicked.piece);
		}
	
		selectedCell.movePieceTo( clicked );
		updateGameOver();
		nextPlayer();
	}
	
}
