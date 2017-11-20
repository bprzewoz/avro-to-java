package json.type.complex;

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

    public <T> T getAttribute(String key, Class<T> tClass, boolean required) throws Exception {
        JsonPair jsonPair = getMember(key);
        if (jsonPair != null) {
            JsonValue jsonValue = jsonPair.getValue();
            if (jsonValue.getClass().equals(tClass)) {
                T t = (T) jsonValue;
                return t;
            } else {
                if (required) {
                    throw new Exception(); // WRONG TYPE
                } else {
                    return null;
                }
            }
        } else {
            if (required) {
                throw new Exception(); // MISSING ATTRIBUTE
            } else {
                return null;
            }
        }
    }

}