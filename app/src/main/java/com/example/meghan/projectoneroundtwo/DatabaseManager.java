package com.example.meghan.projectoneroundtwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager {

    private Context context;
    private SQLHelper helper;
    private SQLiteDatabase db;

    protected static final String DB_NAME = "movies";
    protected static final int DB_VERSION = 1;
    protected static final String DB_TABLE = "ratings";

    protected static final String ID_COL = "_id";
    protected static final String NOTES_NAME_COL = "name";

    private static final String DB_TAG = "DatabaseManager" ;
    private static final String SQLTAG = "SQLHelper" ;

    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close(); //Closes the database - very important!
    }

    //A Bridge between the Database and the ListView, it provides data from the Cursor to the list
    public Cursor getAllNotes() {
        //Fetch all records
        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, NOTES_NAME_COL); //TODO, do I need to add data here?
        return cursor;
    }


    // Add a NOTE to the database.
    // Returns true if movie added, false if movie is already in the database
    public boolean addmmmNote(String name) {
        ContentValues newProduct = new ContentValues();
        newProduct.put(NOTES_NAME_COL, name); //adding name to database

        try {
            db.insertOrThrow(DB_TABLE, null, newProduct);
            Log.d(DB_TAG, "Added note: " + name );
            return true;

        } catch (SQLiteConstraintException sqlce) {
            Log.e(DB_TAG, "error inserting data into table. " +
                    "Name:" + name,  sqlce);
            return false;
        }
    }

    //Method to update the database
    //It's a good idea to delegate Database interaction to a dedicated class
    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context c){
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override //The code to create a table from the SQL stuff
        public void onCreate(SQLiteDatabase db) {
            String createSQLbase = "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT UNIQUE )";

            String createSQL = String.format(createSQLbase, DB_TABLE, ID_COL, NOTES_NAME_COL); //added
            db.execSQL(createSQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
            Log.w(SQLTAG, "Upgrade table - drop and recreate it");
        }
    }
}
