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

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public LinkedList<AvroNode> getFields() {
        return fields;
    }

    public void setFields(LinkedList<AvroNode> fields) {
        this.fields = fields;
    }

    public void printNode(int depth) {
        super.printNode(depth);
        for (AvroNode avroNode : fields) {
            if (avroNode != null) { // NA CZAS TESTOW
                avroNode.printNode(depth + 1);
            }
        }
    }


}
