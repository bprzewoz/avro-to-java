package json.type.complex;

import exceptions.InvalidSchemaException;
import json.type.primitive.JsonString;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-15.
 */
public class JsonObject extends JsonValue {

    private LinkedList<JsonPair> members;
    private String value;

    public JsonObject(int row, int column) {
        super(row, column);
    }

    public JsonObject(int row, int column, LinkedList<JsonPair> members) {
        super(row, column);
        this.members = members;
    }

    public LinkedList<JsonPair> getMembers() {
        return members;
    }

    public void setMembers(LinkedList<JsonPair> members) {
        this.members = members;
    }

    public JsonPair getMember(String key) {
        for (JsonPair jsonPair : members) {
            if (jsonPair.getKey().equals(key)) {
                return jsonPair;
            }
        }
        return null;
    }

    public void printNode(int depth) {
        super.printNode(depth, null);
        for (JsonPair jsonPair : members) {
            jsonPair.printNode(depth + 1);
        }
    }

    public JsonString getNameAttribute() throws InvalidSchemaException {
        return getAttribute("name", JsonString.class, false);
    }

    public JsonString getAttribute(String key) throws InvalidSchemaException {
        return getAttribute(key, JsonString.class, false);
    }

    public <T> T getAttribute(String key, Class<T> tClass) throws InvalidSchemaException {
        return getAttribute(key, tClass, false);
    }

    public <T> T getAttribute(String key, Class<T> tClass, boolean required) throws InvalidSchemaException {
        JsonPair jsonPair = getMember(key);
        if (jsonPair != null) {
            JsonValue jsonValue = jsonPair.getValue();
            if (jsonValue.getClass().equals(tClass)) {
                T t = (T) jsonValue;
                return t;
            } else {
                if (required) {
                    throw new InvalidSchemaException(600, jsonValue, jsonValue.getClass().toString()); // WRONG TYPE
                } else {
                    return null;
                }
            }
        } else {
            if (required) {
                throw new InvalidSchemaException(700, this); // MISSING ATTRIBUTE
            } else {
                return null;
            }
        }
    }

    public void checkPrimitiveAtrributes() throws InvalidSchemaException {
        checkAttributes(new LinkedList<String>(Arrays.asList("type", "name", "default")));
    }

    public void checkAttributes(LinkedList<String> attributes) throws InvalidSchemaException {
        for (JsonPair jsonPair : getMembers()) {
            if (attributes.contains(jsonPair.getKey())) {
                attributes.remove(jsonPair.getKey());
            } else {
                throw new InvalidSchemaException(800, jsonPair, jsonPair.getKey());
            }
        }
    }

}