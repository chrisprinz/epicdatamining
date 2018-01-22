import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MyRecommender {

    Recommender recommender;
    DataModel model;
    private List<Float> values = new LinkedList<>();
    private DecimalFormat decimal = new DecimalFormat(".##");


    private List<String> getRecommendations(long id, int numberOfRecommendations) throws TasteException {
        return recommender.recommend(id, numberOfRecommendations).stream().map(item -> {
            NetflixFileDataModel netflixModel = (NetflixFileDataModel) model;
            values.add(item.getValue());
            return netflixModel.getMovieNameFromID(item.getItemID()) + "\t(" + decimal.format(item.getValue()) + ")";
        }).collect(Collectors.toList());
    }

    void printRecommendations(long id, int numberOfRecommendations) throws TasteException {
        StringBuilder builder = new StringBuilder();
        builder.append("Recommendations through the ")
                .append(this.getClass().getName())
                .append(" for user ")
                .append(id)
                .append(":\n\n");
        getRecommendations(id, numberOfRecommendations).forEach(string -> builder.append(string).append("\n"));
        builder.append("\n\n");
        System.out.print(builder.toString());
    }

    void printStatistics() {
        System.out.print("\nAverage value for the " +
                this.getClass().getName() +
                ": " +
                decimal.format(values.stream()
                        .mapToDouble(each -> Double.parseDouble(each.toString()))
                        .summaryStatistics()
                        .getAverage()) +
                "\n\n\n"
        );
    }


}
