package com.intelligent.data.management.Exercise4Assignment3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

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
    	System.out.println("Characteristic matrix:");
		System.out.println();
    	System.out.print("       ");
    	LongPrimitiveIterator userIDsIterator = data.getUserIDs();
    	while (userIDsIterator.hasNext()) {
    		long userID = userIDsIterator.nextLong();
    		System.out.print(String.format(" | User %d", userID));
    	}
    	System.out.println();
    	System.out.println("--------------------------------------------");
    	
    	LongPrimitiveIterator itemIDsIterator = data.getItemIDs();
    	while (itemIDsIterator.hasNext()) {
    		long itemID = itemIDsIterator.nextLong();
    		System.out.print(String.format("Item %d ", itemID));
    		userIDsIterator = data.getUserIDs();
    		while (userIDsIterator.hasNext()) {
        		long userID = userIDsIterator.nextLong();
        		FastIDSet itemIDs = data.getItemIDsFromUser(userID);
        		if (itemIDs.contains(itemID)) {
        			System.out.print("|   1    ");
        		} else {
        			System.out.print("|   0    ");
        		}
    		}
    		System.out.println();
    	}
		System.out.println();
    	
    	// Choose two hash functions (similar to Figure 3.4) and compute the minhash signatures for the users (similar
    	// to Section 3.3.5).
		System.out.println("Minhash signatures:");
		System.out.println();
    	System.out.println("        |  h_1  |  h_2");
    	System.out.println("------------------------");
    	itemIDsIterator = data.getItemIDs();
    	while (itemIDsIterator.hasNext()) {
    		long itemID = itemIDsIterator.nextLong();
    		System.out.print(String.format("Item %d ", itemID));
    		System.out.print(String.format("|   %d   ", hash1(itemID)));
    		System.out.print(String.format("|   %d   ", hash2(itemID)));
    		System.out.println();
    	}
    	
    	// Print out the resulting pairwise similarities based on the minhashes.
    	
    }
}
