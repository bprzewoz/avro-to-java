package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroBoolean extends AvroType {

    private boolean dflt;

    public AvroBoolean(String dflt) {
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