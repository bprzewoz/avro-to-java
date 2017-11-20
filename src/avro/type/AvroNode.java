package avro.type;

/**
 * Created by splbap on 2017-11-16.
 */
public abstract class AvroNode {

    private int row;
    private int column;
    private String name;

    public AvroNode(int row, int column, String name) {
        this.row = row;
        this.column = column;
        this.name = name;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printNode(int depth) {
        String tab = new String(new char[depth]).replace("\0", "\t");
        System.out.println(String.format("%s%s - %s", tab, name, this.getClass().getSimpleName()));
    }


    public void toJava(String type) {
        System.out.println(String.format("\tprivate %s %s;\n", type, getName()));
        String uppercaseName = getName().substring(0, 1).toUpperCase() + getName().substring(1);
        System.out.println(String.format("\tpublic %s get%s () {\n\t\treturn %s;\n\t }\n", type, uppercaseName, getName()));
        System.out.println(String.format("\tpublic void set%s (%s %s) {\n\t\tthis.%s = %s;\n\t }\n", uppercaseName, type, getName(), getName(), getName()));
    }

}

