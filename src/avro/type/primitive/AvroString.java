package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-18.
 */
public class AvroString extends AvroType {

    private String defaultValue;

    public AvroString(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue != null ? String.format(" = %s", defaultValue) : "";
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getJavaType() {
        return "String";
    }

}
