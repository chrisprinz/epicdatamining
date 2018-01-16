/**
 * Most things taken from: http://technobium.com/introduction-to-clustering-using-apache-mahout/
 */

package com.intelligent.data.management.Exercise6Assignment3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.VectorWritable;
import org.apache.mahout.vectorizer.DocumentProcessor;

public class App 
{
	static String dataFolder;
    static String outputFolder;
    static Configuration configuration;
    static FileSystem fileSystem;
    static Path documentsSequencePath;
    static Path tokenizedDocumentsPath;
    static Path tfidfPath;
    static Path vectorsPath;
    static final String[] FILENAMES = {"book_example_data.txt", "iris_data.txt"};
 
    public static void main(String args[]) throws Exception {
        configuration = new Configuration();
        fileSystem = FileSystem.get(configuration);
 
        dataFolder = "data/";
        for (String filename : FILENAMES) {
            outputFolder = "output/" + filename + "/";
            documentsSequencePath = new Path(outputFolder, "sequence");
            tokenizedDocumentsPath = new Path(outputFolder, DocumentProcessor.TOKENIZED_DOCUMENT_OUTPUT_FOLDER);
            tfidfPath = new Path(outputFolder + "tfidf");
            vectorsPath = new Path(outputFolder + "vectors");

            readFile(filename);
	        clusterFile(); 
	        System.out.println("\n Clusters: ");
	        printSequenceFile(new Path(outputFolder + "/clusters/clusteredPoints/part-m-00000"));
        }
    }
  
    public static void readFile(String filename) throws IOException {
        SequenceFile.Writer writer = new SequenceFile.Writer(fileSystem, configuration, vectorsPath, Text.class, VectorWritable.class);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File (dataFolder + filename)));

		String line;
		int id = 0;
		while ((line = bufferedReader.readLine()) != null) {
			String[] els = line.split(",");
			double[] vals = new double[els.length];
			for (int i=0; i<els.length; i++) {
				vals[i] = Double.parseDouble(els[i]);
			}
			DenseVector V = new DenseVector(vals);
			writer.append(new Text(Integer.toString(id++)), new VectorWritable(V));
		}
		bufferedReader.close();
        writer.close();
    }
 
    static void clusterFile() throws ClassNotFoundException, IOException, InterruptedException {
        String canopyCentroids = outputFolder + "canopy-centroids";
        String clusterOutput = outputFolder + "clusters";
 
        FileSystem fs = FileSystem.get(configuration);
        Path oldClusterPath = new Path(clusterOutput);
 
        if (fs.exists(oldClusterPath)) {
            fs.delete(oldClusterPath, true);
        }
 
        CanopyDriver.run(vectorsPath, new Path(canopyCentroids),
                new EuclideanDistanceMeasure(), 20, 5, true, 0, true);
 
        FuzzyKMeansDriver.run(vectorsPath, new Path(
                canopyCentroids, "clusters-0-final"), new Path(clusterOutput),
                0.01, 20, 2, true, true, 0, false);
    }
 
    static void printSequenceFile(Path path) {
        SequenceFileIterable<Writable, Writable> iterable = new SequenceFileIterable<Writable, Writable>(path, configuration);
        for (Pair<Writable, Writable> pair : iterable) {
            System.out.format("%10s -> %s\n", pair.getFirst(), pair.getSecond());
        }
    }

}
