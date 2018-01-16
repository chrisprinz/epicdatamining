package intelligent.data.management;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.fuzzykmeans.FuzzyKMeansDriver;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.VectorWritable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class KmeansCluster {
    String outputFolder;
    private Configuration configuration;
    private FileSystem fileSystem;
    Path vectorPath;

    KmeansCluster() throws IOException {
        configuration = new Configuration();
        fileSystem = FileSystem.get(configuration);

        outputFolder = "output/";
        vectorPath = new Path(outputFolder, "vector");
    }

    void readData(String fileName) throws IOException {
        AtomicInteger count = new AtomicInteger();

        File file = new File("classes/data/" + fileName);
        System.out.println(file.getAbsolutePath());
        if (!file.exists()) {
            System.err.println("File not found!");
            return;
        }

        try (SequenceFile.Writer writer = new SequenceFile.Writer(fileSystem, configuration, vectorPath,
                Text.class, VectorWritable.class)) {

            try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
                stream.forEach(line -> {
                    try {
                        if (line.isEmpty()) {
                            return;
                        }
                        String[] values = line.split(",");
                        double[] data = new double[values.length];
                        for (int i = 0; i < values.length; i++) {
                            data[i] = Double.parseDouble(values[i]);
                        }

                        DenseVector vector = new DenseVector(data);

                        Text id = new Text(count.toString());
                        VectorWritable vectorWritable = new VectorWritable(vector);

                        writer.append(id, vectorWritable);
                        count.getAndIncrement();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            writer.close();
        }
        System.out.println("Read data successfully!");
    }

    void clusterDocs() throws ClassNotFoundException, IOException,
            InterruptedException {

        String vectorsFolder = outputFolder + "vector";
        String canopyCentroids = outputFolder + "canopy-centroids";
        String clusterOutput = outputFolder + "clusters";

        FileSystem fs = FileSystem.get(configuration);
        Path oldClusterPath = new Path(clusterOutput);

        if (fs.exists(oldClusterPath)) {
            fs.delete(oldClusterPath, true);
        }

        CanopyDriver.run(new Path(vectorsFolder), new Path(canopyCentroids),
                new EuclideanDistanceMeasure(), 20, 5, true, 0, true);

        FuzzyKMeansDriver.run(new Path(vectorsFolder), new Path(
                        canopyCentroids, "clusters-0-final"), new Path(clusterOutput),
                0.01, 20, 2, true, true, 0, false);
    }

    void printSequenceFile(Path path) {
        SequenceFileIterable<Writable, Writable> iterable = new SequenceFileIterable<>(
                path, configuration);
        for (Pair<Writable, Writable> pair : iterable) {
            System.out.format("%10s -> %s\n", pair.getFirst(), pair.getSecond());
        }
    }
}
