package avro.type.primitive;

import avro.type.AvroNode;

/**
 * Created by splbap on 2017-11-18.
 */
public class AvroString extends AvroNode {

    private String dflt;

    public AvroString(int row, int column, String name, String dflt) {
        super(row, column, name);
        this.dflt = dflt;
    }

    public String getDflt() {
        return dflt;
    }

    public void setDflt(String dflt) {
        this.dflt = dflt;
    }

}
