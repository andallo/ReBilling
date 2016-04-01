package models.mongo;

/**
 * Created by andrey on 22.10.15.
 */

import models.FileStore;

import static models.mongo.MongoDS.ds;

public class FileStoreDS {

    public static String save(FileStore fileStore) {
        FileStore _fileStore = getFile(fileStore.getFileName());
        if (_fileStore == null) {
            fileStore.setVersion(1);
        } else {
            fileStore.setVersion(_fileStore.getVersion() + 1);
        }

        Object id = ds().save(fileStore);
        return id.toString();
    }

    public static FileStore getFile(String fileName) {
        FileStore fileStore = ds().createQuery(FileStore.class).field("fileName").equal(fileName)
                .order("-version").limit(1).get();
        return fileStore;
    }

    public static FileStore getFile(String fileName, Integer version) {
        FileStore fileStore = ds().createQuery(FileStore.class).field("fileName").equal(fileName)
                .field("version").equal(version).get();
        return fileStore;
    }
}
