package com.example.kasirapp.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_NAME = "barang";

    static final class BarangColumns implements BaseColumns{
        static  String KODE = "kode";
        static String NAMA_BARANG = "nama_barang";
        static int JUMLAH = 0;
        static String DATE = "date";
    }
}
