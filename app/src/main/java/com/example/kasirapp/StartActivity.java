package com.example.kasirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btnBarang = findViewById(R.id.btnBarang);
        btnBarang.setOnClickListener(this);
//        Button btnTrans = findViewById(R.id.btnTrans);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnBarang){
            Intent barangIntent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(barangIntent);
        }
    }

}