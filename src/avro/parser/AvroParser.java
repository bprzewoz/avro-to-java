package avro.parser;

import avro.type.AvroField;
import avro.type.AvroNode;
import avro.type.complex.AvroEnum;
import avro.type.complex.AvroRecord;
import avro.type.primitive.*;
import files.FileHandler;
import json.lexer.JsonLexer;
import json.parser.JsonParser;
import json.type.complex.JsonArray;
import json.type.complex.JsonObject;
import json.type.complex.JsonPair;
import json.type.complex.JsonValue;
import json.type.primitive.JsonLiteral;
import json.type.primitive.JsonNumber;
import json.type.primitive.JsonString;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-12.
 */
public class AvroParser {

    private JsonObject currentObject;

    public AvroParser(JsonObject jsonObject) {
        this.currentObject = jsonObject;
    }

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler("inputFile.txt", "outputFile.txt");
        JsonLexer jsonLexer = new JsonLexer(fileHandler);
        JsonParser jsonParser = new JsonParser(jsonLexer);
        JsonObject jsonObject = jsonParser.parseFile();
        if (jsonObject != null) {
            AvroParser avroParser = new AvroParser(jsonObject);
            AvroRecord avroRecord = avroParser.parseFile();
            if (avroRecord != null) {
                avroRecord.printNode(0);
            }
        }
        fileHandler.closeFiles();
    }

    public AvroRecord parseFile() {
        try {
            return parseRecord();
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
            return null;
        }
    }

    // PARSE COMPLEX

    private AvroRecord parseRecord() throws Exception {
        JsonString type = getAttribute("type", JsonString.class, false);
        if (type == null || !type.getValue().equals("record")) {
            return null;
        }

        checkAttributes(new LinkedList<String>(Arrays.asList("type", "namespace", "name", "fields")));
        String namespace = getAttribute("namespace", JsonString.class, true).getValue();
        String name = getAttribute("name", JsonString.class, true).getValue();

        return new AvroRecord(currentObject.getRow(), currentObject.getColumn(), name, namespace, parseFields());
    }

    private LinkedList<AvroNode> parseFields() throws Exception {
        LinkedList<AvroNode> fields = new LinkedList<>();
        for (JsonValue jsonValue : getAttribute("fields", JsonArray.class, true).getElements()) {
            if (jsonValue.getClass().equals(JsonObject.class)) {
                currentObject = (JsonObject) jsonValue;
                fields.add(parseObject());
                //fields.add(parseField());
            } else {
                System.out.println("NOT OBJECT");
                throw new Exception(); // NOT OBJECT
            }
        }
        return fields;
    }

    private AvroField parseField() throws Exception {
        JsonObject typeObject = getAttribute("type", JsonObject.class, false);
        JsonString typeString = getAttribute("type", JsonString.class, false);

        String name = getAttribute("name", JsonString.class, true).getValue();
        checkAttributes(new LinkedList<String>(Arrays.asList("type", "name", "default")));

        if (typeObject != null) {
            currentObject = typeObject;
            return new AvroField(name, parseComplex());
        } else if (typeString != null) {
            return new AvroField(name, parsePrimitive());
        } else {
            System.out.println("MISSING TYPE");
            // throw new UnexpectedTokenException(600, currentToken.getType());
            return null;
        }
    }

    private AvroEnum parseEnum() throws Exception {
        JsonString type = getAttribute("type", JsonString.class, false);
        if (type == null || !type.getValue().equals("enum")) {
            return null;
        }

        checkAttributes(new LinkedList<String>(Arrays.asList("type", "namespace", "name", "symbols")));
        String namespace = getAttribute("namespace", JsonString.class, true).getValue();
        String name = getAttribute("name", JsonString.class, true).getValue();

        return new AvroEnum(currentObject.getRow(), currentObject.getColumn(), name, namespace, parseSymbols());
    }

    private LinkedList<String> parseSymbols() throws Exception {
        LinkedList<String> symbols = new LinkedList<>();
        for (JsonValue jsonValue : getAttribute("symbols", JsonArray.class, true).getElements()) {
            if (jsonValue.getClass().equals(JsonString.class)) {
                JsonString jsonString = (JsonString) jsonValue;
                if (!symbols.contains(jsonString.getValue())) {
                    symbols.add(jsonString.getValue());
                } else {
                    System.out.println("DUPLICATE ENUM");
                    throw new Exception(); // DUPLICATE ENUM
                }
            } else {
                System.out.println("NOT STRING");
                throw new Exception(); // NOT STRING
            }
        }
        return symbols;
    }

    private AvroNode parseArray() throws Exception { // DZIWNE PARSOWANIE
        JsonObject typeObject = getAttribute("type", JsonObject.class, false);
        if (typeObject != null) {
            JsonString type = getAttribute("type", JsonString.class, false);
            if (type == null || !type.getValue().equals("array")) {
                return null;
            }
        } else {
            return null;
        }


        checkAttributes(new LinkedList<String>(Arrays.asList("type", "items")));

        return null;
    }

    private AvroNode parseMap() throws Exception {
        JsonString type = getAttribute("type", JsonString.class, false);
        if (type == null || !type.getValue().equals("map")) {
            return null;
        }

        checkAttributes(new LinkedList<String>(Arrays.asList("type", "values")));

        return null;
    }

    private AvroNode parseUnion() throws Exception {
        JsonArray type = getAttribute("type", JsonArray.class, false);
        if (type == null) {
            return null;
        }

        checkAttributes(new LinkedList<String>(Arrays.asList("type", "name")));

        return null;
    }

    private AvroNode parseFixed() throws Exception {
        JsonString type = getAttribute("type", JsonString.class, false);
        if (type == null || !type.getValue().equals("fixed")) {
            return null;
        }

        checkAttributes(new LinkedList<String>(Arrays.asList("type", "name", "size")));

        return null;
    }

    // PARSE PRIMITIVE - ZROBIONE

    private AvroString parseString() throws Exception {
        JsonString type = getAttribute("type", JsonString.class, false);
        if (type == null || !type.getValue().equals("string")) {
            return null;
        }

        String name = getAttribute("name", JsonString.class, true).getValue();
        JsonString jsonString = getAttribute("default", JsonString.class, false);
        String dflt = jsonString != null ? jsonString.getValue() : null;

        return new AvroString(currentObject.getRow(), currentObject.getColumn(), name, dflt);
    }

    private AvroBytes parseBytes() throws Exception {
        JsonString type = getAttribute("type", JsonString.class, false);
        if (type == null || !type.getValue().equals("bytes")) {
            return null;
        }

        String name = getAttribute("name", JsonString.class, true).getValue();
        JsonString jsonString = getAttribute("default", JsonString.class, false);
        String dflt = jsonString != null ? jsonString.getValue() : null;

        return new AvroBytes(currentObject.getRow(), currentObject.getColumn(), name, dflt);
    }

    private AvroDouble parseDouble() throws Exception {
        JsonString type = getAttribute("type", JsonString.class, false);
        if (type == null || !type.getValue().equals("double")) {
            return null;
        }

        String name = getAttribute("name", JsonString.class, true).getValue();
        JsonNumber jsonNumber = getAttribute("default", JsonNumber.class, false);
        String dflt = jsonNumber != null ? jsonNumber.getValue() : null;

        return new AvroDouble(currentObject.getRow(), currentObject.getColumn(), name, dflt);
    }

    private AvroFloat parseFloat() throws Exception {
        JsonString type = getAttribute("type", JsonString.class, false);
        if (type == null || !type.getValue().equals("float")) {
            return null;
        }

        String name = getAttribute("name", JsonString.class, true).getValue();
        JsonNumber jsonNumber = getAttribute("default", JsonNumber.class, false);
        String dflt = jsonNumber != null ? jsonNumber.getValue() : null;

        return new AvroFloat(currentObject.getRow(), currentObject.getColumn(), name, dflt);
    }

    private AvroLong parseLong() throws Exception {
        JsonString type = getAttribute("type", JsonString.class, false);
        if (type == null || !type.getValue().equals("long")) {
            return null;
        }

        String name = getAttribute("name", JsonString.class, true).getValue();
        JsonNumber jsonNumber = getAttribute("default", JsonNumber.class, false);
        String dflt = jsonNumber != null ? jsonNumber.getValue() : null;

        return new AvroLong(currentObject.getRow(), currentObject.getColumn(), name, dflt);
    }

    private AvroInt parseInt() throws Exception {
        JsonString type = getAttribute("type", JsonString.class, false);
        if (type == null || !type.getValue().equals("int")) {
            return null;
        }

        String name = getAttribute("name", JsonString.class, true).getValue();
        JsonNumber jsonNumber = getAttribute("default", JsonNumber.class, false);
        String dflt = jsonNumber != null ? jsonNumber.getValue() : null;

        return new AvroInt(currentObject.getRow(), currentObject.getColumn(), name, dflt);
    }

    private AvroBoolean parseBoolean() throws Exception {
        JsonString type = getAttribute("type", JsonString.class, false);
        if (type == null || !type.getValue().equals("int")) {
            return null;
        }

        String name = getAttribute("name", JsonString.class, true).getValue();
        JsonLiteral jsonLiteral = getAttribute("default", JsonLiteral.class, false);
        String dflt = jsonLiteral != null ? jsonLiteral.getValue() : null;

        return new AvroBoolean(currentObject.getRow(), currentObject.getColumn(), name, dflt);
    }

    // PARSE OBJECT

    private AvroNode parseObject() throws Exception {
        AvroNode avroNode;
        if ((avroNode = parseComplex()) != null) {
            return avroNode;
        } else if ((avroNode = parsePrimitive()) != null) {
            return avroNode;
        } else {
            System.out.println("MISSING TYPE");
            //throw new UnexpectedTokenException(600, currentToken.getType());
            return null;
        }
    }

    private AvroNode parseComplex() throws Exception {
        AvroNode avroNode;
        if ((avroNode = parseRecord()) != null) {
            return avroNode;
        } else if ((avroNode = parseEnum()) != null) {
            return avroNode;
        } else if ((avroNode = parseArray()) != null) {
            return avroNode;
        } else if ((avroNode = parseMap()) != null) {
            return avroNode;
        } else if ((avroNode = parseUnion()) != null) {
            return avroNode;
        } else if ((avroNode = parseFixed()) != null) {
            return avroNode;
        } else {
            return null;
        }
    }

    private AvroNode parsePrimitive() throws Exception {
        AvroNode avroNode;
        if ((avroNode = parseString()) != null) {
            return avroNode;
        } else if ((avroNode = parseBytes()) != null) {
            return avroNode;
        } else if ((avroNode = parseDouble()) != null) {
            return avroNode;
        } else if ((avroNode = parseFloat()) != null) {
            return avroNode;
        } else if ((avroNode = parseLong()) != null) {
            return avroNode;
        } else if ((avroNode = parseInt()) != null) {
            return avroNode;
        } else if ((avroNode = parseBoolean()) != null) {
            return avroNode;
        } else {
            return null;
        }
    }

//    public String getType() throws Exception {
//        return getAttribute("type", JsonString.class, true).getValue();
//    }

    public void checkName(String name) {

    }

    public void checkAttributes(LinkedList<String> attributes) throws Exception {
        for (JsonPair jsonPair : currentObject.getMembers()) {
            if (!attributes.contains(jsonPair.getKey())) {
                System.out.println(jsonPair.getKey());
                throw new Exception();
            }
        }
    }

    public <T> T getAttribute(String key, Class<T> tClass, boolean required) throws Exception {
        JsonPair jsonPair = currentObject.getMember(key);
        if (jsonPair != null) {
            JsonValue jsonValue = jsonPair.getValue();
            if (jsonValue.getClass().equals(tClass)) {
                T t = (T) jsonValue;
                return t;
            } else {
                if (required) {
                    throw new Exception(); // WRONG TYPE
                } else {
                    return null;
                }
            }
        } else {
            if (required) {
                throw new Exception(); // MISSING ATTRIBUTE
            } else {
                return null;
            }
        }
    }

}
