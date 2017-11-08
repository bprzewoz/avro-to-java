package parsing;

import exceptions.UnexpectedTokenException;
import files.FileHandler;
import lexing.Lexer;
import lexing.Token;
import lexing.TokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by splbap on 2017-10-30.
 */
public class OldParser {

    private Lexer lexer;
    // private JsonNode treeRoot;
    private Token currentToken;
    private Token previousToken;
    private JsonNode currentJsonNode;
    private ArrayList<NodeType> states;

    public OldParser(Lexer lexer) {
        this.lexer = lexer;
        this.previousToken = null;
        this.states = new ArrayList<>();
        this.currentToken = lexer.nextToken();
    }

    public JsonNode nextNode() {
        try {
            validateToken(currentToken);
            System.out.println(String.format("CURRENT TOKEN: %s", currentToken.getTokenValue()));
        } catch (UnexpectedTokenException unexpectedTokenException) {
            System.out.println("[ROW " + currentToken.getRow() + ", COL " + currentToken.getColumn() + "] " + unexpectedTokenException.getMessage());
        }

        changeState(currentToken);

        if (currentToken.getTokenType() == TokenType.EOF) {
            return new JsonNode(NodeType.EOF);
        } else {
            previousToken = currentToken;
            currentToken = lexer.nextToken();
            return new JsonNode(NodeType.PAIR);
        }
    }

    private boolean validateToken(Token token) throws UnexpectedTokenException { // WALIDACJA TOKENU
        if (token.getTokenType() == TokenType.LEFT_BRACE) { // {
            if (previousToken == null) {
                return true;
            } else if (expectedTokens(previousToken).contains(token.getTokenType())) {
                return true;
            } else {
                return false;
                //throw new UnexpectedTokenException();
            }
        } else if (token.getTokenType() == TokenType.RIGHT_BRACE) { // }
            if (states.get(states.size() - 1) != NodeType.OBJECT) {
//                throw new UnexpectedTokenException(200, token.getTokenType());
                return false;
            } else if (expectedTokens(previousToken).contains(token.getTokenType())) {
                return true;
            } else {
                return false;
            }
//        } else if (token.getTokenType() == TokenType.LEFT_BRACKET) { // [
//            if (expectedTokens(previousToken).contains(token.getTokenType())) {
//                return true;
//            } else {
//                return false;
//            }
        } else if (token.getTokenType() == TokenType.RIGHT_BRACKET) { // ]
            if (states.get(states.size() - 1) != NodeType.ARRAY) {
                return false;
            } else {
                if (expectedTokens(previousToken).contains(token.getTokenType())) {
                    return true;
                } else {
                    return false;
                }
            }
        } else { // COMMA, COLON, STRING, NUMBER, LITERAL
            if (expectedTokens(previousToken).contains(token.getTokenType())) {
                return true;
            } else {
                return false;
            }
        }
    }

    private List<TokenType> expectedTokens(Token token) { // OCZEKIWANE TOKENY
        if (token.getTokenType() == TokenType.LEFT_BRACE) { // {
            return Arrays.asList(TokenType.RIGHT_BRACE, TokenType.STRING);
        } else if (token.getTokenType() == TokenType.RIGHT_BRACE) { // }
            return Arrays.asList(TokenType.RIGHT_BRACE, TokenType.RIGHT_BRACKET, TokenType.COMMA);
        } else if (token.getTokenType() == TokenType.LEFT_BRACKET) { // [
            return Arrays.asList(TokenType.LEFT_BRACE, TokenType.LEFT_BRACKET, TokenType.STRING, TokenType.NUMBER, TokenType.LITERAL);
        } else if (token.getTokenType() == TokenType.RIGHT_BRACKET) { // ]
            return Arrays.asList(TokenType.RIGHT_BRACE, TokenType.RIGHT_BRACKET, TokenType.COMMA);
        } else if (token.getTokenType() == TokenType.COMMA) { // ,
            return Arrays.asList(TokenType.LEFT_BRACE, TokenType.LEFT_BRACKET, TokenType.STRING, TokenType.NUMBER, TokenType.LITERAL);
        } else if (token.getTokenType() == TokenType.COLON) { // :
            return Arrays.asList(TokenType.LEFT_BRACE, TokenType.LEFT_BRACKET, TokenType.STRING, TokenType.NUMBER, TokenType.LITERAL);
        } else if (token.getTokenType() == TokenType.STRING) {
            return Arrays.asList(TokenType.RIGHT_BRACE, TokenType.RIGHT_BRACKET, TokenType.COLON, TokenType.COMMA);
        } else if (token.getTokenType() == TokenType.NUMBER) {
            return Arrays.asList(TokenType.RIGHT_BRACE, TokenType.RIGHT_BRACKET, TokenType.COLON);
        } else if (token.getTokenType() == TokenType.LITERAL) {
            return Arrays.asList(TokenType.RIGHT_BRACE, TokenType.RIGHT_BRACKET, TokenType.COLON);
        } else {
            return null;
        }
    }

    private void changeState(Token token) { // ZMIANA STANU
        if (token.getTokenType() == TokenType.LEFT_BRACE) { // {
            if (!states.isEmpty() && states.get(states.size() - 1) == NodeType.PAIR) {
                states.remove(states.size() - 1);
            }
            states.add(NodeType.OBJECT);
        } else if (token.getTokenType() == TokenType.RIGHT_BRACE) { // }
            states.remove(states.size() - 1);
        } else if (token.getTokenType() == TokenType.LEFT_BRACKET) { // [
            if (!states.isEmpty() && states.get(states.size() - 1) == NodeType.PAIR) {
                states.remove(states.size() - 1);
            }
            states.add(NodeType.ARRAY);
        } else if (token.getTokenType() == TokenType.RIGHT_BRACKET) { // ]
            states.remove(states.size() - 1);
        } else if (token.getTokenType() == TokenType.COLON) { // :
            states.add(NodeType.PAIR);
        }
    }

}
