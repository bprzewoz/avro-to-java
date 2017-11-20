package exceptions;

import json.token.JsonToken;

/**
 * Created by splbap on 2017-11-01.
 */
public class UnexpectedTokenException extends Exception {

    private String message;

    public UnexpectedTokenException(int errorCode, JsonToken jsonToken) {
        message = String.format("[ROW %d, COL %d] (UNEXPECTED TOKEN EXCEPTION %d) ", jsonToken.getRow(), jsonToken.getColumn(), errorCode);
        switch (errorCode) {
            case 100:
                message += "Object parsing error: expected LEFT_BRACE '{' at start of object, ";
                break;
            case 200:
                message += "Members parsing error: expected COMMA ',' or RIGHT_BRACE '}' after VALUE in object, ";
                break;
            case 300:
                message += "Pair parsing error: expected STRING name or RIGHT_BRACE ‘}’ in object, ";
                break;
            case 400:
                message += "Pair parsing error: expected COLON ':' after STRING name in object, ";
                break;
            case 500:
                message += "Elements parsing error: expected COMMA ',' or RIGHT_BRACKET ']' after array element, ";
                break;
            case 600:
                message += "Value parsing error: expected LEFT_BRACE '{', LEFT_BRACKET '[', STRING, NUMBER or LITERAL after COLON ':' in object, ";
                break;
        }

        message += String.format("but read token %s type.", jsonToken.getType());
    }

    public String getMessage() {
        return message;
    }

}
