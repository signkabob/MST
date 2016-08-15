import java.util.ArrayList;

/**
 * Class Sort is sub program that sort the result (array of edges) from both 
 * adjacency matrix and adjacency list using insertion sort, count sort, and quicksort
 * based on the weight of each edge.
 * 
 * @author Kabo Cheung
 * @version 01-Apr-2016
 */
public class Sort {
	/**
	 * maximum_weight is a method used to find a maximum weight
	 * from the array of edges
	 * 
	 * @param edges - array of unsorted unique edges
	 * @return the maximum weight found in the array of edges
	 */
	public static int maximum_weight(ArrayList<Edge> edges){
		int max_num = 0; 
		for (int i = 0; i< edges.size(); i++){
			if (max_num < edges.get(i).getWeight()){
				max_num = edges.get(i).getWeight();
			}
		}
		return max_num;
	}
	
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
	 * insertion_sort is an insertion sort algorithm
	 * 
	 * @param edges - array of unsorted unique edges
	 * @param recordTime - runtime from either adjacency matrix or adjacency list
	 * @param type - used with either adjacency matrix or adjacency list 
	 * @param display - display the sorted order of edges if less than 10 vertices exist
	 */
	public static void insertion_sort(ArrayList<Edge> edges, long recordTime, String type, boolean display, int nodeSize){
		System.out.format("\n");
		long sort_start_time = System.currentTimeMillis(); // Start the timer for the insertion sort
		
		// Insertion sort algorithm
		for (int i = 0; i < edges.size(); i++){
			for (int j = i; 0 < j; j--){
				if (edges.get(j).lessThan(edges.get(j-1))){
					swap(edges, j, j-1);
				}else{
					break;
				}
			}
		}
		
		long sort_end_time = System.currentTimeMillis(); // End the timer for the insertion sort
//		long sortTime = sort_end_time - sort_start_time; // Find the running time for the insertion sort
//		long runtime = sortTime + recordTime; // Total time for the insertion
//		int total_weight = 0; // Initialize weight to find total weight
//		
//		System.out.println("===================================");
//		System.out.println("SORTED EDGES WITH " + type + " USING INSERTION SORT");
//		for (int i = 0; i < edges.size(); i++){
//			int fromNum = edges.get(i).getFromNode().getNum();
//			int toNum = edges.get(i).getToNode().getNum();
//			int weight = edges.get(i).getWeight();
//			total_weight += weight;
//			if (display){	
//				System.out.format("%d %d weight = %d \n", fromNum, toNum, weight);
//			}
//		}
//	
//		// Results of insertion sort
//		System.out.format("\n");
//		System.out.format("Total weight = %d\n", total_weight);
//		System.out.format("Runtime: %d milliseconds\n", runtime);
		
		Kruskal.start(new ArrayList<Edge>(edges), recordTime, type, "INSERTION SORT", display, nodeSize);
	}
	
	/**
	 * count_sort is a count sort algorithm
	 * 
	 * @param edges - array of unsorted unique edges
	 * @param recordTime - runtime from either adjacency matrix or adjacency list
	 * @param type - used with either adjacency matrix or adjacency list
	 * @param display - display the sorted order of edges if less than 10 vertices exist
	 */
	public static void count_sort(ArrayList<Edge> edges, long recordTime, String type, boolean display, int nodeSize){
		System.out.format("\n");
		long sort_start_time = System.currentTimeMillis(); // Start the timer for the count sort
		
		// Count sort algorithm
		int max_range = maximum_weight(edges);
		ArrayList<Integer> count_array = new ArrayList<Integer>(max_range+1);
		for (int i = 0; i <= max_range+1; i++){
			count_array.add(0);
		}
		
		// Fill up count array
		ArrayList<Edge> aux_array = new ArrayList<Edge>(edges.size());
		for (int i = 0; i < edges.size(); i++){
			aux_array.add(null);
		}
		
		// Find cumulative sum
		for (int i = 0; i < edges.size(); i++){
			int num = edges.get(i).getWeight();
			int count = count_array.get(num + 1);
			count_array.set(num+1, count+1);
		}
		
		// Find correct location in aux
		for (int r = 0; r <= max_range; r++){
			int num1 = count_array.get(r);
			int num2 = count_array.get(r+1);
			count_array.set(r+1, num1+num2);
		}
		
		// Copy back to the new array of ordered edges
		for (int i = 0; i < edges.size(); i++){
			int weight = edges.get(i).getWeight();
			aux_array.set(count_array.get(weight), edges.get(i));
			count_array.set(weight, count_array.get(weight)+1);
		}
		
		long sort_end_time = System.currentTimeMillis(); // End the timer for the count sort
//		long sortTime = sort_end_time - sort_start_time; // Running time for the count sort
//		long runtime = sortTime + recordTime; // Total time for the count sort
//		int total_weight = 0; // Initialize weight to find total weight
		
//		System.out.println("===================================");
//		System.out.println("SORTED EDGES WITH " + type + " USING COUNT SORT");	
//		for (int i = 0; i < edges.size(); i++){
//			int fromNum = aux_array.get(i).getFromNode().getNum();
//			int toNum = aux_array.get(i).getToNode().getNum();
//			int weight = aux_array.get(i).getWeight();
//			total_weight += weight;
//			if (display){
//				System.out.format("%d %d weight = %d \n", fromNum, toNum, weight);
//			}
//		}
//		// Results
//		System.out.format("\n");
//		System.out.format("Total weight = %d\n", total_weight);
//		System.out.format("Runtime: %d milliseconds\n", runtime);
		
		Kruskal.start(new ArrayList<Edge>(aux_array), recordTime, type, "COUNTSORT", display, nodeSize);
	}
	
	/**
	 * partition is part of the quicksort algorithm that chooses a pivot which
	 * is the last index of the array (edges) and begins comparing from lo to hi
	 * 
	 * Modified Lomuto partition scheme
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
			if (edges.get(j).lessThan(pivot)){
				swap(edges, i, j);
				i++;
			// If two or more edges have the same weight, then the sort will be in order of increasing left vertex
			}else if (edges.get(j).getWeight() == pivot.getWeight()){
				if (edges.get(j).getFromNode().getNum() < pivot.getFromNode().getNum()){
					swap(edges, i, j);
					i++;
				}else{
					//If two or more edges have the same weight and the same left vertex, then the sort will be in order of increasing right vertex
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
	 * quicksortStart is a method that begins the quicksort algorithm
	 * 
	 * @param edges - array of unsorted unique edges
	 * @param recordTime - runtime from either adjacency matrix or adjacency list
	 * @param type - used with either adjacency matrix or adjacency list
	 * @param display - display the sorted order of edges if less than 10 vertices exist
	 */
	public static void quicksortStart(ArrayList<Edge> edges, long recordTime, String type, boolean display, int nodeSize){
		System.out.format("\n");
		long sort_start_time = System.currentTimeMillis(); // Start the timer for quicksort
		
		// Begin quicksort algorithm 
		quicksort(edges, 0, edges.size()-1);
		
		long sort_end_time = System.currentTimeMillis(); // End the timer for quicksort
//		long sortTime = sort_end_time - sort_start_time; // Running time for quicksort
//		long runtime = sortTime + recordTime; // Total time for quicksort
//		int total_weight = 0; // Initialize weight to find total weight
		
		
//		System.out.println("===================================");
//		System.out.println("SORTED EDGES WITH " + type + " USING QUICKSORT");	
//		for (int i = 0; i < edges.size(); i++){
//			int fromNum = edges.get(i).getFromNode().getNum();
//			int toNum = edges.get(i).getToNode().getNum();
//			int weight = edges.get(i).getWeight();
//			total_weight += weight;
//			if (display){
//				System.out.format("%d %d weight = %d \n", fromNum, toNum, weight);
//			}
//		}
//		// Results
//		System.out.format("\n");
//		System.out.format("Total weight = %d\n", total_weight);
//		System.out.format("Runtime: %d milliseconds\n", runtime);
		
		Kruskal.start(new ArrayList<Edge>(edges), recordTime, type, "QUICKSORT", display, nodeSize);
	}
	
	/**
	 * sorting is a method that begins sorting algorithms
	 * 
	 * @param record - a class Record consisting of array of unsorted edges and runtimes 
	 * for both adjacency matrix and adjacency list
	 * @param display - display the sorted order of edges if less than 10 vertices exist
	 */
	public static void sorting(Record record, boolean display){
		long matrix_time = record.get_matrix_time();
		long list_time = record.get_list_time();
		int nodeSize = record.get_nodes().size();
		
		// ADJACENCY MAXTRIX
		insertion_sort(new ArrayList<Edge>(record.get_matrix_array()), matrix_time, "MATRIX", display, nodeSize);
		count_sort(new ArrayList<Edge>(record.get_matrix_array()), matrix_time, "MATRIX", display, nodeSize);
		quicksortStart(new ArrayList<Edge>(record.get_matrix_array()), matrix_time, "MATRIX", display, nodeSize);
		
		// ADJACENCY LIST
		insertion_sort(new ArrayList<Edge>(record.get_list_array()), list_time, "LIST", display, nodeSize);
		count_sort(new ArrayList<Edge>(record.get_list_array()), list_time, "LIST", display, nodeSize);
		quicksortStart(new ArrayList<Edge>(record.get_list_array()), list_time, "LIST", display, nodeSize);
	}
}
