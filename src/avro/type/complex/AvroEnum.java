package avro.type.complex;

import avro.type.AvroType;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroEnum extends AvroType {

    private String name;
    private String namespace;
    private LinkedList<String> symbols;

    public AvroEnum(String name, String namespace, LinkedList<String> symbols) {
        this.name = name;
        this.namespace = namespace;
        this.symbols = symbols;
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

    public LinkedList<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(LinkedList<String> symbols) {
        this.symbols = symbols;
    }

    public String getJavaType() {
        return name;
    }

    public String generateJavaCode() {
        String string = "";

        if (namespace != null) {
            string += String.format("package %s;\n\npublic ", namespace);
        }

        string += String.format("enum %s {\n\n", name);

        for (String symbol : symbols) {
            string += String.format("\n%s", symbol);
            if (!symbols.getLast().equals(symbol)) {
                string += ",\n";
            } else {
                string += "\n";
            }
        }

        string += "}\n\n";
        return string;
    }

}
