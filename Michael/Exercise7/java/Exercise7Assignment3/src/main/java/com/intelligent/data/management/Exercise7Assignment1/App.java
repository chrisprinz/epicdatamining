package com.intelligent.data.management.Exercise7Assignment1;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDPlusPlusFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class App 
{	
    public static void main(String args[]) throws IOException, TasteException {
    	NetflixFileDataModel netflixData = new NetflixFileDataModel(new File("data/netflix_data.txt"));
    	DataModel data = netflixData;
    	
    	long[] users = {699878, 835810, 1290128};
    	
    	System.out.println("SVDRecommender using the SVDPlusPlusFactorizer");
    	for (long u : users) {
	    	System.out.println(String.format("Recommended for user %d:", u));
			SVDPlusPlusFactorizer factorizer = new SVDPlusPlusFactorizer(data, 10, 5);
			Recommender recommender = new SVDRecommender(data, factorizer);
			List<RecommendedItem> recommendedItems = recommender.recommend(u, 4);
			for(RecommendedItem r : recommendedItems) {
				System.out.println(netflixData.getMovieNameFromID(r.getItemID()));
			}
    	}
    	System.out.println();
    	
    	System.out.println("SVDRecommender using the ALSWRFactorizer");
    	for (long u : users) {
	    	System.out.println(String.format("Recommended for user %d:", u));
	    	float lambda = new Float(0.02);
			ALSWRFactorizer factorizer = new ALSWRFactorizer(data, 10, lambda, 5);
			Recommender recommender = new SVDRecommender(data, factorizer);
			List<RecommendedItem> recommendedItems = recommender.recommend(u, 4);
			for(RecommendedItem r : recommendedItems) {
				System.out.println(netflixData.getMovieNameFromID(r.getItemID()));
			}
    	}
    	System.out.println();
    	
    	System.out.println("GenericUserBasedRecommender");
    	for (long u : users) {
	    	System.out.println(String.format("Recommended for user %d:", u));
	    	UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(data);
	    	UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, userSimilarity, data);
	    	Recommender recommender = new GenericUserBasedRecommender(data, neighborhood, userSimilarity);
			List<RecommendedItem> recommendedItems = recommender.recommend(u, 4);
			for(RecommendedItem r : recommendedItems) {
				System.out.println(netflixData.getMovieNameFromID(r.getItemID()));
			}
    	}
    	System.out.println();
    }
}
