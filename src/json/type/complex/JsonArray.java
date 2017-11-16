package json.type.complex;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-15.
 */
public class JsonArray extends JsonValue {

    private LinkedList<JsonValue> elements;

    public JsonArray(int row, int column) {
        super(row, column);
        this.elements = new LinkedList<>();
    }

    public JsonArray(int row, int column, LinkedList<JsonValue> elements) {
        super(row, column);
        this.elements = elements;
    }

    public LinkedList<JsonValue> getElements() {
        return elements;
    }

    public void setElements(LinkedList<JsonValue> elements) {
        this.elements = elements;
    }

    public void printNode(int depth) {
        super.printNode(depth, null);
        if (!elements.isEmpty()) {
            for (int i = 0; i < elements.size(); i++) {
                elements.get(i).printNode(depth + 1);
            }
        }
    }

}