package avro.type;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroField {

    private String name;
    private AvroNode type;

    public AvroField(String name, AvroNode type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AvroNode getType() {
        return type;
    }

    public void setType(AvroNode type) {
        this.type = type;
    }

    public void printNode(int depth) {
        String tab = new String(new char[depth]).replace("\0", "\t");
        System.out.println(String.format("%s%s - %s", tab, name, this.getType().getClass().getSimpleName()));
    }

}
