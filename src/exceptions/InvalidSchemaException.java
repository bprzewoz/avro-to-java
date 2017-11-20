package exceptions;

import avro.type.AvroNode;

/**
 * Created by splbap on 2017-11-19.
 */
public class InvalidSchemaException extends Exception {

    private String message;

    public InvalidSchemaException(int errorCode, AvroNode avroNode) {
        message = String.format("[ROW %d, COL %d] (INVALID SCHEMA EXCEPTION %d) ", avroNode.getRow(), avroNode.getColumn(), errorCode);
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
        }

        message += String.format("but read token type.");
    }

    public String getMessage() {
        return message;
    }

}
