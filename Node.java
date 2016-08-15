import java.util.ArrayList;

/**
 * Class Node is a class object acting as the node (or vertex) in the graph
 * 
 * @author Kabo Cheung
 * @version 21-Feb-2016
 */
public class Node implements Cloneable{
	// Private variables
	private int num;
	private ArrayList<Edge>edges;
	private char color;
	private int predecessor;
	
	// Public variables 
	public Node(int num){
		this.num = num;
		this.edges = new ArrayList<Edge>();
		this.color = 'w'; // Default color is white
		this.predecessor = -1;
	}
	
	// Access to the label of this node
	public int getNum(){
		return this.num;
	}
	
	// Access to the edges connected to this node 
	public ArrayList<Edge> getEdges(){
		return this.edges;
	}
	
	// Access to the color of this node; Unused and optional
	public char getColor(){
		return this.color;
	}
	
	// Access to the predecessor of this node
	public int getPredecessor(){
		return this.predecessor;
	}
	
	// Connect the edge to this node to another
	public void addEdge(Node node, int weight){
		Edge edge = new Edge(node, this, weight);
		this.edges.add(edge);
	}
	
	// Change the color of this node to gray
	public void gray(){
		this.color = 'g';
	}
	
	// Change the color of this node to black
	public void black(){
		this.color = 'b';
	}
	
	// Add the predecessor to this node
	public void addPredecessor(int predecessor){
		this.predecessor = predecessor;
	}
	
	// Access to the index of the target edge of this node
	public int checkEdges(int target){
		for (int index = 0; index < this.edges.size(); index++){
			if (this.edges.get(index).getToNode().getNum() == target){
				return index;
			}
		}
		// If no edges detected, return -1
		return -1;
	}
	
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}