package avro.type.primitive;

import avro.type.AvroNode;

/**
 * Created by splbap on 2017-11-19.
 */
public class AvroLong extends AvroNode {

    private long dflt;

    public AvroLong(int row, int column, String name, String dflt) {
        super(row, column, name);
        if (dflt != null) {
            this.dflt = Long.parseLong(dflt);
        }
    }

    public long getDflt() {
        return dflt;
    }

    public void setDflt(long dflt) {
        this.dflt = dflt;
    }

}