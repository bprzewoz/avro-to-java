package lexer;

/**
 * Created by splbap on 2017-10-11.
 */
public enum JsonTokenType {
    LEFT_BRACE,
    RIGHT_BRACE,
    LEFT_BRACKET,
    RIGHT_BRACKET,
    COMMA,
    COLON,
    STRING,
    NUMBER,
    LITERAL,
    ERROR,
    EOF
}