/**
 * Class Set is a class object acting as the holder for parent and rank
 * for a node
 * 
 * @author Kabo Cheung
 * @version 1-Apr-2016
 */
public class Set {
	// private variables
	private int parent;
	private int rank;
	
	// public variables
	public Set(int parent, int rank){
		this.parent = parent;
		this.rank = rank;
	}
	
	// Access to parent
	public int getParent(){
		return this.parent;
	}
	
	// Access to rank
	public int getRank(){
		return this.rank;
	}
	
	// Change the parent
	public void changeParent(int parent){
		this.parent = parent;
	}
	
	// Add up the rank
	public void upRank(){
		this.rank++;
	}
}
