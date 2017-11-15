package com.intelligent.data.management.Exercise4Assignment3;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

public class App 
{
	public static long hash1(long itemID) {
		return (itemID + 1) % 9;
	}
	public static long hash2(long itemID) {
		return (3*itemID + 1) % 9;
	}
	
    public static void main( String[] args ) throws IOException, TasteException
    {
        // load data
    	DataModel data = new FileDataModel(new File("data/data.csv"));
    	
    	// Represent each user as a set of item IDs (note: ignore ratings).
    	// >> We will use the FastIDSet in the DataModel
    	
    	// Print the characteristic matrix (see Section 3.3.1) containing all users as columns and all item IDs as rows
    	// (note: sort the matrix by Item ID).
    	
    	// Choose two hash functions (similar to Figure 3.4) and compute the minhash signatures for the users
		// (similar to Section 3.3.5).

    	//HashMap<Long,Long> h1s = new HashMap<Long,Long>();
    	//HashMap<Long,Long> h2s = new HashMap<Long,Long>();
    	
		// initialize user minhash signatures with max values
		HashMap<Long,Long> minh1s = new HashMap<Long,Long>();
		HashMap<Long,Long> minh2s = new HashMap<Long,Long>();
		LongPrimitiveIterator userIDsIterator = data.getUserIDs();
    	while (userIDsIterator.hasNext()) {
    		long userID = userIDsIterator.nextLong();
    		minh1s.put(userID, Long.MAX_VALUE);
    		minh2s.put(userID, Long.MAX_VALUE);
    	}
    	
    	// Print characteristic matrix with hash signatures
    	System.out.println("Characteristic matrix with hash signatures:");
		System.out.println();
		// Print header line
    	System.out.print("       ");
    	userIDsIterator = data.getUserIDs();
    	while (userIDsIterator.hasNext()) {
    		long userID = userIDsIterator.nextLong();
    		System.out.print(String.format(" | User %d", userID));
    	}
    	System.out.println(" ||  h_1  |  h_2");
    	System.out.println("-------------------------------------------------------------");
    	
    	// Loop through all items
    	LongPrimitiveIterator itemIDsIterator = data.getItemIDs();
    	while (itemIDsIterator.hasNext()) {
    		long itemID = itemIDsIterator.nextLong();
    		System.out.print(String.format("Item %d ", itemID));
    		// Compute the hashes for the item
    		long h1 = hash1(itemID);
    		long h2 = hash2(itemID);
    		// Loop through all users
    		userIDsIterator = data.getUserIDs();
    		while (userIDsIterator.hasNext()) {
        		long userID = userIDsIterator.nextLong();
        		// Get the items for the user
        		FastIDSet itemIDs = data.getItemIDsFromUser(userID);
        		if (itemIDs.contains(itemID)) {
        			// If the user reviewed this item, print 1
        			System.out.print("|   1    ");
        			// Store the hash value if it is less than what is already stored
        			if (h1 < minh1s.get(userID)) {
        				minh1s.put(userID, h1);
        			}
        			if (h2 < minh2s.get(userID)) {
        				minh2s.put(userID, h2);
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
    	userIDsIterator = data.getUserIDs();
    	while (userIDsIterator.hasNext()) {
    		long userID = userIDsIterator.nextLong();
    		System.out.print(String.format(" | User %d", userID));
    	}
    	System.out.println();
    	// Print data for hash function 1
    	System.out.print("h_1");
    	userIDsIterator = data.getUserIDs();
    	while (userIDsIterator.hasNext()) {
    		long userID = userIDsIterator.nextLong();
    		System.out.print(String.format(" |   %d   ", minh1s.get(userID)));
    	}
    	System.out.println();
    	// Print data for hash function 2
    	System.out.print("h_2");
    	userIDsIterator = data.getUserIDs();
    	while (userIDsIterator.hasNext()) {
    		long userID = userIDsIterator.nextLong();
    		System.out.print(String.format(" |   %d   ", minh2s.get(userID)));
    	}
    	System.out.println();
    	System.out.println();

    	// Print out the resulting pairwise similarities based on the minhashes.
    	System.out.println("Estimated pairwise similarities:");
    	System.out.println();
    	// Loop over all users
    	LongPrimitiveIterator i1 = data.getUserIDs();
    	while (i1.hasNext()) {
    		long u1 = i1.nextLong();
    		// Loop again on all users for second user in pair
    		LongPrimitiveIterator i2 = data.getUserIDs();
    		while (i2.hasNext()) {
        		long u2 = i2.nextLong();
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
