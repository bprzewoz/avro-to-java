package json.type.complex;

import json.type.primitive.JsonString;

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

    private JsonPair getMember(String key) {
        for (JsonPair jsonPair : members) {
            if (jsonPair.getKey().equals(key)) {
                return jsonPair;
            }
        }
        return null;
    }

    public <T> T getMemberValue(String key, Class<T> tClass) {
        JsonPair jsonPair = getMember(key);
        if (jsonPair != null) {
            JsonValue jsonValue = jsonPair.getValue();
            if (jsonValue.getClass().equals(tClass)) {
                T t = (T) jsonValue;
                return t;
            } else {
                // throw
                return null;
            }
        } else {
            // throw
            return null;
        }
    }

    public String getStringValue(String key) {
        JsonString jsonString = getMemberValue(key, JsonString.class);
        if (jsonString != null) {
            return value;
        } else {
            return null;
        }
    }

    public void printNode(int depth) {
        super.printNode(depth, null);
        if (members != null) {
            for (int i = 0; i < members.size(); i++) {
                members.get(i).printNode(depth + 1);
            }
        }
    }

}