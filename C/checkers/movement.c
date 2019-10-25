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


	res = canSimplyMoveTo(p, row-1, col-1) || canSimplyMoveTo(p, row-1, col+1) || canSimplyMoveTo(p, row+1, col+1) || canSimplyMoveTo(p, row+1, col-1);
	/*res = canMoveTo(p, row-2, col-2) || canMoveTo(p, row-2, col+2) || canMoveTo(p, row+2, col+2) || canMoveTo(p, row+2, col-2);
	*/
	/*if( (row-2 >= 0) && (col-2 >=0) && isDiffTeam(p, board[row-1][col-1]) ){
		res = isEmpty(row-2, col-2);
	}else if( (row-2 >= 0) && (col+2 < 8) && isDiffTeam(p, board[row-1][col+1]) ){
		res = isEmpty(row-2, col-2);
	}else if( (row+2 > 8) && (col+2 < 8) && isDiffTeam(p, board[row+1][col+1]) ){
		res = isEmpty(row-2, col-2);
	}else if( (row+2 > 8) && (col-2 >= 0) && isDiffTeam(p, board[row+1][col-1]) ){
		res = isEmpty(row-2, col-2);
	}*/

	return res;
}

int canSimplyMoveTo(piece p, int targetRow, int targetCol){
	int res = 0;
	int row = p.row;
	int col = p.col;
	int targetRowExists = targetRow>=0 && targetRow<8;
	int targetColExists = targetCol>=0 && targetCol<8;
	int rowOffset = targetRow-row;
	int colOffset = targetCol-col;

	int isOutOfRange = ((rowOffset!=-1)&&(rowOffset!=1)) || ((colOffset!=-1)&&(colOffset!=1));

	printf("canMoveTo1(%d, %d) = %d\n", targetRow, targetCol, res);
	if(p.team == ' ' || isOutOfRange){
		printf("canMoveTo1.1(%d, %d) = %d\n", targetRow, targetCol, res);
		return res;
	}


	printf("canMoveTo2(%d, %d) = %d\n", targetRow, targetCol, res);
	if(targetRowExists && targetColExists){
		res = isEmpty(targetRow, targetCol);
	}
	
	printf("canMoveTo3(%d, %d) = %d\n", targetRow, targetCol, res);
	return res;
}

void movePiece(piece* pPtr){
	printf("CANMOVE : %d\n", canMove(*pPtr));
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
		if(canSimplyMoveTo(*pPtr, targetRow, targetCol)){
			board[targetRow][targetCol].team = pPtr->team;
			resetCell(pPtr);
		}else{
			printf("Move not availq	Aable.\n");
		}

	}
}
