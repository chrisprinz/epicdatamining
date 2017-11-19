package intelligent.data.management;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class App {

    private static final String INPUT = "data/data.txt";
    private static final int NUMBER_OF_HASH = 2;

    public static void main(String[] args) throws IOException, TasteException {
        // All user IDs
        List<Long> userIds = new ArrayList<>();

        // All item IDs
        List<Long> itemIds = new ArrayList<>();

        // Build the model
        DataModel data = new FileDataModel(new File(INPUT));

        // Get all user IDs
        LongPrimitiveIterator iterator = data.getUserIDs();
        while (iterator.hasNext()) {
            long id = iterator.next();
            userIds.add(id);
        }

        // Get all item IDs
        iterator = data.getItemIDs();
        while (iterator.hasNext()) {
            long id = iterator.next();
            itemIds.add(id);
        }
        Collections.sort(itemIds);

        // Characteristic Matrix
        int maxRow = itemIds.size(), maxCol = userIds.size();
        long[][] matrix = new long[maxRow][maxCol];

        // Hash value
        long[][] hash = new long[maxRow][NUMBER_OF_HASH];

        System.out.println("Characteristic Matrix");
        System.out.println();

        // Print header row
        System.out.print("        ");
        for (long id : userIds) {
            System.out.print("| User " + id + " ");
        }
        System.out.println("|| h1 | h2");
        System.out.println("------------------------------------------------------");

        // For each row
        for (long row : itemIds) {
            System.out.print("Item " + row + " |");

            // Matrix row index
            int iRow = (int) (row % maxRow);

            // For each column
            for (long col : userIds) {
                FastIDSet idSet = data.getItemIDsFromUser(col);

                // Matrix column index
                int iCol = (int) (col % maxCol);

                // If user rated that item, mark 1, otherwise 0
                if (idSet.contains(row)) {
                    matrix[iRow][iCol] = 1;
                } else {
                    matrix[iRow][iCol] = 0;
                }
                System.out.print("   " + matrix[iRow][iCol] + "    |");
            }

            // Print the hash value
            hash[iRow][0] = firstHash(row);
            hash[iRow][1] = secondHash(row);
            System.out.println("| " + hash[iRow][0] + "  | " + hash[iRow][1]);
        }

        System.out.println();

        // Minhash Signature
        long[][] hashSignature = new long[NUMBER_OF_HASH][maxCol];

        // Init the minhash signature
        for (int row = 0; row < NUMBER_OF_HASH; row++) {
            for (int col = 0; col < maxCol; col++) {
                hashSignature[row][col] = Integer.MAX_VALUE;
            }
        }

        System.out.println("Minhash Signature");

        // Compute hash signature
        for (long row : itemIds) {

            // Matrix row index
            int iRow = (int) (row % maxRow);

            for (long col : userIds) {

                // Matrix column index
                int iCol = (int) (col % maxCol);

                // If user rated that item
                if (matrix[iRow][iCol] == 1) {

                    // Update hash signature
                    for (int i = 0; i < NUMBER_OF_HASH; i++) {
                        hashSignature[i][iCol] = Math.min(hashSignature[i][iCol], hash[iRow][i]);
                    }
                }
            }
        }

        // Print header row
        System.out.print("        ");
        for (long id : userIds) {
            System.out.print("| User " + id + " ");
        }
        System.out.println();
        System.out.println("-------------------------------------------");

        // Print the rows
        for (int row = 0; row < NUMBER_OF_HASH; row++) {
            System.out.print("h" + (row + 1) + "      ");
            for (int col = 0; col < maxCol; col++) {
                System.out.print("|   " + hashSignature[row][col] + "    ");
            }
            System.out.println();
        }

        // Compute the similarity
        double[][] userSimilarity = new double[maxCol][maxCol];

        for (long user1 : userIds) {

            // Matrix index
            int i1 = (int) (user1 % maxCol);

            for (long user2 : userIds) {

                // Matrix index
                int i2 = (int) (user2 % maxCol);

                // Create user data
                Set<Long> userData1 = new HashSet<>();
                Set<Long> userData2 = new HashSet<>();

                for (int i = 0; i < NUMBER_OF_HASH; i++) {
                    userData1.add(hashSignature[i][i1]);
                    userData2.add(hashSignature[i][i2]);
                }

                Set<Long> union = new HashSet<>(userData1);
                union.addAll(userData2);

                Set<Long> intersection = new HashSet<>(userData1);
                intersection.retainAll(userData2);

                userSimilarity[i1][i2] = intersection.size() / union.size();
            }
        }

        // Print the similarity
        System.out.println();
        System.out.println("User Similarity");

        // Print header row
        System.out.print("       ");
        for (long id : userIds) {
            System.out.print("| User " + id + " ");
        }
        System.out.println();
        System.out.println("-------------------------------------------");

        // Print the similarity matrix
        for (long row : userIds) {

            // Matrix row index
            int iRow = (int) (row % maxCol);

            System.out.print("User " + row + " ");

            for (long col : userIds) {

                // Matrix row index
                int iCol = (int) (col % maxCol);

                System.out.print("|  " + userSimilarity[iRow][iCol] + "   ");
            }
            System.out.println();
        }
    }

    private static long firstHash(long input) {
        return (input + 1) % 9;
    }

    private static long secondHash(long input) {
        return (6 * input + 1) % 9;
    }
}
