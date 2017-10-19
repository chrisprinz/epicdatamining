public class Main {

    public static void main(String[] args) {
        System.out.println("Exercise 1.2a:");
        System.out.println(Integer.toString((int)Math.round(get_idf(10000000, 40))));
        System.out.println(Integer.toString((int)Math.round(get_idf(10000000, 10000))));
    }

    /**
     * Method to calculate inverse document frequency
     * @param N: Total number of Words
     * @param n_i: Number of occurrences of a term
     * @return value for idf
     */
    private static double get_idf(double N, double n_i)
    {
        return Math.log(N/n_i);
    }
}
