#ifndef MOVEMENT_H
#define MOVEMENT_H

#include "board.h"

/*int setPossibleMoves(piece* p);*/

int canMove(piece);
int canSimplyMoveTo(piece, int, int);
void movePiece(piece*);


#endif /*MOVEMENT_H*/