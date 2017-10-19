import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class TFIDFCalculator {

    private Map<String, Integer> wordCount = new HashMap<String, Integer>();

    public TFIDFCalculator(String path) throws IOException {
        super();
        countWordsFromFile(path, Charset.defaultCharset());
    }

    public double calculate_idf(String term) throws Exception {
        if(wordCount.containsKey(term)) {
            double N = 0;
            for (int n : wordCount.values()) {
                N += n;
            }


        int n = wordCount.get(term);

        return get_idf(N, n);
        }
        else
            throw new Exception("IDF can't be calculated because term was not found in document");
    }

    /**
     * Method to calculate inverse document frequency
     * @param N: Total number of Words
     * @param n_i: Number of occurrences of a term
     * @return value for idf
     */
    private double get_idf(double N, double n_i)
    {
        return log2(N/n_i);
    }


    private static double log2(double x) {
        return (Math.log(x) / Math.log(2));
    }

    private void countWordsFromFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        String[] parts = new String(encoded, encoding).split(" ");

        for (String part : parts) {
            if (wordCount.containsKey(part)) {
                wordCount.put(part, wordCount.get(part) + 1);
            } else {
                wordCount.put(part, 1);
            }
        }
    }
    

}
