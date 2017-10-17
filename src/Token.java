/**
 * Created by splbap on 2017-10-11.
 */
public class Token {

    private String tokenValue;
    private TokenType tokenType;

    public Token(char tokenValue, TokenType tokenType) {
        this.tokenValue = String.valueOf(tokenValue);
        this.tokenType = tokenType;
    }

    public Token(String tokenValue, TokenType tokenType) {
        this.tokenValue = tokenValue;
        this.tokenType = tokenType;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

}