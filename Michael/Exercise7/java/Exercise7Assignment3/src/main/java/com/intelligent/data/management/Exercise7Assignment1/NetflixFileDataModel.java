package com.intelligent.data.management.Exercise7Assignment1;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.impl.model.MemoryIDMigrator;

public class NetflixFileDataModel extends org.apache.mahout.cf.taste.impl.model.file.FileDataModel {
	private static final long serialVersionUID = -3337785425912797856L;
	private static MemoryIDMigrator idMigrator = new MemoryIDMigrator();

	public NetflixFileDataModel(File dataFile) throws IOException {
		super(dataFile);
	}

	@Override
	protected long readItemIDFromString(String value) {
	    long result = idMigrator.toLongID(value);
	    idMigrator.storeMapping(result, value);
	    return result;
	}
	
	public String getMovieNameFromID(long itemID) {
		return idMigrator.toStringID(itemID);
	}
}
