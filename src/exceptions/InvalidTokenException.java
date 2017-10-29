package exceptions;

/**
 * Created by splbap on 2017-10-17.
 */

public class InvalidTokenException extends Exception {

    private String message = "InvalidTokenException: ";

    public InvalidTokenException() {
        message += "missing closing \" of string type.";
    }

    public InvalidTokenException(char token) {
        message += "get '" + token + "', but expected valid token.";
    }

    public InvalidTokenException(String token, String expectedType) {
        message += "get \"" + token + "\", but expected " + expectedType + " type.";
    }

    public void printMessage() {
        System.out.println(message);
    }

}