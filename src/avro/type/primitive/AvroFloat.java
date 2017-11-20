package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroFloat extends AvroType {

    private float dflt;

    public AvroFloat(String dflt) {
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

