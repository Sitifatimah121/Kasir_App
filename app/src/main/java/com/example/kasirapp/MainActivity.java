package com.example.kasirapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import com.example.kasirapp.adapter.BarangAdapter;
import com.example.kasirapp.db.BarangHelper;
import com.example.kasirapp.entity.Barang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements LoadBarangCallback {

    private ProgressBar progressBar;
    private RecyclerView rvBarang;
    private BarangAdapter adapter;

    private static final String EXTRA_STATE = "EXTRA_STATE";


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

        if (savedInstanceState == null){
            new LoadBarangAsync(this, this).execute();
        } else {
            ArrayList<Barang> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null){
                adapter.setListBarang(list);
            }
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListBarang());
    }

    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<Barang> barang) {
        progressBar.setVisibility(View.INVISIBLE);
        if (barang.size() > 0) {
            adapter.setListBarang(barang);
        } else {
            adapter.setListBarang(new ArrayList<Barang>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private static class LoadBarangAsync {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadBarangCallback> weakCallback;
        private LoadBarangAsync(Context context, LoadBarangCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }
        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            weakCallback.get().preExecute();
            executor.execute(() -> {
                Context context = weakContext.get();
                BarangHelper barangHelper = BarangHelper.getInstance(context);
                barangHelper.open();
                Cursor dataCursor = barangHelper.queryAll();
                ArrayList<Barang> barangs = MappingHelper.mapCursorToArrayList(dataCursor);
                barangHelper.close();
                handler.post(() -> weakCallback.get().postExecute(barangs));
            });
        }
    }

}

interface LoadBarangCallback {
    void preExecute();
    void postExecute(ArrayList<Barang> barangs);
}