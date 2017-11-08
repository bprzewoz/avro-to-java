package parsing;

import exceptions.UnexpectedTokenException;
import files.FileHandler;
import lexing.Lexer;
import lexing.Token;
import lexing.TokenType;

import javax.xml.soap.Node;

/**
 * Created by splbap on 2017-10-30.
 */
public class Parser {

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler("inputFile.txt", "outputFile.txt");
        Lexer lexer = new Lexer(fileHandler);
        Parser parser = new Parser(lexer);
        JsonNode jsonNode = parser.parseFile();
        jsonNode.printTree(0);
        fileHandler.closeFiles();
    }

    private Lexer lexer;
    // private JsonNode treeRoot;
    private Token currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.nextToken();
    }

    public JsonNode parseFile() {
        try {
            return parseObject();
        } catch (UnexpectedTokenException unexpectedTokenException) {
            System.out.println("[ROW " + currentToken.getRow() + ", COL " + currentToken.getColumn() + "] " + unexpectedTokenException.getMessage());
            return new JsonNode("ERROR", NodeType.ERROR);
        }
    }

    private JsonNode parseObject() throws UnexpectedTokenException {
        JsonNode jsonObject = new JsonNode(NodeType.OBJECT);
        if (currentToken.getTokenType() != TokenType.LEFT_BRACE) {
            throw new UnexpectedTokenException(100, currentToken.getTokenType());
        }

        if ((currentToken = lexer.nextToken()).getTokenType() == TokenType.RIGHT_BRACE) { // EMPTY OBJECT
            return null;
        }

        do { // PARSE MEMBERS
            JsonNode jsonMember = null;
            if (currentToken.getTokenType() == TokenType.STRING) {
                jsonMember = new JsonNode(currentToken.getTokenValue(), NodeType.PAIR); // PARSE PAIR
            } else {
                throw new UnexpectedTokenException(100, currentToken.getTokenType());
            }

            if ((currentToken = lexer.nextToken()).getTokenType() != TokenType.COLON) {
                throw new UnexpectedTokenException(100, currentToken.getTokenType());
            }

            currentToken = lexer.nextToken();
            JsonNode jsonValue = parseValue();

            if(jsonValue.getNodeType() == NodeType.OBJECT || jsonValue.getNodeType() == NodeType.ARRAY){
                jsonValue.setName(jsonMember.getName());
                jsonObject.addMember(jsonValue);
            } else {
                jsonMember.addMember(jsonValue);
                jsonObject.addMember(jsonMember);
            }

            if (currentToken.getTokenType() == TokenType.RIGHT_BRACE) {
                currentToken = lexer.nextToken();
                return jsonObject;
            }
        }
        while (currentToken.getTokenType() == TokenType.COMMA && (currentToken = lexer.nextToken()).getTokenType() != TokenType.ERROR); // DRUGI ARGUMENT?

        throw new UnexpectedTokenException(100, currentToken.getTokenType());
    }

//    private void parseMembers(JsonNode jsonNode) throws UnexpectedTokenException {
//
//    }

//    private void parsePair() {
//
//    }

    private JsonNode parseArray() throws UnexpectedTokenException {
        JsonNode jsonArray = new JsonNode(NodeType.ARRAY);
        if (currentToken.getTokenType() != TokenType.LEFT_BRACKET) {
            throw new UnexpectedTokenException(100, currentToken.getTokenType());
        }

        if ((currentToken = lexer.nextToken()).getTokenType() == TokenType.RIGHT_BRACKET) { // EMPTY ARRAY
            return null;
        }

        do { // PARSE ELEMENTS
            jsonArray.addMember(parseValue());
            if (currentToken.getTokenType() == TokenType.RIGHT_BRACKET) {
                currentToken = lexer.nextToken();
                return jsonArray;
            }
        }
        while (currentToken.getTokenType() == TokenType.COMMA && (currentToken = lexer.nextToken()).getTokenType() != TokenType.ERROR);

        throw new UnexpectedTokenException(100, currentToken.getTokenType());
    }

    private void parseElements(JsonNode jsonNode) {

    }

    private JsonNode parseValue() throws UnexpectedTokenException {
        switch (currentToken.getTokenType()) {
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
                throw new UnexpectedTokenException(100, currentToken.getTokenType());
        }
    }

    private JsonNode parseString() {
        JsonNode jsonString = new JsonNode(currentToken.getTokenValue(), NodeType.STRING);
        currentToken = lexer.nextToken();
        return jsonString;
    }

    private JsonNode parseNumber() {
        JsonNode jsonNumber = new JsonNode(currentToken.getTokenValue(), NodeType.NUMBER);
        currentToken = lexer.nextToken();
        return jsonNumber;
    }

    private JsonNode parseLiteral() {
        JsonNode jsonLiteral = new JsonNode(currentToken.getTokenValue(), NodeType.LITERAL);
        currentToken = lexer.nextToken();
        return jsonLiteral;
    }

}
