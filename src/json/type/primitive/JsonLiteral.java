package json.type.primitive;

/**
 * Created by splbap on 2017-11-15.
 */
public class JsonLiteral extends JsonPrimitive {

    private boolean literal;

    public JsonLiteral(int row, int column, String value) {
        super(row, column, value);
        literal = Boolean.parseBoolean(value);
    }

    public boolean getLiteral() {
        return literal;
    }

    public void setLiteral(boolean literal) {
        this.literal = literal;
    }

}