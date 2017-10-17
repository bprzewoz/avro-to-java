package exceptions;

/**
 * Created by splbap on 2017-10-17.
 */

public class InvalidTokenException extends Exception {

    public InvalidTokenException() {
        System.out.println("InvalidTokenException: missing closing \" of string type.");
        System.exit(-1);
    }

    public InvalidTokenException(char token) {
        System.out.println("InvalidTokenException: get '" + token + "', but expected valid token.");
        System.exit(-1);
    }

    public InvalidTokenException(String token, String expectedType) {
        System.out.println("InvalidTokenException: get \"" + token + "\", but expected " + expectedType + ".");
        System.exit(-1);
    }
}