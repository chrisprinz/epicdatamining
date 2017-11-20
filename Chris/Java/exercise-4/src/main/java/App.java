import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class App {

    private static int numberOfElements = 0;


    public static void main(String[] args) {

        try {
            DataModel data = getDataModelFromFile();
            Matrix<Long, Long> matrix = getEmptyUserItemCharacteristicMatrix(data);
            matrix = fillUserItemMatrix(data, matrix);
            System.out.println("User - Item - Matrix:");
            System.out.print(matrix);
            System.out.print("______________________\n\n");
            Matrix<Long, String> hashMatrix = getHashItemMatrix(data);
            System.out.println("Item - Hash - Matrix:");
            System.out.print(hashMatrix);
            System.out.print("______________________\n\n");
            Matrix<String, Long> signatureMatrix = calculateSignatureMatrix(matrix, hashMatrix);
            System.out.println("Signature - Matrix:");
            System.out.print(signatureMatrix);
        } catch (TasteException | IOException e) {
            e.printStackTrace();
        }

    }

    private static Matrix<String, Long> calculateSignatureMatrix(Matrix<Long, Long> matrix,
                                                                 Matrix<Long, String> hashMatrix) {
        LinkedList<Long> userIDs = matrix.getColumnIDs();
        LinkedList<Long> itemIDs = matrix.getRowIDs();
        LinkedList<String> hashFunctions = hashMatrix.getColumnIDs();
        Matrix<String,Long> signatureMatrix = new Matrix<>(userIDs, hashFunctions);
        signatureMatrix.initializeWithInfinity();
        for(Long itemID : itemIDs){
            for (Long userID: userIDs){
                if (matrix.get(userID, itemID) > 0){
                    for (String hashFunction : hashFunctions){
                        long oldValue = signatureMatrix.get(userID,hashFunction);
                        long value = hashMatrix.get(hashFunction,itemID);
                        if (value < oldValue){
                            signatureMatrix.addCharacteristic(userID, hashFunction, value);
                        }
                    }
                }
            }
        }

        return signatureMatrix;
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
        for (Long itemID : itemIDs) {
            matrix.addCharacteristic("h_1", itemID, hashOne(itemID));
            matrix.addCharacteristic("h_2", itemID, hashTwo(itemID));
        }
        return matrix;
    }

    private static Long hashOne(Long value) {
        return (value + 1) % numberOfElements;
    }

    private static Long hashTwo(Long value) {

        return (value * 7 + 1) % numberOfElements;
    }

    private static Matrix<Long, Long> getEmptyUserItemCharacteristicMatrix(DataModel data) throws TasteException {
        LinkedList<Long> userIDs = new LinkedList<>();
        data.getUserIDs().forEachRemaining(userIDs::add);
        userIDs.sort(Long::compareTo);
        LinkedList<Long> itemIDs = new LinkedList<>();
        data.getItemIDs().forEachRemaining(itemIDs::add);
        itemIDs.sort(Long::compareTo);
        numberOfElements = itemIDs.size();
        return new Matrix<>(userIDs, itemIDs);
    }

    private static DataModel getDataModelFromFile() throws IOException {
        String filename = "data/data.txt";
        File file = new File(filename);
        return new FileDataModel(file);
    }
}
