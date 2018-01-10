package exceptions;

import json.type.JsonNode;

/**
 * Created by splbap on 2017-11-19.
 */
public class InvalidSchemaException extends Exception {

    private String message;

    public InvalidSchemaException(int errorCode, JsonNode jsonNode) {
        this(errorCode, jsonNode, null);
    }

    public InvalidSchemaException(int errorCode, JsonNode jsonNode, String type) {
        message = String.format("[ROW %d, COL %d] (INVALID SCHEMA EXCEPTION %d) ", jsonNode.getRow(), jsonNode.getColumn(), errorCode);
        switch (errorCode) {
            case 100:
                message += String.format("Wrong type of records field: %s", type);
                break;
            case 200:
                message += String.format("Missing type of records field.");
                break;
            case 300:
                message += String.format("Duplicate enum values: %s", type);
                break;
            case 400:
                message += String.format("Wrong type of enums value: %s", type);
                break;
            case 500:
                message += String.format("Missing type of arrays items.");
                break;
            case 600:
                message += String.format("Wrong type of attribute in object.");
                break;
            case 700:
                message += String.format("Missing attribute in object.");
                break;
            case 800:
                message += String.format("Invalid or duplicate attribute.");
                break;
        }
    }

    public String getMessage() {
        return message;
    }

}
