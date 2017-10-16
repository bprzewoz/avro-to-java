/**
 * Created by splbap on 2017-10-11.
 */
public class Token {

    private String value;
    private TokenType tokenType;

    public Token(char value, TokenType tokenType){
        this.value = String.valueOf(value);
        this.tokenType = tokenType;
    }

    public Token(String value, TokenType tokenType){
        this.value = value;
        this.tokenType = tokenType;
    }

    public String getValue(){
        return value;
    }

    public TokenType getTokenType(){
        return tokenType;
    }

}