/**
 * Most things taken from: http://technobium.com/tfidf-explained-using-apache-mahout/
 */

package com.intelligent.data.management.Exercise2Assignment3;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class App
{
	private static Configuration configuration;
	private static FileSystem fileSystem;
	private static String outputFolder = "output";
	private static Path documentsSequencePath;
	private static Path tokenizedDocumentsPath;
	private static Path tfidfPath;
	private static Path termFrequencyVectorsPath;
	
    public static void main( String[] args ) throws ClassNotFoundException, IOException, InterruptedException, JSONException
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
    		s = s.replaceAll("[^a-zA-Z\\s]", " ");
    		// remove all words with <= 3 characters
    		s = s.replaceAll("\\b[a-zA-Z]{1,3}\\b", " ");
    		// leave only one space instead of any number of whitespace character
    		s = s.replaceAll("\\s+", " ");
    		
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
    
    public static void output() throws JSONException {
    	// create a dictionary with all words
        Path dicFilePath = new Path(outputFolder, "dictionary.file-0");
        HashMap<Integer, Writable> dictionary = new HashMap<Integer, Writable>();
        SequenceFileIterable<Writable, Writable> iterableDic = new SequenceFileIterable<Writable, Writable>(dicFilePath, configuration);
        for (Pair<Writable, Writable> pair : iterableDic) {
        	dictionary.put(Integer.parseInt(pair.getSecond().toString()), pair.getFirst());
        }
        
    	// print all TFIDF scores of all documents in descending order of scores
    	Path path = new Path(outputFolder, "tfidf/tfidf-vectors/part-r-00000");
        SequenceFileIterable<Writable, Writable> iterable = new SequenceFileIterable<Writable, Writable>(path, configuration);
        // for all documents
        for (Pair<Writable, Writable> pair : iterable) {
        	// take a JSON object from the output
            JSONObject json = new JSONObject(pair.getSecond().toString());
            // prepare a list of the scores for all words in the document
            List<Map.Entry<Integer, Double>> docTfidfList = new LinkedList<Map.Entry<Integer, Double>>();
            Iterator keys = json.keys();
            // for all words in the document
            while (keys.hasNext()) {
                String key = (String)keys.next();
                // add the word and its score to the list
                docTfidfList.add(new AbstractMap.SimpleEntry<Integer, Double>(Integer.parseInt(key), (Double)json.get(key)));
            }
            // sort the list in descending order of scores
            docTfidfList.sort(new Comparator<Map.Entry<Integer, Double>>() {
                public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                    return (o2.getValue()).compareTo( o1.getValue() );
                }});
            // print the document name
            System.out.format("\n%10s\n", pair.getFirst());
            // for all words in the document, print the word from the dictionary and its score
            for (Map.Entry<Integer, Double> e : docTfidfList) {
            	Double score = e.getValue();
            	// skip scores lower than 5
            	if (score > 5) {
            		System.out.format("%s : %s\n", dictionary.get(e.getKey()), score);
            	}
            }
        }
        

    }
}
