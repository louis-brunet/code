#include<stdio.h>
#include<stdlib.h>

#include"piece.h"

piece* initPiece(int row, int col){
	piece* p = malloc(sizeof(piece));
	p->row = row;
	p->col = col;

	if( ((row==0 || row==2) && (col%2 == 1)) || (row==1 && col%2 == 0) ){
		p->team = 'o';
	}else if( ((row==5 || row==7) && (col%2 == 0)) || (row==6 && col%2==1)){
		p->team = 'x';
	}else if(row<0 || row>7){
		p->team = ' ';
	}
	return p;
}

void resetCell(piece* pPtr){
	pPtr->team = ' ';
}
