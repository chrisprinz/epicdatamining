package tfidf;

import java.util.Collection;
import java.util.HashSet;

public class Repository {

    Collection<Document> documents = new HashSet<>();

    Repository(Collection<String> filenames) {
        super();
        filenames.forEach(filename -> documents.add(new Document(filename)));
    }

    public double idf(String term) {
        double numberOfDocumentsContainingTerm =
                documents.parallelStream().filter(document -> document.contains(term)).count();
        double totalNumberOfDocuments = documents.size();
        return Math.log(totalNumberOfDocuments / numberOfDocumentsContainingTerm) / Math.log(2);
    }

    public double tfIdf(String term, Document document) {
        double tf = document.tf(term);
        double idf = idf(term);
        return tf * idf;
    }
}
