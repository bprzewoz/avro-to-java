package avro.type.complex;

import avro.type.AvroNode;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-16.
 */
public class AvroRecord extends AvroNode {

    private String namespace;
    private LinkedList<AvroNode> fields;

    public AvroRecord(int row, int column, String name, String namespace, LinkedList<AvroNode> fields) {
        super(row, column, name);
        this.namespace = namespace;
        this.fields = fields;
    }

    public LinkedList<AvroNode> getMembers() {
        return fields;
    }

    public void setMembers(LinkedList<AvroNode> fields) {
        this.fields = fields;
    }

}
