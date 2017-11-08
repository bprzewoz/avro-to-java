package exceptions;

import lexing.Token;
import lexing.TokenType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by splbap on 2017-11-01.
 */
public class UnexpectedTokenException extends Exception {

    private String message;

    public UnexpectedTokenException(int errorCode, TokenType tokenType) {
        message = String.format("(UNEXPECTED TOKEN EXCEPTION %d) ", errorCode);
        switch (errorCode) {
            case 100:
                message += "Get wrong token: ";
                break;
            case 200:
                message += "";
                break;
            default:
                message += "Get wrong token: ";
                break;
        }

        message += String.format("read token %s type, but expected %s type.", tokenType, expectedTokens(tokenType).toString());
    }

    public String getMessage() {
        return message;
    }

    private List<TokenType> expectedTokens(TokenType tokenType) { // OCZEKIWANE TOKENY
        if (tokenType == TokenType.LEFT_BRACE) { // {
            return Arrays.asList(TokenType.RIGHT_BRACE, TokenType.STRING);
        } else if (tokenType == TokenType.RIGHT_BRACE) { // }
            return Arrays.asList(TokenType.RIGHT_BRACE, TokenType.RIGHT_BRACKET, TokenType.COMMA);
        } else if (tokenType == TokenType.LEFT_BRACKET) { // [
            return Arrays.asList(TokenType.LEFT_BRACE, TokenType.LEFT_BRACKET, TokenType.STRING, TokenType.NUMBER, TokenType.LITERAL);
        } else if (tokenType == TokenType.RIGHT_BRACKET) { // ]
            return Arrays.asList(TokenType.RIGHT_BRACE, TokenType.RIGHT_BRACKET, TokenType.COMMA);
        } else if (tokenType == TokenType.COMMA) { // ,
            return Arrays.asList(TokenType.LEFT_BRACE, TokenType.LEFT_BRACKET, TokenType.STRING, TokenType.NUMBER, TokenType.LITERAL);
        } else if (tokenType == TokenType.COLON) { // :
            return Arrays.asList(TokenType.LEFT_BRACE, TokenType.LEFT_BRACKET, TokenType.STRING, TokenType.NUMBER, TokenType.LITERAL);
        } else if (tokenType == TokenType.STRING) {
            return Arrays.asList(TokenType.RIGHT_BRACE, TokenType.RIGHT_BRACKET, TokenType.COLON, TokenType.COMMA);
        } else if (tokenType == TokenType.NUMBER) {
            return Arrays.asList(TokenType.RIGHT_BRACE, TokenType.RIGHT_BRACKET, TokenType.COLON);
        } else if (tokenType == TokenType.LITERAL) {
            return Arrays.asList(TokenType.RIGHT_BRACE, TokenType.RIGHT_BRACKET, TokenType.COLON);
        } else {
            return null;
        }
    }

}
