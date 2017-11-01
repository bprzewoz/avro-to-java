package exceptions;

/**
 * Created by splbap on 2017-11-01.
 */
public class UnexpectedTokenException {

    private String message;

    public UnexpectedTokenException(int errorCode, char tokenValue) {
        this(errorCode, String.valueOf(tokenValue), null);
    }

    public UnexpectedTokenException(int errorCode, String tokenValue, String tokenType) {
        message = String.format("(UNEXPECTED TOKEN EXCEPTION %d)", errorCode);
        switch (errorCode) {
            case 100:
                message += "";
                break;
            case 200:
                message += "";
                break;
            case 300:
                message += "";
                break;
            case 400:
                message += "";
                break;
            case 500:
                message += "";
                break;
            case 600:
                message += "";
                break;
            case 700:
                message += "";
                break;
            case 800:
                message += "";
                break;
            case 900:
                message += "";
                break;
        }

        if (tokenType == null) {
            message += String.format("read expression \"%s\", but expected %s type.", tokenValue, "types");
        } else {
            message += String.format("read expression \"%s\", but expected %s type.", tokenValue, tokenType);
        }
    }

    public String getMessage() {
        return message;
    }

}
