package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-18.
 */
public class AvroString extends AvroType {

    private String dflt;

    public AvroString(String dflt) {
        this.dflt = dflt;
    }

    public String getDflt() {
        return dflt;
    }

    public void setDflt(String dflt) {
        this.dflt = dflt;
    }

}
