package lexing;

/**
 * Created by splbap on 2017-10-11.
 */
public class Token {

    private int row;
    private int column;
    private int hashValue;
    private String tokenValue;
    private TokenType tokenType;

    public Token(int row, int column, char tokenValue, TokenType tokenType) {
        this(row, column, String.valueOf(tokenValue), tokenType);
    }

    public Token(int row, int column, String tokenValue, TokenType tokenType) {
        this.tokenValue = tokenValue;
        this.tokenType = tokenType;
        this.column = column;
        this.row = row;

        if (tokenType == TokenType.STRING) {
            hashValue = calculateHash(tokenValue);
        }
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public int getHashValue(){
        return hashValue;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    private int calculateHash(String string) {
        int hash = 7;
        for (int i = 0; i < string.length(); i++) {
            hash = hash * 31 + string.charAt(i);
        }
        return hash;
    }
}