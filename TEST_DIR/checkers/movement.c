#include<stdio.h>
#include<stdlib.h>

#include"movement.h"
#include"board.h"
#include"piece.h"


int canMove(piece p){
	int res = 0;
	char team = p.team;
	int row = p.row;
	int col = p.col;

	if(team == ' '){
		return res;
	}


	res = canMoveTo(p, row-1, col-1) || canMoveTo(p, row-1, col+1) || canMoveTo(p, row+1, col+1) || canMoveTo(p, row+1, col-1);

	if( (row-2 >= 0) && (col-2 >=0) && isDiffTeam(p, board[row-1][col-1]) ){
		res = isEmpty(row-2, col-2);
	}else if( (row-2 >= 0) && (col+2 < 8) && isDiffTeam(p, board[row-1][col+1]) ){
		res = isEmpty(row-2, col-2);
	}else if( (row+2 > 8) && (col+2 < 8) && isDiffTeam(p, board[row+1][col+1]) ){
		res = isEmpty(row-2, col-2);
	}else if( (row+2 > 8) && (col-2 >= 0) && isDiffTeam(p, board[row+1][col-1]) ){
		res = isEmpty(row-2, col-2);
	}


	return res;
}

int canMoveTo(piece p, int targetRow, int targetCol){
	int res = 0;
	int row = p.row;
	int col = p.col;
	int rowOffset = targetRow - row;
	int colOffset = targetCol - col;
	int targetRowExists = targetRow>=0 && targetRow<8;
	int targetColExists = targetCol>=0 && targetCol<8 ;

	int isOutOfRange = ((rowOffset!=-2)&&(rowOffset!=2)) 
		|| ((rowOffset!=-1)&&(rowOffset!=1))
		|| ((colOffset!=-2)&&(colOffset!=2)) 
		|| ((colOffset!=-1)&&(colOffset!=1));

	if(isEmpty(p.row, p.col) || isOutOfRange){
		return res;
	}


	if(targetRowExists && targetColExists){
		if(rowOffset%2 == 1){
			res = isEmpty(targetRow, targetCol);
		}else{
			piece inbetweenCell = board[rowOffset/2][colOffset/2];
			if( isDiffTeam(p, inbetweenCell) ){
				res = isEmpty(targetRow, targetCol);
			}
		}
	}

	return res;
}

void movePiece(piece* pPtr){
	while( canMove(*pPtr) ){
		char input[3];
		int targetRow, targetCol;

		printf("Enter target cell :\n");

		/*get input*/
		fgets(input, 3, stdin);

		/*convert input*/
		targetRow = 4;
		targetCol = 1;

		/*moves if available*/ 
		if(canMoveTo(*pPtr, targetRow, targetCol)){
			board[targetRow][targetCol].team = pPtr->team;
			resetCell(pPtr);
		}else{
			printf("Move not available.\n");
		}

	}
}
