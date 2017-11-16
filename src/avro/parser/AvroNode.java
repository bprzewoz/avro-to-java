package avro.parser;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-12.
 */
public class AvroNode {

    protected String name;
    protected AvroNodeType avroNodeType;
    protected LinkedList<AvroNode> fieldsList;

    public AvroNode(String name, AvroNodeType avroNodeType) {
        this.name = name;
        this.avroNodeType = avroNodeType;
        this.fieldsList = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AvroNodeType getAvroNodeType() {
        return avroNodeType;
    }

}
