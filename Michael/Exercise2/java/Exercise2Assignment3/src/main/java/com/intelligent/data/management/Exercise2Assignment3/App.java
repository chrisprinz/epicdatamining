/**
 * Most things taken from: http://technobium.com/tfidf-explained-using-apache-mahout/
 */

package com.intelligent.data.management.Exercise2Assignment3;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
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

public class App
{
	private static Configuration configuration;
	private static FileSystem fileSystem;
	private static String outputFolder = "output";
	private static Path documentsSequencePath;
	private static Path tokenizedDocumentsPath;
	private static Path tfidfPath;
	private static Path termFrequencyVectorsPath;
	
    public static void main( String[] args ) throws ClassNotFoundException, IOException, InterruptedException
    {
    	init();
    	readDocuments();
    	calculateTFIDF();
    	output();
    }
    
    public static void init() throws IOException {
    	configuration = new Configuration();
    	fileSystem = FileSystem.get(configuration);
        
        outputFolder = "output/";
        documentsSequencePath = new Path(outputFolder, "sequence");
        tokenizedDocumentsPath = new Path(outputFolder, DocumentProcessor.TOKENIZED_DOCUMENT_OUTPUT_FOLDER);
        tfidfPath = new Path(outputFolder + "tfidf");
        termFrequencyVectorsPath = new Path(outputFolder + DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER);
    }
    
    public static void readDocuments() throws IOException {
    	File dir = new File("data");
    	File[] files = dir.listFiles();
    	SequenceFile.Writer writer = new SequenceFile.Writer(fileSystem, configuration, documentsSequencePath, Text.class, Text.class);
    	
    	for (File file : files) {
    		Text key = new Text(file.getName());
    		
    		// read file
    		String s = FileUtils.readFileToString(file);
    		// clean string from all non-alphabetic characters
    		s = s.replaceAll("[^a-zA-Z\\s]", " ").replaceAll("\\s+", " ");
    		
    		Text data = new Text(s);
    		writer.append(key, data);
    		System.out.println(key);
    	}
    	
    	writer.close();
    }
    
    public static void calculateTFIDF() throws ClassNotFoundException, IOException, InterruptedException {
        DocumentProcessor.tokenizeDocuments(documentsSequencePath, StandardAnalyzer.class, tokenizedDocumentsPath, configuration);
        
        DictionaryVectorizer.createTermFrequencyVectors(tokenizedDocumentsPath, new Path(outputFolder),
        		DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER,
                configuration, 1, 1, 0.0f, PartialVectorMerger.NO_NORMALIZING,
                true, 1, 100, false, false);

        Pair<Long[], List<Path>> documentFrequencies =
        		TFIDFConverter.calculateDF(termFrequencyVectorsPath, tfidfPath, configuration, 100);

        TFIDFConverter.processTfIdf(termFrequencyVectorsPath, tfidfPath,
                configuration, documentFrequencies, 1, 100,
                PartialVectorMerger.NO_NORMALIZING, false, false, false, 1);
    }
    
    public static void output() {
    	Path path = new Path(outputFolder, "tfidf/tfidf-vectors/part-r-00000");
    	// print all TFIDF scores of all documents in no particular order
        SequenceFileIterable<Writable, Writable> iterable = new SequenceFileIterable<Writable, Writable>(path, configuration);
        for (Pair<Writable, Writable> pair : iterable) {
            System.out.format("%10s -> %s\n", pair.getFirst(), pair.getSecond());
        }
        
        Path dicFilePath = new Path(outputFolder, "dictionary.file-0");
        SequenceFileIterable<Writable, Writable> iterableDic = new SequenceFileIterable<Writable, Writable>(dicFilePath, configuration);
        for (Pair<Writable, Writable> pair : iterableDic) {
        	System.out.format("%s -> %s\n", pair.getFirst(), pair.getSecond());
        }

    }
}
