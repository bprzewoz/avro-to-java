package exceptions;

import json.token.JsonTokenType;

/**
 * Created by splbap on 2017-10-17.
 */

public class InvalidTokenException extends Exception {

    private String message;

    public InvalidTokenException(int errorCode, char jsonToken) {
        this(errorCode, String.valueOf(jsonToken), null);
    }

    public InvalidTokenException(int errorCode, String jsonToken, JsonTokenType jsonTokenType) {
        message = String.format("(INVALID TOKEN EXCEPTION %d) ", errorCode);
        switch (errorCode) {
            case 100:
                message += "Illegal octal literal: ";
                break;
            case 200:
                message += "Illegal empty exponent: ";
                break;
            case 300:
                message += "Illegal trailing decimal: ";
                break;
            case 400:
                message += "A negative sign may only precede numbers: ";
                break;
            case 500:
                message += "Unterminated string: ";
                break;
            case 600:
                message += "Invalid escape sequence: ";
                break;
            case 700:
                message += "Invalid Unicode escape sequence: ";
                break;
            case 800:
                message += "Unescaped ASCII control characters are not permitted: ";
                break;
            case 900:
                message += "Unrecognized token: ";
                break;
        }

        if (jsonTokenType == null) {
            message += String.format("read expression \"%s\", but expected VALID type.", jsonToken);
        } else {
            message += String.format("read expression \"%s\", but expected %s type.", jsonToken, jsonTokenType.toString());
        }
    }

    public String getMessage() {
        return message;
    }

}