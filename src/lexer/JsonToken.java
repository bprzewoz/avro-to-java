package lexer;

/**
 * Created by splbap on 2017-10-11.
 */
public class JsonToken {

    private int row;
    private int column;
    private int hashValue;
    private String tokenValue;
    private JsonTokenType jsonTokenType;

    public JsonToken(int row, int column, char tokenValue, JsonTokenType jsonTokenType) {
        this(row, column, String.valueOf(tokenValue), jsonTokenType);
    }

    public JsonToken(int row, int column, String tokenValue, JsonTokenType jsonTokenType) {
        this.tokenValue = tokenValue;
        this.jsonTokenType = jsonTokenType;
        this.column = column;
        this.row = row;

        if (jsonTokenType == JsonTokenType.STRING) {
            hashValue = calculateHash(tokenValue);
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getHashValue() {
        return hashValue;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public JsonTokenType getJsonTokenType() {
        return jsonTokenType;
    }

    private int calculateHash(String string) {
        int hash = 7;
        for (int i = 0; i < string.length(); i++) {
            hash = hash * 31 + string.charAt(i);
        }
        return hash;
    }
}