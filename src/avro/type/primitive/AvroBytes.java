package avro.type.primitive;

import avro.type.AvroNode;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroBytes extends AvroNode {

    private String dflt;

    public AvroBytes(int row, int column, String name, String dflt) {
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