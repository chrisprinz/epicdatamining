package com.intelligent.data.management.Exercise7Assignment1;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.model.DataModel;

public class App 
{
	public static final String FILENAME = "data/netflix_data.txt";
	
    public static void main(String args[]) throws IOException {
    	DataModel model = new NetflixFileDataModel(new File(FILENAME));
    	System.out.println("Something");
    }
}
