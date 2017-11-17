package avro.parser;

import avro.type.AvroNode;
import avro.type.complex.AvroRecord;
import files.FileHandler;
import json.lexer.JsonLexer;
import json.parser.JsonParser;
import json.type.complex.JsonObject;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-12.
 */
public class AvroParser {

    private JsonObject currentObject;

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler("inputFile.txt", "outputFile.txt");
        JsonLexer jsonLexer = new JsonLexer(fileHandler);
        JsonParser jsonParser = new JsonParser(jsonLexer);
        JsonObject jsonObject = jsonParser.parseFile();
        if (jsonObject != null) {
            AvroParser avroParser = new AvroParser(jsonObject);
            avroParser.parseFile();
        }
        fileHandler.closeFiles();
    }

    public AvroParser(JsonObject jsonObject) {
        this.currentObject = jsonObject;
    }

    public AvroRecord parseFile() {
        return parseRecord();
    }

    private AvroRecord parseRecord() {
        String type = currentObject.getStringValue("type");
        if (type == null || type.equals("record")) {
            return null;
        }

        String name = currentObject.getStringValue("name");
        if (name == null) {
            return null;
        }

        String namespace = currentObject.getStringValue("namespace");
        if (namespace == null) {
            return null;
        }

        // parseFields() ?

        return new AvroRecord(currentObject.getRow(), currentObject.getColumn(), name, namespace, new LinkedList<AvroNode>());
    }

    private AvroNode parseEnum() {
        return null;
    }

    private AvroNode parseArray() {
        return null;
    }

    private AvroNode parseMap() {
        return null;
    }

    private AvroNode parseUnion() {
        return null;
    }

    private AvroNode parseFixed() {
        return null;
    }

    private AvroNode parsePrimitive() {
        return null;
    }

    private AvroNode parseObject() {
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
        } else if ((avroNode = parsePrimitive()) != null) {
            return avroNode;
        } else {
            //throw new UnexpectedTokenException(600, currentToken.getType());
            return null;
        }
    }

}
