import java.util.ArrayList;

/**
 * Class Kruskal is sub program that creates MST with least weighted path using the array of sorted edges 
 * created from either adjacency matrix and adjacency list and display the results
 * 
 * @author Kabo Cheung
 * @version 01-Apr-2016
 */
public class Kruskal {
	/**
	 * find is a method that determines which subset a particular
	 * element is in
	 * 
	 * @param x - current node being visited
	 * @param sets - array of sets for references
	 * @return the parent of current node
	 */
	public static int find(int x, ArrayList<Set>sets){
		if (sets.get(x).getParent() != x){
			sets.get(x).changeParent(find(sets.get(x).getParent(), sets));
		}
		return sets.get(x).getParent();
	}
	/**
	 * union is a method that join two subsets into a single subset
	 * 
	 * @param x - 1st subset
	 * @param y - 2nd subset
	 * @param sets - array of set for references
	 */
	public static void union(int x, int y, ArrayList<Set>sets){
		//System.out.println("Union 0 is : "+ x + " "+ sets.get(x).getParent() + " "+ sets.get(x).getRank());
		//System.out.println("Union 1 is : "+ x + " "+ sets.get(y).getParent() + " "+ sets.get(y).getRank());
		int xRoot = find(x, sets);
		int yRoot = find(y, sets);
		
		if (xRoot == yRoot){
			return;
		}
		
		// if x and y are not already in same set, merge them
		if (sets.get(xRoot).getRank() < sets.get(yRoot).getRank()){
			sets.get(xRoot).changeParent(yRoot);
		}else if(sets.get(xRoot).getRank() > sets.get(yRoot).getRank()){
			sets.get(yRoot).changeParent(xRoot);
		}else{
			sets.get(yRoot).changeParent(xRoot);
			sets.get(xRoot).upRank();
		}
	}
	/**
	 * A starter method for Kruskal's algorithm  
	 * 
	 * @param sorted_edges - an array of sorted edges
	 * @param recordTime - runtime from either adjacency matrix or adjacency list
	 * @param type1 - string of adjacency matrix or adjacency list
	 * @param type2 - string of sort algorithm
	 * @param display - display the sorted order of edges if less than 10 vertices exist
	 * @param nodeSize - number of nodes/vertices in the graph
	 */
	public static void start(ArrayList<Edge> sorted_edges, long recordTime, String type1, String type2, boolean display, int nodeSize){
		// Create an array of sets according to the vertices
		ArrayList<Edge> MST_edges = new ArrayList<Edge>();
		ArrayList<Set> sets = new ArrayList<Set>();
		// Make a set of parent and rank for each vertices
		for (int x = 0; x < nodeSize; x++){
			sets.add(new Set(x, 0));
		}
		
		long MST_start_time = System.currentTimeMillis(); // Start the timer for Kruskal
		// Kruskal's algorithm
		for (int i = 0; i < sorted_edges.size(); i++){
			int u = sorted_edges.get(i).getFromNode().getNum();
			int v = sorted_edges.get(i).getToNode().getNum();
			if (find(u, sets) != find(v, sets)){
				MST_edges.add(sorted_edges.get(i));
				union(u, v, sets);
			}
		}
		long MST_end_time = System.currentTimeMillis(); // End the timer for Kruskal
		long MST_time = MST_end_time - MST_start_time; // Running time for Kruskal
		long runtime = MST_time + recordTime; // Total time for Kruskal
		int total_weight = 0; // Initialize weight to find total weight
		
		System.out.println("===================================");
		System.out.println("KRUSKAL WITH " + type1 + " USING " + type2);	
		for (int i = 0; i < MST_edges.size(); i++){
			int fromNum = MST_edges.get(i).getFromNode().getNum();
			int toNum = MST_edges.get(i).getToNode().getNum();
			int weight = MST_edges.get(i).getWeight();
			total_weight += weight;
			if (display){
				System.out.format("%d %d weight = %d \n", fromNum, toNum, weight);
			}
		}
//		//Results
		System.out.format("\n");
		System.out.format("Total weight of MST using Kruskal: %d\n", total_weight);
		System.out.format("Runtime: %d milliseconds\n", runtime);
	}
}
