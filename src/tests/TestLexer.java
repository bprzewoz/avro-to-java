package tests;

import files.FileHandler;
import lexing.Lexer;
import lexing.Token;
import lexing.TokenType;

/**
 * Created by splbap on 2017-10-29.
 */
public class TestLexer {

    private final String RESET = "\u001B[0m";
    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String YELLOW = "\u001B[33m";
    private final String BLUE = "\u001B[34m";

    public TestLexer() {
        test();
    }

    public static void main(String[] args) {
        TestLexer testLexer = new TestLexer();
    }

    public void test() {
        testCases(5, "string");
        testCases(7, "number");
        testCases(3, "literal");
        testCases(5, "object");
        testCases(4, "array");
        testIllegalCases();
    }

    private void testCases(int number, String type) {
        System.out.println(YELLOW + type.toUpperCase() + " TEST CASES:" + RESET);
        for (int i = 1; i <= number; i++) {
            Token token = null;
            boolean passed = true;
            String fileName = type + i + ".txt";
            FileHandler fileHandler = new FileHandler(String.format("tests/lexer/%ss/%s", type, fileName));
            Lexer lexer = new Lexer(fileHandler);
            while ((token = lexer.nextToken()).getTokenType() != TokenType.EOF) {
                if (token.getTokenType() == TokenType.ERROR) {
                    passed = false;
                    break;
                }
            }
            String COLOR = passed ? BLUE : RED;
            String result = passed ? "SUCCESS" : "FAILURE";
            System.out.println(String.format("Result of %d. %s test case: %s%s%s", i, type, COLOR, result, RESET));
            fileHandler.closeFiles();
        }
        System.out.println();
    }

    private void testIllegalCases() {
        System.out.println(YELLOW + "ILLEGAL TEST CASES:" + RESET);
        for (int i = 1; i <= 9; i++) {
            Token token = null;
            boolean passed = false;
            String fileName = "illegal" + i + ".txt";
            FileHandler fileHandler = new FileHandler("tests/lexer/illegals/" + fileName);
            Lexer lexer = new Lexer(fileHandler);
            while ((token = lexer.nextToken()).getTokenType() != TokenType.EOF) {
                if (token.getTokenType() == TokenType.ERROR) {
                    passed = true;
                    break;
                }
            }
            String COLOR = passed ? BLUE : RED;
            String result = passed ? "SUCCESS" : "FAILURE";
            System.out.println("Result of " + i + ". illegal test case: " + COLOR + result + RESET);
            fileHandler.closeFiles();
        }
        System.out.println();
    }
}
