package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-19.
 */
public class AvroDouble extends AvroType {

    private double value;
    private String defaultValue;

    public AvroDouble(String defaultValue) {
        this.defaultValue = defaultValue;
        if (defaultValue != null) {
            this.value = Double.parseDouble(defaultValue);
        }
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue != null ? String.format(" = %s", defaultValue) : "";
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getJavaType() {
        return "double";
    }

}