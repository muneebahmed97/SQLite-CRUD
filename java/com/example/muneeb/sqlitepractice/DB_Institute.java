package com.example.muneeb.sqlitepractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

public class DB_Institute {

    private Context context;
    private SQLiteDatabase ourDB;
    private DBHelper dbHelper;

    DB_Institute(Context c) {
        this.context = c;
    }

    /*********************
     * DB Name and Version
     *********************/
    private static final String DB_NAME = "institute";
    private static final int DB_VERSION = 1;

    /*********************
     * Table Name and Fields
     *********************/
    private static final String TABLE_STUDENT = "Student";
    private static final String STUDENT_FNAME = "fname";
    private static final String STUDENT_LNAME = "lname";

    private static final String CREATE_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_STUDENT +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            STUDENT_FNAME + " TEXT, " +
            STUDENT_LNAME + " TEXT)" ;

    /********************
     * CREATE NEW STUDENT
     ********************/
    public long newStudent(String FName, String LName) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(STUDENT_FNAME, FName);
        contentValues.put(STUDENT_LNAME, LName);

        return ourDB.insert(TABLE_STUDENT, null, contentValues);
    }

    /*********************
     * GETTING ALL RECORDS
     *********************/
    public String getAllRecords() {
        String result = "";

        String[] columns = {"id", STUDENT_FNAME, STUDENT_LNAME};
        Cursor cursor = ourDB.rawQuery("SELECT * FROM " + TABLE_STUDENT + " order by id", null);

        int iID = cursor.getColumnIndex("id");
        int iFName = cursor.getColumnIndex(STUDENT_FNAME);
        int iLName = cursor.getColumnIndex(STUDENT_LNAME);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            result += "ID: " + cursor.getString(iID) + " " + cursor.getString(iFName) +
            " " + cursor.getString(iLName) + " \n";
        }

        return result;
    }

    /*********************
     * UPDATE RECORDS
     *********************/
    public void updatingRecords(int ID, String FName, String LName) {
        ourDB.execSQL("UPDATE " + TABLE_STUDENT + " SET " +
                    STUDENT_FNAME + " = '" + FName + "', " +
                    STUDENT_LNAME + " = '" + LName + "'" +
                    "WHERE ID = " + ID);
    }

    /*********************
     * DELETE RECORDS
     *********************/
    public void deletingRecords(int ID) {
        ourDB.execSQL("DELETE FROM " + TABLE_STUDENT + " " +
                "WHERE ID = " + ID);
    }

    /*********************
     * CREATE DB AND TABLES
     *********************/
    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_STUDENT_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
            onCreate(db);
        }
    }

    /************************
     * DB OPENING AND OPENING
     ************************/
    public DB_Institute open() throws SQLException {
        dbHelper = new DBHelper(context);
        ourDB = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }
}