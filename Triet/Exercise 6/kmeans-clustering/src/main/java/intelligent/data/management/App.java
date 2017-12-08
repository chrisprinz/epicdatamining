package intelligent.data.management;

import org.apache.hadoop.fs.Path;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        KmeansCluster cluster = new KmeansCluster();

        cluster.readData();
        cluster.clusterDocs();

        cluster.printSequenceFile(cluster.vectorPath);

        System.out.println("\n Clusters: ");
        cluster.printSequenceFile(new Path(cluster.outputFolder
                + "clusters/clusteredPoints/part-m-00000"));
    }
}
