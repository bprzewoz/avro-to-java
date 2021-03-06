package avro.type;

/**
 * Created by splbap on 2017-11-20.
 */
public abstract class AvroType {

    public void printNode(int depth) {
        String tab = new String(new char[depth]).replace("\0", "\t");
        System.out.println(String.format("%s%s - %s", tab, null, this.getClass().getSimpleName()));
    }

    public String getJavaType() {
        return "";
    }

    public String generateJavaCode() {
        return "";
    }

    public String getDefaultValue() {
        return "";
    }

}
