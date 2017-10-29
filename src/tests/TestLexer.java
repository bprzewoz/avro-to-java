package tests;

import files.FileHandler;
import lexing.Lexer;
import lexing.Token;
import lexing.TokenType;

/**
 * Created by splbap on 2017-10-29.
 */
public class TestLexer {

    public static void main(String[] args) {
        TestLexer testLexer = new TestLexer();
    }

    private final String RESET = "\u001B[0m";
    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String YELLOW = "\u001B[33m";
    private final String BLUE = "\u001B[34m";

    public TestLexer(){
        test();
    }

    public void test() {
        testStringCases();
        testNumberCases();
        testLiteralCases();
        testIllegalCases();
    }

    private void testStringCases(){
        System.out.println(YELLOW + "STRING TEST CASES:" + RESET);
        for (int i = 1; i <= 5; i++) {
            Token token = null;
            boolean passed = true;
            String fileName = "string" + i + ".txt";
            FileHandler fileHandler = new FileHandler("tests/lexer/strings/" + fileName);
            Lexer lexer = new Lexer(fileHandler);
            while ((token = lexer.nextToken()).getTokenType() != TokenType.EOF) {
                if(token.getTokenType() == TokenType.ERROR){
                    passed = false;
                    break;
                }
            }
            String COLOR = passed?BLUE:RED;
            String result = passed?"SUCCESS":"FAILURE";
            System.out.println("Result of " + i +". string test case: " + COLOR + result + RESET);
            fileHandler.closeFiles();
        }
        System.out.println();
    }

    private void testNumberCases(){
        System.out.println(YELLOW + "NUMBER TEST CASES:" + RESET);
        for (int i = 1; i <= 7; i++) {
            Token token = null;
            boolean passed = true;
            String fileName = "number" + i + ".txt";
            FileHandler fileHandler = new FileHandler("tests/lexer/numbers/" + fileName);
            Lexer lexer = new Lexer(fileHandler);
            while ((token = lexer.nextToken()).getTokenType() != TokenType.EOF) {
                if(token.getTokenType() == TokenType.ERROR){
                    passed = false;
                    break;
                }
            }
            String COLOR = passed?BLUE:RED;
            String result = passed?"SUCCESS":"FAILURE";
            System.out.println("Result of " + i +". number test case: " + COLOR + result + RESET);
            fileHandler.closeFiles();
        }
        System.out.println();
    }

    private void testLiteralCases(){
        System.out.println(YELLOW + "LITERAL TEST CASES:" + RESET);
        for (int i = 1; i <= 3; i++) {
            Token token = null;
            boolean passed = true;
            String fileName = "literal" + i + ".txt";
            FileHandler fileHandler = new FileHandler("tests/lexer/literals/" + fileName);
            Lexer lexer = new Lexer(fileHandler);
            while ((token = lexer.nextToken()).getTokenType() != TokenType.EOF) {
                if(token.getTokenType() == TokenType.ERROR){
                    passed = false;
                    break;
                }
            }
            String COLOR = passed?BLUE:RED;
            String result = passed?"SUCCESS":"FAILURE";
            System.out.println("Result of " + i +". literal test case: " + COLOR + result + RESET);
            fileHandler.closeFiles();
        }
        System.out.println();
    }

    private void testIllegalCases(){
        System.out.println(YELLOW + "ILLEGAL TEST CASES:" + RESET);
        for (int i = 1; i <= 9; i++) {
            Token token = null;
            boolean passed = false;
            String fileName = "illegal" + i + ".txt";
            FileHandler fileHandler = new FileHandler("tests/lexer/illegals/" + fileName);
            Lexer lexer = new Lexer(fileHandler);
            while ((token = lexer.nextToken()).getTokenType() != TokenType.EOF) {
                if(token.getTokenType() == TokenType.ERROR){
                    passed = true;
                    break;
                }
            }
            String COLOR = passed?BLUE:RED;
            String result = passed?"SUCCESS":"FAILURE";
            System.out.println("Result of " + i +". illegal test case: " + COLOR + result + RESET);
            fileHandler.closeFiles();
        }
        System.out.println();
    }
}
