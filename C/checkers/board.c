#include<stdio.h>
#include<stdlib.h>

#include"piece.h"
#include"board.h"

void initBoard(){
	int row, col;

	for(row = 0; row < boardSize; row++){
		for(col = 0; col < boardSize; col++){
			board[row][col] = *initPiece(row, col);
		}
 	}

} 

void printBoard(){
	int row, col;

	for(row = 0; row < boardSize; row++){
		printSeparatorLine();
		printf(" %d ", 8-row);
		for(col = 0; col < boardSize; col++){
			printf("| %c ", board[row][col].team);
		}
		printf("|\n");
 	}
	printSeparatorLine();
	printf("   ");
	for(col = 'a'; col < 'i'; col++){
		printf("  %c ", col);
	}
	printf("\n");
}

void printSeparatorLine(){
	int col;
	printf("   ");
	for(col = 0; col < boardSize; col++){
		printf("+---");
 	}	
 	printf("+\n");
}



int isEmpty(int row, int col){
	if(board[row][col].team == ' '){
		return 1;
	}
	else return 0;
}

int isDiffTeam(piece p1, piece p2){
	if(p1.team != ' ' && p2.team != ' ' && p1.team!=p2.team){
		return 1;
	}else{ 
		return 0;
	}
}

