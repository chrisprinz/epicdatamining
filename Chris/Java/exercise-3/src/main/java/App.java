import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        try {

            DataModel data = getDataModelFromFile("data/data.txt");
            printItemsByUser(data);
            printRatingsByItem(data);
            printPairwiseJaccardUserSimilarities(data);
            printPairwiseJaccardItemSimilarities(data);
        } catch (TasteException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void printPairwiseJaccardItemSimilarities(DataModel data) throws TasteException {
        System.out.print("\n\n\nPairwise Jaccard Item Similarities\n----------------------------------\n");
        TanimotoCoefficientSimilarity similarity = new TanimotoCoefficientSimilarity(data);
        ArrayList<Long> users = new ArrayList<>();
        data.getItemIDs().forEachRemaining(users::add);
        for (int outerIndex = 0; outerIndex < users.size(); outerIndex++) {
            for (int innerIndex = outerIndex + 1; innerIndex < users.size(); innerIndex++) {
                Long item = users.get(outerIndex);
                Long other = users.get(innerIndex);
                try {
                    double score = similarity.itemSimilarity(item, other);
                    System.out.println("item_sim(" + item + ", " + other + ") ~ " + score);
                } catch (TasteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void printPairwiseJaccardUserSimilarities(DataModel data) throws TasteException {
        System.out.print("\n\n\nPairwise Jaccard User Similarities\n----------------------------------\n");
        TanimotoCoefficientSimilarity similarity = new TanimotoCoefficientSimilarity(data);
        ArrayList<Long> users = new ArrayList<>();
        data.getUserIDs().forEachRemaining(users::add);
        for (int outerIndex = 0; outerIndex < users.size(); outerIndex++) {
            for (int innerIndex = outerIndex + 1; innerIndex < users.size(); innerIndex++) {
                Long user = users.get(outerIndex);
                Long other = users.get(innerIndex);
                try {
                    double score = similarity.userSimilarity(user, other);
                    System.out.println("user_sim(" + user + ", " + other + ") ~ " + score);
                } catch (TasteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void printRatingsByItem(DataModel data) throws TasteException {
        System.out.print("\n\n\nRatings by Item\n---------------\n");
        data.getItemIDs().forEachRemaining(id -> {
            System.out.println("Item " + id + ":");
            try {
                data.getPreferencesForItem(id).forEach(preference -> System.out.print(preference.getValue() + ", "));
            } catch (TasteException e) {
                e.printStackTrace();
            }
            System.out.println("\n");
        });
    }

    private static void printItemsByUser(DataModel data) throws TasteException {
        System.out.print("\n\n\nItems by User\n-------------\n");
        data.getUserIDs().forEachRemaining(id -> {
            System.out.println("User " + id + ":");
            try {
                data.getItemIDsFromUser(id).forEach(itemId -> System.out.print(itemId + ", "));
            } catch (TasteException e) {
                e.printStackTrace();
            }
            System.out.println("\n");
        });
    }

    private static DataModel getDataModelFromFile(String filename) throws IOException {
        File file = new File(filename);
        return new FileDataModel(file);
    }
}
