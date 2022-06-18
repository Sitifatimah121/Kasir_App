package com.example.kasirapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView rvBarang;

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
    }
}