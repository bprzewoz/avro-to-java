package tests;

import files.FileHandler;
import lexing.Lexer;
import lexing.Token;
import lexing.TokenType;

/**
 * Created by splbap on 2017-10-29.
 */
public class LexerTest {

    public static void main(String[] args) {
        LexerTest lexerTest = new LexerTest();
        lexerTest.test();
    }

    private final String RESET = "\u001B[0m";
    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String YELLOW = "\u001B[33m";
    private final String BLUE = "\u001B[34m";

    public void test() {
        testCase(5, "string", true);
        testCase(7, "number", true);
        testCase(3, "literal", true);
        testCase(5, "object", true);
        testCase(4, "array", true);
        testCase(9, "illegal", false);
    }

    private void testCase(int number, String type, boolean expectation) {
        System.out.println(String.format("%s%s TEST CASES: %s", YELLOW, type.toUpperCase(), RESET));
        for (int i = 1; i <= number; i++) {
            Token token = null;
            boolean passed = expectation;
            String fileName = String.format("%s%d.txt", type, i);
            FileHandler fileHandler = new FileHandler(String.format("tests/lexer/%ss/%s", type, fileName));
            Lexer lexer = new Lexer(fileHandler);
            while ((token = lexer.nextToken()).getTokenType() != TokenType.EOF) {
                if (token.getTokenType() == TokenType.ERROR) {
                    passed = !expectation;
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
}
