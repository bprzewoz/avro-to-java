package json.type.complex;

import json.type.JsonNode;

/**
 * Created by splbap on 2017-11-15.
 */
public abstract class JsonValue extends JsonNode {

    public JsonValue(int row, int column){
        super(row, column);
    }

    public abstract void printTree(int depth);

}
