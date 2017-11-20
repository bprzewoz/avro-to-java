package json.lexer;

import exceptions.InvalidTokenException;
import files.FileHandler;
import json.token.JsonToken;
import json.token.JsonTokenType;

/**
 * Created by splbap on 2017-10-11.
 */
public class JsonLexer {

    private int row;
    private int column;
    private char currentChar;
    private boolean endOfFile;
    private FileHandler fileHandler;

    public JsonLexer(FileHandler fileHandler) {
        this.row = 1;
        this.column = 1;
        this.endOfFile = false;
        this.fileHandler = fileHandler;
        this.currentChar = nextChar();
    }

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler("inputFile.txt", "outputFile.txt");
        JsonLexer jsonLexer = new JsonLexer(fileHandler);
        JsonToken jsonToken;
        while ((jsonToken = jsonLexer.nextToken()).getType() != JsonTokenType.EOF) {
            if (jsonToken.getType() == JsonTokenType.ERROR) {
                break;
            }
            System.out.println(jsonToken.getValue());
        }
        fileHandler.closeFiles();
    }

    public JsonToken nextToken() {
        JsonToken jsonToken = null;
        while (isSpace(currentChar)) { // BIALE ZNAKI
            currentChar = nextChar();
        }

        if (endOfFile) { // KONIEC PLIKU
            return new JsonToken(row, column, "EOF", JsonTokenType.EOF);
        }

        try {
            switch (currentChar) { // POJEDYCZNE ZNAKI
                case '{':
                    jsonToken = new JsonToken(row, column, currentChar, JsonTokenType.LEFT_BRACE);
                    break;
                case '}':
                    jsonToken = new JsonToken(row, column, currentChar, JsonTokenType.RIGHT_BRACE);
                    break;
                case '[':
                    jsonToken = new JsonToken(row, column, currentChar, JsonTokenType.LEFT_BRACKET);
                    break;
                case ']':
                    jsonToken = new JsonToken(row, column, currentChar, JsonTokenType.RIGHT_BRACKET);
                    break;
                case ',':
                    jsonToken = new JsonToken(row, column, currentChar, JsonTokenType.COMMA);
                    break;
                case ':':
                    jsonToken = new JsonToken(row, column, currentChar, JsonTokenType.COLON);
                    break;
            }

            if (jsonToken != null) { // STRUKTURY ZLOZONE
                currentChar = nextChar();
                return jsonToken;
            }

            if ('"' == currentChar) {
                return new JsonToken(row, column, readString(), JsonTokenType.STRING);
            } else if ('-' == currentChar || isDigit(currentChar)) {
                return new JsonToken(row, column, readNumber(), JsonTokenType.NUMBER);
            } else if (isLetter(currentChar)) {
                return new JsonToken(row, column, readLiteral(), JsonTokenType.LITERAL);
            } else {
                throw new InvalidTokenException(900, currentChar);
            }
        } catch (InvalidTokenException invalidTokenException) {
            System.out.println(String.format("[ROW %d, COL %d] %s", row, column, invalidTokenException.getMessage()));
            return new JsonToken(row, column, "ERROR", JsonTokenType.ERROR);
        }
    }

    private char nextChar() {
        int i = fileHandler.nextChar();
        char c = (i == 13) ? '\n' : (char) i;
        endOfFile = (i == -1);
        if (c == '\n') {
            column = 1;
            row++;
        } else {
            column++;
        }
        return c;
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
                throw new InvalidTokenException(500, string, JsonTokenType.STRING);
            } else if (isSpecialCharacter(currentChar)) {
                throw new InvalidTokenException(800, string + currentChar, JsonTokenType.STRING);
            } else if (currentChar == '\\') { // ZNAKI SPECJALNE
                currentChar = nextChar();
                switch (currentChar) {
                    case '"':
                        string += '"';
                        break;
                    case '\\':
                        string += '\\';
                        break;
                    case '/':
                        string += '/';
                        break;
                    case 'b':
                        string += '\b';
                        break;
                    case 'f':
                        string += '\f';
                        break;
                    case 'n':
                        string += '\n';
                        break;
                    case 'r':
                        string += '\r';
                        break;
                    case 'u':
                        string += "\\u";
                        string += readHexadecimal();
                        break;
                    case 't':
                        string += '\t';
                        break;
                    default:
                        throw new InvalidTokenException(600, string, JsonTokenType.STRING);
                }
            } else {
                string += currentChar;
            }
        }
        currentChar = nextChar();
        return string;
    }

    private String readHexadecimal() throws InvalidTokenException {
        String string = "";
        for (int i = 0; i < 4; i++) {
            currentChar = nextChar();
            string += currentChar;
            if (!isHexadecimalDigit(currentChar)) {
                throw new InvalidTokenException(700, string, JsonTokenType.STRING);
            }
        }
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
                throw new InvalidTokenException(400, string, JsonTokenType.NUMBER);
            }
        }

        if (currentChar == '0') {
            string += currentChar;
            currentChar = nextChar();
            if (isDigit(currentChar)) {
                string += currentChar;
                throw new InvalidTokenException(100, string, JsonTokenType.NUMBER);
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
                throw new InvalidTokenException(300, string, JsonTokenType.NUMBER);
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
                throw new InvalidTokenException(200, string, JsonTokenType.NUMBER);
            }
        }

        return string;
    }

    private String readDigit() {
        String string = "";
        do {
            string += currentChar;
            currentChar = nextChar();
        } while (isDigit(currentChar));
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

        if (validLiteral) {
            currentChar = nextChar();
            return string;
        } else {
            throw new InvalidTokenException(900, string, JsonTokenType.LITERAL);
        }
    }
}