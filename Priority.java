/**
 * Class Priority is a class object acting as the priority holder for each 
 * vertice in the binary heap
 * 
 * @author Kabo Cheung
 * @version 08-May-2016
 */

public class Priority{
	// Private variables
	private int id;
	private double key;
	private int parent;
	private Edge edge;
		
	// Public variables
	public Priority(int id){
		this.id = id;
		this.key = Double.POSITIVE_INFINITY;
		this.parent = -1;
		this.edge = null;
	}
		
	// Access to the id of the vertice
	public int getId(){
		return this.id;
	}
		
	// Access to the key of the vertice
	public double getKey(){
		return this.key;
	}
		
	// Access to the parent of the vertice
	public int getParent(){
		return this.parent;
	}
	
	// Access to the chosen edge with the least weight
	public Edge getEdge(){
		return this.edge;
	}
		
	// Update the key priority
	public void changeKey(int weight){
		this.key = weight;
	}
		
	// Update the parent of the vertice
	public void changeParent(int parent){
		this.parent = parent;
	}
		
	// Update the edge of the vertice
	public void updateEdge(Edge edge){
		this.edge = edge;
	}
}
