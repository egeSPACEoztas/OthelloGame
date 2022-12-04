import Player.Move;
import Player.Player;
import java.time.*;
import java.util.*;  



public class LogicEngine implements Player{
	 DataStructure ds_actual;
	 DataStructure ds_logical;
	 
	 //a copy ds to evaluate moves beforehand.
	 
	 static final int black = 1;
	 static final int white = 2;
	 EvalTree evalTree ;
	 int color;//colour of the player
	 long time;
	 java.util.Random rnd;
	 long est;//event start time
	 //used to callculate how much an event has taken
	 
	 public void init( int order, long t, java.util.Random rnd ) {
		 est=System.currentTimeMillis();
		 color = order;// if starting first that means that this player is black
		 //else it is white
		 time = t;
		 this.rnd=rnd;
		 
		 ds_actual= new DataStructure();
		 
		 
		 //init ends,
		 time -= (est-System.currentTimeMillis());
		 //reduce time by time taken;
	 }
	 
	 public Move nextMove(Move prevMove, long tOpponent, long t ) {
		 
		 est=System.currentTimeMillis();
		 Vector<Coordinate> currentPossibleMoves;
		 Coordinate nextMoveCoordinate;
		 
		 
		 if(prevMove==null) {
			 
			 
			 //this is the first move so we gotta make a move in random
			 if(color==black) 
				 currentPossibleMoves = ds_actual.getBlackPossibleMoves();
			 
			 else
				 currentPossibleMoves = ds_actual.getWhitePossibleMoves();
			//get possible moves from ds_logical
			 nextMoveCoordinate=currentPossibleMoves.get(rnd.nextInt(currentPossibleMoves.size()));
			 time -= (est-System.currentTimeMillis());
			 //also should figure out what to do with given times***
			 return new Move(nextMoveCoordinate.getX(),nextMoveCoordinate.getY()); 
			 //choose one at random
			 //do the move
		 }
		 else {
			 
			 evalTree = new EvalTree(new Coordinate(prevMove.x,prevMove.y));
			 //root of our evaluation is the previous move
			 
			 //previous moves color is different than ours since we didn't do it
			 if(color==black) {
				 ds_actual.setBoardSquare(prevMove.x,prevMove.y,white);
				 //parse previous move
				 currentPossibleMoves = ds_actual.getBlackPossibleMoves();
				 //get new possible moves.
			 }	 
			 else {
				 ds_actual.setBoardSquare(prevMove.x,prevMove.y,black);
				 currentPossibleMoves = ds_actual.getWhitePossibleMoves();
			 }
			 for(Coordinate c : currentPossibleMoves) {
				 evalTree.addChild(new Node<Coordinate>(c, evalTree.getRoot(),color));
				 //add possible moves for black to the evaluation tree
			 }
			 
			 for(Node<Coordinate> node : evalTree.getRoot().children) {
				 //for each of current possible moves evaluate
				 node.setPoints(MinMaxEval(node, currentPossibleMoves,true,3));
			 }
			 nextMoveCoordinate=evalTree.getRoot().getMaxPointedChild().getCoordinate();
			 time -= (est-System.currentTimeMillis());
			 //also should figure out what to do with given times***
			 return new Move(nextMoveCoordinate.getX(),nextMoveCoordinate.getY()); 
		 }
		 
	 }
	 
	 private int MinMaxEval(Node<Coordinate> node, Vector<Coordinate> givenPossibleMoves, boolean isMax,int depth) {
		 //if we want to max value on this iteration
		 //we
		 int retval =0;
		 if(isMax) {
			 //we are evaluating for our own moves
			 if(depth==0) {
				 if(color==black) {
					 //logic engine is playing black
					node.setPoints(node.getParent().getPoints()+ ds_logical.getBlackNum()-ds_logical.getWhiteNum());
				 }
				 else if(color == white) {
					node.setPoints(node.getParent().getPoints()+ ds_logical.getWhiteNum()- ds_logical.getBlackNum());
				 }
				 retval= node.getPoints();
			 }
			 else {
				 if(color==black) {
					 //logic engine is playing black
					
					node.setPoints(node.getParent().getPoints()+ ds_logical.getBlackNum()-ds_logical.getWhiteNum());
					int chidIndex=0;
					for(Coordinate x: givenPossibleMoves) {
						ds_logical.setBoardSquare(x.getX(), x.getY(), color);
						for(Coordinate y:ds_logical.getWhitePossibleMoves()) {
							//now turn changes an we want to min for  white(or the other color)
							node.children.add(new Node(y,node,white));//added all the possible moves.
							//then we should minmax eval these childeren
							node.children.get(chidIndex).setPoints(MinMaxEval(node, ds_logical.getWhitePossibleMoves(),false,depth-1));
							//we are reducing the depth as turn passes from us.
							//we are also minimizing the point search as turn passes from us
							
							//our own
							
						}
						
					}
					node.setPoints(node.getMinPointedChild().getPoints());
					retval= node.getPoints();
				 }
				 else if(color == white) {
					 node.setPoints(node.getParent().getPoints()+ ds_logical.getWhiteNum()- ds_logical.getBlackNum());
					 int chidIndex=0;
						for(Coordinate x: givenPossibleMoves) {
							ds_logical.setBoardSquare(x.getX(), x.getY(), color);
							for(Coordinate y:ds_logical.getBlackPossibleMoves()) {
								//now turn changes an we want to min for  white(or the other color)
								node.children.add(new Node(y,node,black));//added all the possible moves.
								//then we should minmax eval these childeren
								node.children.get(chidIndex).setPoints(MinMaxEval(node, ds_logical.getBlackPossibleMoves(),false,depth-1));
								//we are reducing the depth as turn passes from us.
								//we are also minimizing the point search as turn passes from us
								
								//our own
								
							}
							
						}
						node.setPoints(node.getMinPointedChild().getPoints());
						retval= node.getPoints();
				 }
			 }
		 }
		 else { //is minimzing
			 if(depth==0) {
				 if(color==black) {
					 //logic engine is playing black
					 node.setPoints(node.getParent().getPoints()+ ds_logical.getBlackNum()-ds_logical.getWhiteNum());
				 }
				 else if(color == white) {
					 node.setPoints(node.getParent().getPoints()+ ds_logical.getWhiteNum()- ds_logical.getBlackNum());
				 }
				 retval= node.getPoints();
			 }
			 else {
				 if(color==black) {
					 //logic engine is playing black
					
					node.setPoints(node.getParent().getPoints()+ ds_logical.getBlackNum()-ds_logical.getWhiteNum());
					int chidIndex=0;
					for(Coordinate x: givenPossibleMoves) {
						ds_logical.setBoardSquare(x.getX(), x.getY(), color);
						for(Coordinate y:ds_logical.getWhitePossibleMoves()) {
							//now turn changes an we want to min for  white(or the other color)
							node.children.add(new Node(y,node,white));//added all the possible moves.
							//then we should minmax eval these childeren
							node.children.get(chidIndex).setPoints(MinMaxEval(node, ds_logical.getWhitePossibleMoves(),true,depth));
							//we are not  reducing the depth as turn passes back to us us.
							//we are also maximizing the point search as turn passes to us
							
							//our own
							
						}
						
					}
					node.setPoints(node.getMaxPointedChild().getPoints());
					retval= node.getPoints();
				 }
				 else if(color == white) {
					 node.setPoints(node.getParent().getPoints()+ ds_logical.getWhiteNum()- ds_logical.getBlackNum());
					 int chidIndex=0;
						for(Coordinate x: givenPossibleMoves) {
							ds_logical.setBoardSquare(x.getX(), x.getY(), color);
							for(Coordinate y:ds_logical.getBlackPossibleMoves()) {
								//now turn changes an we want to min for  white(or the other color)
								node.children.add(new Node(y,node,black));//added all the possible moves.
								//then we should minmax eval these childeren
								node.children.get(chidIndex).setPoints(MinMaxEval(node, ds_logical.getBlackPossibleMoves(),true,depth));
								//we are reducing the depth as turn passes from us.
								//we are also minimizing the point search as turn passes from us
								
								//our own
								
							}
							
						}
						node.setPoints(node.getMaxPointedChild().getPoints());
						retval= node.getPoints();
				 }
			 }
		 }
		return retval;
	 }
	  
	
}
