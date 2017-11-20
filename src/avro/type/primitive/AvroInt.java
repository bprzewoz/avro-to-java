package avro.type.primitive;

import avro.type.AvroType;

/**
 * Created by splbap on 2017-11-19.
 */
public class AvroInt extends AvroType {

    private int dflt;

    public AvroInt(String dflt) {
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
