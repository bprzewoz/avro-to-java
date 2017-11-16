package exceptions;

/**
 * Created by splbap on 2017-11-01.
 */
public class InvalidFileException extends Exception {

    private String message;

    public InvalidFileException(int errorCode, String fileName) {
        message = String.format("(INVALID FILE EXCEPTION %d) ", errorCode);
        switch (errorCode) {
            case 100:
                message += String.format("Opening file exception: trying to open a \"%s\" file,", fileName);
                break;
            case 200:
                message += String.format("Reading file exception: trying to read a \"%s\" file,", fileName);
                break;
            case 300:
                message += String.format("Closing file exception: trying to close a \"%s\" file,", fileName);
                break;
        }

        message += String.format(" but getting error instead.");
    }

    public String getMessage() {
        return message;
    }

}
