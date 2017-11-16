package exceptions;

import json.token.JsonTokenType;

/**
 * Created by splbap on 2017-11-01.
 */
public class UnexpectedTokenException extends Exception {

    private String message;

    public UnexpectedTokenException(int errorCode, JsonTokenType jsonTokenType) {
        message = String.format("(UNEXPECTED TOKEN EXCEPTION %d) ", errorCode);
        switch (errorCode) {
            case 100:
                message += "Object parsing error: expected LEFT_BRACE '{' at start of object, ";
                break;
            case 200:
                message += "Members parsing error: expected COMMA ',' or RIGHT_BRACE '}' after property primitive in object, ";
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
                message += "Value parsing error: expected LEFT_BRACE '{', LEFT_BRACKET '', STRING, NUMBER or LITERAL after COLON ':' in object, ";
                break;
        }

        message += String.format("but read token %s type.", jsonTokenType);
    }

    public String getMessage() {
        return message;
    }

}
