package tfidf;

public class Exercise1 {


    /**
     * @param N   number of documents in repository
     * @param n_i number or documents with term i
     */
    double idf(double N, double n_i) {
        return Math.log(N / n_i);
    }

    /**
     * @param i    number of occurrences of a word in a document
     * @param j    number of words in the same document
     * @param maxK maximum number of occurrences of the most frequent word in the same document
     * @param k    number of occurrences of that most frequent word in the same document
     */
    double tf(double i, double j, double maxK,
              double k) {
        return f(i, j) / (maxK * f(k, j));
    }


    /**
     * @param i number of occurrences of a word in a document
     * @param j number of words in the same document
     * @return frequency of a word in a document
     */
    double f(double i, double j) {
        return i / j;
    }

    public void main(String[] args) {

        System.out.println("a)");
        System.out.println(idf(10000000, 40));
        System.out.println(idf(10000000, 10000));
        System.out.println("a)");
        //System.out.println(tf());
        //System.out.println(tf());


    }
}
