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
        char c = (i == 13) ? '\n' : (char) i;
        endOfFile = (i == -1);
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
                System.out.println((int) currentChar + "," + currentChar);
                throw new InvalidTokenException(900, currentChar);
            }
        } catch (InvalidTokenException invalidTokenException) {
            System.out.println("[ROW " + row + ", COL " + column + "] " + invalidTokenException.getMessage());
            return new Token(row, column, "ERROR", TokenType.ERROR);
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

    private boolean isSpecialCharacter(char c) {
        return c == '/' || c == '\b' || c == '\f' || c == '\n' || c == '\r' || c == '\t';
    }

    private String readString() throws InvalidTokenException {
        String string = "";
        while ((currentChar = nextChar()) != '"') {
            if (endOfFile) { // BRAK DOMKNIECIA
                throw new InvalidTokenException(500, string, TokenType.STRING.toString());
            } else if (isSpecialCharacter(currentChar)) {
                throw new InvalidTokenException(800, string + currentChar, TokenType.STRING.toString());
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
                    string += "\\u";
                    for (int i = 0; i < 4; i++) {
                        currentChar = nextChar();
                        string += currentChar;
                        if (!isHexadecimalDigit(currentChar)) {
                            throw new InvalidTokenException(700, string, TokenType.STRING.toString());
                        }
                    }
                } else if (currentChar == 't') {
                    string += '\t';
                } else {
                    throw new InvalidTokenException(600, string, TokenType.STRING.toString());
                }
            } else {
                string += currentChar;
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
                throw new InvalidTokenException(400, string, TokenType.NUMBER.toString());
            }
        }

        string += currentChar;
        if (currentChar == '0') {
            currentChar = nextChar();
            if (isDigit(currentChar)) {
                string += currentChar;
                throw new InvalidTokenException(100, string, TokenType.NUMBER.toString());
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
                throw new InvalidTokenException(300, string, TokenType.NUMBER.toString());
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
                throw new InvalidTokenException(200, string, TokenType.NUMBER.toString());
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
        String string = String.valueOf(currentChar);
        boolean validLiteral = false;

        if ('t' == currentChar) {
            currentChar = nextChar();
            string += currentChar;
            if ('r' == currentChar) {
                currentChar = nextChar();
                string += currentChar;
                if ('u' == currentChar) {
                    currentChar = nextChar();
                    string += currentChar;
                    if ('e' == currentChar) {
                        validLiteral = true;
                    }
                }
            }
        } else if ('f' == currentChar) {
            currentChar = nextChar();
            string += currentChar;
            if ('a' == currentChar) {
                currentChar = nextChar();
                string += currentChar;
                if ('l' == currentChar) {
                    currentChar = nextChar();
                    string += currentChar;
                    if ('s' == currentChar) {
                        currentChar = nextChar();
                        string += currentChar;
                        if ('e' == currentChar) {
                            validLiteral = true;
                        }
                    }
                }
            }
        } else if ('n' == currentChar) {
            currentChar = nextChar();
            string += currentChar;
            if ('u' == currentChar) {
                currentChar = nextChar();
                string += currentChar;
                if ('l' == currentChar) {
                    currentChar = nextChar();
                    string += currentChar;
                    if ('l' == currentChar) {
                        validLiteral = true;
                    }
                }
            }
        }

        currentChar = nextChar();
        if (validLiteral) {
            return string;
        } else {
            throw new InvalidTokenException(900, string, TokenType.LITERAL.toString());
        }
    }
}