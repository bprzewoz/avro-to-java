package avro.type.primitive;

import avro.type.AvroNode;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroBoolean extends AvroNode {

    private boolean dflt;

    public AvroBoolean(int row, int column, String name, String dflt) {
        super(row, column, name);
        if (dflt != null) {
            this.dflt = Boolean.parseBoolean(dflt);
        }
    }

    public boolean getDflt() {
        return dflt;
    }

    public void setDflt(boolean dflt) {
        this.dflt = dflt;
    }

}