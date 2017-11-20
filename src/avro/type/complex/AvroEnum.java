package avro.type.complex;

import avro.type.AvroNode;

import java.util.LinkedList;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroEnum extends AvroNode {

    private String namespace;
    private LinkedList<String> symbols;

    public AvroEnum(int row, int column, String name, String namespace, LinkedList<String> symbols) {
        super(row, column, name);
        this.namespace = namespace;
        this.symbols = symbols;
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

}
