/**
 * Class Edge is a class object acting as the edge that will connect 
 * a pair of vertices in the graph
 * 
 * @author Kabo Cheung
 * @version 13-Mar-2016
 */
public class Edge implements Cloneable{
	// Private variables
	private Node toNode;
	private Node fromNode;
	private int weight;
	
	// Public variables
	public Edge(Node toNode, Node fromNode, int weight){
		this.toNode = toNode;
		this.fromNode = fromNode;
		this.weight = weight;
	}
	
	// Access to the node connecting to this edge; Right vertex
	public Node getToNode(){
		return this.toNode;
	}
	
	// Access to the node connecting from this edge; Left vertex
	public Node getFromNode(){
		return this.fromNode;
	}
	
	// Access to the weight of this edge
	public int getWeight(){
		return this.weight;
	}
	
	// Return true if the weight of this edge is less than that of the given edge; Otherwise, return false 
	public boolean lessThan(Edge edge){
		return (this.weight < edge.weight);
	}
	
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}