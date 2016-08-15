import java.util.ArrayList;

/**
 * Class Record is a class object acting as the holder for multiple variables resulted
 * from the creation of adjacency matrix and adjacency list 
 * 
 * Created due to the Graph class not being object class.
 * 
 * @author Kabo Cheung
 * @version 13-Mar-2016
 */
public class Record{
		// private variables
		private ArrayList<Edge> matrix_array;
		private long matrix_time;
		private ArrayList<Edge> list_array;
		private long list_time;
		private ArrayList<Node> nodes;
		
		// Public variables
		public Record(ArrayList<Edge> matrix_array, long matrix_time, ArrayList<Edge> list_array, long list_time, ArrayList<Node> nodes){
			this.matrix_array = matrix_array;
			this.matrix_time = matrix_time;
			this.list_array = list_array;
			this.list_time = list_time;
			this.nodes = nodes;
		}
		
		// Access to the array of edges found in adjacency matrix 
		public ArrayList<Edge> get_matrix_array(){
			return this.matrix_array;
		}
		
		// Access to running time in adjacency matrix
		public long get_matrix_time(){
			return this.matrix_time;
		}
		
		// Access to the array of edges found in adjacency list
		public ArrayList<Edge> get_list_array(){
			return this.list_array;
		}
		
		// Access to running time in adjacency list
		public long get_list_time(){
			return this.list_time;
		}
		
		// Access to the array of nodes
		public ArrayList<Node> get_nodes(){
			return this.nodes;
		}
	}