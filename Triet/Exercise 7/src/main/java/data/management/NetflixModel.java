package data.management;

import org.apache.mahout.cf.taste.impl.model.MemoryIDMigrator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;

import java.io.File;
import java.io.IOException;

public class NetflixModel extends FileDataModel {

    private static MemoryIDMigrator idMigrator = new MemoryIDMigrator();

    public NetflixModel(File dataFile) throws IOException {
        super(dataFile);
    }

    @Override
    protected long readItemIDFromString(String value) {

        // To turn movie name into ID
        long id = idMigrator.toLongID(value);
        idMigrator.storeMapping(id, value);

        return id;
    }

    public String getMovieNameFromID(long itemID) {
        return idMigrator.toStringID(itemID);
    }
}
