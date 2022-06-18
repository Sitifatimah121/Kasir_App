package com.example.kasirapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.kasirapp.adapter.BarangAdapter;
import com.example.kasirapp.entity.Barang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView rvBarang;
    private BarangAdapter adapter;

    final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    if (result.getResultCode() == BarangCrudActivity.RESULT_ADD) {
                        Barang barang = result.getData().getParcelableExtra(BarangCrudActivity.EXTRA_BARANG);
                        adapter.addItem(barang);
                        rvBarang.smoothScrollToPosition(adapter.getItemCount() - 1);
                        showSnackbarMessage("Satu item berhasil ditambahkan");
                    } else if (result.getResultCode() == BarangCrudActivity.RESULT_UPDATE) {
                        Barang barang = result.getData().getParcelableExtra(BarangCrudActivity.EXTRA_BARANG);
                        int position = result.getData().getIntExtra(BarangCrudActivity.EXTRA_POSITION, 0);
                        adapter.updateItem(position, barang);
                        rvBarang.smoothScrollToPosition(position);
                        showSnackbarMessage("Satu item berhasil diubah");
                    } else if (result.getResultCode() == BarangCrudActivity.RESULT_DELETE) {
                        int position = result.getData().getIntExtra(BarangCrudActivity.EXTRA_POSITION, 0);
                        adapter.removeItem(position);
                        showSnackbarMessage("Satu item berhasil dihapus");
                    }
                }
            });

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvBarang, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Barang");

        progressBar = findViewById(R.id.progressbar);
        rvBarang = findViewById(R.id.rv_barang);
        rvBarang.setLayoutManager(new LinearLayoutManager(this));
        rvBarang.setHasFixedSize(true);

        adapter = new BarangAdapter(new BarangAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Barang selectedBarang, Integer position) {
                Intent intent = new Intent(MainActivity.this, BarangCrudActivity.class);
                intent.putExtra(BarangCrudActivity.EXTRA_BARANG, selectedBarang);
                intent.putExtra(BarangCrudActivity.EXTRA_POSITION, position);
                resultLauncher.launch(intent);
            }
        });
        rvBarang.setAdapter(adapter);

        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BarangCrudActivity.class);
            resultLauncher.launch(intent);
        });
    }
}