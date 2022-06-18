package com.example.kasirapp.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static final class BarangColumns implements BaseColumns{
        public static final String TABLE_NAME = "barang";

        public static final String KODE = "kode";
        public static final String NAMA_BARANG = "nama_barang";
        public static final String JUMLAH = "jumlah";
        public static final String DATE = "date";
    }
}
