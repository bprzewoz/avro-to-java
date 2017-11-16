package json.type.complex;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-15.
 */
public class JsonObject extends JsonValue {

    private LinkedList<JsonPair> members;

    public JsonObject(int row, int column) {
        super(row, column);
        this.members = new LinkedList<>();
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

    public void printTree(int depth) {
        super.printTree(depth, null);
        if (!members.isEmpty()) {
            for (int i = 0; i < members.size(); i++) {
                members.get(i).printTree(depth + 1);
            }
        }
    }

}