#include<stdio.h>
#include<stdlib.h>

void initBoard();
void printBoard();
void printSeparator();
void initBackCell();
void fillPossibleMoves(int[30][2] ,int, int);
int isSameColorOrEmpty(char, char);
char getColor(char);
int isPossibleMove(int, int, int, int, int[30][2]);
void getUserMove(int[], int[30][2]);
void move(int, int, int, int, int[30][2]);
int isThreatened(char, int, int);
int inputColToColNumber(char);
int inputRowToRowNum(int);
char colNumToInputCol(int);
int rowNumToInputRow(int);
void playGame();


// p, r, n, b, q, k  for black
// P, R, N, B, Q, K  for white
// ' ' for empty
char whitePawn = 'P';
char whiteRook = 'R';
char whiteKnight = 'N';
char whiteBishop = 'B';
char whiteQueen = 'Q';
char whiteKing = 'K';
char blackPawn = 'p';
char blackRook = 'r';
char blackKnight = 'n';
char blackBishop = 'b';
char blackQueen = 'q';
char blackKing = 'k';
char board[8][8];


int main(int argc, char * argv[]){
	
	playGame();
	
	return EXIT_SUCCESS;
}

void playGame(){
	int possibleMoves[30][2];
	int userMove[4]; // {row, col, rowTarget, colTarget}
	int isGameOver=0;

	initBoard();
	while(!isGameOver){
		system("clear");
		printBoard();
		getUserMove(userMove, possibleMoves);
		fillPossibleMoves(possibleMoves, userMove[0], userMove[1]);
		move( userMove[0], userMove[1],  userMove[2], userMove[3], possibleMoves);

	}
}

//gets starting and destination square coordinates 
void getUserMove(int res[4], int possibleMoves[30][2]){
	int row;
	char col;
	int rowDest;
	char colDest;
	int scanRes = 0;

	printf("Enter a starting square [a-h][1-8]\n");
	scanRes = scanf(" %c%d", &col, &row);

	if( scanRes!=2 || col<'a' || col>'h' || row<1 || row>8){
		printf("Input error.\n");
		getUserMove(res, possibleMoves);
		return;
	}

	//convert input row/col numbers
	res[0] = inputRowToRowNum(row);

	res[1] = inputColToColNumber(col);


	//print possible moves 
	fillPossibleMoves(possibleMoves, res[0], res[1]);
	for(int i=0; i<30; i++){
		if(possibleMoves[i][0]!=-1) printf("[ %c%d ] ",  colNumToInputCol(possibleMoves[i][1]) ,rowNumToInputRow(possibleMoves[i][0]));
		else if(i==0){
			if(board[row][col]==' '){
				printf("Selected square is empty.\n");
			}else {
				printf("No possible moves !\n");
				getUserMove(res, possibleMoves);
				return;
			}
		}
	}

	//get destination
	printf("Choose a destination square :\n");
	scanRes = 0;
	scanRes = scanf(" %c%d", &colDest, &rowDest);

	if(scanRes!=2 || colDest<97 || colDest>104 || rowDest<1 || rowDest>8){
		printf("Input error.\n");
		getUserMove(res, possibleMoves);
		return;
	}

	res[2] = inputRowToRowNum(rowDest);
	res[3] = inputColToColNumber(colDest);



}

//7-0 to 1-8
int rowNumToInputRow (int row){
	return 8-row;
}

//1-8 to 0-7
int inputRowToRowNum (int row){
	return 8-row;
}

char colNumToInputCol(int col){
	char res=' ';
	if(col==0){
		res='a';
	}else if(col==1){
		res=blackBishop;
	}else if(col==2){
		res='c';
	}else if(col==3){
		res='d';
	}else if(col==4){
		res='e';
	}else if(col==5){
		res='f';
	}else if(col==6){
		res='g';
	}else if(col==7){
		res='h';
	}
	return res;
}

//convert input col to 0-7
int inputColToColNumber(char col){
	int res = -1;
	if(col=='a' || col=='A'){
		res = 0;
	}else if(col==blackBishop|| col==whiteBishop){
		res = 1;
	}else if(col=='c' || col=='C'){
		res = 2;
	}else if(col=='d' || col=='D'){
		res = 3;
	}else if(col=='e' || col=='E'){
		res = 4;
	}else if(col=='f' || col=='F'){
		res = 5;
	}else if(col=='g' || col=='G'){
		res = 6;
	}else if(col=='h' || col=='H'){
		res = 7;
	}
	return res;
}

void initBoard(){
	for(int i=0; i<8; i++){
		for(int j=0; j<8; j++){
			if(i==0 || i==7){
				initBackCell(i,j);
			}else if(i==1){
				board[i][j] = blackPawn;
			}else if(i==6){
				board[i][j] = whitePawn;
			}else{
				board[i][j] = ' ';
			}
		}
	}
}

void initBackCell(int line, int cell){
	//initialise a cell in white or black's back line
	if(line==0){
		//printf("test i=%d, j=%d\n", line, cell);
		if (cell==0 || cell==7) {
			board[0][cell] = blackRook;
		}else if(cell==1 || cell== 6){
			board[0][cell] = blackKnight;
		}else if(cell==2 || cell== 5){
			board[0][cell] = blackBishop;
		}else if(cell==3){
			board[0][cell] = blackQueen;
		}else if(cell==4){
			board[0][cell] = blackKing;
		}

	}else if (line==7){
		if (cell==0 || cell==7) {
			board[7][cell] = whiteRook;
		}else if(cell==1 || cell== 6){
			board[7][cell] = whiteKnight;
		}else if(cell==2 || cell== 5){
			board[7][cell] = whiteBishop;
		}else if(cell==3){
			board[7][cell] = whiteQueen;
		}else if(cell==4){
			board[7][cell] = whiteKing;
		}
	}
}

void printBoard(){
	printSeparator();
	for(int i=0; i<8; i++){
		printf("\t");
		for(int j=0; j<8; j++){
			printf("| %c ", board[i][j]);
		}
		printf("|");
		printSeparator();
	}
	printf("\n");
}

void printSeparator(){
	printf("\n\t+---+---+---+---+---+---+---+---+\n");
}


void fillPossibleMoves(int arr[30][2], int row, int col){
	int currIndex = 0;
	char piece = board[row][col];

	for(int i = 0 ; i < 30 ; i++ ){
		for(int j = 0; j<2; j++){
			arr[i][j] = -1;
		}
	}

	if(piece == ' '){
		return;
	}

    //moves for a rook, and rook-like moves for queen
	if(piece == blackRook || piece == whiteRook || piece == blackQueen || piece == whiteQueen){
		//possible moves in column under the rook
		for( int i=row+1; i<8; i++){
			
			if( isSameColorOrEmpty(piece, board[i][col]) == 1 ){
				break;
			}
			arr[currIndex][0] = i;
			arr[currIndex][1] = col;
			currIndex++;
         //breaks if the target can be eaten 
			if(! (board[i][col]==' ') ){
				break;
			}
		}

		//possible moves in column over the rook
		for( int i=row-1; i>=0; i--){
			
			if( isSameColorOrEmpty(piece, board[i][col]) == 1 ){
				break;
			}
			arr[currIndex][0] = i;
			arr[currIndex][1] = col;
			currIndex++;
		  //breaks if the target can be eaten 
			if(! (board[i][col]==' ') ){
				break;
			}
		}

		//possible moves in row right of the rook
		for( int i=col+1; i<8; i++){

			if( isSameColorOrEmpty(piece, board[row][i]) == 1 ){
				break;
			}
			arr[currIndex][0] = row;
			arr[currIndex][1] = i;
			currIndex++;

		 //breaks if the target can be eaten 
			if(! (board[row][i]==' ') ){
				break;
			}
		}

		//possible moves in row left of the rook
		for( int i=col-1; i>=0; i--){
			
			if( isSameColorOrEmpty(piece, board[row][i]) == 1 ){
				break;
			}
			arr[currIndex][0] = row;
			arr[currIndex][1] = i;
			currIndex++;
        	//breaks if the target can be eaten 
			if(! (board[row][i]==' ') ){
				break;
			}
		}
	}

	//moves for bishop, and bishop-like moves for queen
	if (piece == blackBishop || piece == whiteBishop || piece == blackQueen || piece == whiteQueen){
		int lookingTopLeft = 1;
		int lookingTopRight = 1;
		int lookingBottomRight = 1;
		int lookingBottomLeft = 1;
		int topLeftAvailable;
		int topRightAvailable;
		int bottomRightAvailable;
		int bottomLeftAvailable;


		for(int i=0; i<7; i++){
			topLeftAvailable = ( (row-(i+1) >= 0) && (col-(i+1) >= 0) && (isSameColorOrEmpty( piece, board[row-(i+1)][col-(i+1)] ) != 1) );
			topRightAvailable = ( (row-(i+1) >= 0) && (col+(i+1) < 8) && (isSameColorOrEmpty( piece, board[row-(i+1)][col+(i+1)] ) != 1) );
			bottomRightAvailable = ( (row+(i+1) < 8) && (col+(i+1) < 8) && (isSameColorOrEmpty( piece, board[row+(i+1)][col+(i+1)] ) != 1) );
			bottomLeftAvailable = ( (row+(i+1) < 8) && (col-(i+1) >= 0) && (isSameColorOrEmpty( piece, board[row+(i+1)][col-(i+1)] ) != 1) );

			if(topLeftAvailable && lookingTopLeft){
				arr[currIndex][0] = row-(i+1);
				arr[currIndex][1] = col-(i+1);
				currIndex++;
			}else{
				lookingTopLeft = 0;
			}
			if(topRightAvailable && lookingTopRight){
				arr[currIndex][0] = row-(i+1);
				arr[currIndex][1] = col+(i+1);
				currIndex++;
			}else{
				lookingTopRight = 0;
			}
			if(bottomRightAvailable && lookingBottomRight){
				arr[currIndex][0] = row+(i+1);
				arr[currIndex][1] = col+(i+1);
				currIndex++;
			}else {
				lookingBottomRight = 0;
			}if(bottomLeftAvailable && lookingBottomLeft){
				arr[currIndex][0] = row+(i+1);
				arr[currIndex][1] = col-(i+1);
				currIndex++;
			}else {
				lookingBottomLeft = 0;
			}
		}
	}
	
	//moves for a white pawn
	else if (piece == whitePawn){
		//checks for first pawn move
		if(row==6){
			int firstCellEmpty = (isSameColorOrEmpty(whitePawn, board[row-1][col]) == -1);
			int secondCellEmpty = (isSameColorOrEmpty(whitePawn, board[row-2][col]) == -1);
			if( firstCellEmpty==1 && secondCellEmpty==1 ){
				arr[currIndex][0] = row-2;
				arr[currIndex][1] = col;
				currIndex++;
			}
		}
		//checks for cell above pawn
		if( isSameColorOrEmpty(whitePawn, board[row-1][col]) == -1 ){
			arr[currIndex][0] = row-1;
			arr[currIndex][1] = col;
			currIndex++;
		}
		//checks for available captures
		if( (row-1 >= 0) && (col-1 >= 0) && (isSameColorOrEmpty(whitePawn, board[row-1][col-1]) == 0) ){
			arr[currIndex][0] = row-1;
			arr[currIndex][1] = col-1;
			currIndex++;
		}
		if( (row-1 >= 0) && (col+1 < 8) && (isSameColorOrEmpty(whitePawn, board[row-1][col+1]) == 0) ){
			arr[currIndex][0] = row-1;
			arr[currIndex][1] = col+1;
			currIndex++;
		}
	}
	
	//moves for black pawn
	else if (piece == blackPawn){
		if(row==1){
			int firstCellEmpty = (isSameColorOrEmpty(whitePawn, board[row+1][col]) == -1);
			int secondCellEmpty = (isSameColorOrEmpty(whitePawn, board[row+2][col]) == -1);
			if( firstCellEmpty==1 && secondCellEmpty==1 ){
				arr[currIndex][0] = row+2;
				arr[currIndex][1] = col;
				currIndex++;
			}
		}
		//checks for cell above pawn
		if( isSameColorOrEmpty(whitePawn, board[row+1][col]) == -1 ){
			arr[currIndex][0] = row+1;
			arr[currIndex][1] = col;
			currIndex++;
		}
		//checks for available captures
		if( (row+1 < 8) && (col-1 >= 0) && (isSameColorOrEmpty(whitePawn, board[row+1][col-1]) == 0) ){
			arr[currIndex][0] = row+1;
			arr[currIndex][1] = col-1;
			currIndex++;
		}
		if( (row+1 < 8) && (col+1 < 8) && (isSameColorOrEmpty(whitePawn, board[row+1][col+1]) == 0) ){
			arr[currIndex][0] = row+1;
			arr[currIndex][1] = col+1;
			currIndex++;
		}
	}
	
	//moves for knight - 8 possibilities (clockwise: topleft, topright, righttop, rightbottom, bottomright, bottomleft, leftbottom, lefttop)
	else if (piece == blackKnight || piece == whiteKnight){
		int topLeftAvailable = ( (row-2 >= 0) && (col-1 >= 0) && (isSameColorOrEmpty( piece, board[row-2][col-1] ) != 1) );
		int topRightAvailable = ( (row-2 >= 0) && (col+1 < 8) && (isSameColorOrEmpty( piece, board[row-2][col+1] ) != 1) );
		int rightTopAvailable = ( (row-1 >= 0) && (col+2 < 8) && (isSameColorOrEmpty( piece, board[row-1][col+2] ) != 1) );
		int rightBottomAvailable = ( (row+1 < 8) && (col+2 < 8) && (isSameColorOrEmpty( piece, board[row+1][col+2] ) != 1) );
		int bottomRightAvailable = ( (row+2 < 8) && (col+1 < 8) && (isSameColorOrEmpty( piece, board[row+2][col+1] ) != 1) );
		int bottomLeftAvailable = ( (row+2 < 8) && (col-1 >= 0) && (isSameColorOrEmpty( piece, board[row+2][col-1] ) != 1) );
		int leftBottomAvailable = ( (row+1 < 8) && (col-2 >= 0) && (isSameColorOrEmpty( piece, board[row+1][col-2] ) != 1) );
		int leftTopAvailable = ( (row-1 >= 0) && (col-2 >= 0) && (isSameColorOrEmpty( piece, board[row-1][col-2] ) != 1) );

		if(topLeftAvailable){
			arr[currIndex][0] = row-2;
			arr[currIndex][1] = col-1;
			currIndex++;
		}if(topRightAvailable){
			arr[currIndex][0] = row-2;
			arr[currIndex][1] = col+1;
			currIndex++;

		}if(rightTopAvailable){
			arr[currIndex][0] = row-1;
			arr[currIndex][1] = col+2;
			currIndex++;
			
		}if(rightBottomAvailable){
			arr[currIndex][0] = row+1;
			arr[currIndex][1] = col+2;
			currIndex++;
			
		}if(bottomRightAvailable){
			arr[currIndex][0] = row+2;
			arr[currIndex][1] = col+1;
			currIndex++;
			
		}if(bottomLeftAvailable){
			arr[currIndex][0] = row+2;
			arr[currIndex][1] = col-1;
			currIndex++;
			
		}if(leftBottomAvailable){
			arr[currIndex][0] = row+1;
			arr[currIndex][1] = col-2;
			currIndex++;
			
		}if(leftTopAvailable){
			arr[currIndex][0] = row-1;
			arr[currIndex][1] = col-2;
			currIndex++;

		}
	}
	
	//moves for king 
	else if (piece == blackKing || piece == whiteKing){
		int topLeftAvailable = ((row-1 >=0) && (col-1 >=0) && isSameColorOrEmpty(board[row][col], board[row-1][col-1]) != 1);
		int topAvailable = ((row-1 >=0) && isSameColorOrEmpty(board[row][col], board[row-1][col]) != 1);
		int topRightAvailable = ((row-1 >= 0) && (col+1 < 8) && isSameColorOrEmpty(board[row][col], board[row-1][col+1]) != 1);
		int rightAvailable = ( (col+1 < 8) && isSameColorOrEmpty(board[row][col], board[row][col+1]) != 1);
		int leftAvailable = ( (col-1 >=0) && isSameColorOrEmpty(board[row][col], board[row][col-1]) != 1);
		int bottomLeftAvailable = ((row+1 >=0) && (col-1 >=0) && isSameColorOrEmpty(board[row][col], board[row+1][col-1]) != 1);
		int bottomAvailable = ((row+1 >=0) && isSameColorOrEmpty(board[row][col], board[row+1][col]) != 1);
		int bottomRightAvailable = ((row+1 >= 0) && (col+1 < 8) && isSameColorOrEmpty(board[row][col], board[row+1][col+1]) != 1);

		if(topLeftAvailable && !isThreatened(piece, row-1, col-1)){
			arr[currIndex][0] = row-1;
			arr[currIndex][1] = col-1;
			currIndex++;
		}

		if(topAvailable && !isThreatened(piece, row-1, col)){
			arr[currIndex][0] = row-1;
			arr[currIndex][1] = col;
			currIndex++;
		}

		if(topRightAvailable && !isThreatened(piece, row-1, col+1)){
			arr[currIndex][0] = row-1;
			arr[currIndex][1] = col+1;
			currIndex++;
		}

		if(rightAvailable && !isThreatened(piece, row, col+1)){
			arr[currIndex][0] = row;
			arr[currIndex][1] = col+1;
			currIndex++;
		}

		if(leftAvailable && !isThreatened(piece, row, col-1)){
			arr[currIndex][0] = row;
			arr[currIndex][1] = col-1;
			currIndex++;
		}

		if(bottomLeftAvailable && !isThreatened(piece, row+1, col-1)){
			arr[currIndex][0] = row+1;
			arr[currIndex][1] = col-1;
			currIndex++;
		}

		if(topLeftAvailable && !isThreatened(piece, row+1, col)){
			arr[currIndex][0] = row+1;
			arr[currIndex][1] = col;
			currIndex++;
		}

		if(topLeftAvailable && !isThreatened(piece, row+1, col+1)){
			arr[currIndex][0] = row+1;
			arr[currIndex][1] = col+1;
			currIndex++;
		}


	}
}

//returns 1 if both chars are spaces or same color
//returns -1 if only the target cell is empty
//returns 0 if target is a different colored piece
int isSameColorOrEmpty(char piece, char target){
	if( getColor(piece) == getColor(target)){
		return 1;
	}else if(target==' '){
		return -1;
	}else return 0;
}

//returns B, W, or ' '
char getColor(char piece){
	if( piece==blackRook || piece==blackKnight || piece==blackBishop || piece==blackPawn || piece==blackQueen || piece==blackKing ){
		return whiteBishop;
	}else if( piece==whiteRook || piece==whiteKnight || piece==whiteBishop || piece==whitePawn || piece==whiteQueen || piece==whiteKing ){
		return 'W';
	}else return ' ';
}

void move(int fromRow, int fromCol, int targetRow, int targetCol, int possibleMoves[30][2]){
	if(isPossibleMove(fromRow, fromCol, targetRow, targetCol, possibleMoves)){
		board[targetRow][targetCol] = board[fromRow][fromCol];
		board[fromRow][fromCol] = ' ';
	}else printf("Move not avaiblable.\n");
}

int isPossibleMove(int fromRow, int fromCol, int targetRow, int targetCol, int possibleMoves[30][2]){
	int res = 0;

	
	for(int i=0; i<30; i++){
		if(possibleMoves[i][0] == targetRow && possibleMoves[i][1] == targetCol){
			res = 1;
		}

	}
	return res;
}

//takes blackKing or whiteKing and the coordinates of the cell to check
//returns 1 if board[row][col] is threatened by the other color
int isThreatened(char king, int row, int col){

	if(king!=blackKing && king!=whiteKing){
		printf("Err: isThreatened()'s first arg should be blackKing or whiteKing\n");
	}

	char ennemyRook = (king == whiteKing ? blackRook : whiteRook);
	char ennemyQueen = (king == whiteKing ? blackQueen : whiteQueen);
	char ennemyBishop = (king == whiteKing ? blackBishop : whiteBishop);
	char ennemyKnight = (king == whiteKing ? blackKnight : whiteKnight);
	char ennemyPawn = (king == whiteKing ? blackPawn : whitePawn);
	char ennemyKing = (king == whiteKing ? blackKing : whiteKing);

	//check for rooks/queen threatening
	  //check for threat underneath
	for (int i=row+1; i<8; i++){
		if(isSameColorOrEmpty(king, board[i][col]) == 1){
			break;
		}
		if( board[i][col] == ennemyRook || board[i][col] == ennemyQueen){
			return 1;
		}
	}
	  //check for threat above
	for (int i=row-1; i>=0; i--){
		if(isSameColorOrEmpty(king, board[i][col]) == 1){
			break;
		}
		if( board[i][col] == ennemyRook || board[i][col] == ennemyQueen){
			return 1;
		}
	}
	  //check for threat to the right
	for (int i=col+1; i<8; i++){
		if(isSameColorOrEmpty(king, board[row][i]) == 1){
			break;
		}
		if( board[row][i] == ennemyRook || board[row][i] == ennemyQueen){
			return 1;
		}
	}
	  //check for threat to the left
	for (int i=col-1; i>=0; i--){
		if(isSameColorOrEmpty(king, board[row][i]) == 1){
			break;
		}
		if( board[row][i] == ennemyRook || board[row][i] == ennemyQueen){
			return 1;
		}
	}
	//check for bishops/queen threatening 
	  //check bottom right diagonal
	for ( int i=0; (row+(i+1)<8) && (col+(i+1)<8) ; i++ ){
		if(isSameColorOrEmpty(king, board[row+(i+1)][col+(i+1)]) == 1){
			break;
		}
		if( board[row+(i+1)][col+(i+1)] == ennemyBishop || board[row+(i+1)][col+(i+1)] == ennemyQueen){
			return 1;
		}
	}

	  //check bottom left diagonal
	for ( int i=0; (row+(i+1)<8) && (col-(i+1)>=0) ; i++ ){
		if(isSameColorOrEmpty(king, board[row+(i+1)][col-(i+1)]) == 1){
			break;
		}
		if( board[row+(i+1)][col-(i+1)] == ennemyBishop || board[row+(i+1)][col-(i+1)] == ennemyQueen){
			return 1;
		}
	}

	  //check top left diagonal
	for ( int i=0; (row-(i+1)>=0) && (col-(i+1)>=0) ; i++ ){
		if(isSameColorOrEmpty(king, board[row-(i+1)][col-(i+1)]) == 1){
			break;
		}
		if( board[row-(i+1)][col-(i+1)] == ennemyBishop || board[row-(i+1)][col-(i+1)] == ennemyQueen){
			return 1;
		}
	}

	  //check top right diagonal
	for ( int i=0; (row-(i+1)>=0) && (col+(i+1)<8) ; i++ ){
		if(isSameColorOrEmpty(king, board[row-(i+1)][col+(i+1)]) == 1){
			break;
		}
		if( board[row-(i+1)][col+(i+1)] == ennemyBishop || board[row-(i+1)][col+(i+1)] == ennemyQueen){
			return 1;
		}
	}

	
	//check for pawns
	int isPawnTopLeft = (king==whiteKing) && (row-1 >= 0) && (col-1 >= 0) && (board[row-1][col-1]==ennemyPawn);
	int isPawnTopRight = (king==whiteKing) && (row-1 >= 0) && (col+1 < 8) && (board[row-1][col+1]==ennemyPawn);
	int isPawnBottomRight = (king==blackKing) && (row+1 < 8) && (col+1 < 8) && (board[row+1][col+1]==ennemyPawn);
	int isPawnBottomLeft = (king==blackKing) && (row+1 < 8) && (col-1 >= 0) && (board[row+1][col-1]==ennemyPawn);

	if(isPawnTopLeft || isPawnTopRight || isPawnBottomRight || isPawnBottomLeft){
		return 1;
	}

	//check for knights 	
	int isNTopLeft = (row-2 >= 0) && (col-1 >= 0) && board[row-2][col-1]==ennemyKnight;
	int isNTopRight = (row-2 >= 0) && (col+1 < 8) && board[row-2][col+1]==ennemyKnight;
	int isNRightTop = (row-1 >= 0) && (col+2 < 8) && board[row-1][col+2]==ennemyKnight;
	int isNRightBottom = (row+1 < 8) && (col+2 < 8) && board[row+1][col+2]==ennemyKnight;
	int isNBottomRight = (row+2 < 8) && (col+1 < 8) && board[row+2][col+1]==ennemyKnight;
	int isNBottomLeft = (row+2 < 8) && (col-1 >= 0) && board[row+2][col-1]==ennemyKnight;
	int isNLeftBottom = (row+1 < 8) && (col-2 >= 0) && board[row+1][col-2]==ennemyKnight;
	int isNLeftTop = (row-1 >= 0) && (col-2 >= 0) && board[row-1][col-2]==ennemyKnight;

	if(isNTopLeft||isNTopRight||isNRightTop||isNRightBottom||isNBottomRight||isNBottomLeft||isNLeftBottom||isNLeftTop){
		return 1;
	}


	//check for king
	int isKingTopLeft = (row-1 >= 0) && (col-1 >= 0) && (board[row-1][col-1]==ennemyKing);
	int isKingTopRight = (row-1 >= 0) && (col+1 < 8) && (board[row-1][col+1]==ennemyKing);
	int isKingBottomRight = (row+1 < 8) && (col+1 < 8) && (board[row+1][col+1]==ennemyKing);
	int isKingBottomLeft = (row+1 < 8) && (col+1 >= 0) && (board[row+1][col-1]==ennemyKing);

	if(isKingTopLeft || isKingTopRight || isKingBottomRight || isKingBottomLeft){
		return 1;
	}
	
	return 0;

}