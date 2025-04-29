package com.editions.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class NoteDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int version = 1;

    private static final String TABLE_NAME = "Note";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String MESSAGE = "message";

    String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE + " TEXT, " + MESSAGE + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    String selectQuery = "SELECT * FROM " + TABLE_NAME;

    public Context context;
    public NoteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "TABLE CREATED", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            Toast.makeText(context, "TABLE UPDATED", Toast.LENGTH_SHORT).show();
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//END

    //Insert Data in to Database
    public long InsertData(String title, String message) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(MESSAGE, message);
        long result = database.insert(TABLE_NAME, null, values);
        return result;

    }//END

    //Read Data from Database
    public Cursor SelectData() {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(selectQuery, null);
    }

    //Delete Data From database
    public boolean deleteData(String id) {
        SQLiteDatabase sqLiteDatabase =  getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID+" = ?", new String[] {id});
        return true;
    }

    //Update Data from Database
    public boolean updateData(String id, String title, String message){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(MESSAGE, message);
        int result = database.update(TABLE_NAME, contentValues, ID+" = ?", new String[] {id});
        return result > 0;
    }

}

