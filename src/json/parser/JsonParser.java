package json.parser;

import exceptions.UnexpectedTokenException;
import files.FileHandler;
import json.lexer.JsonLexer;
import json.token.JsonToken;
import json.token.JsonTokenType;

/**
 * Created by splbap on 2017-10-30.
 */
public class JsonParser {

    private JsonLexer jsonLexer;
    private JsonToken currentJsonToken;

    public JsonParser(JsonLexer jsonLexer) {
        this.jsonLexer = jsonLexer;
        this.currentJsonToken = jsonLexer.nextToken();
    }

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler("inputFile.txt", "outputFile.txt");
        JsonLexer jsonLexer = new JsonLexer(fileHandler);
        JsonParser jsonParser = new JsonParser(jsonLexer);
        JsonNode jsonNode = jsonParser.parseFile();
        jsonNode.printTree(0);
        fileHandler.closeFiles();
    }

    public JsonNode parseFile() {
        try {
            return parseObject();
        } catch (UnexpectedTokenException unexpectedTokenException) {
            System.out.println("[ROW " + currentJsonToken.getRow() + ", COL " + currentJsonToken.getColumn() + "] " + unexpectedTokenException.getMessage());
            return new JsonNode("ERROR", JsonNodeType.ERROR);
        }
    }

    private JsonNode parseObject() throws UnexpectedTokenException {
        JsonNode jsonObject = new JsonNode(JsonNodeType.OBJECT);
        if (currentJsonToken.getType() != JsonTokenType.LEFT_BRACE) {
            throw new UnexpectedTokenException(100, currentJsonToken.getType());
        }

        if ((currentJsonToken = jsonLexer.nextToken()).getType() == JsonTokenType.RIGHT_BRACE) { // EMPTY OBJECT
            currentJsonToken = jsonLexer.nextToken();
            return jsonObject;
        }

        return parseMembers(jsonObject);
    }

    private JsonNode parseMembers(JsonNode jsonObject) throws UnexpectedTokenException {
        do {
            parsePair(jsonObject);

            if (currentJsonToken.getType() == JsonTokenType.RIGHT_BRACE) {
                currentJsonToken = jsonLexer.nextToken();
                return jsonObject;
            }
        }
        while (currentJsonToken.getType() == JsonTokenType.COMMA && (currentJsonToken = jsonLexer.nextToken()).getType() != null);

        throw new UnexpectedTokenException(200, currentJsonToken.getType());
    }

    private void parsePair(JsonNode jsonObject) throws UnexpectedTokenException {
        JsonNode jsonMember = null;
        if (currentJsonToken.getType() == JsonTokenType.STRING) {
            jsonMember = new JsonNode(currentJsonToken.getValue(), JsonNodeType.PAIR);
        } else {
            throw new UnexpectedTokenException(300, currentJsonToken.getType());
        }

        if ((currentJsonToken = jsonLexer.nextToken()).getType() != JsonTokenType.COLON) {
            throw new UnexpectedTokenException(400, currentJsonToken.getType());
        }

        currentJsonToken = jsonLexer.nextToken();
        JsonNode jsonValue = parseValue();

        if (jsonValue.getJsonNodeType() == JsonNodeType.OBJECT || jsonValue.getJsonNodeType() == JsonNodeType.ARRAY) {
            jsonValue.setString(jsonMember.getString());
            jsonObject.addMember(jsonValue);
        } else {
            jsonMember.addMember(jsonValue);
            jsonObject.addMember(jsonMember);
        }

    }

    private JsonNode parseArray() throws UnexpectedTokenException {
        JsonNode jsonArray = new JsonNode(JsonNodeType.ARRAY);
        if (currentJsonToken.getType() != JsonTokenType.LEFT_BRACKET) {
            throw new UnexpectedTokenException(100, currentJsonToken.getType());
        }

        if ((currentJsonToken = jsonLexer.nextToken()).getType() == JsonTokenType.RIGHT_BRACKET) { // EMPTY ARRAY
            currentJsonToken = jsonLexer.nextToken();
            return jsonArray;
        }

        return parseElements(jsonArray);
    }

    private JsonNode parseElements(JsonNode jsonArray) throws UnexpectedTokenException {
        do {
            jsonArray.addMember(parseValue());
            if (currentJsonToken.getType() == JsonTokenType.RIGHT_BRACKET) {
                currentJsonToken = jsonLexer.nextToken();
                return jsonArray;
            }
        }
        while (currentJsonToken.getType() == JsonTokenType.COMMA && (currentJsonToken = jsonLexer.nextToken()).getType() != null);

        throw new UnexpectedTokenException(500, currentJsonToken.getType());
    }

    private JsonNode parseValue() throws UnexpectedTokenException {
        switch (currentJsonToken.getType()) {
            case LEFT_BRACE:
                return parseObject();
            case LEFT_BRACKET:
                return parseArray();
            case STRING:
                return parseString();
            case NUMBER:
                return parseNumber();
            case LITERAL:
                return parseLiteral();
            default:
                throw new UnexpectedTokenException(600, currentJsonToken.getType());
        }
    }

    private JsonNode parseString() {
        JsonNode jsonString = new JsonNode(currentJsonToken.getValue(), JsonNodeType.STRING);
        currentJsonToken = jsonLexer.nextToken();
        return jsonString;
    }

    private JsonNode parseNumber() {
        JsonNode jsonNumber = new JsonNode(currentJsonToken.getValue(), JsonNodeType.NUMBER);
        currentJsonToken = jsonLexer.nextToken();
        return jsonNumber;
    }

    private JsonNode parseLiteral() {
        JsonNode jsonLiteral = new JsonNode(currentJsonToken.getValue(), JsonNodeType.LITERAL);
        currentJsonToken = jsonLexer.nextToken();
        return jsonLiteral;
    }

}
