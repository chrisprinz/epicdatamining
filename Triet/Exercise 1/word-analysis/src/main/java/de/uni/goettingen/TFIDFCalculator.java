package de.uni.goettingen;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.Vector.Element;
import org.apache.mahout.math.VectorWritable;
import org.apache.mahout.vectorizer.DictionaryVectorizer;
import org.apache.mahout.vectorizer.DocumentProcessor;
import org.apache.mahout.vectorizer.common.PartialVectorMerger;
import org.apache.mahout.vectorizer.tfidf.TFIDFConverter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.groupingBy;

class TFIDFCalculator {

    private String outputFolder;
    private Configuration configuration;
    private FileSystem fileSystem;
    private Path documentsSequencePath;
    private Path tokenizedDocumentsPath;
    private Path tfidfPath;
    private Path termFrequencyVectorsPath;

    TFIDFCalculator() throws IOException {
        configuration = new Configuration();
        fileSystem = FileSystem.get(configuration);

        outputFolder = "output/";
        documentsSequencePath = new Path(outputFolder, "sequence");
        tokenizedDocumentsPath = new Path(outputFolder, DocumentProcessor.TOKENIZED_DOCUMENT_OUTPUT_FOLDER);
        tfidfPath = new Path(outputFolder + "tfidf");
        termFrequencyVectorsPath = new Path(outputFolder + DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER);
    }

    void readData() throws IOException {
        File folder = new File("data");
        File[] files = folder.listFiles();
        if (files == null) {
            System.err.println("The directory is empty!");
            return;
        }

        try (SequenceFile.Writer writer = new SequenceFile.Writer(fileSystem, configuration, documentsSequencePath,
                Text.class, Text.class)) {
            for (File file : files) {
                Text id = new Text(file.getName());

                StringBuilder builder = new StringBuilder();
                try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
                    stream.forEach(builder::append);
                }
                Text content = new Text(builder.toString());
                writer.append(id, content);
            }
            writer.close();
        }
        System.out.println("Read data successfully!");
    }

    void calculateTFIDF() throws InterruptedException, IOException, ClassNotFoundException {

        // Tokenize the documents using Apache Lucene StandardAnalyzer
        DocumentProcessor.tokenizeDocuments(documentsSequencePath, StandardAnalyzer.class, tokenizedDocumentsPath,
                configuration);

        DictionaryVectorizer.createTermFrequencyVectors(tokenizedDocumentsPath, new Path(outputFolder),
                DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER, configuration, 1, 1, 0.0f,
                PartialVectorMerger.NO_NORMALIZING, true, 1, 100, false, false);

        Pair<Long[], List<Path>> documentFrequencies = TFIDFConverter.calculateDF(termFrequencyVectorsPath, tfidfPath,
                configuration, 100);

        TFIDFConverter.processTfIdf(termFrequencyVectorsPath, tfidfPath, configuration, documentFrequencies, 1, 100,
                PartialVectorMerger.NO_NORMALIZING, false, false, false, 1);

        System.out.println("The calculation has finished successfully!");
    }

    void processOutput() throws IOException {
        Path path = new Path(outputFolder, "dictionary.file-0");
        Map<Integer, String> indexWord = new HashMap<>();

        SequenceFileIterable<Text, IntWritable> iterable = new SequenceFileIterable<>(path,
                configuration);
        for (Pair<Text, IntWritable> pair : iterable) {
            indexWord.put(pair.getSecond().get(), pair.getFirst().toString());
        }

        path = new Path(outputFolder + "tfidf/tfidf-vectors/part-r-00000");
        List<WordData> wordFreq = new ArrayList<>();

        SequenceFileIterable<Text, VectorWritable> iterable2 = new SequenceFileIterable<>(path,
                configuration);
        for (Pair<Text, VectorWritable> pair : iterable2) {
            String document = pair.getFirst().toString();
            Vector vector = pair.getSecond().get();

            Iterator<Element> iterator = vector.all().iterator();
            int index = -1;
            while (iterator.hasNext()) {
                index++;
                String word = indexWord.get(index);
                double frequency = iterator.next().get();
                if (frequency > 0) {
                    WordData data = new WordData(word, iterator.next().get(), document);
                    wordFreq.add(data);
                }
            }
        }
        Map<String, List<WordData>> results = wordFreq.stream()
                .sorted(comparingDouble(WordData::getFrequency).reversed()).collect(groupingBy(WordData::getDocument));
        writeOutputToFile(results);
        System.out.println("The result has been generated!");
    }

    private void writeOutputToFile(Map<String, List<WordData>> data)
            throws IOException {
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("output/result.txt"), "utf-8"))) {

            Iterator<Entry<String, List<WordData>>> it = data.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, List<WordData>> pair = it.next();
                String fileName = pair.getKey();
                List<WordData> frequency = pair.getValue();

                writer.write(fileName);
                writer.write("\n");

                for (WordData word : frequency) {
                    if (word.getFrequency() != 0) {
                        writer.write(word.getWord() + ": " + word.getFrequency());
                        writer.write("\n");
                    }
                }

                it.remove(); // avoids a ConcurrentModificationException
                writer.write("--------------------------------------------\n");
            }
        }
    }
}
