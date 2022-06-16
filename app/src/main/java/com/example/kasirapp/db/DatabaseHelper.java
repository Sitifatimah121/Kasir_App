package com.example.kasirapp.db;

public class DatabaseHelper {
    public static String DATABASE_NAME = "dbnoteapp";

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
}
