package tests;

import files.FileHandler;
import json.lexer.JsonLexer;
import json.parser.JsonParser;
import json.token.JsonToken;
import json.type.JsonNode;

/**
 * Created by splbap on 2017-10-29.
 */
public class JsonParserTest {

    private final String RESET = "\u001B[0m";
    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String YELLOW = "\u001B[33m";
    private final String BLUE = "\u001B[34m";

    public static void main(String[] args) {
        JsonParserTest jsonParserTest = new JsonParserTest();
        jsonParserTest.test();
    }

    public void test() {
        testCase(1, "object", true);
        testCase(1, "member", true);
        testCase(1, "pair", true);
        testCase(1, "array", true);
        testCase(1, "element", true);
        testCase(1, "value", true);

        testCase(6, "illegal", false);
    }

    private void testCase(int number, String type, boolean expectation) {
        System.out.println(String.format("%s%s TEST CASES: %s", YELLOW, type.toUpperCase(), RESET));
        for (int i = 1; i <= number; i++) {
            JsonToken jsonToken = null;
            boolean passed = expectation;
            String fileName = String.format("%s%d.txt", type, i);
            FileHandler fileHandler = new FileHandler(String.format("tests/json/parser/%ss/%s", type, fileName));
            JsonLexer jsonLexer = new JsonLexer(fileHandler);
            JsonParser jsonParser = new JsonParser(jsonLexer);
            JsonNode jsonNode = jsonParser.parseFile();
            if (jsonNode == null) {
                passed = !expectation;
            }
            String COLOR = passed ? BLUE : RED;
            String result = passed ? "SUCCESS" : "FAILURE";
            System.out.println(String.format("Result of %d. %s test case: %s%s%s", i, type, COLOR, result, RESET));
            fileHandler.closeFiles();
        }
        System.out.println();
    }
}
