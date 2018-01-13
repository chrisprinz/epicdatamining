import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.fuzzykmeans.FuzzyKMeansDriver;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.VectorWritable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {

    private static final String[] filenames = {"book_example_data.txt", "iris_data.txt"};
    private static final String DATA_DIRECTORY = "data/";
    private static final String TARGET_DIRECTORY = "target/";
    private static final String VECTOR_DIRECTORY = TARGET_DIRECTORY + "vectors/";
    private static final String CENTROID_DIRECTORY = TARGET_DIRECTORY + "centroids/";
    private static final String CLUSTER_DIRECTORY = TARGET_DIRECTORY + "clusters/";

    private static Configuration configuration = new Configuration();
    private static FileSystem fileSystem;

    public static void main(String[] args) {
        try {
            System.out.println("\n\n\nInitializing\n\n\n");
            initialize();
            for (String filename : filenames) {
                readDocument(filename);
                cleanFileSystem(filename);
                performClustering(filename);
                printSequenceFile(filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException | NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }


    private static void initialize() throws IOException {
        fileSystem = FileSystem.get(configuration);
    }

    private static void cleanFileSystem(String fullFilename) throws IOException {
        String filename = fullFilename.substring(0, fullFilename.lastIndexOf("."));
        Path oldClusterPath = new Path(CLUSTER_DIRECTORY, filename);
        if (fileSystem.exists(oldClusterPath)) {
            fileSystem.delete(oldClusterPath, true);
        }
    }

    private static void readDocument(String fullFilename) {
        String filename = fullFilename.substring(0, fullFilename.lastIndexOf("."));
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_DIRECTORY + fullFilename));
             SequenceFile.Writer writer = new SequenceFile.Writer(fileSystem,
                     configuration, new Path(VECTOR_DIRECTORY, filename), Text.class, VectorWritable.class)) {
            long id = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                double[] values = new double[elements.length];
                for (int i = 0; i < elements.length; i++) {
                    values[i] = Double.parseDouble(elements[i]);
                }
                DenseVector vector = new DenseVector(values);
                writer.append(new Text(String.valueOf(id)), new VectorWritable(vector));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void performClustering(String fullFilename) {
        String filename = fullFilename.substring(0, fullFilename.lastIndexOf("."));
        try {
            CanopyDriver.run(new Path(VECTOR_DIRECTORY, filename), new Path(CENTROID_DIRECTORY, filename),
                    new EuclideanDistanceMeasure(), 20, 5, true, 0, true);

            FuzzyKMeansDriver.run(new Path(VECTOR_DIRECTORY, filename), new Path(CENTROID_DIRECTORY, filename+"/clusters-0-final"),
                    new Path(CLUSTER_DIRECTORY, filename), 0.01, 20, 2, true, true, 0, false);
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void printSequenceFile(String fullFilename) {
        String filename = fullFilename.substring(0, fullFilename.lastIndexOf("."));
        SequenceFileIterable<Writable, Writable> iterable =
                new SequenceFileIterable<>(new Path(CLUSTER_DIRECTORY, filename+"/clusteredPoints/part-m-00000"), configuration);
        iterable.forEach(pair -> System.out.format("%10s -> %s\n", pair.getFirst(), pair.getSecond()));
    }

}
