import org.omg.CORBA.DynAnyPackage.Invalid;
import exceptions.InvalidTokenException;

import java.io.EOFException;

/**
 * Created by splbap on 2017-10-11.
 */
public class Lexer {

    // private Token nextToken; // Logowanie listy tokenow do pliku kontrolonego
    private boolean endOfFile;
    private FileHandler fileHandler;

    public Lexer(FileHandler fileHandler) {
        this.endOfFile = false;
        this.fileHandler = fileHandler;
    }

    private char nextChar() {
        int i = fileHandler.nextChar();
        endOfFile = (i == -1);
        char c = (char) i;
        return c;
    }

    public Token nextToken() {
        char c = nextChar();
        while (isSpace(c)) {
            c = nextChar();
        }

        if (endOfFile) {
            return new Token("EOF", TokenType.EOF);
        }

        try {
            if ('{' == c) {
                return new Token(c, TokenType.LEFT_BRACKET);
            } else if ('}' == c) {
                return new Token(c, TokenType.RIGHT_BRACE);
            } else if ('[' == c) {
                return new Token(c, TokenType.LEFT_BRACE);
            } else if (']' == c) {
                return new Token(c, TokenType.RIGHT_BRACE);
            } else if (',' == c) {
                return new Token(c, TokenType.COMMA);
            } else if (':' == c) {
                return new Token(c, TokenType.COLON);
            } else if (';' == c) {
                return new Token(c, TokenType.SEMICOLON);
            } else if ('"' == c) {
                return new Token(readString(), TokenType.STRING);
            } else if (isDigit(c) || '-' == c) {
                return new Token(readNumber(c), TokenType.NUMBER);
            } else if (isLetter(c)) {
                return new Token(readLiteral(c), TokenType.LITERAL);
            } else {
                throw new InvalidTokenException(c);
            }
        } catch (InvalidTokenException e) {
            return null;
        }
    }

    private boolean isSpace(char c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private String readString() throws InvalidTokenException {
        char c;
        String string = "";
        while ((c = nextChar()) != '"') {
            if (endOfFile) {
                throw new InvalidTokenException();
            }
            string += c;
        }
        return string;
    }

    private String readNumber(char c) throws InvalidTokenException {
        String string = String.valueOf(c);
        if (c == '0') {
            fileHandler.mark();
            if ((c = nextChar()) == '.') {
                string += c;
                string += readDigit();
                return string;
            } else if (!isDigit(c)) {
                fileHandler.reset();
                return string;
            } else {
                string += c;
                throw new InvalidTokenException(string, TokenType.NUMBER.toString());
            }
        } else {
            string += readDigit();
            fileHandler.mark();
            if ((c = nextChar()) == '.' && isDigit(c = nextChar())) {
                string += Character.toString('.') + Character.toString(c);
                string += readDigit();
            } else {
                fileHandler.reset();
            }
            return string;
        }
    }

    private String readDigit() {
        String string = "";
        char c = nextChar();
        while (isDigit(c)) {
            string += c;
            fileHandler.mark();
            c = nextChar();
        }
        fileHandler.reset();
        return string;
    }

    private String readLiteral(char c) throws InvalidTokenException {
        String string = "";
        while (isLetter(c)) {
            string += c;
            fileHandler.mark();
            c = nextChar();
        }
        fileHandler.reset();
        string = string.toLowerCase();

        if (string == "true" || string == "false" || string == "null") {
            return string;
        } else {
            throw new InvalidTokenException(string, TokenType.LITERAL.toString());
        }
    }

}