package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import Model.Model_class;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "grocery";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ITEM = "item";
    public static final String COLUMN_PRICE = "price";


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Grocery.db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating TablesQ
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL("create table grocery" +
                "(id integer primary key autoincrement, item text, price text)");

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS grocery");

        // Create tables again
        onCreate(db);
    }

    public long insertGrocery(String item, String price) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_ITEM, item);
        values.put(COLUMN_PRICE, price);

        // insert row
        long id = db.insert(TABLE_NAME, null, values);


        // close db connection

        // return newly inserted row id
        return id;
    }

    public Model_class getGrocery (long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Model_class.TABLE_NAME,
                new String[]{Model_class.COLUMN_ID, Model_class.COLUMN_ITEM, Model_class.COLUMN_PRICE},
                Model_class.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Model_class grocery = new Model_class(
                cursor.getInt(cursor.getColumnIndex(Model_class.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Model_class.COLUMN_ITEM)),
                cursor.getString(cursor.getColumnIndex(Model_class.COLUMN_PRICE)));

        // close the db connection
        cursor.close();

        return grocery;
    }

    public List<Model_class> getAllGroceries() {
        List<Model_class> groceries = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Model_class.TABLE_NAME + " ORDER BY " +
                Model_class.COLUMN_PRICE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Model_class grocery = new Model_class();
                grocery.setId(cursor.getInt(cursor.getColumnIndex(Model_class.COLUMN_ID)));
                grocery.setItem(cursor.getString(cursor.getColumnIndex(Model_class.COLUMN_ITEM)));
                grocery.setPrice(cursor.getString(cursor.getColumnIndex(Model_class.COLUMN_PRICE)));

                groceries.add(grocery);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return groceries;
    }

    public int getGroceryCount() {
        String countQuery = "SELECT  * FROM " + Model_class.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

   /* public int updateGrocery(Model_class grocry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Model_class.COLUMN_ITEM, Model_class.getItem());
        values.put(Model_class.COLUMN_PRICE, Model_class.getPrice());


        // updating row
        return db.update(Model_class.TABLE_NAME, values, Model_class.COLUMN_ID + " = ?",
                new String[]{String.valueOf(grocry.getId())});
    }

    */

    public void deleteGrocery(Model_class grocery) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Model_class.TABLE_NAME, Model_class.COLUMN_ID + " = ?",
                new String[]{String.valueOf(grocery.getId())});
        db.close();
    }

}
