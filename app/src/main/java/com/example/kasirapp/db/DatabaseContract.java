package com.example.kasirapp.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static final class BarangColumns implements BaseColumns{
        public static String TABLE_NAME = "barang";

        public static  String KODE = "kode";
        public static String NAMA_BARANG = "nama_barang";
        public static int JUMLAH = 0;
        public static String DATE = "date";
    }
}
