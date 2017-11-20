package avro.type.complex;

import avro.type.AvroField;
import avro.type.AvroType;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-16.
 */
public class AvroRecord extends AvroType {

    private String name;
    private String namespace;
    private LinkedList<AvroField> fields;

    public AvroRecord(String name, String namespace, LinkedList<AvroField> fields) {
        this.name = name;
        this.namespace = namespace;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public LinkedList<AvroField> getFields() {
        return fields;
    }

    public void setFields(LinkedList<AvroField> fields) {
        this.fields = fields;
    }

    public void printNode(int depth) {
        super.printNode(depth);
        for (AvroField avroField : fields) {
            if (avroField != null) { // NA CZAS TESTOW
                avroField.printNode(depth + 1);
            }
        }
    }

}
