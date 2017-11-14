package cpr.idm.mahout_jaccard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class TFIDFCalculator_Mahout {

    public static void main(String args[]) throws Exception {
    	
    	DataModel userAnalysis = new FileDataModel(new File("/home/cp/git/epicdatamining/Christoph/src/Exercise3/input_data/data.txt"));
    	
    	// list of items user rated
    	List<Long> user_ids = new ArrayList<Long>();
    	LongPrimitiveIterator userIDsIterator = userAnalysis.getUserIDs();
    	while (userIDsIterator.hasNext()) {
    		long userID = userIDsIterator.nextLong();
    		user_ids.add(userID);
    		FastIDSet itemIDs = userAnalysis.getItemIDsFromUser(userID);
    		System.out.println(String.format("User %d rated %s", userID, itemIDs));
    	}
    
    	
    	// ratings per item
		LongPrimitiveIterator itemIDsIterator = userAnalysis.getItemIDs();
		while (itemIDsIterator.hasNext()) {
			long itemID = itemIDsIterator.nextLong();
			//ids.add(itemID);
			System.out.print(String.format("Item %d got ratings: ", itemID));
			PreferenceArray aPrefs = userAnalysis.getPreferencesForItem(itemID);
			for (Preference pref : aPrefs) {
				System.out.print(String.format("%.1f ", pref.getValue()));
			}
			System.out.println();
		};
				
		//Jaccard similarity for each pair of users
		TanimotoCoefficientSimilarity jaccardCalculator = new TanimotoCoefficientSimilarity(userAnalysis);
		LongPrimitiveIterator user1Iterator = userAnalysis.getUserIDs();
		
		while (user1Iterator.hasNext()) {
			long user1 = user1Iterator.nextLong();
			LongPrimitiveIterator user2Iterator = userAnalysis.getUserIDs();
			while (user2Iterator.hasNext()) {
				long user2 = user2Iterator.nextLong();
					double similarity = jaccardCalculator.userSimilarity(user1, user2);
					System.out.println(String.format("Similarity of User %d and %d is %.2f%%", user1, user2, similarity*100));
			}
		}
		
		//jaccard similarity for products
    	ItemSimilarity itemSimilarity = new TanimotoCoefficientSimilarity(userAnalysis);
    	LongPrimitiveIterator item1Iterator = userAnalysis.getItemIDs();
    	while (item1Iterator.hasNext()) {
			long item1 = item1Iterator.nextLong();
    		LongPrimitiveIterator item2Iterator = userAnalysis.getItemIDs();
    		while (item2Iterator.hasNext()) {
    			long item2 = item2Iterator.nextLong();
				double similarity = itemSimilarity.itemSimilarity(item1, item2);
				System.out.println(String.format("Similarity of Item %d and %d is %.2f%%", item1, item2, similarity*100));
    		}
    	}	
    }
}