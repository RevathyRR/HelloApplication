package Model;

public class Model_class {
    public static final String TABLE_NAME = "grocery";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ITEM = "item";
    public static final String COLUMN_PRICE = "price";

    private String item;
    private int  id;
    private double price;


    public static final String CREATE_TABLE =
            "CREATE TABLE" + TABLE_NAME +"("
                    + COLUMN_ID +"INTEGR PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_ITEM + "TEXT, "
                    + COLUMN_PRICE + "INTEGER"
                    + ")";

    public Model_class() {

    }

    public Model_class(int id, String item, double price) {
        this.id = id;
        this.item = item;
        this.price = price;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
