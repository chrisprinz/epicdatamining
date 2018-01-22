import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class App {

    private static final String DATA_DIRECTORY = "data/";
    private static final String FILE_NAME = "netflix_data.txt";
    private static final long[] USER_IDs = {2238060, 1300759, 2143500}; // some users with > 20 movies
    private static final int NUMBER_OF_RECOMMENDATIONS = 4;

    private static List<MyRecommender> recommenders = new LinkedList<>();

    public static void main(String[] args) {
        try {
            initialize();
            for(MyRecommender recommender: recommenders) {
                for(long user: USER_IDs){
                    recommender.printRecommendations(user, NUMBER_OF_RECOMMENDATIONS);
                }
                recommender.printStatistics();
            }
        } catch (TasteException | IOException e) {
            e.printStackTrace();
        }
    }


    private static void initialize() throws IOException, TasteException {
        DataModel model = new NetflixFileDataModel(new File(DATA_DIRECTORY + FILE_NAME));
        recommenders.add(new MySVDPlusPlusRecommender(model));
        recommenders.add(new MySVDALSWRRecommender(model));
        recommenders.add(new MyUserBasedRecommender(model));
    }
}
