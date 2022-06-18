package com.example.kasirapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Barang implements Parcelable {
    private int id;
    private String kode_barang;
    private String nama_barang;
    private int jumlah_barang;
    private String date;

    //getter and setter
    public int getId(){
        return id;
    }

    public void setId(int id){ this.id = id; }

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
        parcel.writeInt(this.id);
        parcel.writeString(this.kode_barang);
        parcel.writeString(this.nama_barang);
        parcel.writeInt(this.jumlah_barang);
        parcel.writeString(this.date);
    }

    public Barang() {

    }

    public Barang(int id, String kode_barang, String nama_barang, int jumlah_barang, String date){
        this.id = id;
        this.kode_barang = kode_barang;
        this.nama_barang = nama_barang;
        this.jumlah_barang = jumlah_barang;
        this.date = date;
    }

    protected Barang(Parcel in) {
        this.id = in.readInt();
        this.kode_barang = in.readString();
        this.nama_barang = in.readString();
        this.jumlah_barang = in.readInt();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Barang> CREATOR = new Parcelable.Creator<Barang>() {
        @Override
        public Barang createFromParcel(Parcel in) {
            return new Barang(in);
        }

        @Override
        public Barang[] newArray(int size) {
            return new Barang[size];
        }
    };

}
