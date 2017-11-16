package avro.parser.node;

import avro.parser.AvroNode;
import avro.parser.AvroNodeType;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by splbap on 2017-11-12.
 */
public class AvroRecordNode extends AvroNode {

    private String namespace;
    private List<AvroNode> fields;

    public AvroRecordNode(String name, String namespace, AvroNodeType avroNodeType) {
        super(name, avroNodeType);
        this.namespace = namespace;
        this.fields = new LinkedList<AvroNode>();
    }

    public void addField(AvroNode avroNode) {
        fields.add(avroNode);
    }

}
