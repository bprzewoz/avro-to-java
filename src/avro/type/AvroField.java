package avro.type;

/**
 * Created by splbap on 2017-11-20.
 */
public class AvroField {

    private String name;
    private AvroType type;

    public AvroField(String name, AvroType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AvroType getType() {
        return type;
    }

    public void setType(AvroType type) {
        this.type = type;
    }

    public void printNode(int depth) {
        String tab = new String(new char[depth]).replace("\0", "\t");
        System.out.println(String.format("%s%s - %s", tab, name, type.getClass().getSimpleName()));
    }

    public String getJavaDeclaration() {
        return String.format("\tprivate %s %s%s;", type.getJavaType(), name, type.getDefaultValue());
    }

    public String getJavaGetter() {
        return String.format("\tpublic %s get%s() {\n\t\treturn %s;\n\t}\n", type.getJavaType(), capitalizeString(name), name);
    }

    public String getJavaSetter() {
        return String.format("\tpublic void set%s(%s %s) {\n\t\tthis.%s = %s;\n\t}\n", capitalizeString(name), type.getJavaType(), name, name, name);
    }

    private String capitalizeString(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

}