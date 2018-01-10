package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-19.
 */
public class AvroInt extends AvroType {

    private int value;
    private String defaultValue;

    public AvroInt(String defaultValue) {
        this.defaultValue = defaultValue;
        if (defaultValue != null) {
            this.value = Integer.parseInt(defaultValue);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue != null ? String.format(" = %s", defaultValue) : "";
    }

    public void setDefaultValue(String defaultValue){
        this.defaultValue = defaultValue;
    }

    public String getJavaType() {
        return "int";
    }

}
