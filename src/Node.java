import java.util.List; 
import java.util.ArrayList;


public class Node<Coordinate> {
	    private Coordinate coordinate; //basically move that is done to get to this point
	    private int evalPoints;
	    private int turn; //1 for black. 2 for white. who's turn to be evaluated 
	    //current turn doer. who will do the next move?
	    private Node<Coordinate> parent;
	    public List<Node<Coordinate>> children;
	    private DataStructure BoardInstance;
	    int x = ((Coordinate) coordinate).getX();
	    //constructor 
	    public Node(Coordinate coordinate, Node<Coordinate> parent, int turn){
	    	this.coordinate=coordinate;
	    	this.parent=parent;
	    	this.turn=turn;
	    	//Bug one Can't reach into the functions.
	    	setBoard(parent.getBoard());
	    		BoardInstance.setBoardSquare(1,1,turn);
	    	
	    }
	    public void setBoard(DataStructure BoardInstance) {
	    	this.BoardInstance= BoardInstance;
	    }
	    public DataStructure getBoard() {
	    	return BoardInstance;
	    }
	    public int getTurn() {
	    	return turn;
	    }
	    public void setTurn(int x) {
	    	 turn = x;
	    }
	    
	    public int getPoints() {
	    	return evalPoints;
	    }
	    public void setPoints(int x) {
	    	 evalPoints = x;
	    }
	    
	    public Node<Coordinate> getParent() {
	    	return parent;
	    }
	    public Coordinate getCoordinate() {
	    	return coordinate;
	    }
	    public void addChild(Coordinate child, int turn) {
			children.add(new Node<Coordinate>(child, this,turn) );
		}
	    public Node<Coordinate> getMaxPointedChild() {
	    	int max=0;
	    	int maxindex=0;
	    	int index = 0;
	    	for(Node<Coordinate> n : children) {
	    		if (n.getPoints()>=max)
	    			maxindex=index;
	    		index++;
	    	}
	    	return children.get(maxindex);
	    }
	    public Node<Coordinate> getMinPointedChild() {
	    	int min=0;
	    	int minindex=0;
	    	int index = 0;
	    	for(Node<Coordinate> n : children) {
	    		if (n.getPoints()<=min)
	    			minindex=index;
	    		index++;
	    	}
	    	return children.get(minindex);
	    }
	    
}