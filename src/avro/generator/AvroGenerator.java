package avro.generator;

import avro.type.complex.AvroRecord;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroGenerator {

    private AvroRecord avroRecord;

    public AvroGenerator(AvroRecord avroRecord) {
        this.avroRecord = avroRecord;
    }

    public AvroRecord getAvroRecord() {
        return avroRecord;
    }

    public void setAvroRecord(AvroRecord avroRecord) {
        this.avroRecord = avroRecord;
    }

    public void generateJava() {
        System.out.println("GENERATE JAVA");
    }

}
