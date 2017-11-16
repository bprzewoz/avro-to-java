package json.type.primitive;

/**
 * Created by splbap on 2017-11-15.
 */
public class JsonString extends JsonPrimitive {

    private int hash;

    public JsonString(int row, int column, String value) {
        super(row, column, value);
        hash = calculateHash(value);
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    private int calculateHash(String string) {
        int hash = 7;
        for (int i = 0; i < string.length(); i++) {
            hash = hash * 31 + string.charAt(i);
        }
        return hash;
    }

}
