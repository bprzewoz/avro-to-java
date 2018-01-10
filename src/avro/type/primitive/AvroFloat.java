package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroFloat extends AvroType {

    private float value;
    private String defaultValue;

    public AvroFloat(String defaultValue) {
        this.defaultValue = defaultValue;
        if (defaultValue != null) {
            this.value = Float.parseFloat(defaultValue);
        }
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue != null ? String.format(" = %s", defaultValue) : "";
    }

    public void setDefaultValue(String defaultValue){
        this.defaultValue = defaultValue;
    }

    public String getJavaType() {
        return "float";
    }

}