package json.token;

/**
 * Created by splbap on 2017-10-11.
 */
public class JsonToken {

    private int row;
    private int column;
    private String value;
    private JsonTokenType type;

    public JsonToken(int row, int column, char value, JsonTokenType type) {
        this(row, column, String.valueOf(value), type);
    }

    public JsonToken(int row, int column, String value, JsonTokenType type) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.type = type;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public JsonTokenType getType() {
        return type;
    }

    public void setType(JsonTokenType type) {
        this.type = type;
    }

}