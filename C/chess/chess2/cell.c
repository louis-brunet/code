#include"cell.h"

cell* initCell(int row, int col){
	cell* cellPtr = malloc(sizeof(cell));
	piece* piecePtr = malloc(sizeof(piece));

	if(col==0 || col==7){
		setType(piecePtr, rook);
	}else if(col==1 || col==6){
		setType(piecePtr, knight);
	}else if(col==2 || col==5){
		setType(piecePtr, bishop);
	}else if(col==3){
		setType(piecePtr, queen);
	}else if(col==4){
		setType(piecePtr, king);
	}
	
	if(row==1 || row==6){
		setType(piecePtr, pawn);
	}

	if(row==0 || row==1){
		setTeam(piecePtr, black);	
	}else if(row==6 || row==7){
		setTeam(piecePtr, white);
	}

	cellPtr->piece = *piecePtr;
	
	return cellPtr;
}
