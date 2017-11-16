import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class App {


    public static void main(String[] args) {

        try {
            DataModel data = getDataModelFromFile();
            Matrix matrix = getUserItemCharacteristicMatrix(data);
            matrix = fillUserItemMatrix(data, matrix);
            System.out.print(matrix);

        } catch (TasteException | IOException e) {
            e.printStackTrace();
        }

    }

    private static Matrix fillUserItemMatrix(DataModel data, Matrix matrix) throws TasteException {
        data.getUserIDs().forEachRemaining(user -> {
            try {
                data.getItemIDsFromUser(user).forEach(item -> matrix.addCharacteristic(user, item));
            } catch (TasteException e) {
                e.printStackTrace();
            }
        });
        return matrix;
    }

    private static Matrix getUserItemCharacteristicMatrix(DataModel data) throws TasteException {
        LinkedList<Long> userIDs = new LinkedList<>();
        data.getUserIDs().forEachRemaining(userIDs::add);
        userIDs.sort(Long::compareTo);
        LinkedList<Long> itemIDs = new LinkedList<>();
        data.getItemIDs().forEachRemaining(itemIDs::add);
        itemIDs.sort(Long::compareTo);
        return new Matrix(userIDs, itemIDs);
    }

    private static DataModel getDataModelFromFile() throws IOException {
        String filename = "data/data.txt";
        File file = new File(filename);
        return new FileDataModel(file);
    }
}
