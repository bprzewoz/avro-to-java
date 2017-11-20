package avro.type.primitive;

import avro.type.AvroNode;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroFloat extends AvroNode {

    private float dflt;

    public AvroFloat(int row, int column, String name, String dflt) {
        super(row, column, name);
        if (dflt != null) {
            this.dflt = Float.parseFloat(dflt);
        }
    }

    public float getDflt() {
        return dflt;
    }

    public void setDflt(float dflt) {
        this.dflt = dflt;
    }

}

