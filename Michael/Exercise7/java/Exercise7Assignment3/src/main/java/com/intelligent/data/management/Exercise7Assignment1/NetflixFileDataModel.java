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

	/**
	 * Subclasses may wish to override this if ID values in the file are not numeric. This provides a hook by which
	 * subclasses can inject an IDMigrator to perform translation.
	 */
	@Override
	protected long readItemIDFromString(String value) {
	    long result = idMigrator.toLongID(value);
	    idMigrator.storeMapping(result, value);
	    return result;
	}
}
