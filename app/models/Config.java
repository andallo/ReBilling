package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Map;

/**
 * Created by andrey on 21.10.15.
 */
@Entity(value = "Config", noClassnameStored = true)
public class Config {

    @Id
    private ObjectId objectId;
    String parameterName;
    String parameterValue;

    public String getId() {
        return objectId == null ? null : objectId.toString();
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getParameterName() {
        return parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

}
