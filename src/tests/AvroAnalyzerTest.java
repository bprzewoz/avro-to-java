package tests;

import avro.analyzer.AvroAnalyzer;
import avro.type.AvroType;
import files.FileHandler;
import json.lexer.JsonLexer;
import json.parser.JsonParser;
import json.token.JsonToken;
import json.token.JsonTokenType;
import json.type.complex.JsonObject;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-12-05.
 */
public class AvroAnalyzerTest {

    private final String RESET = "\u001B[0m";
    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String YELLOW = "\u001B[33m";
    private final String BLUE = "\u001B[34m";

    public static void main(String[] args) {
        AvroAnalyzerTest avroAnalyzerTest = new AvroAnalyzerTest();
        avroAnalyzerTest.test();
    }

    public void test() {
        testCase(8, "illegal", false);
    }

    private void testCase(int number, String type, boolean expectation) {
        System.out.println(String.format("%s%s TEST CASES: %s", YELLOW, type.toUpperCase(), RESET));
        for (int i = 1; i <= number; i++) {
            JsonToken jsonToken = null;
            boolean passed = expectation;
            String fileName = String.format("%s%d.txt", type, i);
            FileHandler fileHandler = new FileHandler(String.format("tests/avro/analyzer/%ss/%s", type, fileName));
            JsonLexer jsonLexer = new JsonLexer(fileHandler);
            JsonParser jsonParser = new JsonParser(jsonLexer);
            JsonObject jsonObject = jsonParser.parseFile();
            AvroAnalyzer avroAnalyzer = new AvroAnalyzer(jsonObject);
            LinkedList<AvroType> javaClasses = avroAnalyzer.analyzeFile();
            if (javaClasses == null) {
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