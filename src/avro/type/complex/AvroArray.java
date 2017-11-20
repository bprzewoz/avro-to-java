package avro.type.complex;

import avro.type.AvroNode;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroArray extends AvroNode {

    private String items;

    public AvroArray(int row, int column, String name, String items) {
        super(row, column, name);
        this.items = items;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

}
