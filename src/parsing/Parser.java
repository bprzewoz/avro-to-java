package parsing;

import lexing.Lexer;
import lexing.Token;
import lexing.TokenType;

import java.util.Arrays;
import java.util.Vector;

/**
 * Created by splbap on 2017-10-30.
 */
public class Parser {

    private Lexer lexer;
    private Node treeRoot;
    private Token lastToken;
    private Token currentToken;
    private Node currentNode;
    private Vector<NodeType> states;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.lastToken = null;
    }

    public Node nextNode() {
        Token token = lexer.nextToken();
        validateToken(token);


        return new Node();
    }

    private boolean validateToken(Token token) {
        if (token.getTokenType() == TokenType.LEFT_BRACE) { // LEFT_BRACE
            if (Arrays.asList(TokenType.COLON, TokenType.COMMA, TokenType.LEFT_BRACE).contains(lastToken.getTokenType())) {
                return true;
            } else {
                return false;
            }
            //    if (lastToken.row == -1 && lastToken.column == -1) {
            //      return true
        } else if (token.getTokenType() == TokenType.RIGHT_BRACE) { // RIGHT_BRACE
            if (states.firstElement() != NodeType.OBJECT) {
                return false;
            } else {
                if (Arrays.asList(TokenType.STRING, TokenType.NUMBER, TokenType.LITERAL, TokenType.RIGHT_BRACKET).contains(lastToken.getTokenType())) {
                    return true;
                } else {
                    return false;
                }
            }
        } else if (token.getTokenType() == TokenType.LEFT_BRACKET) { // LEFT_BRACKET
            if (lastToken.getTokenType() == TokenType.COLON) {
                return true;
            } else {
                //throw
                return false;
            }
        } else if (token.getTokenType() == TokenType.RIGHT_BRACKET) { // RIGHT_BRACKET
            if (states.lastElement() != NodeType.ARRAY) {
                return false;
            } else {
                if (Arrays.asList(TokenType.STRING, TokenType.NUMBER, TokenType.LITERAL, TokenType.RIGHT_BRACE).contains(lastToken.getTokenType())) {
                    return true;
                } else {
                    return false;
                }
            }
        } else if (token.getTokenType() == TokenType.COMMA) { // COMMA
            if (Arrays.asList(TokenType.STRING, TokenType.NUMBER, TokenType.LITERAL, TokenType.RIGHT_BRACE, TokenType.RIGHT_BRACKET).contains(lastToken.getTokenType())) {
                return true;
            } else {
                return false;
                //throw
            }
        } else if (token.getTokenType() == TokenType.COLON) { // COLON
            if (lastToken.getTokenType() == TokenType.STRING) {
                return true;
            } else {
                return false;
                //throw
            }
        } else if (token.getTokenType() == TokenType.SEMICOLON) { // SEMICOLON
            if (lastToken.getTokenType() == TokenType.RIGHT_BRACE) {
                return true;
            } else {
                return false;
                // throw
            }
        } else if (token.getTokenType() == TokenType.STRING) { // STRING
            if (Arrays.asList(TokenType.LEFT_BRACE, TokenType.LEFT_BRACKET, TokenType.COLON, TokenType.COMMA).contains(lastToken.getTokenType())) {
                return true;
            } else {
                return false;
            }
        } else if (token.getTokenType() == TokenType.NUMBER) { // NUMBER
            if (Arrays.asList(TokenType.COLON, TokenType.COMMA, TokenType.RIGHT_BRACKET).contains(lastToken.getTokenType())) {
                return true;
            } else {
                return false;
            }
        } else if (token.getTokenType() == TokenType.LITERAL) { // LITERAL
            if (Arrays.asList(TokenType.COLON, TokenType.COMMA, TokenType.RIGHT_BRACKET).contains(lastToken.getTokenType())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void analyzeToken(Token token) {

    }
}
