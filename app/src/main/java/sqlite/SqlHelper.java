package sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jeff on 2014-12-25.
 */
public class SqlHelper extends SQLiteOpenHelper {
//SQliteOpenHelper job is to open the database if it exists, creating it if it does not, and upgrading it as necessary.

    private static final String DATABASE_NAME = "personalBankerDB";
    private static final String TableName = "TodoTable";
    private static final String Id = "_id";
    private static final String Title = "Title";
    private static final int DATABASE_VERSION = 1;

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    public void remove(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }

    public void update(){

    }

    @Override
    //oncreate is called when the database is created for the first time. Creation of table and initial data inside the tables should  be put here
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE SpendingsTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, Title VARCHAR(255), Amount FLOAT, Category VARCHAR(255), Day INTEGER, Month INTEGER, Year INTEGER);");

    }

    @Override
    //called when the database needs to be updated. Ie when the database is changed. Use this method to drop tables, add tables, etc
    // if you add new columns you can use ALTER TABLE to insert them into a live table
    // if you rename or remove columns, use ALTER TABLE to rename the old table, then create a new table and populate the new table
    // with the contents of the old table
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS SpendingsTable");
        // Recreate table
        onCreate(db);

    }

}
