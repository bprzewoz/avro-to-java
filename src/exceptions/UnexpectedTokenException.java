package exceptions;

import lexer.JsonTokenType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by splbap on 2017-11-01.
 */
public class UnexpectedTokenException extends Exception {

    private String message;

    public UnexpectedTokenException(int errorCode, JsonTokenType jsonTokenType) {
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

        message += String.format("read token %s type, but expected %s type.", jsonTokenType, expectedTokens(jsonTokenType).toString());
    }

    public String getMessage() {
        return message;
    }

    private List<JsonTokenType> expectedTokens(JsonTokenType jsonTokenType) { // OCZEKIWANE TOKENY
        if (jsonTokenType == JsonTokenType.LEFT_BRACE) { // {
            return Arrays.asList(JsonTokenType.RIGHT_BRACE, JsonTokenType.STRING);
        } else if (jsonTokenType == JsonTokenType.RIGHT_BRACE) { // }
            return Arrays.asList(JsonTokenType.RIGHT_BRACE, JsonTokenType.RIGHT_BRACKET, JsonTokenType.COMMA);
        } else if (jsonTokenType == JsonTokenType.LEFT_BRACKET) { // [
            return Arrays.asList(JsonTokenType.LEFT_BRACE, JsonTokenType.LEFT_BRACKET, JsonTokenType.STRING, JsonTokenType.NUMBER, JsonTokenType.LITERAL);
        } else if (jsonTokenType == JsonTokenType.RIGHT_BRACKET) { // ]
            return Arrays.asList(JsonTokenType.RIGHT_BRACE, JsonTokenType.RIGHT_BRACKET, JsonTokenType.COMMA);
        } else if (jsonTokenType == JsonTokenType.COMMA) { // ,
            return Arrays.asList(JsonTokenType.LEFT_BRACE, JsonTokenType.LEFT_BRACKET, JsonTokenType.STRING, JsonTokenType.NUMBER, JsonTokenType.LITERAL);
        } else if (jsonTokenType == JsonTokenType.COLON) { // :
            return Arrays.asList(JsonTokenType.LEFT_BRACE, JsonTokenType.LEFT_BRACKET, JsonTokenType.STRING, JsonTokenType.NUMBER, JsonTokenType.LITERAL);
        } else if (jsonTokenType == JsonTokenType.STRING) {
            return Arrays.asList(JsonTokenType.RIGHT_BRACE, JsonTokenType.RIGHT_BRACKET, JsonTokenType.COLON, JsonTokenType.COMMA);
        } else if (jsonTokenType == JsonTokenType.NUMBER) {
            return Arrays.asList(JsonTokenType.RIGHT_BRACE, JsonTokenType.RIGHT_BRACKET, JsonTokenType.COLON);
        } else if (jsonTokenType == JsonTokenType.LITERAL) {
            return Arrays.asList(JsonTokenType.RIGHT_BRACE, JsonTokenType.RIGHT_BRACKET, JsonTokenType.COLON);
        } else {
            return null;
        }
    }

}
