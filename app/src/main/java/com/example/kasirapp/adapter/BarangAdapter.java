package com.example.kasirapp.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirapp.entity.Barang;

import java.util.ArrayList;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder> {

    private final OnItemClickCallback onItemClickCallback;

    public BarangAdapter(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    private final ArrayList<Barang> listBarang = new ArrayList<>();

    public ArrayList<Barang> getListBarang(){
        return listBarang;
    }

    public void setListBarang(ArrayList<Barang> listBarang){
        if (listBarang.size() > 0){
            this.listBarang.clear();
        }
        this.listBarang.addAll(listBarang);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    // add item
    public void addItem(Barang barang){
        this.listBarang.add(barang);
        notifyItemInserted(listBarang.size() -1);
    }

    // update item
    public void updateItem(int position, Barang barang){
        this.listBarang.set(position, barang);
        notifyItemChanged(position, barang);
    }

    // remove item
    public void removeItem(int position){
        this.listBarang.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listBarang.size());
    }

    public interface OnItemClickCallback {
        void onItemClicked(Barang selectedBarang, Integer position);
    }
}