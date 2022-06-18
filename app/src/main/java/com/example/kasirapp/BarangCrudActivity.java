package com.example.kasirapp;

import static android.content.ContentValues.TAG;
import static com.example.kasirapp.db.DatabaseContract.BarangColumns.DATE;
import static com.example.kasirapp.db.DatabaseContract.BarangColumns.JUMLAH;
import static com.example.kasirapp.db.DatabaseContract.BarangColumns.KODE;
import static com.example.kasirapp.db.DatabaseContract.BarangColumns.NAMA_BARANG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kasirapp.db.BarangHelper;
import com.example.kasirapp.entity.Barang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BarangCrudActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtKode, edtName, edtJumlah;

    private boolean isEdit = false;
    private Barang barang;
    private int position;
    private BarangHelper barangHelper;

    public static final String EXTRA_BARANG = "extra_barang";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_crud);

        edtKode = findViewById(R.id.edt_kode);
        edtName = findViewById(R.id.edt_name_barang);
        edtJumlah = findViewById(R.id.edt_stok);
        Button btnSubmit = findViewById(R.id.btn_submit);

        barangHelper = BarangHelper.getInstance(getApplicationContext());
        barangHelper.open();

        barang = getIntent().getParcelableExtra(EXTRA_BARANG);
        if (barang != null){
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        } else {
            barang = new Barang();
        }

        String actionBarTitle;
        String btnTitle;

        if (isEdit) {
            actionBarTitle = "Ubah";
            btnTitle = "Update";

            if (barang != null) {
                edtKode.setText(barang.getKode_barang());
                edtName.setText(barang.getNama_barang());
                Log.d(TAG, "onCreate: why" + barang.getJumlah_barang());
                edtJumlah.setText(Integer.toString(barang.getJumlah_barang()));
            }
        } else {
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSubmit.setText(btnTitle);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit){
            String kode = edtKode.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String jumlah = edtJumlah.getText().toString().trim();

            if (TextUtils.isEmpty(name)){
                edtName.setError("Field can not be blank");
                return;
            }

            barang.setKode_barang(kode);
            barang.setNama_barang(name);
            Log.d(TAG, "onClick: jumlah" + jumlah);
            barang.setJumlah_barang(Integer.parseInt(jumlah));

            Intent intent = new Intent();
            intent.putExtra(EXTRA_BARANG, barang);
            intent.putExtra(EXTRA_POSITION, position);

            ContentValues values = new ContentValues();
            values.put(KODE, kode);
            values.put(NAMA_BARANG, name);
            values.put(JUMLAH, jumlah);

            if (isEdit) {
                long result = barangHelper.update(String.valueOf(barang.getId()), values);
                if (result > 0){
                    setResult(RESULT_UPDATE, intent);
                    finish();
                } else {
                    Toast.makeText(BarangCrudActivity.this, "Update gagal", Toast.LENGTH_SHORT).show();
                }
            } else {
                barang.setDate(getCurrentDate());
                values.put(DATE, getCurrentDate());
                long result = barangHelper.insert(values);

                if (result > 0){
                    barang.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    finish();
                } else {
                    Toast.makeText(BarangCrudActivity.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit){
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete){
            showAlertDialog(ALERT_DIALOG_DELETE);
        } else if (id == android.R.id.home){
            showAlertDialog(ALERT_DIALOG_CLOSE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose){
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan ?";
        } else {
            dialogMessage = "Apakah anda ingin menghapus item ini ?";
            dialogTitle = "Hapus Barang";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", (dialog, id) -> {
                    if (isDialogClose){
                        finish();
                    } else {
                        long result = barangHelper.deleteById(String.valueOf(barang.getId()));
                        if (result > 0){
                            Intent intent = new Intent();
                            intent.putExtra(EXTRA_POSITION, position);
                            setResult(RESULT_DELETE, intent);
                            finish();
                        } else {
                            Toast.makeText(BarangCrudActivity.this, "Gagagl menghapus data", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}