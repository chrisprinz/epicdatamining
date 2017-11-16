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
            Matrix<Long, Long> matrix = getEmptyUserItemCharacteristicMatrix(data);
            matrix = fillUserItemMatrix(data, matrix);
            System.out.print(matrix);
            Matrix<Long, String> hashMatrix = getHashItemMatrix(data);

        } catch (TasteException | IOException e) {
            e.printStackTrace();
        }

    }

    private static Matrix<Long, Long> fillUserItemMatrix(DataModel data, Matrix<Long, Long> matrix)
            throws TasteException {
        data.getUserIDs().forEachRemaining(user -> {
            try {
                data.getItemIDsFromUser(user).forEach(item -> matrix.addCharacteristic(user, item));
            } catch (TasteException e) {
                e.printStackTrace();
            }
        });
        return matrix;
    }


    private static Matrix<Long, String> getHashItemMatrix(DataModel data) throws TasteException {
        LinkedList<Long> itemIDs = new LinkedList<>();
        data.getItemIDs().forEachRemaining(itemIDs::add);
        itemIDs.sort(Long::compareTo);
        LinkedList<String> hashFunctions = new LinkedList<>();
        hashFunctions.add("h_1");
        hashFunctions.add("h_2");
        Matrix<Long, String> matrix = new Matrix<>(hashFunctions, itemIDs);
        for (Long itemID: itemIDs){
            matrix.addCharacteristic("h_1", hashOne(itemID));
            matrix.addCharacteristic("h_2", hashTwo(itemID));
        }
        return matrix;
    }

    private static Long hashOne(Long value) {
        return null;
    }

    private static Long hashTwo(Long value) {
        return null;
    }

    private static Matrix<Long, Long> getEmptyUserItemCharacteristicMatrix(DataModel data) throws TasteException {
        LinkedList<Long> userIDs = new LinkedList<>();
        data.getUserIDs().forEachRemaining(userIDs::add);
        userIDs.sort(Long::compareTo);
        LinkedList<Long> itemIDs = new LinkedList<>();
        data.getItemIDs().forEachRemaining(itemIDs::add);
        itemIDs.sort(Long::compareTo);
        return new Matrix<>(userIDs, itemIDs);
    }

    private static DataModel getDataModelFromFile() throws IOException {
        String filename = "data/data.txt";
        File file = new File(filename);
        return new FileDataModel(file);
    }
}
