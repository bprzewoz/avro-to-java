package avro.type.complex;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroArray extends AvroType {

    private AvroType items;

    public AvroArray(AvroType items) {
        this.items = items;
    }

    public AvroType getItems() {
        return items;
    }

    public void setItems(AvroType items) {
        this.items = items;
    }

    public String getJavaType() {
        return String.format("%s[]", items.getJavaType());
    }

}
