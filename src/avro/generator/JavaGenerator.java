package avro.generator;

import avro.type.AvroType;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-20.
 */
public class JavaGenerator {

    private LinkedList<AvroType> javaClasses;

    public JavaGenerator(LinkedList<AvroType> javaClasses) {
        this.javaClasses = javaClasses;
    }

    public LinkedList<AvroType> getJavaClasses() {
        return javaClasses;
    }

    public void setJavaClasses(LinkedList<AvroType> javaClasses) {
        this.javaClasses = javaClasses;
    }

    public String generateJava() {
        String string = "";
        for (AvroType avroType : javaClasses) {
            string += avroType.generateJavaCode();
        }
        return string;
    }

}
