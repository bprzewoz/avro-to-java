package avro.type;

/**
 * Created by splbap on 2017-11-16.
 */
public class AvroNode {

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

    public void printNode(int depth, String string) {
        String tab = new String(new char[depth]).replace("\0", "\t");
        System.out.println(String.format("%s%s - %s", tab, string, this.getClass().getSimpleName()));
    }

}

