import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.*;
import java.net.URL;
import java.util.List;

public class App {

    public static void main(String[] args) {

        try {

            URL url = App.class.getResource("data.txt");
            File file = new File(url.getFile());
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // load data
            DataModel data = new FileDataModel(file);

            UserSimilarity similarity = new PearsonCorrelationSimilarity(data);

            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, data);

            UserBasedRecommender recommender = new GenericUserBasedRecommender(data, neighborhood, similarity);

            List<RecommendedItem> recommendations = recommender.recommend(2, 3);
            recommendations.forEach(System.out::println);
        } catch (TasteException | IOException e) {
            e.printStackTrace();
        }

    }
}
