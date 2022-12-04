import java.util.*;  

public class DataStructure {
	
	
	// first array is the x values
	// second array is the y values
	private  Square[][] GameBoard;
	private Vector<Coordinate> WhitePossibleMoves;
	private Vector<Coordinate> BlackPossibleMoves;
	private int whiteNum;
	private int blackNum;
	
	public int getWhiteNum() {
		return whiteNum;
	}
	public int getBlackNum() {
		return blackNum;
	}
	
	private class Square {
		static final int black = 1;
		static final int white = 2;
		static final int empty = 0;
		private int state = empty;
		public Coordinate coordinate;
		
		public Square(int x, int y) {
			coordinate = new Coordinate(x, y);
		}
		int setSquare(int value) {
			
			if (value != black && value!=white) {
				System.out.println("Invalid square value! "+ value);
				return (state = 0);
				
			}
			else {
				if(value == black)
					blackNum++;
				else if(value == white)
					whiteNum++;
				return state = value;
			}
				
				
		}
		int getSquareState() {
			return state;
		}
		//for easy switching
		void switchSquare() {
			if(state == black) {
				state = white;
				blackNum--;
				whiteNum++;
			}
			else if (state == white) {
				state=black;
				blackNum++;
				whiteNum--;
			}
		}
	}
	public int getSquareState(int x,int y ) {
		return this.GameBoard[x][y].getSquareState();
	}
	public DataStructure() {
		this.GameBoard = new Square[8][8];
		for (int i = 0; i < 8; i++) {
			 for(int j= 0; j<8; j++) {
				 GameBoard[i][j] = new Square(i,j);
			 }
		}
		this.GameBoard[3][3].setSquare(Square.white);
		this.GameBoard[4][4].setSquare(Square.white);
		this.GameBoard[4][3].setSquare(Square.black);
		this.GameBoard[3][4].setSquare(Square.black);
		this.WhitePossibleMoves= new Vector<Coordinate>();
		this.BlackPossibleMoves= new Vector<Coordinate>();
	}
	
	
	
	
	//we will try to see if we are flanking any other opposition stones. 
	//if we find any flanks 
	//		we will turn the flanked squares to our 
	private void conductFlank(Square flanker) {
		//we will need to search different vectors 
		for(int i = flanker.coordinate.getX()-1; i>=0; i--) {
			//search left vector
			//if square at coordinates x/y is different than the flanker AND not null
			// this is also the first square we have come across that hits this criteria
			// that means we have conducted a flank
			//reverse the loop and switch each tile up until the square coordinates.
			if(GameBoard[i][flanker.coordinate.getY()].getSquareState()==0){
				break;//we hit a empty place there is no flank on this vector.
			}
			else if(GameBoard[i][flanker.coordinate.getY()].getSquareState()==flanker.getSquareState()) {
				//we hit opposing site of flank
				i++;
				for(;i<flanker.coordinate.getX();i++) {
					//travel back to flanker
					GameBoard[i][flanker.coordinate.getY()].switchSquare();
				}
				break;
			}
			
			
		}
		for(int i = flanker.coordinate.getX()+1; i<8; i++) {
			//search right vector
			if(GameBoard[i][flanker.coordinate.getY()].getSquareState()==0){
				break;//we hit a empty place there is no flank on this vector.
			}
			else if(GameBoard[i][flanker.coordinate.getY()].getSquareState()==flanker.getSquareState()) {
				//we hit opposing site of flank
				i--;
				for(;i>flanker.coordinate.getX();i--) {
					//travel back to flanker
					GameBoard[i][flanker.coordinate.getY()].switchSquare();
				}
				break;
			}
		}
		
		for(int j = flanker.coordinate.getY()-1; j>=0; j--) {
			//search north vector
			if(GameBoard[flanker.coordinate.getX()][j].getSquareState()==0){
				break;//we hit a empty place there is no flank on this vector.
			}
			else if(GameBoard[flanker.coordinate.getX()][j].getSquareState()==flanker.getSquareState()) {
				//we hit opposing site of flank
				j++;
				for(;j<flanker.coordinate.getY();j++) {
					//travel back to flanker
					GameBoard[flanker.coordinate.getX()][j].switchSquare();
				}
				break;
			}
		}
		for(int j = flanker.coordinate.getY()+1; j<8; j++) {
			//search south vector
			if(GameBoard[flanker.coordinate.getX()][j].getSquareState()==0){
				break;//we hit a empty place there is no flank on this vector.
			}
			else if(GameBoard[flanker.coordinate.getX()][j].getSquareState()==flanker.getSquareState()) {
				//we hit opposing site of flank
				j--;
				for(;j>flanker.coordinate.getY();j--) {
					//travel back to flanker
					GameBoard[flanker.coordinate.getX()][j].switchSquare();
				}
				break;
			}
			
		}
		//we gotta search crross vectors 
		
		for(int c = 1; flanker.coordinate.getY()- c>=0 && flanker.coordinate.getX()- c >=0; c++) {
			 //search north west 
			int cY =flanker.coordinate.getY()- c;
			int cX =flanker.coordinate.getX()- c;
			if(GameBoard[cX][cY].getSquareState()==0) {
				break;
			}
			else if(GameBoard[cX][cY].getSquareState()==flanker.getSquareState()) {
				c--;
				for(;c>0;c--) {
					GameBoard[flanker.coordinate.getX()-c ][flanker.coordinate.getY()- c].switchSquare();
				}
				break;
			}
		}
		for(int c = 1; c+flanker.coordinate.getY()<8 && flanker.coordinate.getX()-c >=0; c++) {
			 //search sought west
			int cY =flanker.coordinate.getY()+ c;
			int cX =flanker.coordinate.getX()- c;
			
			if(GameBoard[cX][cY].getSquareState()==0) {
				break;
			}
			else if(GameBoard[cX][cY].getSquareState()==flanker.getSquareState()) {
				c--;
				for(;c>0;c--) {
					GameBoard[flanker.coordinate.getX()-c ][flanker.coordinate.getY()+ c].switchSquare();
				}
				break;
			}
		}
		for(int c = 1; flanker.coordinate.getY()-c>=0 && c+flanker.coordinate.getX()<8; c++) {
			 //search north east 
			int cY =flanker.coordinate.getY()- c;
			int cX =flanker.coordinate.getX()+ c;
			
			if(GameBoard[cX][cY].getSquareState()==0) {
				break;
			}
			else if(GameBoard[cX][cY].getSquareState()==flanker.getSquareState()) {
				c--;
				for(;c>0;c--) {
					GameBoard[flanker.coordinate.getX()+c ][flanker.coordinate.getY()- c].switchSquare();
				}
				break;
			}
		}
		for(int c = 1; c+flanker.coordinate.getY()<8 && c+flanker.coordinate.getX()<8; c++) {
			 //search sought east
			int cY =flanker.coordinate.getY()+ c;
			int cX =flanker.coordinate.getX()+ c;
			if(GameBoard[cX][cY].getSquareState()==0) {
				break;
			}
			else if(GameBoard[cX][cY].getSquareState()==flanker.getSquareState()) {
				c--;
				for(;c>0;c--) {
					GameBoard[flanker.coordinate.getX()+c ][flanker.coordinate.getY()+ c].switchSquare();
				}
				break;
			}
		}
		FindAllPossibleMovesFor(flanker.getSquareState());
		if(flanker.getSquareState()==1)
			FindAllPossibleMovesFor(2);
		else
			FindAllPossibleMovesFor(1);
		
	}
	
	private Vector<Coordinate> squareScan(Square flanker) {
		//this method is for finding possible flanks 
		//that is possible with given square 
		//it takes a single square and returns an 
		//array of possible other EMPTY coordinates that 
		//would result in a flank to the opposition
		
		
		//with this one
		Vector<Coordinate> possibleMoves = new Vector<Coordinate>(); 
		
		//we will need to search different vectors 
		boolean includes_oposition = false;
		for(int i = flanker.coordinate.getX()-1; i>=0; i--) {
			//search left vector
			
			if(GameBoard[i][flanker.coordinate.getY()].getSquareState()==flanker.getSquareState()) {
				break;
				//if we hit a same vector then it is impossible to flank from here as the path is already taken
				
			}
			else if(GameBoard[i][flanker.coordinate.getY()].getSquareState()==0) {
				if(includes_oposition) {
					possibleMoves.add(GameBoard[i][flanker.coordinate.getY()].coordinate);
					break;
				}
				else
					break;
				//if we have found an oposition token when traversing path 
				   // then this empty square allows us to flank them
				//else its just empty space and doesn't allow us to flank
			}
			else {
				//this else means that we have found an opposition token to flank!
				includes_oposition = true;
			}
			
			
			
		}
		
		
		includes_oposition = false;//reset if for each vector of search
		for(int i = flanker.coordinate.getX()+1; i<8; i++) {
			//search right vector
			
			
			if(GameBoard[i][flanker.coordinate.getY()].getSquareState()==flanker.getSquareState()) {
				break;
			}
			else if(GameBoard[i][flanker.coordinate.getY()].getSquareState()==0) {
				if(includes_oposition) {
					possibleMoves.add(GameBoard[i][flanker.coordinate.getY()].coordinate);
					break;//we stop looking at this direction because there isn't any other flanking pos this place
				}
					
				else
					break;
				//if we have found an oposition token when traversing path 
				   // then this empty square allows us to flank them
				//else its just empty space and doesn't allow us to flank
			}
			else {
				//this else means that we have found an opposition token to flank!
				includes_oposition = true;
			}
			
			
		}
		
		
		includes_oposition = false;//reset if for each vector of search
		for(int j = flanker.coordinate.getY()-1; j>=0; j--) {
			//search north vector
			if(GameBoard[flanker.coordinate.getX( )][j].getSquareState()==flanker.getSquareState()) {
				break;
				
			}
			else if(GameBoard[flanker.coordinate.getX() ][j].getSquareState()==0) {
				if(includes_oposition) {
					possibleMoves.add(GameBoard[flanker.coordinate.getX() ][j].coordinate);
				    break;
				}
				else
					break;
				//if we have found an oposition token when traversing path 
				   // then this empty square allows us to flank them
				//else its just empty space and doesn't allow us to flank
			}
			else {
				//this else means that we have found an opposition token to flank!
				includes_oposition = true;
			}
			
		}
		
		
		includes_oposition = false;//reset if for each vector of search
		for(int j = flanker.coordinate.getY()+1; j<8; j++) {
			//search south vector
			if(GameBoard[flanker.coordinate.getX( )][j].getSquareState()==flanker.getSquareState()) {
				break;
				
			}
			else if(GameBoard[flanker.coordinate.getX() ][j].getSquareState()==0) {
				if(includes_oposition) {

					possibleMoves.add(GameBoard[flanker.coordinate.getX() ][j].coordinate);
					break;
				}
				else
					break;
				//if we have found an oposition token when traversing path 
				   // then this empty square allows us to flank them
				//else its just empty space and doesn't allow us to flank
			}
			else {
				//this else means that we have found an opposition token to flank!
				includes_oposition = true;
			}
			
		}
		
		
		//we have to search cross vectors 
		includes_oposition = false;//reset if for each vector of search
		for(int c = 1; flanker.coordinate.getY()- c>=0 && flanker.coordinate.getX()- c >=0; c++) {
			 //search north west 
			int cY =flanker.coordinate.getY()- c;
			int cX =flanker.coordinate.getX()- c;
			
			if(GameBoard[cX][cY].getSquareState()==flanker.getSquareState()) {
				break;
			}
			else if(GameBoard[cX][cY].getSquareState()==0) {
				if(includes_oposition) {
					possibleMoves.add(GameBoard[cX][cY].coordinate);
					break;
				}
				else
					break;
				//if we have found an oposition token when traversing path 
				   // then this empty square allows us to flank them
				//else its just empty space and doesn't allow us to flank
			}
			else {
				//this else means that we have found an opposition token to flank!
				includes_oposition = true;
			}
			
		}
		
		includes_oposition = false;//reset if for each vector of search
		for(int c = 1; c+flanker.coordinate.getY()<8 && flanker.coordinate.getX()-c >=0; c++) {
			 //search sought west
			int cY =flanker.coordinate.getY()+ c;
			int cX =flanker.coordinate.getX()- c;
			
			if(GameBoard[cX][cY].getSquareState()==flanker.getSquareState()) {
				
				break;
			}
			if(GameBoard[cX][cY].getSquareState()==0) {
				if(includes_oposition) {
					possibleMoves.add(GameBoard[cX][cY].coordinate);
					break;
				}
				else
					break;
				//if we have found an oposition token when traversing path 
				   // then this empty square allows us to flank them
				//else its just empty space and doesn't allow us to flank
			}
			else {
				//this else means that we have found an opposition token to flank!
				includes_oposition = true;
			}
			
		}
		
		includes_oposition = false;//reset if for each vector of search
		for(int c = 1; flanker.coordinate.getY()-c>=0 && c+flanker.coordinate.getX()<8; c++) {
			 //search north east 
			int cY =flanker.coordinate.getY()- c;
			int cX =flanker.coordinate.getX()+ c;
			
			if(GameBoard[cX][cY].getSquareState()==flanker.getSquareState()) {
				break;
				
			}
			if(GameBoard[cX][cY].getSquareState()==0) {
				if(includes_oposition) {
					possibleMoves.add(GameBoard[cX][cY].coordinate);
					break;
				}
				else
					break;
				//if we have found an oposition token when traversing path 
				   // then this empty square allows us to flank them
				//else its just empty space and doesn't allow us to flank
			}
			else {
				//this else means that we have found an opposition token to flank!
				includes_oposition = true;
			}
			
		}
		
		
		includes_oposition = false;//reset if for each vector of search
		for(int c = 1; c+flanker.coordinate.getY()<8 && c+flanker.coordinate.getX()<8; c++) {
			 //search sought east
			int cY =flanker.coordinate.getY()+ c;
			int cX =flanker.coordinate.getX()+ c;
			if(GameBoard[cX][cY].getSquareState()==flanker.getSquareState()) {
				break;
				
			}
			if(GameBoard[cX][cY].getSquareState()==0) {
				if(includes_oposition) {
					possibleMoves.add(GameBoard[cX][cY].coordinate);
					break;
				}
				else
					break;
				//if we have found an oposition token when traversing path 
				   // then this empty square allows us to flank them
				//else its just empty space and doesn't allow us to flank
			}
			else {
				//this else means that we have found an opposition token to flank!
				includes_oposition = true;
			}
			
		}
		return possibleMoves;
	}
	
	// we should implement  a function to find all possible moves not just a single one.
	private void FindAllPossibleMovesFor(int player) {
		if(player==1) {
			//we work with black pieces
			if(!BlackPossibleMoves.isEmpty())
				BlackPossibleMoves.clear();
			//clear previous moves.
			for(Square[] xAxis :GameBoard) {
				for(Square yAxisSquare : xAxis) {
					if(yAxisSquare.getSquareState() == player ) {
						Vector<Coordinate> yAxisSquarePossibleMoves = squareScan(yAxisSquare);
						//get all the possible moves that can be done from there
						
						
						boolean isDup = false;//initiate is duplicate value before loop so don't waste time
						for(Coordinate k : yAxisSquarePossibleMoves) {
							//search if k is duplicate / already in possible moves vector
							isDup = false;//we assume k coord is new
							for (Coordinate l : BlackPossibleMoves) {
								if(l.getX() == k.getX() &&
								   l.getY() == k.getY()) {
									isDup = true;
									break;
								}
							}
							if(!isDup) {
								BlackPossibleMoves.add(k);
							}
						}
					}
					
					
				}
			}
		}
		else if(player == 2) {
			//we work with white pieces
			if(!WhitePossibleMoves.isEmpty())
				WhitePossibleMoves.clear();
			
			//clear previous moves.
			for(Square[] xAxis :GameBoard) {
				for(Square yAxisSquare : xAxis) {
					if(yAxisSquare.getSquareState()==player) {
						Vector<Coordinate> yAxisSquarePossibleMoves = squareScan(yAxisSquare);
						//get all the possible moves that can be done from there
						
						
						boolean isDup = false;//initiate is duplicate value before loop so don't waste time
						for(Coordinate k : yAxisSquarePossibleMoves) {
							//search if k is duplicate / already in possible moves vector
							isDup = false;//we assume k coord is new
							for (Coordinate l : WhitePossibleMoves) {
								if(l.getX() == k.getX() &&
								   l.getY() == k.getY()) {
									isDup = true;
									break;
								}
							}
							if(!isDup) {
								WhitePossibleMoves.add(k);
							}
						}
					}
					
					
				}
			}
		}
		else {
			System.out.println("Invalid player value! Cant Find possible moves!");
		}
		
	}
	// maybe use trees 
	public Vector<Coordinate> getWhitePossibleMoves() {
		return WhitePossibleMoves;
	}
	public Vector<Coordinate> getBlackPossibleMoves() {
		return BlackPossibleMoves;
	}

	public void setBoardSquare(int x, int y, int p) {
		GameBoard[x][y].setSquare(p);
		conductFlank(GameBoard[x][y]);
	}
	
	public void printBoard() {
		String str = "";
		for(int k= 0; k<8; k++) {
			for(int l=0; l<8;l++){
				str+="["+this.GameBoard[l][k].getSquareState()+"] ";
			}
			str+= "\n";
		}
		System.out.print(str);
	}
	

	public void printPossibleMoves(){
		System.out.println("black possible moves: ");
		for(Coordinate k:BlackPossibleMoves) {
			System.out.print("(x:"+k.getX() +" ,y:"+k.getY()+")");
		}
		System.out.println("\n white possible moves: ");
		for(Coordinate k:WhitePossibleMoves) {
			System.out.print("(x:"+k.getX() +" ,y:"+k.getY()+")");
			
		}
		System.out.println("\n");
	}
	
	public static void main(String[] args){
		//test system black starts 
		System.out.println("wellcome ");
		DataStructure ds = new DataStructure();
		ds.printBoard();
		
		
		ds.FindAllPossibleMovesFor(2);
		ds.FindAllPossibleMovesFor(1);
		ds.printPossibleMoves();
		Scanner scnnr = new Scanner(System.in);
		int x = 0;
		int y = 0;
		boolean illegalMove=true;

		
		while(true) {
			System.out.println("black starts ");
			
			if(ds.BlackPossibleMoves.isEmpty()) {
				System.out.println("White wins!");
				break;
			}
			ds.printPossibleMoves();
			// black makes it's move 
			illegalMove=true;
			while(illegalMove) {
				System.out.println("Black to move. Place a black tile.");
				System.out.println("enter x:");
				x= scnnr.nextInt();
				System.out.println("enter y:");
				y= scnnr.nextInt();
				for(Coordinate k:ds.BlackPossibleMoves) {
					if (k.getX()==x&&k.getY()==y) {
						illegalMove = false;
					}
				}
				System.out.println(illegalMove);
				if(illegalMove) {
					System.out.println("YOU ENTERED AN ILLEGAL MOVE TRY IT AGAIN");
					System.out.println("Possible moves:");
					ds.printPossibleMoves();
					System.out.println("");
				}
				
			}
			
			
			
			
			//set our desired squre black
			ds.GameBoard[x][y].setSquare(1);
			
			ds.printBoard();
			System.out.println("before flanking change");
			ds.conductFlank(ds.GameBoard[x][y]);
			ds.printBoard();
			System.out.println("after flanking change\n");
			
			
			
			if(ds.WhitePossibleMoves.isEmpty()) {
				System.out.println("Black wins!");
				break;
			}
			ds.printPossibleMoves();
			// white makes it's move
			illegalMove=true;
			while(illegalMove) {
				System.out.println("white to move. Place a white tile.");
				System.out.println("enter x:");
				x= scnnr.nextInt();
				System.out.println("enter y:");
				y= scnnr.nextInt();
				for(Coordinate k:ds.WhitePossibleMoves) {
					if (k.getX()==x&&k.getY()==y) {
						illegalMove = false;
					}
				}
				System.out.println(illegalMove);
				if(illegalMove) {
					System.out.println("YOU ENTERED AN ILLEGAL MOVE TRY IT AGAIN");
					System.out.println("Possible moves:");
					ds.printPossibleMoves();
					System.out.println("");
				}
			}
			ds.GameBoard[x][y].setSquare(2);
			ds.printBoard();
			System.out.println("before flanking change");
			ds.conductFlank(ds.GameBoard[x][y]);
			ds.printBoard();
			System.out.println("after flanking change\n");
			
			
		}
		
	}
	
}

