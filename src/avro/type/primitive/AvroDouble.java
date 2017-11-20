package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-19.
 */
public class AvroDouble extends AvroType {

    private double dflt;

    public AvroDouble(String dflt) {
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