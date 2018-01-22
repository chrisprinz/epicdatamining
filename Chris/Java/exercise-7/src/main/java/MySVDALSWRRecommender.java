import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;

class MySVDALSWRRecommender extends MyRecommender {
    MySVDALSWRRecommender(DataModel model) throws TasteException {
        Factorizer factorizer = new ALSWRFactorizer(model, 10, 0.2f, 5);
        this.model = model;
        recommender = new SVDRecommender(model, factorizer);
    }

}
