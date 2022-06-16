package com.example.kasirapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbbarangapp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_BARANG = String.format(
            "CREATE TABLE %S" + "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s VARCHAR NOT NULL," +
                    " %s VARCHAR NOT NULL," +
                    " %s VARCHAR NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NAME,
            DatabaseContract.BarangColumns._ID,
            DatabaseContract.BarangColumns.KODE,
            DatabaseContract.BarangColumns.NAMA_BARANG,
            DatabaseContract.BarangColumns.JUMLAH,
            DatabaseContract.BarangColumns.DATE
    );

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_BARANG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(db);
    }
}
