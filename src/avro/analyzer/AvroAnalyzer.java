package avro.analyzer;

import avro.generator.JavaGenerator;
import avro.iterator.AvroIterator;
import avro.type.AvroField;
import avro.type.AvroType;
import avro.type.complex.*;
import avro.type.primitive.*;
import exceptions.InvalidSchemaException;
import files.FileHandler;
import json.lexer.JsonLexer;
import json.parser.JsonParser;
import json.type.complex.JsonArray;
import json.type.complex.JsonObject;
import json.type.complex.JsonValue;
import json.type.primitive.JsonLiteral;
import json.type.primitive.JsonNumber;
import json.type.primitive.JsonString;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-12.
 */
public class AvroAnalyzer {

    public static void main(String[] args) throws IOException {
        FileHandler fileHandler = new FileHandler("files\\complexFile.txt", "files\\outputFile.txt");
        JsonLexer jsonLexer = new JsonLexer(fileHandler);
        JsonParser jsonParser = new JsonParser(jsonLexer);
        JsonObject jsonObject = jsonParser.parseFile();
        if (jsonObject != null) {
            AvroAnalyzer avroAnalyzer = new AvroAnalyzer(jsonObject);
            LinkedList<AvroType> javaClasses = avroAnalyzer.analyzeFile();
            if (javaClasses != null) {
                JavaGenerator javaGenerator = new JavaGenerator(javaClasses);
                System.out.print(javaGenerator.generateJava());
                fileHandler.write(javaGenerator.generateJava());
            }
        }
        fileHandler.closeFiles();
    }

    private AvroIterator avroIterator;
    private LinkedList<AvroType> javaClasses;

    public AvroAnalyzer(JsonObject jsonObject) {
        avroIterator = new AvroIterator(jsonObject);
        javaClasses = new LinkedList<>();
    }

    public LinkedList<AvroType> analyzeFile() {
        try {
            analyzeRecord();
            return javaClasses;
        } catch (InvalidSchemaException invalidSchemaException ) {
            System.out.println(invalidSchemaException.getMessage());
            return null;
        }
    }

    // ANALYZE RECORD

    private AvroRecord analyzeRecord() throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();
        JsonString type = currentObject.getAttribute("type");
        if (type != null && type.getValue().equals("record")) {
            currentObject.checkAttributes(new LinkedList<String>(Arrays.asList("type", "namespace", "name", "fields")));
            String name = currentObject.getAttribute("name", JsonString.class, true).getValue();
            JsonString jsonString = currentObject.getAttribute("namespace");
            String namespace = jsonString != null ? jsonString.getValue() : null;

            AvroRecord avroRecord = new AvroRecord(name, namespace, analyzeFields());
            javaClasses.addFirst(avroRecord);
            return avroRecord;
        }
        return null;
    }

    private LinkedList<AvroField> analyzeFields() throws InvalidSchemaException {
        JsonArray jsonArray = avroIterator.getCurrentObject().getAttribute("fields", JsonArray.class, true);
        LinkedList<JsonValue> elements = jsonArray.getElements();
        LinkedList<AvroField> fields = new LinkedList<>();
        for (JsonValue jsonValue : elements) {
            if (jsonValue.getClass().equals(JsonObject.class)) {
                avroIterator.setCurrentObject(jsonValue);
                fields.add(analyzeField());
            } else {
                throw new InvalidSchemaException(100, jsonValue, jsonValue.getClass().toString()); // WRONG TYPE
            }
        }
        return fields;
    }

    private AvroField analyzeField() throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();
        currentObject.checkAttributes(new LinkedList<String>(Arrays.asList("type", "name", "default")));
        String name = avroIterator.getCurrentObject().getNameAttribute().getValue();

        JsonObject typeObject = avroIterator.getCurrentObject().getAttribute("type", JsonObject.class);
        if (typeObject != null) {
            avroIterator.setCurrentObject(typeObject);
            return new AvroField(name, analyzeComplex());
        }

        JsonString typeString = avroIterator.getCurrentObject().getAttribute("type", JsonString.class);
        if (typeString != null) {
            return new AvroField(name, analyzePrimitive("type"));
        }

        throw new InvalidSchemaException(200, currentObject); // MISSING TYPE ATTRIBUTE
    }

    // ANALYZE ENUM

    private AvroEnum analyzeEnum() throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();
        JsonString type = currentObject.getAttribute("type");
        if (type != null && type.getValue().equals("enum")) {
            currentObject.checkAttributes(new LinkedList<String>(Arrays.asList("type", "name", "namespace", "symbols")));
            String name = avroIterator.getCurrentObject().getAttribute("name", JsonString.class, true).getValue();
            String namespace = avroIterator.getCurrentObject().getAttribute("namespace", JsonString.class, true).getValue();

            AvroEnum avroEnum = new AvroEnum(name, namespace, analyzeSymbols());
            javaClasses.addFirst(avroEnum);
            return avroEnum;
        }
        return null;
    }

    private LinkedList<String> analyzeSymbols() throws InvalidSchemaException {
        JsonArray jsonArray = avroIterator.getCurrentObject().getAttribute("symbols", JsonArray.class, true);
        LinkedList<JsonValue> elements = jsonArray.getElements();
        LinkedList<String> symbols = new LinkedList<>();
        for (JsonValue jsonValue : elements) {
            if (jsonValue.getClass().equals(JsonString.class)) {
                JsonString jsonString = (JsonString) jsonValue;
                if (!symbols.contains(jsonString.getValue())) {
                    symbols.add(jsonString.getValue().toUpperCase());
                } else {
                    throw new InvalidSchemaException(300, jsonString, jsonString.getValue()); // DUPLICATE ENUM
                }
            } else {
                throw new InvalidSchemaException(400, jsonValue, jsonValue.getClass().toString()); // WRONG ENUM TYPE
            }
        }
        return symbols;
    }

    // ANALYZE ARRAY

    @Nullable
    private AvroArray analyzeArray() throws InvalidSchemaException { // DZIWNE PARSOWANIE
        JsonObject currentObject = avroIterator.getCurrentObject();
        JsonString type = currentObject.getAttribute("type", JsonString.class);
        if (type != null && type.getValue().equals("array")) {
            currentObject.checkAttributes(new LinkedList<String>(Arrays.asList("type", "items")));
            return new AvroArray(analyzeItems());
        }
        return null;
    }

    private AvroType analyzeItems() throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();

        JsonObject typeObject = currentObject.getAttribute("items", JsonObject.class);
        if (typeObject != null) {
            avroIterator.setCurrentObject(typeObject);
            return analyzeComplex();
        }

        JsonString typeString = currentObject.getAttribute("items", JsonString.class);
        if (typeString != null) {
            return analyzePrimitive("items");
        }

        throw new InvalidSchemaException(500, currentObject); // MISSING TYPE
    }

    @Nullable
    private AvroMap parseMap() throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();
        JsonString type = currentObject.getAttribute("type");
        if (type == null || !type.getValue().equals("map")) {
            currentObject.checkAttributes(new LinkedList<String>(Arrays.asList("type", "values")));
            return null;
        }
        return null;
    }

    @Nullable
    private AvroUnion parseUnion() throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();
        JsonString type = currentObject.getAttribute("type");
        if (type != null && type.getValue().equals("union")) {
            currentObject.checkAttributes(new LinkedList<String>(Arrays.asList("type", "name")));
            return null;
        }
        return null;
    }

    @Nullable
    private AvroFixed parseFixed() throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();
        JsonString type = currentObject.getAttribute("type");
        if (type != null && type.getValue().equals("fixed")) {
            currentObject.checkAttributes(new LinkedList<String>(Arrays.asList("type", "name", "size")));
            return null;
        }
        return null;
    }

    // PARSE PRIMITIVE

    @Nullable
    private AvroString analyzeString(String key) throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();
        JsonString type = currentObject.getAttribute(key);
        if (type != null && type.getValue().equals("string")) {
            currentObject.checkPrimitiveAtrributes();
            JsonString jsonString = avroIterator.getCurrentObject().getAttribute("default", JsonString.class);
            String defaultValue = jsonString != null ? jsonString.getValue() : null;
            return new AvroString(defaultValue);
        }
        return null;
    }

    @Nullable
    private AvroBytes analyzeBytes(String key) throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();
        JsonString type = currentObject.getAttribute(key);
        if (type != null && type.getValue().equals("bytes")) {
            currentObject.checkPrimitiveAtrributes();
            JsonNumber jsonNumber = avroIterator.getCurrentObject().getAttribute("default", JsonNumber.class);
            String defaultValue = jsonNumber != null ? jsonNumber.getValue() : null;
            return new AvroBytes(defaultValue);
        }

        return null;
    }

    @Nullable
    private AvroDouble analyzeDouble(String key) throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();
        JsonString type = currentObject.getAttribute(key);
        if (type != null && type.getValue().equals("double")) {
            currentObject.checkPrimitiveAtrributes();
            JsonNumber jsonNumber = avroIterator.getCurrentObject().getAttribute("default", JsonNumber.class);
            String defaultValue = jsonNumber != null ? jsonNumber.getValue() : null;
            return new AvroDouble(defaultValue);
        }
        return null;
    }

    @Nullable
    private AvroFloat analyzeFloat(String key) throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();
        JsonString type = currentObject.getAttribute(key);
        if (type != null && type.getValue().equals("float")) {
            currentObject.checkPrimitiveAtrributes();
            JsonNumber jsonNumber = avroIterator.getCurrentObject().getAttribute("default", JsonNumber.class);
            String defaultValue = jsonNumber != null ? jsonNumber.getValue() : null;
            return new AvroFloat(defaultValue);
        }
        return null;
    }

    @Nullable
    private AvroLong analyzeLong(String key) throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();
        JsonString type = currentObject.getAttribute(key);
        if (type != null && type.getValue().equals("long")) {
            currentObject.checkPrimitiveAtrributes();
            JsonNumber jsonNumber = avroIterator.getCurrentObject().getAttribute("default", JsonNumber.class);
            String defaultValue = jsonNumber != null ? jsonNumber.getValue() : null;
            return new AvroLong(defaultValue);
        }
        return null;
    }

    @Nullable
    private AvroInt analyzeInt(String key) throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();
        JsonString type = currentObject.getAttribute(key);
        if (type != null && type.getValue().equals("int")) {
            currentObject.checkPrimitiveAtrributes();
            JsonNumber jsonNumber = avroIterator.getCurrentObject().getAttribute("default", JsonNumber.class);
            String defaultValue = jsonNumber != null ? jsonNumber.getValue() : null;
            return new AvroInt(defaultValue);
        }
        return null;
    }

    @Nullable
    private AvroBoolean analyzeBoolean(String key) throws InvalidSchemaException {
        JsonObject currentObject = avroIterator.getCurrentObject();
        JsonString type = currentObject.getAttribute(key);
        if (type != null && type.getValue().equals("boolean")) {
            currentObject.checkAttributes(new LinkedList<String>(Arrays.asList("name", "type", "default")));
            JsonLiteral jsonLiteral = avroIterator.getCurrentObject().getAttribute("default", JsonLiteral.class);
            String defaultValue = jsonLiteral != null ? jsonLiteral.getValue() : null;
            return new AvroBoolean(defaultValue);
        }
        return null;
    }

    @Nullable
    private AvroType analyzeComplex() throws InvalidSchemaException {
        AvroType avroType;
        if ((avroType = analyzeRecord()) != null) {
            return avroType;
        } else if ((avroType = analyzeEnum()) != null) {
            return avroType;
        } else if ((avroType = analyzeArray()) != null) {
            return avroType;
        } else if ((avroType = parseMap()) != null) {
            return avroType;
        } else if ((avroType = parseUnion()) != null) {
            return avroType;
        } else if ((avroType = parseFixed()) != null) {
            return avroType;
        } else {
            return null;
        }
    }

    @Nullable
    private AvroType analyzePrimitive(String key) throws InvalidSchemaException {
        AvroType avroType;
        if ((avroType = analyzeString(key)) != null) {
            return avroType;
        } else if ((avroType = analyzeBytes(key)) != null) {
            return avroType;
        } else if ((avroType = analyzeDouble(key)) != null) {
            return avroType;
        } else if ((avroType = analyzeFloat(key)) != null) {
            return avroType;
        } else if ((avroType = analyzeLong(key)) != null) {
            return avroType;
        } else if ((avroType = analyzeInt(key)) != null) {
            return avroType;
        } else if ((avroType = analyzeBoolean(key)) != null) {
            return avroType;
        } else {
            return null;
        }
    }

}
