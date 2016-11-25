package com.example.danyllo.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Danyllo on 23-11-2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDB.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE = "todolist";
    //private static final String _ID = "_id";

    private String toDoID = "task";
    private String colorID = "colored";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 0, " + toDoID + " TEXT, " + colorID + " TEXT)";
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
        values.put(colorID, String.valueOf(0)); //0 for transparent
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

    public void updateColor(String task, int color) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colorID, String.valueOf(color));
        db.update(TABLE, values, " task = ? ", new String[] {task});
        db.close();
    }

    public void delete(String task) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, " task = ? ", new String[] {task});
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

    public ArrayList<String> readColors() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT _id , " + colorID + " FROM " + TABLE;
        ArrayList<String> colorList = new ArrayList<String>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                colorList.add(cursor.getString(cursor.getColumnIndex(colorID)));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return colorList;
    }
}
