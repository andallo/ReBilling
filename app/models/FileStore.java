package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

/**
 * Created by andrey on 22.10.15.
 */

@Entity(value = "FileStore", noClassnameStored = true)
public class FileStore {

    @Id
    private ObjectId objectId;

    @Indexed
    private String fileName;
    private String contentType;
    private Integer version;
    private byte[] bytes;

    public String getId() {
        return objectId == null ? null : objectId.toString();
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
