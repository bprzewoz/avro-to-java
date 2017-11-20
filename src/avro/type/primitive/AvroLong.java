package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-19.
 */
public class AvroLong extends AvroType {

    private long dflt;

    public AvroLong(String dflt) {
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