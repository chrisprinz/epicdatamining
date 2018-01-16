package data.management;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDPlusPlusFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException, TasteException {

        // Read the input from file to Mahout data model
        NetflixModel model = new NetflixModel(new File("data/netflix_data.txt"));

        // 3 users used for recommendation
        final long[] users = {1641844, 1011593, 519380};

        // For output export
        OutputPrinter printer = new OutputPrinter();

        printer.writeStringToFile("SVDPlusPlusFactorizer");
        for (long user : users) {

            // Recommended movies for a user
            List<String> movies = new ArrayList<>();

            SVDPlusPlusFactorizer factorizer = new SVDPlusPlusFactorizer(model, 10, 10);
            Recommender recommender = new SVDRecommender(model, factorizer);

            // Recommend 4 movies
            List<RecommendedItem> recommendedItems = recommender.recommend(user, 4);

            for(RecommendedItem item : recommendedItems) {
                movies.add(model.getMovieNameFromID(item.getItemID()));
            }

            printer.writeToFile(user + "", movies);
        }
        printer.writeStringToFile("----------------------------------------------------\n");


        printer.writeStringToFile("ALSWRFactorizer");
        for (long user : users) {

            // Recommended movies for a user
            List<String> movies = new ArrayList<>();

            ALSWRFactorizer factorizer = new ALSWRFactorizer(model, 10, 0.02, 10);
            Recommender recommender = new SVDRecommender(model, factorizer);

            // Recommend 4 movies
            List<RecommendedItem> recommendedItems = recommender.recommend(user, 4);

            for(RecommendedItem item : recommendedItems) {
                movies.add(model.getMovieNameFromID(item.getItemID()));
            }

            printer.writeToFile(user + "", movies);
        }
        printer.writeStringToFile("----------------------------------------------------\n");


        printer.writeStringToFile("GenericUserBasedRecommender");
        for (long user : users) {

            // Recommended movies for a user
            List<String> movies = new ArrayList<>();

            UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(model);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, userSimilarity, model);
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);

            // Recommend 4 movies
            List<RecommendedItem> recommendedItems = recommender.recommend(user, 4);

            for(RecommendedItem item : recommendedItems) {
                movies.add(model.getMovieNameFromID(item.getItemID()));
            }

            printer.writeToFile(user + "", movies);
        }
    }
}
