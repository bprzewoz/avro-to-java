package json.type.primitive;

/**
 * Created by splbap on 2017-11-15.
 */
public class JsonNumber extends JsonPrimitive {

    private double number;

    public JsonNumber(int row, int column, String value) {
        super(row, column, value);
        number = Double.parseDouble(value);
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

}
