#ifndef BOARD_H
#define BOARD_H

#include "piece.h"

#define boardSize 8

piece board[boardSize][boardSize];


void initBoard();
void printBoard();
void printSeparatorLine();
int isDiffTeam(piece, piece);
int isEmpty(int, int);

#endif /* BOARD_H */