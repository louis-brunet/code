#ifndef PIECE_H
#define PIECE_H

#include<stdio.h>
#include<stdlib.h>



typedef struct piece
{
	char team;
	char type;
}piece;

char getTeam(piece);
int setTeam(piece*, char);
char getType(piece);
int setType(piece*, char);


#endif/*PIECE_H*/