package com.example.kasirapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirapp.R;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvKode.setText(listBarang.get(position).getKode_barang());
        holder.tvName.setText(listBarang.get(position).getNama_barang());
        holder.tvJumlah.setText(listBarang.get(position).getJumlah_barang());
        holder.tvDate.setText(listBarang.get(position).getDate());
        holder.cvBarang.setOnClickListener(v -> onItemClickCallback.onItemClicked(listBarang.get(position), position));
    }

    @Override
    public int getItemCount() {
        return listBarang.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvKode, tvName, tvJumlah, tvDate;
        final CardView cvBarang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKode = itemView.findViewById(R.id.tv_item_code);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvJumlah = itemView.findViewById(R.id.tv_item_stok);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            cvBarang = itemView.findViewById(R.id.cv_item_note);
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
