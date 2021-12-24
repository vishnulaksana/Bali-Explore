package com.wisnulaksana.baliexplore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewActivity extends AppCompatActivity {
    TextView txtNamaTempat;
    TextView txtHarga;
    TextView txtTipewisata;
    TextView txtFasilitas;
    String namaTempat;
    String harga;
    String tipewisata;
    String fasilitas;

    private DataHelper db;
    private List<Place> placeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        db = new DataHelper(this);
        placeList = db.selectPlaceData();


        txtNamaTempat = findViewById(R.id.isi_namalengkap);
        txtHarga = findViewById(R.id.isi_harga);
        txtTipewisata = findViewById(R.id.isi_tipewisata);
        txtFasilitas = findViewById(R.id.isi_fasilitas);

        namaTempat = placeList.get(placeList.size()-1).getNamaTempat();
        harga = placeList.get(placeList.size()-1).getHarga();
        tipewisata = placeList.get(placeList.size()-1).getTipewisata();
        fasilitas = placeList.get(placeList.size()-1).getFasilitas();

        txtNamaTempat.setText(namaTempat);
        txtHarga.setText(harga);
        txtTipewisata.setText(tipewisata);
        txtFasilitas.setText(fasilitas);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Menampilkan Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"Menjeda Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Toast.makeText(this," Memulai Activity Kembali", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Menghancurkan activity", Toast.LENGTH_SHORT).show();
    }
}
