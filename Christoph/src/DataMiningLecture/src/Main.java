import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        System.out.println("Exercise 1.2a:");
        System.out.println(Integer.toString((int)Math.round(DocumentCollection.calculate_idf(10000000, 40))));
        System.out.println(Integer.toString((int)Math.round(DocumentCollection.calculate_idf(10000000, 10000))));


        String[] paths = {"C:/Users/prinz/Documents/epicdatamining/Christoph/src/DataMiningLecture/src/Faust_I.txt",
                "C:/Users/prinz/Documents/epicdatamining/Christoph/src/DataMiningLecture/src/Faust_II.txt"};
        DocumentCollection Documents = new DocumentCollection(paths);
        try{
            System.out.println("Test For TF_IDF Calculation from Document");
            System.out.println("TFIDF of given word in loaded document is: " +
                    Double.toString(Documents.get_tf_idf("haus","C:/Users/prinz/Documents/epicdatamining/Christoph/src/DataMiningLecture/src/Faust_I.txt" )));
        }
        catch (Exception e)
        {
            System.out.println("Text cant be found");
        }
    }
}
