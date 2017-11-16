package avro.parser;

import avro.parser.node.AvroRecordNode;
import json.parser.JsonNode;
import json.parser.JsonNodeType;

/**
 * Created by splbap on 2017-11-12.
 */
public class AvroParser {

    private JsonNode currentNode;

    public AvroParser(JsonNode jsonRoot) {
        this.currentNode = jsonRoot;
    }

    public AvroNode parseNode() {
        AvroNodeType avroNodeType = AvroNodeType.valueOf(getPairValue("type", JsonNodeType.STRING));
        switch (avroNodeType) {
            case RECORD:
                return parseRecord();
            case ENUM:
                return parseEnum();
            case ARRAY:
                return parseArray();
            case MAP:
                return parseMap();
            case UNION:
                return parseUnion();
            case FIXED:
                return parseFixed();
//            case
//                return parsePrimitive();
        }
        return parseRecord();
    }

    public AvroNode parseRecord() {
        AvroNode avroRecord = new AvroRecordNode(getPairValue("name", JsonNodeType.STRING), getPairValue("namespace", JsonNodeType.STRING), AvroNodeType.RECORD);

        // TO DO

        return avroRecord;
    }

    public AvroNode parseEnum() {
        return null;
    }

    public AvroNode parseArray() {
        return null;
    }

    public AvroNode parseMap() {
        return null;
    }

    public AvroNode parseUnion() {
        return null;
    }

    public AvroNode parseFixed() {
        return null;
    }

    public void parsePrimitive() {

    }

    private String getPairValue(String string, JsonNodeType jsonNodeType) {
        JsonNode nameNode;
        JsonNode parentNode;
        if ((parentNode = currentNode.getMember(string)) != null) {
            if (parentNode.getJsonNodeType() == JsonNodeType.PAIR) {
                if ((nameNode = parentNode.getMember(1)).getJsonNodeType() == jsonNodeType) {
                    return nameNode.getMember(1).getString();
                } else {
                    // throw
                }
            } else {
                // throw
            }
        } else {
            // throw
        }
        return null;
    }
}
