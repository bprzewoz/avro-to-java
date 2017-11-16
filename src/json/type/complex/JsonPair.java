package json.type.complex;

import json.type.JsonNode;

/**
 * Created by splbap on 2017-11-15.
 */
public class JsonPair extends JsonNode {

    private String key;
    private JsonValue value;

    public JsonPair(int row, int column, String key, JsonValue value) {
        super(row, column);
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public JsonValue getValue() {
        return value;
    }

    public void setValue(JsonValue value) {
        this.value = value;
    }

    public void printTree(int depth) {
        super.printTree(depth, key);
        value.printTree(depth + 1);
    }

}
