package avro.type.complex;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroArray extends AvroType {

    private String items;

    public AvroArray(String items) {
        this.items = items;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

}
