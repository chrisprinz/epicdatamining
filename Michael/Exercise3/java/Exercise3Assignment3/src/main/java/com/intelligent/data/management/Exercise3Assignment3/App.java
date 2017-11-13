package com.intelligent.data.management.Exercise3Assignment3;

import java.io.File;
import java.io.IOException;

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
    public static void main( String[] args ) throws IOException, TasteException
    {
        // load data
    	DataModel data = new FileDataModel(new File("data/data.csv"));
    	
    	// Print out for each user the list of items that the user rated.
    	LongPrimitiveIterator userIDsIterator = data.getUserIDs();
    	while (userIDsIterator.hasNext()) {
    		long userID = userIDsIterator.nextLong();
    		FastIDSet itemIDs = data.getItemIDsFromUser(userID);
    		System.out.println(String.format("User %d: %s", userID, itemIDs));
    	}
    	
    	// Print out for each item the list of ratings that it received.
    	LongPrimitiveIterator itemIDsIterator = data.getItemIDs();
    	while (itemIDsIterator.hasNext()) {
    		long itemID = itemIDsIterator.nextLong();
    		System.out.print(String.format("Item %d: ", itemID));
    		PreferenceArray aPrefs = data.getPreferencesForItem(itemID);
    		for (Preference pref : aPrefs) {
	    		System.out.print(String.format("%.1f ", pref.getValue()));
    		}
    		System.out.println();
    	}
    	
    	// Compute the Jaccard similarity for each pair of users
    	UserSimilarity userSimilarity = new TanimotoCoefficientSimilarity(data);
    	LongPrimitiveIterator user1Iterator = data.getUserIDs();
    	while (user1Iterator.hasNext()) {
			long user1 = user1Iterator.nextLong();
    		LongPrimitiveIterator user2Iterator = data.getUserIDs();
    		while (user2Iterator.hasNext()) {
    			long user2 = user2Iterator.nextLong();
    			if (user2 != user1) {
    				double similarity = userSimilarity.userSimilarity(user1, user2);
    				System.out.println(String.format("Similarity Users (%d,%d) = %.2f", user1, user2, similarity));
    			}
    		}
    	}
    	
    	// Compute the Jaccard similarity for each pair of items
    	ItemSimilarity itemSimilarity = new TanimotoCoefficientSimilarity(data);
    	LongPrimitiveIterator item1Iterator = data.getItemIDs();
    	while (item1Iterator.hasNext()) {
			long item1 = item1Iterator.nextLong();
    		LongPrimitiveIterator item2Iterator = data.getItemIDs();
    		while (item2Iterator.hasNext()) {
    			long item2 = item2Iterator.nextLong();
    			if (item2 != item1) {
    				double similarity = itemSimilarity.itemSimilarity(item1, item2);
    				System.out.println(String.format("Similarity Items (%d,%d) = %.2f", item1, item2, similarity));
    			}
    		}
    	}
    	
    }
}
