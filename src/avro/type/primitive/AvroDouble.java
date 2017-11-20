package avro.type.primitive;

import avro.type.AvroNode;

/**
 * Created by splbap on 2017-11-19.
 */
public class AvroDouble extends AvroNode {

    private double dflt;

    public AvroDouble(int row, int column, String name, String dflt) {
        super(row, column, name);
        if (dflt != null) {
            this.dflt = Double.parseDouble(dflt);
        }
    }

    public double getDflt() {
        return dflt;
    }

    public void setDflt(double dflt) {
        this.dflt = dflt;
    }

}