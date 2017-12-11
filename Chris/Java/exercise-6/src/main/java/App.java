import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;

public class App {

    private static final String DATA_DIRECTORY = "data";
    private static final String TARGET_DIRECTORY = "target/";

    private static Configuration configuration = new Configuration();
    private static FileSystem fileSystem;

    public static void main(String[] args) {
        try {
            System.out.println("\n\n\nInitializing\n\n\n");
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException | NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void initialize() throws IOException {
        fileSystem = FileSystem.get(configuration);
    }

}
