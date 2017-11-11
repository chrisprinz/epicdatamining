package trietdoan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import static org.apache.commons.math.util.MathUtils.round;

public class App {

    private static final String INPUT = "data/data.txt";

    public static void main(String[] args) throws IOException, TasteException {

        // All user IDs
        List<Long> userIds = new ArrayList<>();

        // All item IDs
        List<Long> itemIds = new ArrayList<>();

        // Similarity between users, user 1 - user 2: xyz
        Map<Pair<Long, Long>, Double> userSimilarities = new HashMap<>();

        // Similarity between items, item 1 - item 2: xyz
        Map<Pair<Long, Long>, Double> itemSimilarities = new HashMap<>();

        // For output generation
        OutputPrinter printer = new OutputPrinter();

        // Build the model
        DataModel data = new FileDataModel(new File(INPUT));

        // Print rating of each user
        LongPrimitiveIterator iterator = data.getUserIDs();
        while (iterator.hasNext()) {
            long id = iterator.next();
            userIds.add(id);
            PreferenceArray preferences = data.getPreferencesFromUser(id);
            printer.printRate(id, preferences, true);
        }

        // Print rating of each item
        iterator = data.getItemIDs();
        while (iterator.hasNext()) {
            long id = iterator.next();
            itemIds.add(id);
            PreferenceArray preferences = data.getPreferencesForItem(id);
            printer.printRate(id, preferences, false);
        }

        TanimotoCoefficientSimilarity similarity = new TanimotoCoefficientSimilarity(data);

        // Calculate user similarity
        for (long id1 : userIds) {
            for (long id2 : userIds) {
                Pair<Long, Long> key = new Pair<>(id1, id2);
                double value = round(similarity.userSimilarity(id1, id2), 2);
                userSimilarities.put(key, value);
            }
        }

        printer.printSimilarity(userSimilarities, "User Similarity", userIds);

        // Calculate item similarity
        for (long id1 : itemIds) {
            for (long id2 : itemIds) {
                Pair<Long, Long> key = new Pair<>(id1, id2);
                double value = round(similarity.itemSimilarity(id1, id2), 2);
                itemSimilarities.put(key, value);
            }
        }

        printer.printSimilarity(itemSimilarities, "Item Similarity", itemIds);
        System.out.println("The program has been executed successfully");
    }
}
