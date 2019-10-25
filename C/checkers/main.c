#include<stdio.h>
#include<stdlib.h>

#include"piece.h"
#include"board.h"
#include"movement.h"

int main(int argc, char * argv[]){
	initBoard();

	printBoard();
	
	movePiece(&board[5][0]);


	return EXIT_SUCCESS;
}

