import java.util.ArrayList;
import java.util.Random;
/**
 * Class Graph is sub program that generates a randomly connected, 
 * undirected, weighted graph and display results in adjacency matrix, 
 * adjacency list, and a list of predecessors. 
 * 
 * @author Kabo Cheung
 * @version 13-Mar-2016
 */
public class Graph{
	/**
	 * result is a method that displays results in adjacency matrix, 
	 * adjacency list, and a list of predecessors. Used only if there is
	 * less than 10 vertices
	 * 
	 * @param graph - a graph of connected vertices
	 * @param display - display the results if less than 10 vertices exist
	 * 
	 * @return a class Record consisting of array of unsorted edges and runtimes for 
	 * both adjacency matrix and adjacency list
	 */
	
	public static Record result(ArrayList<Node> graph, boolean display){
		// For the adjacency matrix, display the weight of each edge found 
		// in the graph using a single nested loop
		ArrayList<Edge> matrix_array = new ArrayList<Edge>(); // Initialize the array of edges for adjacency matrix
		long matrix_start_time = System.currentTimeMillis(); // Start the timer for the adjacency matrix
		if (display){
			System.out.format("\n");
			System.out.println("The graph as an adjacency matrix:\n");
		}
		for (int x = 0; x < graph.size(); x++){
			for (int y = 0; y < graph.size(); y++){
				int connected = graph.get(x).checkEdges(y);
				if (connected != -1){
					if (x < y){ // Add only unique edges to the array 
						matrix_array.add(graph.get(x).getEdges().get(connected));
					}
					int weight = graph.get(x).getEdges().get(connected).getWeight();
					if (display){
						System.out.format("%-4d", weight);
					}
				}else{
					if (display){
						System.out.format("%-4d", 0); // If no edge detected, weight = 0 
					}
				}
			}
			if (display){
				System.out.print("\n\n");
			}
		}
		
		long matrix_end_time = System.currentTimeMillis(); // Start the timer for the adjacency list
		long matrix_time = matrix_end_time - matrix_start_time; // Find the running time for adjacency matrix
		
		// For the adjacency list, display the label of each node, its edges, 
		// and the weight of its edges using a single nested loop
		ArrayList<Edge> list_array = new ArrayList<Edge>(); // Initialize array of edges for the adjacency list
		long list_start_time = System.currentTimeMillis(); // Start the timer for the adjacency list
		if (display){
			System.out.println("The graph as an adjacency list:");
		}
		for (int vertice = 0; vertice < graph.size(); vertice++){
			int size = graph.get(vertice).getEdges().size(); 
			if (display){
				System.out.format("%d-> ", vertice);
			}
			
			for (int edge = 0; edge < size; edge++){
				if (vertice < (graph.get(vertice).getEdges().get(edge).getToNode().getNum())){ // Add only unique edges to the array
					list_array.add(graph.get(vertice).getEdges().get(edge));
				}
				Edge checkEdge = graph.get(vertice).getEdges().get(edge);
				int num = checkEdge.getToNode().getNum();
				int weight = checkEdge.getWeight();
				if (display){
					System.out.format("%d(%d) ", num, weight);
				}
			}
			if (display){
				System.out.format("\n");
			}
		}
		
		long list_end_time = System.currentTimeMillis(); // End the timer for the adjacency list
		long list_time = list_end_time - list_start_time; // Find the running time for the adjacency list
		
		if (display){
			System.out.format("\n");
		}
		
		// DFS Result
		if (display){
			System.out.println("Depth-First Search:");
			System.out.println("Vertices:");
			for (int v = 0; v < graph.size(); v++){
				System.out.format("%2d", v);
			}
		}
		
		// For the list of predecessors, display the predecessor of each node
		// found in the graph
		if (display){
			System.out.print("\nPredecessors:\n");
			for (int v = 0; v < graph.size(); v++){
				int predecessor = graph.get(v).getPredecessor();
				System.out.format("%2d", predecessor);
			}
		}
		return new Record(matrix_array, matrix_time, list_array, list_time, graph);
	}
	
	/**
	 * DFS_Visit is a recursive Depth First Search algorithm used after the first DFS algorithm 
	 * 
	 * @param graph - the graph of connected vertices
	 * @param num - label of the predecessor 
	 * @param count - number of vertices visited
	 * 
	 * @return count - number of vertices visited to be kept through the recursion
	 */
	public static int DFS_Visit(ArrayList<Node> graph, int num, int count){
		graph.get(num).gray(); //Change the color of the predecessor to gray
		for (int v = 0; v < graph.get(num).getEdges().size(); v++){
			Node checkNode = graph.get(num).getEdges().get(v).getToNode();
			// Visit the white node from each edge of this predecessor
			if (checkNode.getColor() == 'w'){
				count++;
				graph.get(checkNode.getNum()).addPredecessor(num);
				count = DFS_Visit(graph, checkNode.getNum(), count);
			}
		}
		graph.get(num).black();
		return count;
	}
	
	/**
	 * DFS is a Depth First Search algorithm used to check if the graph is connected.  
	 * 
	 * @param graph - the graph of connected vertices
	 * 
	 * @return true if the number of visited vertices is the same as the total number 
	 * vertices of the graph. Otherwise, return false
	 * 
	 */
	public static boolean DFS(ArrayList<Node> graph){
		int count = 0;
		for (int v = 0; v < graph.get(0).getEdges().size(); v++){
			if (graph.get(v).getColor() == 'w'){
				count++;
				count = DFS_Visit(graph, graph.get(v).getNum(), count);
			}
		}
		return count == graph.size();
	}
	
	/**
	 * creation is a random graph generator method. It uses random generators to determine  
	 * the chance of connection in each pair of different vertices and the value of the weight.  
	 * 
	 * @param n - number of vertices to be implemented 
	 * @param seed - seed for random generators
	 * @param p - possibility of connecting the pair of vertices
	 * 
	 * @return vertices - a successful generated random graph of connected vertices
	 * 
	 */
	public static ArrayList<Node>creation(int n, int seed, double p){
		// Set up random generators with seed 
		Random generator1 = new Random(seed); // Possibility of connecting 
		Random generator2 = new Random(seed*2); // Randomize the weight value
		while (true){
			ArrayList<Node> vertices = new ArrayList<Node>();
			for (int i = 0; i < n; i++){
				vertices.add(new Node(i));
			}
			// For each pair of different vertices using a single nested loop, 
			// generate a random chance to determine if these should be connected. 
			// If connected, generate a random value for the weight of the edge. 
			// Worst case if p is 0, meaning no connected graph; immediately return the vertices then
			if (p == 0){
				System.out.println("No connected graph due to p = 0");
				return vertices;
			}
			
			for (int a = 0; a < n; a++){
				for (int b = 0 + a; b < n; b++){
					if (a != b){
						double connect = generator1.nextDouble();
						if (connect <= p){
							int weight = generator2.nextInt(n) + 1;
							vertices.get(a).addEdge(vertices.get(b), weight);
							vertices.get(b).addEdge(vertices.get(a), weight);
						}
					}
				}
			}
			
			// If the graph of vertices is connected, then the random graph generation 
			// is successful. Otherwise, start over and generate another random graph.
			if (DFS(vertices)){
				return vertices;
			}
		}
	}
}
