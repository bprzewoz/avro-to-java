package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroBoolean extends AvroType {

    private boolean value;
    private String defaultValue;

    public AvroBoolean(String defaultValue) {
        this.defaultValue = defaultValue;
        if (defaultValue != null) {
            this.value = Boolean.parseBoolean(defaultValue);
        }
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue != null ? String.format(" = %s", defaultValue) : "";
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getJavaType() {
        return "boolean";
    }

}