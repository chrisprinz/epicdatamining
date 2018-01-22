import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

class MyUserBasedRecommender extends MyRecommender {

    MyUserBasedRecommender(DataModel model) throws TasteException {
        this.model = model;
        UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, userSimilarity, model);
        recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
    }

}
