package avro.type.primitive;

import avro.type.AvroNode;

/**
 * Created by splbap on 2017-11-19.
 */
public class AvroInt extends AvroNode {

    private int dflt;

    public AvroInt(int row, int column, String name, String dflt) {
        super(row, column, name);
        if (dflt != null) {
            this.dflt = Integer.parseInt(dflt);
        }
    }

    public int getDflt() {
        return dflt;
    }

    public void setDflt(int dflt) {
        this.dflt = dflt;
    }


}
