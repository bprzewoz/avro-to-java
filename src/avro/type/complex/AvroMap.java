package avro.type.complex;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroMap extends AvroType {

    private String values;

    public AvroMap(String values) {
        this.values = values;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String items) {
        this.values = values;
    }

}
