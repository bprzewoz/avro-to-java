package json.parser;

import exceptions.UnexpectedTokenException;
import files.FileHandler;
import json.lexer.JsonLexer;
import json.token.JsonToken;
import json.token.JsonTokenType;
import json.type.complex.JsonArray;
import json.type.complex.JsonObject;
import json.type.complex.JsonPair;
import json.type.complex.JsonValue;
import json.type.primitive.JsonLiteral;
import json.type.primitive.JsonNumber;
import json.type.primitive.JsonString;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-10-30.
 */
public class SmartParser {

    private JsonLexer jsonLexer;
    private JsonToken currentToken;

    public SmartParser(JsonLexer jsonLexer) {
        this.jsonLexer = jsonLexer;
        this.currentToken = jsonLexer.nextToken();
    }

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler("inputFile.txt", "outputFile.txt");
        JsonLexer jsonLexer = new JsonLexer(fileHandler);
        SmartParser smartParser = new SmartParser(jsonLexer);
        JsonObject jsonObject = smartParser.parseFile();
        jsonObject.printTree(0);
        fileHandler.closeFiles();
    }

    public JsonObject parseFile() {
        try {
            return parseObject();
        } catch (UnexpectedTokenException unexpectedTokenException) {
            System.out.println("[ROW " + currentToken.getRow() + ", COL " + currentToken.getColumn() + "] " + unexpectedTokenException.getMessage());
            return null;
        }
    }

    private JsonObject parseObject() throws UnexpectedTokenException {
        if (currentToken.getType() != JsonTokenType.LEFT_BRACE) {
            return null;
            //throw new UnexpectedTokenException(100, currentToken.getType());
        }

        int row = currentToken.getRow();
        int column = currentToken.getColumn();

        if ((currentToken = jsonLexer.nextToken()).getType() == JsonTokenType.RIGHT_BRACE) { // EMPTY OBJECT
            currentToken = jsonLexer.nextToken();
            return new JsonObject(row, column);
        }

        LinkedList<JsonPair> members = parseMembers();
        return new JsonObject(row, column, members);
    }

    private LinkedList<JsonPair> parseMembers() throws UnexpectedTokenException {
        LinkedList<JsonPair> members = new LinkedList<>();
        do {
            members.add(parsePair());

            if (currentToken.getType() == JsonTokenType.RIGHT_BRACE) {
                currentToken = jsonLexer.nextToken();
                return members;
            }
        }
        while (currentToken.getType() == JsonTokenType.COMMA && (currentToken = jsonLexer.nextToken()).getType() != null);

        throw new UnexpectedTokenException(200, currentToken.getType());
    }

    private JsonPair parsePair() throws UnexpectedTokenException {
        String key;
        if (currentToken.getType() == JsonTokenType.STRING) {
            key = currentToken.getValue();
        } else {
            throw new UnexpectedTokenException(300, currentToken.getType());
        }

        int row = currentToken.getRow();
        int column = currentToken.getColumn();

        if ((currentToken = jsonLexer.nextToken()).getType() != JsonTokenType.COLON) {
            throw new UnexpectedTokenException(400, currentToken.getType());
        }

        currentToken = jsonLexer.nextToken();
        JsonValue jsonValue = parseValue();
        return new JsonPair(row, column, key, jsonValue);
    }

    private JsonArray parseArray() throws UnexpectedTokenException {
        if (currentToken.getType() != JsonTokenType.LEFT_BRACKET) {
            return null;
            //throw new UnexpectedTokenException(100, currentToken.getType());
        }

        int row = currentToken.getRow();
        int column = currentToken.getColumn();

        if ((currentToken = jsonLexer.nextToken()).getType() == JsonTokenType.RIGHT_BRACKET) { // EMPTY ARRAY
            currentToken = jsonLexer.nextToken();
            return new JsonArray(row, column);
        }

        LinkedList<JsonValue> elements = parseElements();
        return new JsonArray(row, column, elements);
    }

    private LinkedList<JsonValue> parseElements() throws UnexpectedTokenException {
        LinkedList<JsonValue> elements = new LinkedList<>();
        do {
            elements.add(parseValue());
            if (currentToken.getType() == JsonTokenType.RIGHT_BRACKET) {
                currentToken = jsonLexer.nextToken();
                return elements;
            }
        }
        while (currentToken.getType() == JsonTokenType.COMMA && (currentToken = jsonLexer.nextToken()).getType() != null);

        throw new UnexpectedTokenException(500, currentToken.getType());
    }

    private JsonValue parseValue() throws UnexpectedTokenException {
        JsonValue jsonValue;
        if ((jsonValue = parseObject()) != null) {
            return jsonValue;
        } else if ((jsonValue = parseArray()) != null) {
            return jsonValue;
        } else if ((jsonValue = parseString()) != null) {
            return jsonValue;
        } else if ((jsonValue = parseNumber()) != null) {
            return jsonValue;
        } else if ((jsonValue = parseLiteral()) != null) {
            return jsonValue;
        } else {
            throw new UnexpectedTokenException(600, currentToken.getType());
        }
    }

    private JsonString parseString() {
        if (currentToken.getType() == JsonTokenType.STRING) {
            JsonString jsonString = new JsonString(currentToken.getRow(), currentToken.getColumn(), currentToken.getValue());
            currentToken = jsonLexer.nextToken();
            return jsonString;
        } else {
            return null;
        }
    }

    private JsonNumber parseNumber() {
        if (currentToken.getType() == JsonTokenType.NUMBER) {
            JsonNumber jsonNumber = new JsonNumber(currentToken.getRow(), currentToken.getColumn(), currentToken.getValue());
            currentToken = jsonLexer.nextToken();
            return jsonNumber;
        } else {
            return null;
        }
    }

    private JsonLiteral parseLiteral() {
        if (currentToken.getType() == JsonTokenType.NUMBER) {
            JsonLiteral jsonLiteral = new JsonLiteral(currentToken.getRow(), currentToken.getColumn(), currentToken.getValue());
            currentToken = jsonLexer.nextToken();
            return jsonLiteral;
        } else {
            return null;
        }
    }

}
