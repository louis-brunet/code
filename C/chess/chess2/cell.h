#ifndef CELL_H
#define CELL_H

#include<stdio.h>
#include<stdlib.h>

#include"piece.h"

typedef struct cell{
	int row, cell;
	piece piece;
}cell;

cell*  initCell(int, int);

#endif /*CELL_H*/