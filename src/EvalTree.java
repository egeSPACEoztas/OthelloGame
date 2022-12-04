import java.util.List; 
import java.util.ArrayList;

public class EvalTree {
	private Node<Coordinate> root;
	
	public EvalTree(Coordinate rootCoord) {
	    root = new Node<Coordinate>(rootCoord,root,0);
	    root.children = new ArrayList<Node<Coordinate>>();
	}
	
	public EvalTree(Coordinate rootCoord, int startTurn) {
	    root = new Node<Coordinate>(rootCoord, null,startTurn);
	    root.children = new ArrayList<Node<Coordinate>>();
	}
	
	public void clearTree() {
		root.children = null;
		root.setTurn(0);
		root.setPoints(0);
	}
	
	public void addChild(Node<Coordinate> node) {
		root.children.add(node);
	}
	public Node<Coordinate> getRoot() {
		return root;
	}
	
}
