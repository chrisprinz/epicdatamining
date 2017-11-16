import java.util.Arrays;
import java.util.LinkedList;

class Matrix {

    private LinkedList<Long> columnIDs = new LinkedList<>();
    private LinkedList<Long> rowIDs = new LinkedList<>();
    private long[][] values;

    Matrix(LinkedList<Long> columnIDs, LinkedList<Long> rowIDs) {
        this.columnIDs = columnIDs;
        this.rowIDs = rowIDs;
        values = new long[rowIDs.size()][columnIDs.size()];
    }

    void addCharacteristic(Long columnID, Long rowID) {
        int columnIndex = columnIDs.indexOf(columnID);
        int rowIndex = rowIDs.indexOf(rowID);
        values[rowIndex][columnIndex] = 1L;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("IDs\t");
        columnIDs.forEach(id -> builder.append(id).append("\t"));
        builder.append("\n");
        int row = 0;
        for (Long id : rowIDs){
            builder.append(id).append("\t");
            for (int column = 0; column < columnIDs.size(); column++){
                builder.append(values[row][column]).append("\t");
            }
            builder.append("\n");
            row++;
        }

        return builder.toString();
    }
}
