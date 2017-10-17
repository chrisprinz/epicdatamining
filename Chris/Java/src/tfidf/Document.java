package tfidf;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Document {
    private Map<String, Double> wordCounts = new HashMap<>();
    private double totalNumberOfWords = 0;
    private String mostFrequentWord;
    private double occurrencesOfMostFrequentWord = 0;

    public Document(String filename) {
        super();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), Charset.forName("UTF-8")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = trim(line);
                Collection<String> words = Arrays.asList(trimmedLine.split(" "));
                words.forEach(word -> {
                    if (wordCounts.containsKey(word)) {
                        double oldCount = wordCounts.get(word);
                        if (oldCount + 1 > occurrencesOfMostFrequentWord) {
                            occurrencesOfMostFrequentWord = oldCount + 1;
                            mostFrequentWord = word;
                        }
                        wordCounts.put(word, oldCount + 1);
                    } else {
                        wordCounts.put(word, 1.0);
                    }

                    totalNumberOfWords++;
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
        }
    }

    public boolean contains(String term){
        return wordCounts.get(term) != null;
    }

    public double tf(String term) {
        return (wordCounts.get(term) / totalNumberOfWords) / (occurrencesOfMostFrequentWord / totalNumberOfWords);
    }

    private String trim(String string) {
        return string.replaceAll("[^A-z]", "");
    }
}
