#ifndef PIECE_H
#define PIECE_H

typedef struct Pieces{ 
	int row;
	int col;
	char team;
}piece;

piece* initPiece(int, int);
void resetCell(piece*);



#endif /* PIECE_H */
