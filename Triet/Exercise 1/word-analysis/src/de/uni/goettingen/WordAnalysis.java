package de.uni.goettingen;

import java.util.HashMap;
import java.util.List;

public class WordAnalysis {

    /**
     * Calculate a Term Frequency in a document
     * @param term The term we want to calculate the frequency
     * @param doc The document which contains the word
     * @return The term frequency
     */
    public double TermFrequency(String term, List<String> doc) {

        // If the document is empty, or the word is not in the document
        if (doc.size() == 0 || !doc.contains(term)) {

            // Simply return 0
            return 0;
        }

        // The document has at least 1 word
        double max = 1;

        // Key: word, value: frequency
        HashMap<String, Integer> map = new HashMap<>();

        for (String word : doc) {

            // Put new word to the map
            if (!map.containsKey(word)) {
                map.put(word, 1);
            } else {

                // If the word is already in the map, increase the count
                int freq = map.get(word);
                freq++;

                // Check if its frequency is max
                if (freq > max) {
                    max = freq;
                }
                map.put(word, freq);
            }
        }

        // Get the input frequency and calculate the TF
        int termFreq = map.get(term);
        return termFreq / max;
    }

    /**
     * Calculate the Invert Document Frequency
     * @param docs All documents we have
     * @param term The term we want to calculate
     * @return The IDF value
     */
    public double InvertDocumentFrequency(List<List<String>> docs, String term) {

        // Number of documents containing the term
        double count = 0;

        // For each document
        for (List<String> doc : docs) {

            // For each word in that document
            for (String word : doc) {

                // If the document contains the term
                if (term.equalsIgnoreCase(word)) {
                    count++;
                    break;
                }
            }
        }

        return Math.log(docs.size() / count) / Math.log(2);
    }
}
