package avro.type.complex;

import avro.type.AvroNode;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroMap extends AvroNode {

    private String values;

    public AvroMap(int row, int column, String name, String values) {
        super(row, column, name);
        this.values = values;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String items) {
        this.values = values;
    }

}
