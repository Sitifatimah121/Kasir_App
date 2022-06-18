package com.example.kasirapp.db;

import static com.example.kasirapp.db.DatabaseContract.BarangColumns.TABLE_NAME;
import static com.example.kasirapp.db.DatabaseContract.BarangColumns;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbbarangapp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_BARANG = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER," +
                    " %s TEXT NOT NULL)",
            TABLE_NAME,
            BarangColumns._ID,
            BarangColumns.KODE,
            BarangColumns.NAMA_BARANG,
            BarangColumns.JUMLAH,
            BarangColumns.DATE
    );

    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_BARANG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
