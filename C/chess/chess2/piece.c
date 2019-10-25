#include"piece.h"

char white = 'w';
char black = 'b';
char pawn = 'P';
char rook = 'R';
char knight = 'N';
char bishop = 'B';
char queen = 'Q';
char king = 'K';

char getTeam(piece p){

	return p.team;
}

char getType(piece p){

	return p.type;
}

int setTeam(piece* pPtr, char targetTeam){
	
	int isTeamChanged = 0;

	if(pPtr->team != targetTeam){
		pPtr->team = targetTeam;
		isTeamChanged = 1;
	}

	return isTeamChanged;
}

int setType(piece* pPtr, char targetType){

	int isTypeChanged = 0;

	if(pPtr->type != targetType){
		pPtr->type = targetType;
		isTypeChanged = 1;
	}

	return isTypeChanged;	
}

/*void initTypesTeams(){
	white = 'w';
	black = 'b';
	pawn = 'P';
	rook = 'R';
	knight = 'N';
	bishop = 'B';
	queen = 'Q';
	king = 'K';
}*/