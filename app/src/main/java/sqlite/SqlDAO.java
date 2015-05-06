package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Entries.DateC;
import Entries.Entry;

/**
 * Created by jeff on 2014-12-25.
 */
public class SqlDAO {
    private SQLiteDatabase sqliteDatabase;
    private SqlHelper sqlHelper;

    public SqlDAO(Context context) {
        sqlHelper = new SqlHelper(context);
        sqliteDatabase = sqlHelper.getWritableDatabase();
    }

    public void close() {
        sqliteDatabase.close();
    }

    public void createSpending(String title, double amount, String category, int day, int month, int year) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", title);
        contentValues.put("Amount", amount);
        contentValues.put("Category", category);
        contentValues.put("Day", day);
        contentValues.put("Month", month);
        contentValues.put("Year", year);
        sqliteDatabase.insert("SpendingsTable", null, contentValues);

    }

    public void removeEntryFromDAO(Context context) {
        //sqliteDatabase.delete("SpendingsTable", "_id = " + entryID, null);
        sqlHelper.remove(context);
    }

    public void delete(int id){
        sqliteDatabase.delete("SpendingsTable", "_id = " + id, null);
    }

    public void deleteByName(String name){
        sqliteDatabase.delete("SpendingsTable", "Title =?", new String[]{name});
    }


    public void removeAllEntries() {
        sqliteDatabase.delete("SpendingsTable", null, null);
        sqlHelper.onUpgrade(sqliteDatabase, 1, 2);
    }

    public List<Entry> getEntries() {
        List<Entry> listOfEntries = new ArrayList<Entry>();

        String[] tableColumns = new String[]{"_id", "Title", "Amount", "Category", "Day", "Month", "Year"};

        Cursor cursor = sqliteDatabase.query("SpendingsTable", tableColumns, null, null, null, null, null, null);
        cursor.moveToFirst();
        //!cursor.isAfterLast()
        while (!cursor.isAfterLast()) {
            int nameIndex = cursor.getColumnIndex("Title");
            int amountIndex = cursor.getColumnIndex("Amount");
            int categoryIndex = cursor.getColumnIndex("Category");
            int dayIndex = cursor.getColumnIndex("Day");
            int monthIndex = cursor.getColumnIndex("Month");
            int yearIndex = cursor.getColumnIndex("Year");
            DateC date = new DateC(cursor.getInt(dayIndex), cursor.getInt(monthIndex), cursor.getInt(yearIndex));
            Entry entry = new Entry(cursor.getString(nameIndex), cursor.getInt(amountIndex), cursor.getString(categoryIndex), date);
            entry.setId(cursor.getInt(0));

            listOfEntries.add(entry);
            cursor.moveToNext();
        }

        return listOfEntries;

    }
}
