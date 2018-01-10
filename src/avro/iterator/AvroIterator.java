package avro.iterator;

import json.type.complex.JsonObject;
import json.type.complex.JsonValue;

/**
 * Created by splbap on 2017-12-04.
 */
public class AvroIterator {

    private JsonObject currentObject;

    public AvroIterator(JsonObject currentObject) {
        this.currentObject = currentObject;
    }

    public JsonObject getCurrentObject() {
        return currentObject;
    }

    public void setCurrentObject(JsonObject currentObject) {
        this.currentObject = currentObject;
    }

    public void setCurrentObject(JsonValue currentObject) {
        this.currentObject = (JsonObject) currentObject;
    }

    public <T> T getAttribute(String key, Class<T> tClass) throws Exception {
        return currentObject.getAttribute(key, tClass, false);
    }

    public <T> T getAttribute(String key, Class<T> tClass, boolean required) throws Exception {
        return currentObject.getAttribute(key, tClass, required);
    }

}
