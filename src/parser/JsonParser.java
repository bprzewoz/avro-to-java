package parser;

import exceptions.UnexpectedTokenException;
import files.FileHandler;
import lexer.JsonLexer;
import lexer.JsonToken;
import lexer.JsonTokenType;

/**
 * Created by splbap on 2017-10-30.
 */
public class JsonParser {

    private JsonLexer jsonLexer;
    // private JsonNode treeRoot;
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
        if (currentJsonToken.getJsonTokenType() != JsonTokenType.LEFT_BRACE) {
            throw new UnexpectedTokenException(100, currentJsonToken.getJsonTokenType());
        }

        if ((currentJsonToken = jsonLexer.nextToken()).getJsonTokenType() == JsonTokenType.RIGHT_BRACE) { // EMPTY OBJECT
            return null;
        }

        return parseMembers(jsonObject);
    }

    private JsonNode parseMembers(JsonNode jsonObject) throws UnexpectedTokenException {
        do {
            parsePair(jsonObject);

            if (currentJsonToken.getJsonTokenType() == JsonTokenType.RIGHT_BRACE) {
                currentJsonToken = jsonLexer.nextToken();
                return jsonObject;
            }
        }
        while (currentJsonToken.getJsonTokenType() == JsonTokenType.COMMA && (currentJsonToken = jsonLexer.nextToken()).getJsonTokenType() != JsonTokenType.ERROR);

        throw new UnexpectedTokenException(100, currentJsonToken.getJsonTokenType());
    }

    private void parsePair(JsonNode jsonObject) throws UnexpectedTokenException {
        JsonNode jsonMember = null;
        if (currentJsonToken.getJsonTokenType() == JsonTokenType.STRING) {
            jsonMember = new JsonNode(currentJsonToken.getTokenValue(), JsonNodeType.PAIR);
        } else {
            throw new UnexpectedTokenException(100, currentJsonToken.getJsonTokenType());
        }

        if ((currentJsonToken = jsonLexer.nextToken()).getJsonTokenType() != JsonTokenType.COLON) {
            throw new UnexpectedTokenException(100, currentJsonToken.getJsonTokenType());
        }

        currentJsonToken = jsonLexer.nextToken();
        JsonNode jsonValue = parseValue();

        if (jsonValue.getJsonNodeType() == JsonNodeType.OBJECT || jsonValue.getJsonNodeType() == JsonNodeType.ARRAY) { // DO PRZEMYSLENIA
            jsonValue.setName(jsonMember.getName());
            jsonObject.addMember(jsonValue);
        } else {
            jsonMember.addMember(jsonValue);
            jsonObject.addMember(jsonMember);
        }
    }

    private JsonNode parseArray() throws UnexpectedTokenException {
        JsonNode jsonArray = new JsonNode(JsonNodeType.ARRAY);
        if (currentJsonToken.getJsonTokenType() != JsonTokenType.LEFT_BRACKET) {
            throw new UnexpectedTokenException(100, currentJsonToken.getJsonTokenType());
        }

        if ((currentJsonToken = jsonLexer.nextToken()).getJsonTokenType() == JsonTokenType.RIGHT_BRACKET) { // EMPTY ARRAY
            return null;
        }

        return parseElements(jsonArray);
    }

    private JsonNode parseElements(JsonNode jsonArray) throws UnexpectedTokenException {
        do {
            jsonArray.addMember(parseValue());
            if (currentJsonToken.getJsonTokenType() == JsonTokenType.RIGHT_BRACKET) {
                currentJsonToken = jsonLexer.nextToken();
                return jsonArray;
            }
        }
        while (currentJsonToken.getJsonTokenType() == JsonTokenType.COMMA && (currentJsonToken = jsonLexer.nextToken()).getJsonTokenType() != JsonTokenType.ERROR);

        throw new UnexpectedTokenException(100, currentJsonToken.getJsonTokenType());
    }

    private JsonNode parseValue() throws UnexpectedTokenException {
        switch (currentJsonToken.getJsonTokenType()) {
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
                throw new UnexpectedTokenException(100, currentJsonToken.getJsonTokenType());
        }
    }

    private JsonNode parseString() {
        JsonNode jsonString = new JsonNode(currentJsonToken.getTokenValue(), JsonNodeType.STRING);
        currentJsonToken = jsonLexer.nextToken();
        return jsonString;
    }

    private JsonNode parseNumber() {
        JsonNode jsonNumber = new JsonNode(currentJsonToken.getTokenValue(), JsonNodeType.NUMBER);
        currentJsonToken = jsonLexer.nextToken();
        return jsonNumber;
    }

    private JsonNode parseLiteral() {
        JsonNode jsonLiteral = new JsonNode(currentJsonToken.getTokenValue(), JsonNodeType.LITERAL);
        currentJsonToken = jsonLexer.nextToken();
        return jsonLiteral;
    }

}
