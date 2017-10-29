package lexing;

import exceptions.InvalidTokenException;
import files.FileHandler;

/**
 * Created by splbap on 2017-10-11.
 */
public class Lexer {

    private int row;
    private int column;
    private char currentChar;
    private boolean endOfFile;
    private FileHandler fileHandler;

    public Lexer(FileHandler fileHandler) {
        this.row = 1;
        this.column = 1;
        this.endOfFile = false;
        this.fileHandler = fileHandler;
        this.currentChar = nextChar();
    }

    private char nextChar() {
        int i = fileHandler.nextChar();
        endOfFile = (i == -1);
        char c = (char) i;
        if (c == '\n') {
            row++;
            column = 1;
        } else {
            column++;
        }
        return c;
    }

    public Token nextToken() {
        Token token = null;
        while (isSpace(currentChar)) { // BIALE ZNAKI
            currentChar = nextChar();
        }

        if (endOfFile) { // KONIEC PLIKU
            return new Token(row, column, currentChar, TokenType.EOF);
        }

        try {
            switch (currentChar) { // POJEDYCZNE ZNAKI
                case '{':
                    token = new Token(row, column, currentChar, TokenType.LEFT_BRACE);
                    break;
                case '}':
                    token = new Token(row, column, currentChar, TokenType.RIGHT_BRACE);
                    break;
                case '[':
                    token = new Token(row, column, currentChar, TokenType.LEFT_BRACKET);
                    break;
                case ']':
                    token = new Token(row, column, currentChar, TokenType.RIGHT_BRACKET);
                    break;
                case ',':
                    token = new Token(row, column, currentChar, TokenType.COMMA);
                    break;
                case ':':
                    token = new Token(row, column, currentChar, TokenType.COLON);
                    break;
                case ';':
                    token = new Token(row, column, currentChar, TokenType.SEMICOLON);
                    break;
            }

            if (token != null) { // STRUKTURY ZLOZONE
                currentChar = nextChar();
                return token;
            } else if ('"' == currentChar) {
                return new Token(row, column, readString(), TokenType.STRING);
            } else if ('-' == currentChar || isDigit(currentChar)) {
                return new Token(row, column, readNumber(), TokenType.NUMBER);
            } else if (isLetter(currentChar)) {
                return new Token(row, column, readLiteral(), TokenType.LITERAL);
            } else {
                throw new InvalidTokenException(currentChar);
            }
        } catch (InvalidTokenException invalidTokenException) {
            System.out.print("[" + row + ", " + column + "] ");
            invalidTokenException.printMessage();
            System.exit(-1);
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

    private boolean isHexadecimalDigit(char c) {
        return isDigit(c) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    private String readString() throws InvalidTokenException {
        String string = "";
        while ((currentChar = nextChar()) != '"') {
            if (endOfFile) { // BRAK DOMKNIECIA
                throw new InvalidTokenException();
            } else if (currentChar == '\\') { // ZNAKI SPECJALNE
                currentChar = nextChar();
                if (currentChar == '"') {
                    string += '"';
                } else if (currentChar == '\\') {
                    string += '\\';
                } else if (currentChar == '/') {
                    string += '/';
                } else if (currentChar == 'b') {
                    string += '\b';
                } else if (currentChar == 'f') {
                    string += '\f';
                } else if (currentChar == 'n') {
                    string += '\n';
                } else if (currentChar == 'r') {
                    string += '\r';
                } else if (currentChar == 'u') { // LICZBA SZESNASTKOWA
                    String hexadecimal = "";
                    for (int i = 0; i < 4; i++) {
                        currentChar = nextChar();
                        hexadecimal += currentChar;
                        if (!isHexadecimalDigit(currentChar)) {
                            string += "\\u" + hexadecimal;
                            throw new InvalidTokenException(string, TokenType.STRING.toString());
                        }
                    }
                    string += Integer.parseInt(hexadecimal, 16);
                } else if (currentChar == 't') {
                    string += '\t';
                } else {
                    throw new InvalidTokenException(string, TokenType.STRING.toString());
                }
            } else {
                string += currentChar;
                // hash+=c;
            }
        }
        currentChar = nextChar();
        return string;
    }

    private String readNumber() throws InvalidTokenException { // 3 etapy wedlug diagramu
        String string = "";

        // ETAP 1
        if (currentChar == '-') {
            string += currentChar;
            currentChar = nextChar();
            if (!isDigit(currentChar)) {
                string += currentChar;
                throw new InvalidTokenException(string, TokenType.NUMBER.toString());
            }
        }

        string += currentChar;
        if (currentChar == '0') {
            currentChar = nextChar();
            if (currentChar == ',') {
                return string;
            } else if (currentChar != '.' && currentChar != 'e' && currentChar != 'E') {
                string += currentChar;
                throw new InvalidTokenException(string, TokenType.NUMBER.toString());
            }
        } else {
            string += readDigit();
        }

        // ETAP 2
        if (currentChar == '.') {
            string += currentChar;
            currentChar = nextChar();
            if (isDigit(currentChar)) {
                string += readDigit();
            } else {
                string += currentChar;
                throw new InvalidTokenException(string, TokenType.NUMBER.toString());
            }
        }

        // ETAP 3
        if (currentChar == 'e' || currentChar == 'E') {
            string += currentChar;
            currentChar = nextChar();
            if (currentChar == '-' || currentChar == '+') {
                string += currentChar;
                currentChar = nextChar();
            }
            if (isDigit(currentChar)) {
                string += readDigit();
            } else {
                string += currentChar;
                throw new InvalidTokenException(string, TokenType.NUMBER.toString());
            }
        }

        return string;
    }

    private String readDigit() {
        String string = "";
        currentChar = nextChar();
        while (isDigit(currentChar)) {
            string += currentChar;
            currentChar = nextChar();
        }
        return string;
    }

    private String readLiteral() throws InvalidTokenException {
        String string = "";

        if ('t' == currentChar) {
            string += currentChar;
            if ('r' == (currentChar = nextChar())) {
                string += currentChar;
                if ('u' == (currentChar = nextChar())) {
                    string += currentChar;
                    if ('e' == (currentChar = nextChar())) {
                        string += currentChar;
                    } else {
                        string += currentChar;
                        throw new InvalidTokenException(string, TokenType.LITERAL.toString());
                    }
                }
            }
        } else if ('f' == currentChar) {
            string += currentChar;
            if ('a' == (currentChar = nextChar())) {
                string += currentChar;
                if ('l' == (currentChar = nextChar())) {
                    string += currentChar;
                    if ('s' == (currentChar = nextChar())) {
                        string += currentChar;
                        if ('e' == (currentChar = nextChar())) {
                            string += currentChar;
                        } else {
                            string += currentChar;
                            throw new InvalidTokenException(string, TokenType.LITERAL.toString());
                        }
                    }
                }
            }
        } else if ('n' == currentChar) {
            string += currentChar;
            if ('u' == (currentChar = nextChar())) {
                string += currentChar;
                if ('l' == (currentChar = nextChar())) {
                    string += currentChar;
                    if ('l' == (currentChar = nextChar())) {
                        string += currentChar;
                    } else {
                        string += currentChar;
                        throw new InvalidTokenException(string, TokenType.LITERAL.toString());
                    }
                }
            }
        }

        currentChar = nextChar();
        return string;
    }

}