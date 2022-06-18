package com.example.kasirapp;

import android.database.Cursor;

import com.example.kasirapp.db.DatabaseContract;
import com.example.kasirapp.entity.Barang;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Barang> mapCursorToArrayList(Cursor barangCursor){
        ArrayList<Barang> barangList = new ArrayList<>();

        while (barangCursor.moveToNext()){
            int id = barangCursor.getInt(barangCursor.getColumnIndexOrThrow(DatabaseContract.BarangColumns._ID));
            String kode = barangCursor.getString(barangCursor.getColumnIndexOrThrow(DatabaseContract.BarangColumns.KODE));
            String name = barangCursor.getString(barangCursor.getColumnIndexOrThrow(DatabaseContract.BarangColumns.NAMA_BARANG));
            int jumlah = barangCursor.getInt(barangCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContract.BarangColumns.JUMLAH)));
            String date = barangCursor.getString(barangCursor.getColumnIndexOrThrow(DatabaseContract.BarangColumns.DATE));
            barangList.add(new Barang(id, kode, name, jumlah, date));
        }

        return barangList;
    }
}
