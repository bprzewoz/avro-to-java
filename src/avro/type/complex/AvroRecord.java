package avro.type.complex;

import avro.type.AvroField;
import avro.type.AvroType;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-16.
 */
public class AvroRecord extends AvroType {

    private String name;
    private String namespace;
    private LinkedList<AvroField> fields;

    public AvroRecord(String name, String namespace, LinkedList<AvroField> fields) {
        this.name = name;
        this.namespace = namespace;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public LinkedList<AvroField> getFields() {
        return fields;
    }

    public void setFields(LinkedList<AvroField> fields) {
        this.fields = fields;
    }

    public void printNode(int depth) {
        super.printNode(depth);
        for (AvroField avroField : fields) {
            if (avroField != null) { // NA CZAS TESTOW
                avroField.printNode(depth + 1);
            }
        }
    }

    public String getJavaType() {
        return name;
    }

    public String generateJavaCode() {
        String string = "";

        if (namespace != null) {
            string += String.format("package %s;\n\npublic ", namespace); // NAZWA PAKIETU
        }

        string += String.format("class %s {\n\n", name); // NAZWA KLASY

        for (AvroField avroField : fields) { // DEKLARACJE ZMIENNYCH
            string += String.format("%s\n", avroField.getJavaDeclaration());
            if (fields.getLast().equals(avroField)) {
                string += "\n";
            }
        }

        string += String.format("\tpublic %s () {\n\t}\n\n", name); // KONSTRUKTOR BEZARGUMENTOWY

        { // KONSTRUKTOR ARGUMENTOWY
            string += String.format("\tpublic %s (", name);
            for (AvroField avroField : fields) {
                string += String.format("%s %s", avroField.getType().getJavaType(), avroField.getName());
                if (!fields.getLast().equals(avroField)) {
                    string += ", ";
                }
            }
            string += ") {\n";
            for (AvroField avroField : fields) {
                string += String.format("\t\tthis.%s = %s;\n", avroField.getName(), avroField.getName());
            }
            string += "\t}\n\n";
        }

        for (AvroField avroField : fields) {
            string += String.format("%s\n", avroField.getJavaGetter());
            string += String.format("%s\n", avroField.getJavaSetter());
        }

        string += "}\n\n";

        return string;
    }

}
