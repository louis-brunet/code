#include<stdio.h>
#include<stdlib.h>

#include"cell.h"
#include "board.h"

void printSeparator();

cell board[8][8];

void initBoard(){
	int row, col;
	cell* cPtr;

	for(row = 0; row<8; row++){
		for(col = 0; col<8; col++){
			cPtr = initCell(row, col);
			board[row][col] = *cPtr;
		}
	}
}

void printBoard(){
	int row, col;
	printSeparator();
	for(row=0; row<8; row++){
		printf("\t%d ", 8-row );
		for(col=0; col<8; col++){
			cell cell= board[row][col];
			printf("| %c%c ", cell.piece.team, cell.piece.type);
		}
		printf("|");
		printSeparator();
	}
	printf("\t     a    b    c    d    e    f    g    h\n\n");
}

void printSeparator(){
	printf("\n\t  +----+----+----+----+----+----+----+----+\n");
}
