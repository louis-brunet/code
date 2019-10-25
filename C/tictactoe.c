#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

void printGrid();
void printRow();
void place();
void playTurn();
void printCellNums();
int isGameOver();
int findRowNum();
int findColumnNum();
int isEmpty();
int isADiagWon();
int isRowWon();
int isColumnWon();
char* availableCells();
int getCellNum();
void twoPlayerGame();
int endGame();
void onePlayerGame();
void cpuTurn(char, char);
int isGridEmpty();


char grid[3][3];
char currP;

int main(void){
	srand ( time(0) );
	if(rand()%2==0) currP='X';
	else currP='O';
	
	for( int i=0 ; i<3 ; i++){
		for( int j=0 ; j<3 ; j++){
			grid[i][j]=' '; 
		}
	}	


	int playerNum;
	printf("How many players ? [1-2] ");
	scanf("%d", &playerNum);

	if(playerNum==2){
		do{
			twoPlayerGame();
		} while(endGame()==1);
	}else if(playerNum==1){
		do{
			onePlayerGame();
		}while(endGame()==1);
	}



	return EXIT_SUCCESS;


}

void twoPlayerGame(){
	while(! isGameOver()){
		if(currP=='X') currP='O';
		else currP='X';
		playTurn();
	}
}

void onePlayerGame(){
	char playerChar;
	char cpuChar;
	char ans;

	system("clear");
	printf("Pls choose a side [X-O] : ");
	int i=0;
	while(ans!='o' && ans!='x' && ans!='X' && ans!='O' ){
		
		printf("%d", i++);
		scanf("%c", &ans);
	}
	if(ans=='X' || ans=='x'){
		playerChar='X';
		cpuChar='O';
	}else if(ans=='O' || ans=='o'){
		playerChar='O';
		cpuChar='X';
	}

	while(!isGameOver()){
		if(currP==playerChar) playTurn();
		else cpuTurn(cpuChar, playerChar);
	}
}

int endGame(){
	system("clear");
	printf("\n");
	printGrid();
	if(isGameOver()) printf("%c wins!\n", currP);
	
	char ans;
	char line[100];

	while( ans != 'y' && ans != 'n' ){
		printf("Play again ? (y/n) ");
		fgets(line, sizeof(line), stdin);

		if(sscanf(line, "%c", &ans)==EOF){
			ans = 'w';
		}
	}


	if(ans=='n'){
		return 0;
	}else if(ans=='y'){
		return 1;
	}
}

void playTurn(){
	int cell;
	int turnIsOver = 0;

	while (! turnIsOver) {
		system("clear");
	
		printCellNums();
		printf("\n");
		printGrid();
		cell=getCellNum();

		if(isEmpty(cell)) {
			place(cell);
			turnIsOver = 1;
		}
	}
}

void cpuTurn(char cpu, char player){
	//if grid is empty, play randomly in a corner or the center
	if(isGridEmpty()){
		int corner = rand() % 5;
		switch(corner){
			case 0 :
				place(1);
				break;
			case 1 : 
				place(3);
				break;
			case 2 : 
				place(7);
				break;
			case 3 :
				place(9);
				break;
			case 4 :
				place(5);

		}
	}

	
}

int isGridEmpty(){
	int res=1;
	for(int i=1; i<=9; i++){
		if(!isEmpty(i)) res=0;
	}
	return res;
}

int getCellNum(){
	int cell=-1;
	char line[100];

	printf("\nEnter a cell number pls : ");

	while( cell<1 || cell>9 ){
		printf("[%s] : ", availableCells());
		fgets(line, sizeof(line), stdin);

		if(sscanf(line, "%d", &cell)==EOF){
			cell = -1;
		}
	}
	return cell;
}

void printGrid(){
	printf("\t\t-------------\n");
	printRow(0);
	printf("\t\t|-----------|\n");
	printRow(1);
	printf("\t\t|-----------|\n");
	printRow(2);
	printf("\t\t-------------\n");
}

void printCellNums(){
	int cellNumbers[3][3]={{1,2,3},{4,5,6},{7,8,9}};
	printf("\t\t-------------\n");
	printf("\t\t| %d | %d | %d |\n", cellNumbers[0][0], cellNumbers[0][1], cellNumbers[0][2]);
	printf("\t\t|-----------|\n");
	printf("\t\t| %d | %d | %d |\n", cellNumbers[1][0], cellNumbers[1][1], cellNumbers[1][2]);
	printf("\t\t|-----------|\n");
	printf("\t\t| %d | %d | %d |\n", cellNumbers[2][0], cellNumbers[2][1], cellNumbers[2][2]);
	printf("\t\t-------------\n");	
}

void printRow(int row){

	printf("\t\t| %c | %c | %c |\n", grid[row][0], grid[row][1], grid[row][2]);
}

void place(int cell){
	int row = findRowNum(cell);
	int column = findColumnNum(cell);

	grid[row][column]=currP;
}

int findRowNum(int cell){
	if (cell==1 || cell==2 || cell==3) return 0;
	else if (cell==4 || cell==5 || cell==6)	return 1;
	else return 2;
}

int findColumnNum(int cell){
	if (cell==1 || cell==4 || cell==7) return 0;
	else if (cell==2 || cell==5 || cell==8)	return 1;
	else return 2;	
}

int isEmpty(int cell){
	int row = findRowNum(cell);
	int column = findColumnNum(cell);

	if(grid[row][column]==' ') return 1;
	else return 0;
}

int isGameOver(){
	if(isRowWon(0) || isRowWon(1) || isRowWon(2) || isColumnWon(0) || isColumnWon(1) || isColumnWon(2) || isADiagWon() ) return 1;
	else return 0;
}

int isRowWon(int row){
	if(grid[row][0]!=' ' && grid[row][0]==grid[row][1] && grid[row][1]==grid[row][2]) return 1;
	else return 0;
}

int isColumnWon(int column){
	if(grid[0][column]!=' ' && grid[0][column]==grid[1][column] && grid[1][column]==grid[2][column]) return 1;
	else return 0;
}

int isADiagWon(){
	if(grid[0][0]!=' ' && grid[0][0]==grid[1][1] && grid[1][1]==grid[2][2]) return 1;
	else if (grid[0][2]!=' ' && grid[0][2]==grid[1][1] && grid[1][1]==grid[2][0]) return 1;
	else return 0;
}

char* availableCells(){
	char *list = malloc(sizeof(char) * 10);
	//int currIdx=0;

	for(int i=1;i<=9;i++){
		if(isEmpty(i)){
			//snprintf(list, 10, list + "%d", i);s
			//list[currIdx]=(char)i;
			//currIdx++;
			char temp[2];
			snprintf(temp, 2, "%d", i);
			strcat(list, temp);
		}
	}
	return list;
}