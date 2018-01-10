package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroBytes extends AvroType {

    private byte value;
    private String defaultValue;

    public AvroBytes(String defaultValue) {
        this.defaultValue = defaultValue;
        if (defaultValue != null) {
            this.value = Byte.parseByte(defaultValue);
        }
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue != null ? String.format(" = %s", defaultValue) : "";
    }

    public void setDefaultValue(String defaultValue){
        this.defaultValue = defaultValue;
    }

    public String getJavaType() {
        return "byte";
    }

}