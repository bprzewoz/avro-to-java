package json.type.primitive;

import json.type.complex.JsonValue;

/**
 * Created by splbap on 2017-11-16.
 */
public class JsonPrimitive extends JsonValue {

    private String value;

    public JsonPrimitive(int row, int column, String value){
        super(row, column);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void printTree(int depth) {
        String tab = new String(new char[depth]).replace("\0", "\t");
        System.out.println(String.format("%s%s - %s", tab, value, this.getClass().getSimpleName()));
    }

}
