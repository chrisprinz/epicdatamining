import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDPlusPlusFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;

class MySVDPlusPlusRecommender extends MyRecommender {

    MySVDPlusPlusRecommender(DataModel model) throws TasteException {
        SVDPlusPlusFactorizer factorizer = new SVDPlusPlusFactorizer(model, 10, 5);
        this.model = model;
        recommender = new SVDRecommender(model, factorizer);
    }

}
