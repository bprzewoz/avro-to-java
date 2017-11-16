package json.type.complex;

/**
 * Created by splbap on 2017-11-15.
 */
public class JsonPair {

    private String key;
    private JsonValue value;

    public JsonPair(String key, JsonValue value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public JsonValue getValue() {
        return value;
    }

    public void setValue(JsonValue value) {
        this.value = value;
    }

    public void printTree(int depth) {
        String tab = new String(new char[depth]).replace("\0", "\t");
        System.out.println(String.format("%s%s - %s", tab, key, this.getClass().getSimpleName()));
        value.printTree(depth + 1);
    }

}
