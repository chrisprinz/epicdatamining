package tfidf;

public class Exercise1 {


    /**
     * @param N   number of documents in repository
     * @param n_i number or documents with term i
     */
    static double idf(double N, double n_i) {
        return Math.log(N / n_i);
    }

    /**
     * @param i number of occurrences of a word in a document
     * @param k number of occurrences of that most frequent word in the same document
     */
    static double tf(double i, double k) {
        return i / k;
    }


    /**
     *
     * @param N number of documents in repository
     * @param n_i number or documents with term i
     * @param i number of occurrences of a word in a document
     * @param k number of occurrences of that most frequent word in the same document
     */
    static double tfidf(double N, double n_i, double i, double k) {
        return tf(i,k) * idf(N, n_i);
    }

    public static void main(String[] args) {

        System.out.println("a)");
        System.out.println(idf(10000000, 40));
        System.out.println(idf(10000000, 10000));
        System.out.println("b)");
        System.out.println(tfidf(10000000, 320, 1, 15));
        System.out.println(tfidf(10000000, 320, 5, 15));


    }
}