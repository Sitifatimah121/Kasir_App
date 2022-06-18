package com.example.kasirapp.db;

import static android.provider.BaseColumns._ID;

import static com.example.kasirapp.db.DatabaseContract.BarangColumns.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BarangHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static BarangHelper INSTANCE;

    private static SQLiteDatabase database;

    private BarangHelper(Context context){
        dataBaseHelper = new DatabaseHelper(context);
    }


    public static BarangHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new BarangHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    //open close connection
    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    //get data
    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC");
    }

    //get data by id

    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    // insert data
    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    //update data
    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    //delete data
    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
