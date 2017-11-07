package com.technobium;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.vectorizer.DictionaryVectorizer;
import org.apache.mahout.vectorizer.DocumentProcessor;
import org.apache.mahout.vectorizer.common.PartialVectorMerger;
import org.apache.mahout.vectorizer.tfidf.TFIDFConverter;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


//Adaption of the example presented in:
//"http://technobium.com/tfidf-explained-using-apache-mahout/"

public class TFIDFCalculator_Mahout {

    String outputFolder;
    Configuration configuration;
    FileSystem fileSystem;
    Path documentsSequencePath;
    Path tokenizedDocumentsPath;
    Path tfidfPath;
    Path termFrequencyVectorsPath;

    public static void main(String args[]) throws Exception {

        TFIDFCalculator_Mahout tfidf_calculator = new TFIDFCalculator_Mahout();

        tfidf_calculator.loadTxtDocuments();
        tfidf_calculator.calculateTfIdf();
        
        tfidf_calculator.print_results();
    }

    public TFIDFCalculator_Mahout() throws IOException {
        configuration = new Configuration();
        fileSystem = FileSystem.get(configuration);

        //Initialize Output Paths
        outputFolder = "output/";
        documentsSequencePath = new Path(outputFolder, "sequence");
        tokenizedDocumentsPath = new Path(outputFolder,
                DocumentProcessor.TOKENIZED_DOCUMENT_OUTPUT_FOLDER);
        tfidfPath = new Path(outputFolder + "tfidf");
        termFrequencyVectorsPath = new Path(outputFolder
                + DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER);
    }
    
    public void loadTxtDocuments() throws IOException {
    	File dir = new File("input_data");
    	File[] files = dir.listFiles();
    	SequenceFile.Writer writer = new SequenceFile.Writer(fileSystem, configuration, documentsSequencePath, Text.class, Text.class);
    	
    	for (File file : files) {
    		//Key
    		Text name = new Text(file.getName());
    		
    		// read file
    		String content = FileUtils.readFileToString(file);
    		
    		//remove unnecessary stuff
    		// delete all non-alphabetic characters
    		content = content.replaceAll("[^a-zA-Z\\s]", " ");
    
    		// delete whitespaces
    		content = content.replaceAll("\\s+", " ");
    		
    		Text cleanedText = new Text(content);
    		writer.append(name, cleanedText);
    	}
    	
    	writer.close();
    }

    public void calculateTfIdf() throws ClassNotFoundException, IOException,
            InterruptedException {
    	
    	System.out.println("Used Stop-Words are: " + StopAnalyzer.ENGLISH_STOP_WORDS_SET.toString());
    	//Source for this: https://stackoverflow.com/questions/17527741/what-is-the-default-list-of-stopwords-used-in-lucenes-stopfilter

        // Tokenize the documents using Apache Lucene StandardAnalyzer
    	//This also removes Stopwords
        DocumentProcessor.tokenizeDocuments(documentsSequencePath,
                StandardAnalyzer.class, tokenizedDocumentsPath, configuration);
        
        //count words
        DictionaryVectorizer.createTermFrequencyVectors(tokenizedDocumentsPath,
                new Path(outputFolder),
                DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER,
                configuration, 1, 1, 0.0f, PartialVectorMerger.NO_NORMALIZING,
                true, 1, 100, false, false);
        
        //count words per document
        Pair<Long[], List<Path>> documentFrequencies = TFIDFConverter
        		.calculateDF(termFrequencyVectorsPath, tfidfPath,
                        configuration, 100);

        //calculate tfidf per document using total word count and document word count
        TFIDFConverter.processTfIdf(termFrequencyVectorsPath, tfidfPath,
                configuration, documentFrequencies, 1, 100,
                PartialVectorMerger.NO_NORMALIZING, false, false, false, 1);
    }
    
    public void print_results() throws JSONException {
    	//1. create a dictionary words[index] = word
        Path dicFilePath = new Path(outputFolder, "dictionary.file-0");
        HashMap<Integer, Writable> word_dictionary = new HashMap<Integer, Writable>();
        SequenceFileIterable<Writable, Writable> iterableDic = new SequenceFileIterable<Writable, Writable>(dicFilePath, configuration);
        
        for (Pair<Writable, Writable> pair : iterableDic) {
        	word_dictionary.put(Integer.parseInt(pair.getSecond().toString()), pair.getFirst());
        }
        
    	//2. create tfidf score dictionary
        //documentcollection[documentname]={wordindex: tfidf_score}
    	Path path = new Path(outputFolder, "tfidf/tfidf-vectors/part-r-00000");
        SequenceFileIterable<Writable, Writable> iterable = new SequenceFileIterable<Writable, Writable>(path, configuration);
        
        for (Pair<Writable, Writable> pair : iterable) {
        	//get list of tfidf scores (per document)
            JSONObject tfidf_values = new JSONObject(pair.getSecond().toString());
            // convert to java list
            List<Map.Entry<Integer, Double>> docTfidfList = new LinkedList<Map.Entry<Integer, Double>>();
            
            //fill list with values
            Iterator keys = tfidf_values.keys();            
            while (keys.hasNext()) {
                String key = (String)keys.next();
                docTfidfList.add(new AbstractMap.SimpleEntry<Integer, Double>(Integer.parseInt(key), (Double)tfidf_values.get(key)));
            }
            
            // sort the list in descending order of scores
            docTfidfList.sort(new Comparator<Map.Entry<Integer, Double>>() {
                public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                    return (o2.getValue()).compareTo( o1.getValue() );
                }});
            
            //-----------------print-----------------------//
            System.out.format("\n%2s\n", pair.getFirst());
            // for all words in the document, print the word from the dictionary and its score            
            for (Map.Entry<Integer, Double> e : docTfidfList) {
            	Double tfidf_score = e.getValue();
            	if(tfidf_score > 4)
            		System.out.format("%s : %s\n", word_dictionary.get(e.getKey()), tfidf_score);
            }
        }
    }
}