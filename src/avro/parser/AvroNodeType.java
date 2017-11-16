package avro.parser;

/**
 * Created by splbap on 2017-11-12.
 */
public enum AvroNodeType {
    ROOT,
    RECORD,
    ENUM,
    ARRAY,
    MAP,
    UNION,
    FIXED,
    PRIMITIVE,
    ERROR
}
