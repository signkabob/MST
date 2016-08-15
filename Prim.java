import java.util.ArrayList;

/**
 * Class Prim is sub program that create the binary heap of vertices
 * with priority queue and use Prim's algorithm to find the shortest path
 * based on the weight of the edges connecting from one vertice    
 * 
 * @author Kabo Cheung
 * @version 08-May-2016
 *
 */
public class Prim {
	/**
	 * swap is a method that swap two desired elements in an array 
	 * 
	 * @param edges - array of unique edges
	 * @param index1 - 1st element
	 * @param index2 - 2nd element
	 */
	public static void swap(ArrayList<Edge>edges, int index1, int index2){
		Edge temp = edges.get(index1);
		edges.set(index1, edges.get(index2));
		edges.set(index2, temp);
	}
	
	/**
	 * partition is part of the quicksort algorithm that chooses a pivot which
	 * is the last index of the array (edges) and begins comparing from lo to hi
	 * 
	 * Modified Lomuto partition scheme from Part 3 and change weight to id of toNode
	 * 
	 * @param edges - array of unsorted unique edges
	 * @param lo - low index
	 * @param hi - high index
	 * @return index that is to divide the partition to be sorted
	 */
	public static int partition(ArrayList<Edge> edges, int lo, int hi){
		Edge pivot = edges.get(hi);
		int i = lo;
		for (int j= lo; j<=hi; j++){
			if (edges.get(j).getToNode().getNum() < pivot.getToNode().getNum()){
				swap(edges, i, j);
				i++;
			// If two or more edges have the same toNode's id, then the sort will be in order of increasing left vertex
			}else if (edges.get(j).getToNode().getNum() == pivot.getToNode().getNum()){
				if (edges.get(j).getFromNode().getNum() < pivot.getFromNode().getNum()){
					swap(edges, i, j);
					i++;
				}else{
					//If two or more edges have the same toNode's id and the same left vertex, then the sort will be in order of increasing right vertex
					if (edges.get(j).getFromNode().getNum() == pivot.getFromNode().getNum()){
						if (edges.get(j).getToNode().getNum() < pivot.getToNode().getNum()){
							swap(edges, i, j);
							i++;
						}
					}
				}
			}
		}
		swap(edges, i, hi);
		return i;
	}
	
	/**
	 * quicksort is a quicksort starter algorithm which is recursive 
	 * 
	 * Modified Lomuto partition scheme
	 * 
	 * @param edges - array of unsorted unique edges
	 * @param lo - low index
	 * @param hi - high index
	 */
	public static void quicksort(ArrayList<Edge> edges, int lo, int hi){
		if (lo < hi){
			int j = partition(edges, lo, hi);
			quicksort(edges, lo, j-1);
			quicksort(edges, j + 1, hi);
		}
	}
	
	/**
	 * swim is a bottom-up approach to restore the heap order
	 *  
	 * @param heap - corrupted heap tree  
	 * @param k - the vertice to be exchanged bottom-up until the 
	 * heap order is corrected or reach the root vertice 
	 * 
	 * @return a correct heap tree
	 */
	public static ArrayList<Priority> swim(ArrayList<Priority> heap, int k){
		while (k > 1 && heap.get(k/2-1).getKey() > heap.get(k-1).getKey()){
			Priority temp = heap.get(k/2-1);
			heap.set(k/2-1, heap.get(k-1));
			heap.set(k-1, temp);
			k = k/2;
		} 
		return heap;
	}
	
	/**
	 * heapify checks if a heap tree order is correct.
	 * 
	 * @param heap - a heap tree
	 * @return - a corrected heap tree
	 */
	public static ArrayList<Priority> heapify(ArrayList<Priority> heap){
		for (int k = 1; 2*k+1 <= heap.size(); k++){
			// for each parent, check left child and right child if the heap order is correct
			Priority parent = heap.get(k-1);
			// Checking left child
			if (2*k <= heap.size()){
				Priority left = heap.get(2*k-1);
				// if the priority of left child is less than that of the parent, then swim-up
				if (left.getKey() < parent.getKey()){
					heap = swim(heap, 2*k);
				}
			}else{
				break; // if no left child exist, finish heapifying
			}
			
			// Checking right child
			if (2*k+1 <= heap.size()){
				Priority right = heap.get(2*k);
				// if the priority of right child is less than that of the parent, then swim-up
				if (right.getKey() < parent.getKey()){
					heap = swim(heap, 2*k+1);
				}
			}else{
				break; // if no right child exist, finish heapifying
			}
		}
		return heap;
	}
	
	/**
	 * prim is a starter method for Prim's algorithm with the creation of 
	 * priority queue and binary heap
	 * 
	 * @param nodes - an array of vertices
	 * @param recordTime - runtime from either adjacency matrix or adjacency list
	 * @param type - string of adjacency matrix or adjacency list
	 * @param display - display the sorted order of edges if less than 10 vertices exist 
	 * @param nodeSize - number of nodes/vertices in the graph
	 */
	public static void prim(ArrayList<Node> nodes, long recordTime, String type, boolean display, int nodeSize){
		long MST_start_time = System.currentTimeMillis(); // Start the timer for Kruskal
		// Prim's algorithm
		ArrayList<Priority> heap = new ArrayList<Priority>(); // Priority queue
		ArrayList<Edge> MST_edges = new ArrayList<Edge>(); // Array of edges in MST
		ArrayList<Integer> MST = new ArrayList<Integer>(); // Array of ids in MST
		//Initialize the heap binary   
		for (int i = 0; i < nodeSize; i++){
			heap.add(new Priority(i));
		}
		
		// Remove and add the vertice 0 to the MST
		heap.remove(0);
		MST.add(0);
		int rootId = 0;
		int num = nodeSize - 1;

		while (num > 0){
			// update priorities of the neighbors of the start vertice		
			Node root = nodes.get(rootId);
			// for each edge from the root vertice
			for (int i = 0; i < root.getEdges().size(); i++){
				Edge edge = root.getEdges().get(i);
				int toNodeNum = edge.getToNode().getNum();
				//if the vertice connecting to the root is not in MST yet
				if (! MST.contains(toNodeNum)){
					// search for the matched vertice in the binary heap 
					for (int j = 0; j < heap.size(); j++){
						if (toNodeNum == heap.get(j).getId()){
							// if the weight edge is less than the priority of the vertice 
							if (edge.getWeight() < heap.get(j).getKey()){
								// Update the priority of the vertice and stop searching
								heap.get(j).changeParent(root.getNum());
								heap.get(j).changeKey(edge.getWeight());
								heap.get(j).updateEdge(edge);
								break;
							}
						}
					}
				}
			}
			
			// build heap in bottom-up approach
			heap = heapify(heap);
			
			// delete root of the new vertice and add it to MST
			MST_edges.add(heap.get(0).getEdge());
			MST.add(heap.get(0).getId());
			
			// Move the last element in Priority to the first place
			// and update rootId to the removed root vertice (last element)
			rootId = heap.get(0).getId();
			heap.set(0, heap.get(heap.size()-1));
			heap.remove(heap.size()-1);
			num--;	
		}
		
		quicksort(MST_edges, 0, MST_edges.size()-1);
		
		long MST_end_time = System.currentTimeMillis(); // End the timer for Prim
		long MST_time = MST_end_time - MST_start_time; // Running time for Prim
		long runtime = MST_time + recordTime; // Total time for Prim
		int total_weight = 0;
		System.out.println("\n===================================");
		System.out.println("PRIM WITH ADJACENCY " + type);
		for (int i = 0; i < MST_edges.size(); i++){
			int fromNum = MST_edges.get(i).getFromNode().getNum();
			int toNum = MST_edges.get(i).getToNode().getNum();
			int weight = MST_edges.get(i).getWeight();
			total_weight += weight;
			if (display){
				System.out.format("%d %d weight = %d \n", fromNum, toNum, weight);
			}
		}
		System.out.format("\n");
		System.out.format("Total weight of MST using Prim: %d\n", total_weight);
		System.out.format("Runtime: %d milliseconds\n", runtime);
	}
	
	/**
	 * start is a method that begins Prim's algorithm
	 * 
	 * @param record - a class Record consisting of array of unsorted edges and runtimes 
	 * for both adjacency matrix and adjacency list
	 * @param display - display the sorted order of edges if less than 10 vertices exist
	 */
	public static void start(Record record, boolean display){
		long matrix_time = record.get_matrix_time();
		long list_time = record.get_list_time();
		int nodeSize = record.get_nodes().size();
		
		// ADJACENCY MATRIX
		prim(new ArrayList<Node>(record.get_nodes()), matrix_time, "MATRIX", display, nodeSize);
		// ADJACENCY LIST
		prim(new ArrayList<Node>(record.get_nodes()), list_time, "LIST", display, nodeSize);		
	}
}
