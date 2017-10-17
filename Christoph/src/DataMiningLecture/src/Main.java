public class Main {

    public static void main(String[] args) {
        System.out.println(Integer.toString(get_idf(10000000, 40)));
        System.out.println(Integer.toString(get_idf(10000000, 10000)));
    }

    private static int get_idf(double N, double n)
    {
        double result = Math.log(N/n);
        return (int)Math.round(result);
    }
}
