import java.util.HashMap;

public class Main {
	
	public static double tf(String word, String doc) {
		// create new hashmap to store occurrences of each word
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		// store max occurrences
		int max = 1;
		// split the document into words
		String[] words = doc.split(" ");
		// for all words
		for (String w : words) {
			// if map does not contain word, put it
			if (!map.containsKey(w)) {
				map.put(w, 1);
			} else {
				// get current occurrence count
				int count = map.get(w);
				// increase count
				count++;
				// store the count
				map.put(w, count);
				// update max if count is larger
				if (count > max) {
					max = count;
				}
			}
		}
		int count = 0;
		// if map contains word, return it's occurrence count
		if (map.containsKey(word)) {
			count = map.get(word);
		}
		// return the term frequency
		return (double)count / (double)max;
	}
	
	public static double log2(double x) {
		// change base formula
		return (Math.log(x) / Math.log(2));
	}
	
	public static double idf(String word, String docs[]) {
		// count the number of documents where word occurs
		int count = 0;
		// for all documents
		for (String doc : docs) {
			// split the document into words
			String[] words = doc.split(" ");
			// for all words in document
			for (String w : words) {
				// if word is found
				if (w.equals(word)) {
					// increase count
					count++;
					// don't keep counting for the same document
					break;
				}
			}
		}
		// return inverse document frequency
		return log2((double)docs.length / (double)count);
	}
	
}
