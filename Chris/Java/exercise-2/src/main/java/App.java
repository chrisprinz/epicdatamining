import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.vectorizer.DictionaryVectorizer;
import org.apache.mahout.vectorizer.DocumentProcessor;
import org.apache.mahout.vectorizer.common.PartialVectorMerger;
import org.apache.mahout.vectorizer.tfidf.TFIDFConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class App {

    private static final String DATA_DIRECTORY = "data";
    private static final String TARGET_DIRECTORY = "target/";

    private static Configuration configuration = new Configuration();
    private static FileSystem fileSystem;
    private static Path documentsSequencePath;

    public static void main(String[] args) {
        try {
        	System.out.println("\n\n\nInitializing\n\n\n");
            initialize();
        	System.out.println("\n\n\nReading Documents\n\n\n");
			//readDocuments();
			System.out.println("\n\n\nPerforming TFIDF\n\n\n");
			//performTFIDF();
			System.out.println("\n\n\nPrinting results\n\n\n");
			printResults(TARGET_DIRECTORY);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException | NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void performTFIDF() {
        Path tokenizedDocumentsPath = new Path(TARGET_DIRECTORY,
                DocumentProcessor.TOKENIZED_DOCUMENT_OUTPUT_FOLDER);
        Path tfIdfPath = new Path(TARGET_DIRECTORY + "tfidf");
        Path termFrequencyVectorsPath = new Path(TARGET_DIRECTORY
                + DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER);

        // Tokenize the documents using Apache Lucene StandardAnalyzer
        try {
            DocumentProcessor.tokenizeDocuments(documentsSequencePath,
                    StandardAnalyzer.class, tokenizedDocumentsPath, configuration);
            DictionaryVectorizer.createTermFrequencyVectors(tokenizedDocumentsPath,
                    new Path(TARGET_DIRECTORY),
                    DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER,
                    configuration, 1, 1, 0.0f, PartialVectorMerger.NO_NORMALIZING,
                    true, 1, 100, false, false);
            Pair<Long[], List<Path>> documentFrequencies = TFIDFConverter
                    .calculateDF(termFrequencyVectorsPath, tfIdfPath,
                            configuration, 100);
            TFIDFConverter.processTfIdf(termFrequencyVectorsPath, tfIdfPath,
                    configuration, documentFrequencies, 1, 100,
                    PartialVectorMerger.NO_NORMALIZING, false, false, false, 1);
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private static void initialize() throws IOException {
        fileSystem = FileSystem.get(configuration);
        documentsSequencePath = new Path(TARGET_DIRECTORY, "sequence");
    }

    private static void readDocuments() {
        File dataDirectory = new File(DATA_DIRECTORY);
        File[] fileArray = dataDirectory.listFiles();
        if (!dataDirectory.isDirectory()) {
            throw new IllegalStateException("Data directory path misconfigured, try again.");
        } else if (fileArray == null) {
            throw new IllegalStateException("Data directory path empty, try again.");
        }

        try (SequenceFile.Writer writer = new SequenceFile.Writer(fileSystem,
                configuration, documentsSequencePath, Text.class, Text.class)) {
            List<File> files = Arrays.asList(fileArray);
            files.forEach(file -> {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder builder = new StringBuilder();
                    reader.lines().forEach(line -> builder.append(trim(line)));
                    Text id = new Text(file.getName());
                    Text content = new Text(builder.toString());
                    writer.append(id, content);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String trim(String string) {
        return string.replaceAll("[^A-z\\s]", "").replaceAll("\\s+", " ").concat(" ");
    }

    private static void printResults(String filename) {
        File dataDirectory = new File(filename);
        File[] fileArray = dataDirectory.listFiles();
        if (!dataDirectory.isDirectory()) {
            throw new IllegalStateException("Target directory path misconfigured, try again.");
        } else if (fileArray == null) {
            throw new IllegalStateException("Target directory path empty, try again.");
        }

        List<File> files = Arrays.asList(fileArray);
        files.forEach(file -> {
        	if (file.isDirectory()) {
        		printResults(file.getPath());
        	} else if(file.getName().endsWith("00000")) {
        		Path path = new Path(file.getPath());
        		SequenceFileIterable<Writable, Writable> iterable = new SequenceFileIterable<>(path, configuration);
        		for (Pair<Writable, Writable> pair : iterable) {
        			System.out.format("%10s -> %s\n", pair.getFirst(), pair.getSecond());
        		}
        		System.out.println("Finshed File.");
        	}
        });

    }

}
