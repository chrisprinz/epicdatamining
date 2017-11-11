package trietdoan;

import javafx.util.Pair;
import org.apache.mahout.cf.taste.model.PreferenceArray;

import java.io.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class OutputPrinter {

    private static final String OUTPUT = "output/result.txt";

    public OutputPrinter() {
        File file = new File(OUTPUT);

        if (file.exists()) {
            file.delete();
        }
    }

    public void printRate(long id, PreferenceArray data, boolean forUser) throws IOException {

        String identifier = "Item id";
        if (forUser) {
            identifier = "User id";
        }

        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(OUTPUT, true), "utf-8"))) {
            writer.write(identifier + " " + id + "\n");
            data.forEach(item -> {
                try {
                    if (forUser) {
                        writer.write(item.getItemID() + ": " + item.getValue() + "\n");
                    } else {
                        writer.write(item.getUserID() + ": " + item.getValue() + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.write("--------------------------------------------\n");
        }
    }

    public void printSimilarity(Map<Pair<Long, Long>, Double> map, String title, List<Long> ids) throws IOException {

        DecimalFormat format = new DecimalFormat("0.00");

        StringBuilder builder = new StringBuilder();

        builder.append(title).append("\n");

        builder.append("\t");
        for (long id : ids) {
            builder.append(id).append("\t\t");
        }
        builder.append("\n");

        for (Long row : ids) {
            builder.append(row).append("\t");

            for (Long col : ids) {

                Pair<Long, Long> key = new Pair<>(row, col);
                double value = map.get(key);
                builder.append(format.format(value)).append("\t");
            }
            builder.append("\n");
        }

        builder.append("--------------------------------------------\n");

        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(OUTPUT, true), "utf-8"))) {
            writer.write(builder.toString());
        }
    }
}