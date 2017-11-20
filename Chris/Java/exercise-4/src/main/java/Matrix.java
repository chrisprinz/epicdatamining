import java.util.LinkedList;

class Matrix<T, E> {

    private LinkedList<E> columnIDs = new LinkedList<>();
    private LinkedList<T> rowIDs = new LinkedList<>();
    private long[][] values;

    Matrix(LinkedList<E> columnIDs, LinkedList<T> rowIDs) {
        this.columnIDs = columnIDs;
        this.rowIDs = rowIDs;
        values = new long[rowIDs.size()][columnIDs.size()];
    }

    void addCharacteristic(E columnID, T rowID) {
        addCharacteristic(columnID, rowID, 1L);
    }

    void addCharacteristic(E columnID, T rowID, long value) {
        int columnIndex = columnIDs.indexOf(columnID);
        int rowIndex = rowIDs.indexOf(rowID);
        values[rowIndex][columnIndex] = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IDs\t");
        columnIDs.forEach(id -> builder.append(id).append("\t"));
        builder.append("\n");
        int row = 0;
        for (T id : rowIDs) {
            builder.append(id).append("\t");
            for (int column = 0; column < columnIDs.size(); column++) {
                builder.append(values[row][column]).append("\t");
            }
            builder.append("\n");
            row++;
        }

        return builder.toString();
    }
}
