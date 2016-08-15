import java.io.*;
import java.util.ArrayList;

/**
 * Class MST is a main program that reads a text file consisting of n vertices,
 * seed, and p chance to generate a randomly connected, undirected, weighted graph
 * and display results in runtime, adjacency matrix, an adjacency list, and 
 * a list of predecessors.
 *
 * @author Kabo Cheung
 * @version 08-May-2016
 */
public class MST{
	
	public static boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	} 
	/**
	 * 
	 * Main program
	 * 
	 * @param args - Input file to be used for generating a random graph 
	 * 
	 * Usage: java MST <File>
	 */
	public static void main(String[] args) throws Exception {
		//Error condition: No input
		if (args.length == 0){
			System.out.println("Error! No input!");
			System.out.println("Usage: java MST file");
			System.exit(1);
		}
		String filename = args[0];
		int n; // Number of vertices
		int seed; // Seed for random generator 
		double p; // Possibility of connecting the pair of vertices
		
		try{
			// Read the file
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			// Assign n and seed; Error condition: these are not integers
			n = Integer.parseInt(br.readLine());
			seed = Integer.parseInt(br.readLine());
			
			// Assign p; Error condition: p is not a real number
			String p_string = br.readLine();
			if (! isNumeric(p_string)){
				System.out.println("p is not a real number");
				System.exit(1);
			}
			p = Double.parseDouble(p_string);
			
			// Close the file
			br.close();
			
			// Error condition: n is not greater than 1
			if (!(n > 1)){
				System.out.println("n must be greater than 1");
				System.exit(1);
			}
			
			// Error condition: p is less than 0 and more than 1 
			if (p < 0 || p > 1){
				System.out.println("p must be between 0 and 1");
				System.exit(1);
			}
			// Start the timer
			long startTime = System.currentTimeMillis();
			// Start generating a random graph
			ArrayList<Node> graph = Graph.creation(n, seed, p);
			
			// End the timer
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			
			// Display the results and the runtime
			System.out.format("TEST: n=%d, seed=%d, p=%s\n", n, seed, Double.toString(p));
			System.out.format("Time to generate the graph: %d milliseconds\n", totalTime);
			
			// Display the results if there is less vertices than 10
			// Begin sorting and Prim's algorithm 
			if (n < 10){
				Record record = Graph.result(graph, true);
				Sort.sorting(record, true);
				Prim.start(record, true);
			}else{
				Record record = Graph.result(graph, false);
				Sort.sorting(record, false);
				Prim.start(record,  false);
			}
			
		// Display the error messages if an error condition is met
		}catch(FileNotFoundException e) {
            System.out.println("Input file not found");
            System.exit(1);
        }catch(IOException e){
			System.out.println("Input file not found");
			System.exit(1);
		}catch(NumberFormatException e){
			System.out.println("Both n and seed must be an integer");
			System.exit(1);
		}
		
		// Successful run
		System.exit(0);
	}
}
