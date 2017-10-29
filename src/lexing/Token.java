package lexing;

/**
 * Created by splbap on 2017-10-11.
 */
public class Token {

    private int row;
    private int column;
    private String tokenValue;
    private TokenType tokenType;

    public Token(int row, int column, TokenType tokenType) {
        this.row = row;
        this.column = column;
        this.tokenType = tokenType;
    }

    public Token(int row, int column, char tokenValue, TokenType tokenType) {
        this.tokenValue = String.valueOf(tokenValue);
        this.tokenType = tokenType;
        this.column = column;
        this.row = row;
    }

    public Token(int row, int column, String tokenValue, TokenType tokenType) {
        this.tokenValue = tokenValue;
        this.tokenType = tokenType;
        this.column = column;
        this.row = row;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

}