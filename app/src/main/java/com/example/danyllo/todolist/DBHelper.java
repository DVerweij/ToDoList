package com.example.danyllo.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Danyllo on 23-11-2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "todolist";

    private String toDoID = "task";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + toDoID + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void create(String task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(toDoID, task);
        db.insert(TABLE, null, values);
        db.close();
    }

    public void update(String task, int taskID) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(toDoID, task);
        db.update(TABLE, values, "_id = ? ", new String[] {String.valueOf(taskID)});
        db.close();
    }

    public void delete(int taskID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, " _id = ? ", new String[] {String.valueOf(taskID)});
        db.close();
    }

    public ArrayList<String> read() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT _id , " + toDoID + " FROM " + TABLE;
        ArrayList<String> toDoList = new ArrayList<String>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                toDoList.add(cursor.getString(cursor.getColumnIndex(toDoID)));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return toDoList;
    }
}
