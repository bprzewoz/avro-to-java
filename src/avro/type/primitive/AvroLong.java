package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-19.
 */
public class AvroLong extends AvroType {

    private long value;
    private String defaultValue;

    public AvroLong(String defaultValue) {
        this.defaultValue = defaultValue;
        if (defaultValue != null) {
            this.value = Long.parseLong(defaultValue);
        }
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue != null ? String.format(" = %s", defaultValue) : "";
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getJavaType() {
        return "long";
    }

}