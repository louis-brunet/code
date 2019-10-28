public class Piece {
	final static String white = "white";
	final static String black = "black";
	final static String rook = "rook";
	final static String knight = "knight";
	final static String bishop = "bishop";
	final static String queen = "queen";
	final static String king = "king";
	final static String pawn = "pawn";
	String team;
	String type;
	String iconPath;
	
	//create copy of piece p
	public Piece(Piece p) {
		team = p.team;
		type = p.type;
		iconPath = p.iconPath;
	}
	
	//create piece at row, col
	public Piece(int row, int col) {
		initType(row, col);
		initTeam(row);
		initPath();
	}
	
	private void initType(int row, int col) {
		if(row == 1 || row == 6 ) {
			type = pawn;
		}else if(col == 0 || col == 7) {
			type = rook;
		}else if(col == 1 || col == 6) {
			type = knight;
		}else if(col == 2 || col == 5) {
			type = bishop;
		}else if(col == 3) {
			type = queen;
		}else if(col == 4) {
			type = king;
		}else {
			type = "";
		}
	}
	
	private void initTeam(int row) {
		if( row < 2 ) {
			team = black;
		}else if( row > 5) {
			team = white;
		}else {
			team = "";
		}
	}
	
	private void initPath() {
		if(type != "" && type != "") {
			iconPath = "/resources/"+team+type+".png" ;
		}else {
			iconPath = "";
		}
	}
	
	public void empty() {
		team = "";
		type = "";
		iconPath = "";
	}
	
	public boolean isEmpty() {
		return ( team == "" || type == "" );
	}
	
	public boolean isSameTeam(Piece p) {
		return ( p.team == team);
	}
	
	public boolean isSameTeam(String team) {
		return ( this.team == team);
	}
	
	public boolean isOpposingTeam(Piece p) {
		if( team == white ) {
			return (p.team == black);
		}else if ( team == black) {
			return  (p.team == white);
		}else return false;
	}
	
	public boolean isOpposingTeam(String team) {
		if( this.team == white ) {
			return (team == black);
		}else if ( this.team == black ) {
			return  (team == white);
		}else return false;
	}
	
	public void setType(String type) {
		this.type = type;
		initPath();
	}
	
}
