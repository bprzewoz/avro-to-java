package json.type;

/**
 * Created by splbap on 2017-11-16.
 */
public class JsonNode {

    private int row;
    private int column;

    public JsonNode(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
