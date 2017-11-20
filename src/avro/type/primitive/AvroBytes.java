package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroBytes extends AvroType {

    private String dflt;

    public AvroBytes(String dflt) {
        this.dflt = dflt;
    }

    public String getDflt() {
        return dflt;
    }

    public void setDflt(String dflt) {
        this.dflt = dflt;
    }

}