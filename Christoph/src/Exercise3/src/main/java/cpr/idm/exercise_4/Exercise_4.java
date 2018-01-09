package cpr.idm.exercise_4;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Exercise_4 {

	public static int hash1(int itemID) {
		return (itemID + 1) % 9;
	}
	public static int hash2(int itemID) {
		return (3*itemID + 1) % 9;
	}
	
    public static void main( String[] args ) throws IOException
    {
        // load data
    	String path = "/home/cp/eclipse-workspace/Exercise_4/src/main/java/cpr/idm/exercise4/data.txt";
    	List<String> list;
    	try (Stream<String> stream = Files.lines(Paths.get(path))) {
            list = stream.collect(Collectors.toList());}
    
    	Map<Integer, List<Integer>> users_bought_items = new HashMap<Integer, List<Integer>>();
    	List<Integer> items = new ArrayList<Integer>();
    	
    	
    	for(String line: list) {
    		String[] parts = line.split(",");
    		Integer user = Integer.parseInt(parts[0]);
    		Integer article = Integer.parseInt(parts[1]);
    		items.add(article);
    		if (users_bought_items.containsKey(user))
    			users_bought_items.get(user).add(article);
    		else
    		{
    			List<Integer> bought_articles = new ArrayList<Integer>();
    			bought_articles.add(article);
    			users_bought_items.put(user, bought_articles);
    		}		
    	}
    	//remove duplicates from article list
    	items = new ArrayList<Integer>(new LinkedHashSet<Integer>(items));
    	
    	// Print characteristic matrix with hash signatures
    	System.out.println("Characteristic matrix with hash signatures:");
		System.out.println();
		// Print header line
    	System.out.print("       ");
    	   	
    	// initialize user minhash signatures with max values and do acutal printing
		HashMap<Integer,Long> minh1s = new HashMap<Integer,Long>();
		HashMap<Integer,Long> minh2s = new HashMap<Integer,Long>();
    	for(int user_id: users_bought_items.keySet()) {
    		minh1s.put(user_id, Long.MAX_VALUE);
    		minh2s.put(user_id, Long.MAX_VALUE);
    		System.out.print(String.format(" | User %d", user_id));
    	}
       
    	System.out.println(" ||  h_1  |  h_2");
    	System.out.println("-------------------------------------------------------------");
    	
    	// Loop through all items
    	
    	for(int item_id: items)
    	{
    		
    		System.out.print(String.format("Item %d ", item_id));
    		// Compute the hashes for the item
    		long h1 = hash1(item_id);
    		long h2 = hash2(item_id);
    		// Loop through all users
    		for(int user_id: users_bought_items.keySet()) {
        		// Get the items for the user
        		List<Integer> itemIDs = users_bought_items.get(user_id);
        		if (itemIDs.contains(item_id)) {
        			// If the user reviewed this item, print 1
        			System.out.print("|   1    ");
        			// Store the hash value if it is less than what is already stored
        			if (h1 < minh1s.get(user_id)) {
        				minh1s.put(user_id, h1);
        			}
        			if (h2 < minh2s.get(user_id)) {
        				minh2s.put(user_id, h2);
        			}
        		} else {
        			// If the user did not review this item, print 0
        			System.out.print("|   0    ");
        		}
    		}
    		//h1s.put(itemID, h1);
    		// Print the hash values
    		System.out.print(String.format("||   %d   ", h1));
    		//h2s.put(itemID, h2);
    		System.out.print(String.format("|   %d   ", h2));
    		System.out.println();
    	}
		System.out.println();
		
		// Print minhash signature matrix for users
		System.out.println("Minhash signature matrix for users:");
		System.out.println();
		System.out.print("   ");
		// Print header line
		for(int user_id: users_bought_items.keySet()) {
    		System.out.print(String.format(" | User %d", user_id));
    	}
    	System.out.println();
    	// Print data for hash function 1
    	System.out.print("h_1");
    	for(int user_id: users_bought_items.keySet()) {
    		System.out.print(String.format(" |   %d   ", minh1s.get(user_id)));
    	}
 
    	System.out.println();
    	// Print data for hash function 2
    	System.out.print("h_2");

    	for(int user_id: users_bought_items.keySet()) {
    		System.out.print(String.format(" |   %d   ", minh2s.get(user_id)));
    	}
    	System.out.println();
    	System.out.println();

    	// Print out the resulting pairwise similarities based on the minhashes.
    	System.out.println("Estimated pairwise similarities:");
    	System.out.println();
    	// Loop over all users
    	for(int u1: users_bought_items.keySet()) {
    		// Loop again on all users for second user in pair
    		for(int u2: users_bought_items.keySet()) {
        		// Take unique pairs of users, assuming they come in order
        		if (u1 < u2) {
        			// Count similarities
        			int sim = 0;
        			if (minh1s.get(u1) == minh1s.get(u2)) {
        				sim++;
        			}
        			if (minh2s.get(u1) == minh2s.get(u2)) {
        				sim++;
        			}
        			// Divide similarity count by 2 for two hash functions
        			System.out.println(String.format("User %d, User %d = %.2f", u1, u2, sim/2.));
        		}
    		}
    	}
    }
}