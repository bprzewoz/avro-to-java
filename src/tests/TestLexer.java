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

    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String BLUE = "\u001B[34m";
    private final String RESET = "\u001B[0m";

    public TestLexer(){
        test();
    }

    public void test() {
        testIllegalCases();
    }

    private void testIllegalCases(){
        System.out.println("ILLEGAL TEST CASES:");
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
    }

}
