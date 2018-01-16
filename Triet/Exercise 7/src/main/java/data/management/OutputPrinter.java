package data.management;

import java.io.*;
import java.util.List;

public class OutputPrinter {

    private static final String OUTPUT = "output/result.txt";

    public OutputPrinter() {
        File file = new File(OUTPUT);

        if (file.exists()) {
            file.delete();
        }
    }

    public void writeStringToFile(String message) throws IOException {
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(OUTPUT, true), "utf-8"))) {
            writer.write(message);
        }
    }

    public void writeToFile(String userId, List<String> movies) throws IOException {
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(OUTPUT, true), "utf-8"))) {

            writer.write("- User: " + userId + "\n");

            for (String movie : movies) {
                writer.write("    + " + movie + "\n");
            }

            writer.write("----------------------------------------------------\n");
        }
    }
}
