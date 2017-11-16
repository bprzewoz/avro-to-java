package json.type.primitive;

import json.type.complex.JsonValue;

/**
 * Created by splbap on 2017-11-16.
 */
public class JsonPrimitive extends JsonValue {

    private String value;

    public JsonPrimitive(int row, int column, String value) {
        super(row, column);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void printNode(int depth) {
        super.printNode(depth, value);
    }

}
