# Introducing TF.IDF and hash functions

## a)

`idf(40) = log_2(10,000,000/40) ~ 18`

`idf(10,000) = log_2(10,000,000/10,000) ~ 10`

## b)
`tfidf(1) = (1/15) * log_2(10,000,000/320) ~ 1`

`tfidf(5) = (5/15) * log_2(10,000,000/320) ~ 5`

## c)

For the hash function `h(x) = x mod 15`, a population of all 
non-negative multiples of a constant `c` is suitable, if `c=1`,
as all 15 buckets are equally likely to be "hit", if the 
population consists of the natural numbers.

# Implementation

As the exercise is rather vaguely phrased, two solutions to the 
problem are presented, a short, and a more complete version:

## Short Solution

```Java

    /**
     * @param N   number of documents in repository
     * @param n_i number or documents with term i
     */
    static double idf(double N, double n_i) {
        return Math.log(N / n_i)/Math.log(2);
    }

    /**
     * @param i number of occurrences of a word in a document
     * @param k number of occurrences of that most frequent word in the same document
     */
    static double tf(double i, double k) {
        return i / k;
    }

```

## Complete Solution
It consists of two classes in an object-oriented approach: `Document` 
and `Repository`, the implementation of which follow:

### Document

```Java
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
        return wordCounts.get(term) / occurrencesOfMostFrequentWord;
    }

    private String trim(String string) {
        return string.replaceAll("[^A-z]", "");
    }
}
```

### Repository

```Java
package tfidf;

import java.util.Collection;
import java.util.HashSet;

public class Repository {

    Collection<Document> documents = new HashSet<>();

    Repository(Collection<String> filenames) {
        super();
        filenames.forEach(filename -> documents.add(new Document(filename)));
    }

    public double idf(String term) {
        double numberOfDocumentsContainingTerm =
                documents.parallelStream().filter(document -> document.contains(term)).count();
        double totalNumberOfDocuments = documents.size();
        return Math.log(totalNumberOfDocuments / numberOfDocumentsContainingTerm) / Math.log(2);
    }

    public double tfIdf(String term, Document document) {
        double tf = document.tf(term);
        double idf = idf(term);
        return tf * idf;
    }
}
```