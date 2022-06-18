package com.example.kasirapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Barang implements Parcelable {
    private int id;
    private String kode_barang;
    private String nama_barang;
    private int jumlah_barang;
    private String date;

    public Barang(int id, String kode_barang, String nama_barang, int jumlah_barang, String date){
        this.id = id;
        this.kode_barang = kode_barang;
        this.nama_barang = nama_barang;
        this.jumlah_barang = jumlah_barang;
        this.date = date;
    }

    protected Barang(Parcel in) {
        id = in.readInt();
        kode_barang = in.readString();
        nama_barang = in.readString();
        jumlah_barang = in.readInt();
        date = in.readString();
    }

    public static final Creator<Barang> CREATOR = new Creator<Barang>() {
        @Override
        public Barang createFromParcel(Parcel in) {
            return new Barang(in);
        }

        @Override
        public Barang[] newArray(int size) {
            return new Barang[size];
        }
    };

    public Barang() {

    }

    //getter and setter
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getKode_barang(){
        return kode_barang;
    }

    public void setKode_barang(String kode_barang){
        this.kode_barang = kode_barang;
    }

    public String getNama_barang(){
        return nama_barang;
    }

    public void setNama_barang(String nama_barang){
        this.nama_barang = nama_barang;
    }

    public int getJumlah_barang(){
        return jumlah_barang;
    }

    public void setJumlah_barang(int jumlah_barang){
        this.jumlah_barang = jumlah_barang;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(kode_barang);
        parcel.writeString(nama_barang);
        parcel.writeInt(jumlah_barang);
        parcel.writeString(date);
    }
}
